package ar.edu.utn.frba.dds.DTO;

public class MensajeDTO {
    private String mensaje;
    private boolean leido;

    public MensajeDTO(String mensaje, boolean leido){
        this.mensaje = mensaje;
        this.leido = leido;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }
}
