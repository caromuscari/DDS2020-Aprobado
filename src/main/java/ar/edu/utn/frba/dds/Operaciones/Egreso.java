package ar.edu.utn.frba.dds.Operaciones;

import ar.edu.utn.frba.dds.Categorizacion.Categoria;
import ar.edu.utn.frba.dds.DateConverter;
import ar.edu.utn.frba.dds.Licitacion.ItemOperacionPresupuesto;
import ar.edu.utn.frba.dds.Licitacion.Presupuesto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Egreso {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "egreso_id")
    @JsonIgnore
    private List<DocumentoComercial> documentos;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "egreso_id")
    @JsonIgnore
    private List<ItemOperacionEgreso> items;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medioDePago_id")
    @JsonIgnore
    private MedioDePago medioDePago;
    private Double precioTotal;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "proveedor_id")
    @JsonIgnore
    private Proveedor proveedor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "presupuesto_id")
    @JsonIgnore
    private Presupuesto presupuesto;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "egreso_categorias", joinColumns = @JoinColumn(name = "egreso_id"), inverseJoinColumns = @JoinColumn(name = "categorias_id"))
    @JsonIgnore
    private List<Categoria> categorias;

    @Column
    @Convert(converter = DateConverter.class)
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDate fecha;

    private Boolean vinculado;
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Egreso(){
        // Para Hibernate
    }

    public Egreso(List<ItemOperacionEgreso> items, Proveedor proveedor, String nombre) {
        this.items = items;
        this.proveedor = proveedor;
        this.categorias = new ArrayList<>();
        this.nombre = nombre;
        calcularPrecio();
        this.vinculado = false;
        this.fecha = LocalDate.now();
    }

    public List<DocumentoComercial> getDocumentos() { return documentos; }
    public void setDocumentos(List<DocumentoComercial> documentos) { this.documentos = documentos; }

    public List<ItemOperacionEgreso> getItems() { return items; }
    public void setItems(List<ItemOperacionEgreso> items) { this.items = items; }

    public MedioDePago getMedioDePago() { return medioDePago; }
    public void setMedioDePago(MedioDePago medioDePago) { this.medioDePago = medioDePago; }

    public Double getPrecioTotal() { calcularPrecio(); return precioTotal; }
    public void setPrecioTotal(Double precioTotal) { this.precioTotal = precioTotal; }

    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }

    public Presupuesto getPresupuesto() { return presupuesto; }
    public void setPresupuesto(Presupuesto presupuesto) { this.presupuesto = presupuesto; }

    public List<Categoria> getCategorias() { return categorias; }
    public void setCategorias(List<Categoria> categorias) { this.categorias = categorias; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Boolean getVinculado() { return vinculado; }
    public void setVinculado(Boolean vinculado) { this.vinculado = vinculado; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    //Metodos
    public void registrarMedioDePago(MedioDePago medioDePago) {
        this.medioDePago = medioDePago;
    }

    public void adjuntarDocumento(DocumentoComercial documento){
        this.documentos.add(documento);
    }

    public void calcularPrecio(){
        this.precioTotal = this.items.stream().mapToDouble(i -> i.precioTotal()).sum();
    }

    public boolean verificarCantidadItems(){
        return items.stream().mapToInt(i -> i.getCantidad()).sum() == presupuesto.
                getItems().stream().mapToInt(i -> i.getCantidad()).sum();
    }

    private boolean compararItems(ItemOperacionEgreso egr, ItemOperacionPresupuesto pres){
        return egr.getItemEgreso().getCategoria() == pres.getItemPresupuesto().getCategoria()
                && egr.getItemEgreso().getTipo() == pres.getItemPresupuesto().getTipo();
    }

    public boolean verificarEgreso(){
        boolean validez = false;
        Iterator<ItemOperacionEgreso> egreso = items.iterator();
        while(egreso.hasNext()) {
            ItemOperacionEgreso e = egreso.next();
            if (!presupuesto.getItems().stream().anyMatch(i -> compararItems(e,i))) {
                return validez;
            }
        }
        return !validez;
    }

    public void asociarCategoria(Categoria categoria){
        this.categorias.add(categoria);
    }

    public boolean contieneCategoria(Categoria categoria){
        List<Boolean> resultados = this.categorias.stream().map(c -> {if (c.equals(categoria)) return true;
                                        return categoria.contieneCategoriaHija(c);}).collect(Collectors.toList());

        return resultados.stream().anyMatch(c -> c.equals(true));
    }

    public void vincular(){
        this.vinculado = true;
    }

}
