package ar.edu.utn.frba.dds.Entidad;

import ar.edu.utn.frba.dds.Categorizacion.Categoria;
import ar.edu.utn.frba.dds.Categorizacion.CriterioCategoria;
import ar.edu.utn.frba.dds.Operaciones.Egreso;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Organizacion {

    private List<Entidad> entidades;
    private List<CriterioCategoria> criterios;

    public List<Entidad> getEntidades() {
        return entidades;
    }
    public void setEntidades(List<Entidad> entidades) {
        this.entidades = entidades;
    }

    public List<CriterioCategoria> getCriterios() { return criterios; }
    public void setCriterios(List<CriterioCategoria> criterios) { this.criterios = criterios; }

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

    public void crearCriterio(String nombre, List<Categoria> categorias){
        CriterioCategoria criterio = new CriterioCategoria(nombre,categorias);
        this.criterios.add(criterio);
    }

    public void crearCategoria(String categoria, CriterioCategoria criterio){
        criterio.getCategorias().add(new Categoria(categoria));
    }
}
