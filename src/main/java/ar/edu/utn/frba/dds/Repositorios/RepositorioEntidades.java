package ar.edu.utn.frba.dds.Repositorios;

import ar.edu.utn.frba.dds.Entidad.Entidad;

import java.util.ArrayList;
import java.util.List;

public class RepositorioEntidades {

    public static RepositorioEntidades instance = null;
    private List<Entidad>  entidades = new ArrayList<>();

    public static RepositorioEntidades getInstance() {
        if (instance == null) {
            instance = new RepositorioEntidades();
        }
        return instance;
    }

    public void crearEntidad(Entidad entidad) {
        entidades.add(entidad);
    }

    public List<Entidad> obtenerEntidades() {
        return entidades;
    }

    public void eliminarEntidad(Entidad entidad){
        entidades.remove(entidad);
    }
}
