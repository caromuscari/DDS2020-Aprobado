package ar.edu.utn.frba.dds.Vinculador;

import ar.edu.utn.frba.dds.Operaciones.Egreso;
import ar.edu.utn.frba.dds.Operaciones.Ingreso;
import ar.edu.utn.frba.dds.Entidad.Entidad;

import java.util.List;

public class Vinculador {
    private List<CondicionVinculacion> condiciones;
    private CriterioEjecucion criterio;

    public List<CondicionVinculacion> getCondiciones() { return condiciones; }
    public void setCondiciones(List<CondicionVinculacion> condiciones) { this.condiciones = condiciones; }

    public CriterioEjecucion getCriterio() { return criterio; }
    public void setCriterio(CriterioEjecucion criterio) { this.criterio = criterio; }


    public void vincular(Entidad entidad){
        // Proceso de vinculación
        List<Ingreso> ingresos = entidad.getIngresos();
        List<Egreso> egresos = entidad.getEgresos();
        criterio.procesarEjecucion(egresos, ingresos, condiciones);
    }
}
