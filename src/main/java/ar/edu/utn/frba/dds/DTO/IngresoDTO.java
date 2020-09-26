package ar.edu.utn.frba.dds.DTO;

import ar.edu.utn.frba.dds.Entidad.Entidad;
import ar.edu.utn.frba.dds.Operaciones.Ingreso;

public class IngresoDTO {
    private Ingreso ingreso;
    private EntidadDTO entidad;

    public IngresoDTO(Ingreso ingreso, Entidad entidad) {
        this.ingreso = ingreso;
        this.entidad = new EntidadDTO(entidad);
    }

    public Ingreso getIngreso() {
        return ingreso;
    }

    public void setIngreso(Ingreso ingreso) {
        this.ingreso = ingreso;
    }

    public EntidadDTO getEntidad() {
        return entidad;
    }

    public void setEntidad(EntidadDTO entidad) {
        this.entidad = entidad;
    }
}
