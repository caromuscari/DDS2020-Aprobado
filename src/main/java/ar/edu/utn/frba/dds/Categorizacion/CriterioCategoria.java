package ar.edu.utn.frba.dds.Categorizacion;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<Categoria> getTotalCategorias() {
        List<Categoria> categorias = this.categorias.stream().map(c -> c.getCategoriasHijas()).flatMap(List::stream).collect(Collectors.toList());
        categorias.addAll(this.categorias);
        return categorias;
    }
}
