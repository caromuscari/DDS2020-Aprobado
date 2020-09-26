package ar.edu.utn.frba.dds.Entidad;

import ar.edu.utn.frba.dds.Categorizacion.Categoria;
import ar.edu.utn.frba.dds.Categorizacion.CriterioCategoria;
import ar.edu.utn.frba.dds.Operaciones.Egreso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Organizacion {

    private String nombre;
    private String descripcion;
    private List<Entidad> entidades;
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
}
