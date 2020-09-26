package ar.edu.utn.frba.dds.DTO;

import ar.edu.utn.frba.dds.Licitacion.Presupuesto;
import ar.edu.utn.frba.dds.Operaciones.Proveedor;

public class PresupuestoDTO {
    private Double precioTotal;
    private Proveedor proveedor;
    private String nombre;

    public PresupuestoDTO(Presupuesto presupuesto) {
        this.precioTotal = presupuesto.getPrecioTotal();
        this.proveedor = presupuesto.getProveedor();
        this.nombre = presupuesto.getNombre();
    }

    public Double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
