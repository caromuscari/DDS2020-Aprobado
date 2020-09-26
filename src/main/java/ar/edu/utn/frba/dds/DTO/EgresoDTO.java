package ar.edu.utn.frba.dds.DTO;

import ar.edu.utn.frba.dds.Entidad.Entidad;
import ar.edu.utn.frba.dds.Operaciones.Egreso;

public class EgresoDTO {
    private Egreso egreso;
    private EntidadDTO  entidad;

    public EgresoDTO(Egreso egreso, Entidad entidad) {
        this.egreso = egreso;
        this.entidad = new EntidadDTO(entidad);
    }

    public Egreso getEgreso() {
        return egreso;
    }

    public void setEgreso(Egreso egreso) {
        this.egreso = egreso;
    }

    public EntidadDTO getEntidad() {
        return entidad;
    }

    public void setEntidad(EntidadDTO entidad) {
        this.entidad = entidad;
    }
}
