package ar.edu.utn.frba.dds.Entidad;

import ar.edu.utn.frba.dds.Egreso.Egreso;

import java.util.List;

public abstract class Entidad {
    private String nombre;
    private List<Egreso> egresos;

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
}