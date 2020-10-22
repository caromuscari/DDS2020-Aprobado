package ar.edu.utn.frba.dds.Licitacion;

import ar.edu.utn.frba.dds.ResultadoLicitacion.ErrorMenorValor;
import ar.edu.utn.frba.dds.ResultadoLicitacion.EstadoValidacion;
import ar.edu.utn.frba.dds.ResultadoLicitacion.ResultadoValidacion;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Comparator;

@Entity
@DiscriminatorValue("MenorValor")
public class MenorValor extends CriterioSeleccion{
    @Override
    public ResultadoValidacion validar(Licitacion licitacion) {
        ResultadoValidacion resultado;
        licitacion.getPresupuestos().sort(Comparator.comparingDouble(Presupuesto::getPrecioTotal));
        Presupuesto menorValor = licitacion.getPresupuestos().get(0);
        if (Double.compare(licitacion.getEgreso().getPrecioTotal(),menorValor.getPrecioTotal()) != 0){
            resultado = new ResultadoValidacion(EstadoValidacion.ERROR,new ErrorMenorValor(licitacion.getEgreso().getPrecioTotal(),menorValor.getPrecioTotal()),licitacion);
        }
        else resultado = new ResultadoValidacion(EstadoValidacion.OK, licitacion);
        return resultado;
    }
}
