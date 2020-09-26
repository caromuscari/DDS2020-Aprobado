package ar.edu.utn.frba.dds.Repositorios;

import ar.edu.utn.frba.dds.Operaciones.Egreso;
import ar.edu.utn.frba.dds.Operaciones.ItemOperacionEgreso;
import ar.edu.utn.frba.dds.Operaciones.Proveedor;

import java.util.ArrayList;
import java.util.List;

public class RepositorioEgresos {

    public static RepositorioEgresos instance = null;
    private List<Egreso> egresos = new ArrayList<>();

    private RepositorioEgresos() {}

    public static RepositorioEgresos getInstance() {
        if (instance == null) {
            instance = new RepositorioEgresos();
        }
        return instance;
    }

    public Egreso crearEgreso(List<ItemOperacionEgreso> items, Proveedor proveedor, String nombre) {
        Egreso egreso = new Egreso(items, proveedor, nombre);
        egresos.add(egreso);
        return egreso;
    }

    public List<Egreso> obtenerEgresos() {
        return egresos;
    }

    public Egreso obtenerEgresoPorNombre(String nombre) {
        for (Egreso egreso : egresos) {
            if (nombre.equals(egreso.getNombre())) {
                return egreso;
            }
        }
        return null;
    }

    public void eliminarEgreso(Egreso egreso){
        egresos.remove(egreso);
    }

    public Egreso obtenerEgresoPorId(String id) {
        for (Egreso egreso : egresos) {
            if (Integer.parseInt(id) == egreso.getId()) {
                return egreso;
            }
        }
        return null;
    }
}
