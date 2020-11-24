package ar.edu.utn.frba.dds.Entidad;

import ar.edu.utn.frba.dds.Categorizacion.Categoria;
import ar.edu.utn.frba.dds.Licitacion.Licitacion;
import ar.edu.utn.frba.dds.Operaciones.Egreso;
import ar.edu.utn.frba.dds.Operaciones.Ingreso;
import ar.edu.utn.frba.dds.Operaciones.ItemOperacionEgreso;
import ar.edu.utn.frba.dds.Operaciones.Proveedor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipoEntidad", discriminatorType = DiscriminatorType.STRING)
public abstract class Entidad {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "entidad_id")
    private List<Egreso> egresos;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "entidad_id")
    private List<Licitacion> licitaciones;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "entidad_id")
    private List<Ingreso> ingresos;

    public Entidad() {
    }

    public Entidad(String nombre) {
        this.nombre = nombre;
        this.egresos = new ArrayList<>();
        this.licitaciones = new ArrayList<>();
        this.ingresos = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Egreso> getEgresos() { return egresos; }
    public void setEgresos(List<Egreso> egresos) {
        this.egresos = egresos;
    }

    public List<Ingreso> getIngresos() { return ingresos; }
    public void setIngresos(List<Ingreso> ingresos) { this.ingresos = ingresos; }

    public List<Licitacion> getLicitaciones() { return licitaciones; }
    public void setLicitaciones(List<Licitacion> licitaciones) { this.licitaciones = licitaciones; }

    // Metodos
    public Double obtenerTotalEgresos(){
        return this.egresos.stream().
            mapToDouble(egreso -> egreso.getPrecioTotal()).sum();
    }

    public Egreso generarEgreso(List<ItemOperacionEgreso> items, Proveedor proveedor, String nombre){
        Egreso egreso = new Egreso(items,proveedor,nombre);
        this.egresos.add(egreso);

        return egreso;
    }

    public List<Egreso> filtrarEgresos(Categoria categoria){
        return this.egresos.stream().filter(e -> e.contieneCategoria(categoria)).collect(Collectors.toList());
    }

    public void addLicitacion(Licitacion licitacion){ this.licitaciones.add(licitacion);}
}
