package ar.edu.utn.frba.dds.ResultadoLicitacion;

import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "ErrorCantidadPrespuestos")
@DiscriminatorValue("ErrorCantidadPrespuestos")
public class ErrorCantidadPresupuestos extends ErrorValidacion {

    @Expose(serialize = true)
    @Column
    private Integer cantidadSolicitada;

    @Expose(serialize = true)
    @Column
    private Integer cantidadObtenida;

    @Expose(serialize = true)
    @Column
    private String mensaje;

    public ErrorCantidadPresupuestos(Integer cantidadSolicitada, Integer cantidadObtenida) {
        this.cantidadSolicitada = cantidadSolicitada;
        this.cantidadObtenida = cantidadObtenida;
        this.mensaje = "La cantidad de presupuestos solicitada fue "+cantidadSolicitada+" pero se obtuvieron "+cantidadObtenida+" presupuestos";
    }

    public ErrorCantidadPresupuestos(){
        // Para Hibernate
    }

    @Override
    public String obtenerMensaje() {
        return mensaje;
    }
}
