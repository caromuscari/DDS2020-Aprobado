package ar.edu.utn.frba.dds.Licitacion;

import ar.edu.utn.frba.dds.ResultadoLicitacion.ResultadoValidacion;

public interface CriterioSeleccion {

    public ResultadoValidacion validar(Licitacion licitacion);
}
