package ar.edu.utn.frba.dds.Entidad;

import ar.edu.utn.frba.dds.Categorizacion.Categoria;
import ar.edu.utn.frba.dds.Categorizacion.CriterioCategoria;
import ar.edu.utn.frba.dds.Operaciones.Egreso;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Organizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String nombre;
    private String descripcion;

    @OneToMany
    @JoinColumn(name = "organizacion_id")
    private List<Entidad> entidades;

    @OneToMany
    @JoinColumn(name = "organizacion_id")
    private List<CriterioCategoria> criterios;

    public Organizacion(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.entidades = new ArrayList<>();
        this.criterios = new ArrayList<>();
    }

    public List<Entidad> getEntidades() {
        return entidades;
    }
    public void setEntidades(List<Entidad> entidades) {
        this.entidades = entidades;
    }

    public List<CriterioCategoria> getCriterios() { return criterios; }
    public void setCriterios(List<CriterioCategoria> criterios) { this.criterios = criterios; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    //Metodos
    public Double obtenerTotalEgresos(){
        return entidades.stream().
                mapToDouble(entidad -> entidad.obtenerTotalEgresos()).
                sum();
    }

    public List<Egreso> obtenerListaEgresos(){
        return this.entidades.stream().map(e -> e.getEgresos()).
                flatMap(Collection::stream).collect(Collectors.toList());
    }

    public void asociarCriterio(CriterioCategoria criterioCategoria){
        this.criterios.add(criterioCategoria);
    }

    public Categoria obtenerCategoria(String nombre){
        List<Categoria> totalCategorias = this.criterios.stream().map(c -> c.getTotalCategorias()).flatMap(List::stream).collect(Collectors.toList());

        return totalCategorias.stream().filter(c -> c.getNombre().matches(nombre)).findFirst().get();
    }
}
