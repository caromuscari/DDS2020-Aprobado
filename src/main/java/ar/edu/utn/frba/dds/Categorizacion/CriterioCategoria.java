package ar.edu.utn.frba.dds.Categorizacion;

import java.util.List;

public class CriterioCategoria {
    private String nombre;
    private List<Categoria> categorias;

    public CriterioCategoria(String nombre, List<Categoria> categorias) {
        this.nombre = nombre;
        this.categorias = categorias;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<Categoria> getCategorias() { return categorias; }
    public void setCategorias(List<Categoria> categorias) { this.categorias = categorias; }
}
