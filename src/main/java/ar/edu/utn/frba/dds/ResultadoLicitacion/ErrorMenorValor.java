package ar.edu.utn.frba.dds.ResultadoLicitacion;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "ErrorMenorValor")
@DiscriminatorValue("ErrorMenorValor")
public class ErrorMenorValor extends ErrorValidacion {
    @Column
    private Double precioTotalEgreso;
    @Column
    private Double precioTotalMenorPresupuesto;
    @Column
    private String mensaje;

    public ErrorMenorValor(Double precioTotalEgreso, Double precioTotalMenorPresupuesto) {
        this.precioTotalEgreso = precioTotalEgreso;
        this.precioTotalMenorPresupuesto = precioTotalMenorPresupuesto;
        this.mensaje = "No se eligió el presupuesto de menor valor. El egreso registrado tiene un precio total de "+precioTotalEgreso+" y el presupuesto de menor valor tiene un precio total de " + precioTotalMenorPresupuesto;
    }

    public ErrorMenorValor(){
        // Para Hibernate
    }

    @Override
    public String obtenerMensaje() {
        return mensaje;
    }
}
