package ar.edu.utn.frba.dds.DTO;

import ar.edu.utn.frba.dds.Entidad.Entidad;
import ar.edu.utn.frba.dds.Licitacion.Licitacion;
import ar.edu.utn.frba.dds.Licitacion.Presupuesto;

import java.util.ArrayList;
import java.util.List;

public class LicitacionDTO {
    private List<PresupuestoDTO> presupuestos;
    private String nombre;
    private Entidad entidad;

    public LicitacionDTO(Licitacion licitacion, Entidad entidad) {
        this.presupuestos = new ArrayList<>();
        licitacion.getPresupuestos().forEach(presupuesto -> this.presupuestos.add(new PresupuestoDTO(presupuesto)));
        this.nombre = licitacion.getNombre();
        this.entidad = entidad;
    }

    public List<PresupuestoDTO> getPresupuestos() {
        return presupuestos;
    }

    public void setPresupuestos(List<PresupuestoDTO> presupuestos) {
        this.presupuestos = presupuestos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }
}
