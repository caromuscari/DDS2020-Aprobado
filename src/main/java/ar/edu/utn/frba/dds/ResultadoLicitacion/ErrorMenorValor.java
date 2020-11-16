package ar.edu.utn.frba.dds.ResultadoLicitacion;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ErrorMenorValor")
public class ErrorMenorValor extends ErrorValidacion {
    private Double precioTotalEgreso;
    private Double precioTotalMenorPresupuesto;

    public ErrorMenorValor(Double precioTotalEgreso, Double precioTotalMenorPresupuesto) {
        this.precioTotalEgreso = precioTotalEgreso;
        this.precioTotalMenorPresupuesto = precioTotalMenorPresupuesto;
    }

    public ErrorMenorValor(){
        // Para Hibernate
    }

    @Override
    public String obtenerMensaje() {
        return String.format("No se eligió el presupuesto de menor valor. El egreso registrado tiene un precio total de {0} y el presupuesto de menor valor tiene un precio total de {1}",precioTotalEgreso,precioTotalMenorPresupuesto);
    }
}
