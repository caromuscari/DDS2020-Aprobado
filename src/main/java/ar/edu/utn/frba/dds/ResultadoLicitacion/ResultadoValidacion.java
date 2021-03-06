package ar.edu.utn.frba.dds.ResultadoLicitacion;

import ar.edu.utn.frba.dds.Licitacion.Licitacion;
import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity
public class ResultadoValidacion {
    @Id
    @GeneratedValue
    @Expose(serialize = false)
    private int id;

    @Enumerated(EnumType.STRING)
    @Expose(serialize = true)
    private EstadoValidacion estado;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "error_id")
    @Expose(serialize = true)
    private ErrorValidacion error;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "licitacion_id")
    @Expose(serialize = false)
    private Licitacion licitacion;

    public ResultadoValidacion() {
        // Para Hibernate
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
        if (EstadoValidacion.ERROR.equals(estado)){
            return error.obtenerMensaje();
        }
        else return "La validacion se realizo correctamente";
    }
}
