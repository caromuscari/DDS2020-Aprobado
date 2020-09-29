package ar.edu.utn.frba.dds.Repositorios;

import ar.edu.utn.frba.dds.Categorizacion.Categoria;
import ar.edu.utn.frba.dds.Entidad.Entidad;

import java.util.ArrayList;
import java.util.List;

public class RepositorioCategorias {
    public static RepositorioCategorias instance = null;
    private List<Categoria> categorias = new ArrayList<>();

    public static RepositorioCategorias getInstance() {
        if (instance == null) {
            instance = new RepositorioCategorias();
        }
        return instance;

    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }
}
