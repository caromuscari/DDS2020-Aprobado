package ar.edu.utn.frba.dds.Entidad;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("EntidadBase")
public class EntidadBase extends Entidad {

    private String descripcion;

    @OneToOne
    @JoinColumn(name = "entJuridica_id")
    private EntidadJuridica entidadJuridica;

    public EntidadBase(String nombre, String descripcion, EntidadJuridica entidadJuridica) {
        super(nombre);
        this.descripcion = descripcion;
        this.entidadJuridica = entidadJuridica;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EntidadJuridica getEntidadJuridica() {
        return entidadJuridica;
    }

    public void setEntidadJuridica(EntidadJuridica entidadJuridica) {
        this.entidadJuridica = entidadJuridica;
    }
}
