package ar.edu.utn.frba.dds.DTO;

import ar.edu.utn.frba.dds.Entidad.Entidad;

public class EntidadDTO {
    private String nombre;

    public EntidadDTO(Entidad entidad) {
        this.nombre = entidad.getNombre();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
