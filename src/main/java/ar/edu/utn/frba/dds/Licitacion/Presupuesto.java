package ar.edu.utn.frba.dds.Licitacion;

import ar.edu.utn.frba.dds.Categorizacion.Categoria;
import ar.edu.utn.frba.dds.Operaciones.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Entity
public class Presupuesto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "presupuesto_id")
    private List<DocumentoComercial> documentos;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "presupuesto_id")
    private List<ItemOperacionPresupuesto> items;
    private Double precioTotal;

    @OneToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "presupuesto_categoria",
            joinColumns = { @JoinColumn(name = "presupuesto_id") },
            inverseJoinColumns = { @JoinColumn(name = "categoria_id") }
    )
    private List<Categoria> categorias;

    private String nombre;

    public Presupuesto(){
        // Para Hibernate
    }

    public Presupuesto(List<DocumentoComercial> documentos, List<ItemOperacionPresupuesto> items, Double precioTotal, Proveedor proveedor,String nombre) {
        this.documentos = documentos;
        this.items = items;
        this.precioTotal = precioTotal;
        this.proveedor = proveedor;
        this.categorias = new ArrayList<>();
        this.nombre = nombre;
    }

    public Presupuesto(List<ItemOperacionPresupuesto> items, Proveedor proveedor,String nombre) {
        this.items = items;
        this.proveedor = proveedor;
        this.categorias = new ArrayList<>();
        this.nombre = nombre;
    }

    public List<DocumentoComercial> getDocumentos() { return documentos; }
    public void setDocumentos(List<DocumentoComercial> documentos) { this.documentos = documentos; }

    public List<ItemOperacionPresupuesto> getItems() { return items; }
    public void setItems(List<ItemOperacionPresupuesto> items) { this.items = items; }

    public Double getPrecioTotal() { calcularPrecio(); return precioTotal; }
    public void setPrecioTotal(Double precioTotal) { this.precioTotal = precioTotal; }

    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }

    public List<Categoria> getCategorias() { return categorias; }
    public void setCategorias(List<Categoria> categorias) { this.categorias = categorias; }

    public int getId() { return id; }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //Metodos
    public void asociarCategoria(Categoria categoria){
        this.categorias.add(categoria);
    }

    public boolean contieneCategoria(Categoria categoria){
        List<Boolean> resultados = this.categorias.stream().map(c -> {if (c.equals(categoria)) return true;
            return c.contieneCategoriaHija(categoria);}).collect(Collectors.toList());

        return resultados.stream().anyMatch(c -> c.equals(true));
    }

    public boolean contieneCategoria(String categoria){
        List<Boolean> resultados = this.categorias.stream().map(c -> {if (c.getNombre().matches(categoria)) return true;
           return c.contieneCategoriaHija(categoria);}).collect(Collectors.toList());

        return resultados.stream().anyMatch(c -> c.equals(true));
    }

    public void calcularPrecio(){
        this.precioTotal = this.items.stream().mapToDouble(i -> i.precioTotal()).sum();
    }

    public boolean itemsIguales(List<ItemOperacionEgreso> itemsEgreso){
        List<CategoriaItem> categorias = this.items.stream().map(ItemOperacionPresupuesto::getItemPresupuesto).map(ItemPresupuesto::getCategoria).collect(Collectors.toList());
        categorias.removeAll(itemsEgreso.stream().map(ItemOperacionEgreso::getItemEgreso).map(ItemEgreso::getCategoria).collect(Collectors.toList()));
        if (categorias.size() == 0) return true;
        else return false;
    }

}
