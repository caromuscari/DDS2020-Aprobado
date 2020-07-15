package ar.edu.utn.frba.dds.Operaciones;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Ingreso {

    public Ingreso(String descripcion, Double montoTotal, LocalDate fecha) {
        this.descripcion = descripcion;
        this.montoTotal = montoTotal;
        this.fecha = fecha;
        this.egresos = new ArrayList<>();
    }

    private String descripcion;
    private Double montoTotal;
    private LocalDate fecha;
    private List<Egreso> egresos;

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(Double montoTotal) { this.montoTotal = montoTotal; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public List<Egreso> getEgresos() { return egresos; }
    public void setEgresos(List<Egreso> egresos) { this.egresos = egresos; }

    //Metodos
    public void asociarEgreso(Egreso egreso){
        this.egresos.add(egreso);
    }

    public Double montoTotalEgresos()
    {
        return egresos.stream().mapToDouble(Egreso::getPrecioTotal).sum();
    }
}
