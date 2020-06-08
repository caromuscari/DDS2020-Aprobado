package ar.edu.utn.frba.dds.ResultadoLicitacion;

public class ErrorCantidadPresupuestos implements ErrorValidacion {
    private Integer cantidadSolicitada;
    private Integer cantidadObtenida;

    public ErrorCantidadPresupuestos(Integer cantidadSolicitada, Integer cantidadObtenida) {
        this.cantidadSolicitada = cantidadSolicitada;
        this.cantidadObtenida = cantidadObtenida;
    }

    @Override
    public String obtenerMensaje() {
        return String.format("La cantidad de presupuestos solicitada fue {0} pero se obtuvieron {1} presupuestos", cantidadSolicitada, cantidadObtenida);
    }
}
