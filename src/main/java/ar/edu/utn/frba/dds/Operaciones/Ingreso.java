package ar.edu.utn.frba.dds.Operaciones;

import ar.edu.utn.frba.dds.Categorizacion.Categoria;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ingreso {

    public Ingreso(String descripcion, Double montoTotal, LocalDate fecha) {
        this.id = new Random().nextInt(1000);
        this.descripcion = descripcion;
        this.montoTotal = montoTotal;
        this.fecha = fecha;
        this.egresos = new ArrayList<>();
        this.categorias = new ArrayList<>();
    }

    private int id;
    private String descripcion;
    private Double montoTotal;
    private LocalDate fecha;
    private List<Egreso> egresos;
    private List<Categoria> categorias;

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(Double montoTotal) { this.montoTotal = montoTotal; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public List<Egreso> getEgresos() { return egresos; }
    public void setEgresos(List<Egreso> egresos) { this.egresos = egresos; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public List<Categoria> getCategorias() { return categorias; }
    public void setCategorias(List<Categoria> categorias) { this.categorias = categorias; }

    //Metodos
    public void asociarEgreso(Egreso egreso){
        this.egresos.add(egreso);
        egreso.vincular();
    }

    public Double montoTotalEgresos()
    {
        return egresos.stream().mapToDouble(Egreso::getPrecioTotal).sum();
    }
}
