package ar.edu.utn.frba.dds.Licitacion;

import ar.edu.utn.frba.dds.ResultadoLicitacion.ErrorCantidadPresupuestos;
import ar.edu.utn.frba.dds.ResultadoLicitacion.ErrorMenorValor;
import ar.edu.utn.frba.dds.ResultadoLicitacion.EstadoValidacion;
import ar.edu.utn.frba.dds.ResultadoLicitacion.ResultadoValidacion;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CantidadPrespuestos")
public class CantidadPresupuestos extends CriterioSeleccion {
    @Override
    public ResultadoValidacion validar(Licitacion licitacion) {
        ResultadoValidacion resultado;
        if (licitacion.getPresupuestos().size() < licitacion.getPresupuestosRequeridos()){
            resultado = new ResultadoValidacion(EstadoValidacion.ERROR,new ErrorCantidadPresupuestos(licitacion.getPresupuestosRequeridos(), licitacion.getPresupuestos().size()),licitacion);
        }
        else resultado = new ResultadoValidacion(EstadoValidacion.OK,licitacion);
        return resultado;
    }
}
