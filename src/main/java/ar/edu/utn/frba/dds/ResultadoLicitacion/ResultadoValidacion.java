package ar.edu.utn.frba.dds.ResultadoLicitacion;

public class ResultadoValidacion {
    private EstadoValidacion estado;
    private ErrorValidacion error;
    private Integer idLicitacion;

    public ResultadoValidacion() {
    }

    public ResultadoValidacion(EstadoValidacion estado, Integer idLicitacion) {
        this.estado = estado;
        this.idLicitacion = idLicitacion;
    }

    public ResultadoValidacion(EstadoValidacion estado, ErrorValidacion error, Integer idLicitacion) {
        this.estado = estado;
        this.error = error;
        this.idLicitacion = idLicitacion;
    }

    public EstadoValidacion getEstado() { return estado; }

    public void setEstado(EstadoValidacion estado) { this.estado = estado; }

    public ErrorValidacion getError() { return error; }

    public void setError(ErrorValidacion error) { this.error = error; }

    public Integer getIdLicitacion() { return idLicitacion; }

    public void setIdLicitacion(Integer idLicitacion) { this.idLicitacion = idLicitacion; }

    public String obtenerMensaje() {
        if (estado.equals(EstadoValidacion.ERROR)){
            return String.format("Error en la validacion {0}. {1}",idLicitacion, error.obtenerMensaje());
        }
        else return String.format("No se registraron errores en la validación {0}",idLicitacion);
    }
}
