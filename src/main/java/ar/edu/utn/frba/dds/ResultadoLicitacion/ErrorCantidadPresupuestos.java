package ar.edu.utn.frba.dds.ResultadoLicitacion;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "ErrorCantidadPrespuestos")
@DiscriminatorValue("ErrorCantidadPrespuestos")
public class ErrorCantidadPresupuestos extends ErrorValidacion {

    @Column
    private Integer cantidadSolicitada;

    @Column
    private Integer cantidadObtenida;

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
