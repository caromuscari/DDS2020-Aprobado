package ar.edu.utn.frba.dds.ResultadoLicitacion;

import ar.edu.utn.frba.dds.Licitacion.Licitacion;

public class ResultadoValidacion {
    private EstadoValidacion estado;
    private ErrorValidacion error;
    private Licitacion licitacion;

    public ResultadoValidacion() {
    }

    public ResultadoValidacion(EstadoValidacion estado, Licitacion licitacion) {
        this.estado = estado;
        this.licitacion = licitacion;
    }

    public ResultadoValidacion(EstadoValidacion estado, ErrorValidacion error, Licitacion licitacion) {
        this.estado = estado;
        this.error = error;
        this.licitacion = licitacion;
    }

    public EstadoValidacion getEstado() { return estado; }
    public void setEstado(EstadoValidacion estado) { this.estado = estado; }

    public ErrorValidacion getError() { return error; }
    public void setError(ErrorValidacion error) { this.error = error; }

    public Licitacion getLicitacion() { return licitacion; }
    public void setLicitacion(Licitacion licitacion) { this.licitacion = licitacion; }

    public String obtenerMensaje() {
        if (estado.equals(EstadoValidacion.ERROR)){
            return error.obtenerMensaje();
        }
        else return "La validacion se realizo correctamente";
    }
}
