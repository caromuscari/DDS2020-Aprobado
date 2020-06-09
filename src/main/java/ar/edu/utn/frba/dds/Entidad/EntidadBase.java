package ar.edu.utn.frba.dds.Entidad;

public class EntidadBase extends Entidad {

    private String descripcion;
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
