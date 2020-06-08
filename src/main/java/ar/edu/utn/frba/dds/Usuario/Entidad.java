package ar.edu.utn.frba.dds.Usuario;

import ar.edu.utn.frba.dds.Egreso.Egreso;

import java.util.List;

public abstract class Entidad {
    private String nombre;
    private List<Egreso> egresos;

    public Entidad(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<Egreso> getEgresos() { return egresos; }

    public void setEgresos(List<Egreso> egresos) { this.egresos = egresos; }

    public void agregarEgreso(Egreso egreso){
        this.egresos.add(egreso);
    }
}
