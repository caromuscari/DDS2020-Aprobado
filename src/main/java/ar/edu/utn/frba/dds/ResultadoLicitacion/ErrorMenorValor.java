package ar.edu.utn.frba.dds.ResultadoLicitacion;

import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "ErrorMenorValor")
@DiscriminatorValue("ErrorMenorValor")
public class ErrorMenorValor extends ErrorValidacion {
    @Expose(serialize = true)
    @Column
    private Double precioTotalEgreso;

    @Expose(serialize = true)
    @Column
    private Double precioTotalMenorPresupuesto;

    @Expose(serialize = true)
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
