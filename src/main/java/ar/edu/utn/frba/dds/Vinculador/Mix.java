package ar.edu.utn.frba.dds.Vinculador;

import ar.edu.utn.frba.dds.Operaciones.Egreso;
import ar.edu.utn.frba.dds.Operaciones.Ingreso;
import ar.edu.utn.frba.dds.Entidad.Entidad;

import java.util.Iterator;
import java.util.List;

public class Mix implements CriterioEjecucion{
    private List<CriterioEjecucion> criterios;

    @Override
    public void procesarEjecucion(List<Egreso> egresos, List<Ingreso> ingresos, List<CondicionVinculacion> condiciones) {
        Iterator<CriterioEjecucion> ejecutador = criterios.iterator();
        while(ejecutador.hasNext()) {
            CriterioEjecucion ejec = ejecutador.next();
            ejec.procesarEjecucion(egresos,ingresos,condiciones);
        }
    }
}
