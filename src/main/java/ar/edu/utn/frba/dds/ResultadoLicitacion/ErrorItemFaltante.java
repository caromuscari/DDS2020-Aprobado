package ar.edu.utn.frba.dds.ResultadoLicitacion;

public class ErrorItemFaltante implements ErrorValidacion {
    private String codigoItemFaltante;


    @Override
    public String obtenerMensaje() {
        return String.format("Al egreso registrado le falta el item de código {0}", codigoItemFaltante);
    }
}
