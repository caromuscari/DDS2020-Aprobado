package ar.edu.utn.frba.dds.Repositorios;

import ar.edu.utn.frba.dds.Controladores.Egresos;
import ar.edu.utn.frba.dds.Entidad.Empresa;
import ar.edu.utn.frba.dds.Entidad.Entidad;
import ar.edu.utn.frba.dds.Entidad.TipoActividad;
import ar.edu.utn.frba.dds.Licitacion.Presupuesto;
import ar.edu.utn.frba.dds.Operaciones.Egreso;
import ar.edu.utn.frba.dds.Operaciones.Ingreso;

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

    public void borrarEgreso(String id) {
        entidades.forEach(entidad -> entidad.getEgresos().removeIf(egreso -> Integer.parseInt(id) == egreso.getId()));
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

    public Entidad obtenerEntidadPorNombre(String nombreEntidad){
        return entidades.stream().filter(e -> e.getNombre().equals(nombreEntidad)).findFirst().get();
    }

    public Ingreso obtenerIngresoPorId(String id) {
        for (Ingreso ingreso : entidades.stream().map(e -> e.getIngresos()).flatMap(List::stream).collect(Collectors.toList())) {
            if (Integer.parseInt(id) == ingreso.getId()) {
                return ingreso;
            }
        }
        return null;
    }

    public Presupuesto obtenerPresupuestoPorId(String id) {
        for (Presupuesto presupuesto : entidades.stream().map(e -> e.getLicitaciones()).flatMap(List::stream).map(l -> l.getPresupuestos()).flatMap(List::stream).collect(Collectors.toList())) {
            if (Integer.parseInt(id) == presupuesto.getId()) {
                return presupuesto;
            }
        }
        return null;
    }
}
