package ar.edu.utn.frba.dds.Entidad;

import ar.edu.utn.frba.dds.Egreso.Egreso;
import ar.edu.utn.frba.dds.Egreso.ItemOperacionEgreso;
import ar.edu.utn.frba.dds.Egreso.Proveedor;

import java.util.ArrayList;
import java.util.List;

public abstract class Entidad {
    private String nombre;
    private List<Egreso> egresos;

    public Entidad() {
    }

    public Entidad(String nombre) {
        this.nombre = nombre;
        this.egresos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Egreso> getEgresos() {
        return egresos;
    }

    public void setEgresos(List<Egreso> egresos) {
        this.egresos = egresos;
    }

    public Double obtenerTotalEgresos(){
        return this.egresos.stream().
            mapToDouble(egreso -> egreso.getPrecioTotal()).sum();
    }

    public void generarEgreso(List<ItemOperacionEgreso> items, Proveedor proveedor){
        Egreso egreso = new Egreso(items,proveedor);
        this.egresos.add(egreso);
    }
}
