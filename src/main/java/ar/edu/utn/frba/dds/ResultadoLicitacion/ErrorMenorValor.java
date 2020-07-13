package ar.edu.utn.frba.dds.ResultadoLicitacion;

public class ErrorMenorValor implements ErrorValidacion {
    private Double precioTotalEgreso;
    private Double precioTotalMenorPresupuesto;

    public ErrorMenorValor(Double precioTotalEgreso, Double precioTotalMenorPresupuesto) {
        this.precioTotalEgreso = precioTotalEgreso;
        this.precioTotalMenorPresupuesto = precioTotalMenorPresupuesto;
    }

    @Override
    public String obtenerMensaje() {
        return String.format("No se eligió el presupuesto de menor valor. El egreso registrado tiene un precio total de {0} y el presupuesto de menor valor tiene un precio total de {1}",precioTotalEgreso,precioTotalMenorPresupuesto);
    }
}
