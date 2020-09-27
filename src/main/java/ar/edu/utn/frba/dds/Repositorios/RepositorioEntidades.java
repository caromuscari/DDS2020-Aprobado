package ar.edu.utn.frba.dds.Repositorios;

import ar.edu.utn.frba.dds.Entidad.Entidad;
import ar.edu.utn.frba.dds.Operaciones.Egreso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public Egreso obtenerEgresoPorId(String id) {
        for (Egreso egreso : entidades.stream().map(e -> e.getEgresos()).flatMap(List::stream).collect(Collectors.toList())) {
            if (Integer.parseInt(id) == egreso.getId()) {
                return egreso;
            }
        }
        return null;
    }
    public Egreso obtenerEgresoPorNombre(String nombre) {
        for (Egreso egreso : entidades.stream().map(e -> e.getEgresos()).flatMap(List::stream).collect(Collectors.toList())) {
            if (nombre.equals(egreso.getNombre())) {
                return egreso;
            }
        }
        return null;
    }

    public Entidad obtenerEntidadDeEgreso(Egreso egreso){
        return entidades.stream().filter(e -> e.getEgresos().contains(egreso)).findFirst().get();
    }
}