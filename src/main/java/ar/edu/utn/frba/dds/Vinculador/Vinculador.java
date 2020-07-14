package ar.edu.utn.frba.dds.Vinculador;

import ar.edu.utn.frba.dds.Operaciones.Egreso;
import ar.edu.utn.frba.dds.Operaciones.Ingreso;
import ar.edu.utn.frba.dds.Entidad.Entidad;

import java.util.List;

public abstract class Vinculador {
    private List<CondicionVinculacion> condiciones;
    private TipoOrden ordenIngresos;
    private TipoOrden ordenEgresos;

    public List<CondicionVinculacion> getCondiciones() { return condiciones; }
    public void setCondiciones(List<CondicionVinculacion> condiciones) { this.condiciones = condiciones; }

    public TipoOrden getOrdenIngresos() { return ordenIngresos; }
    public void setOrdenIngresos(TipoOrden ordenIngresos) { this.ordenIngresos = ordenIngresos; }

    public TipoOrden getOrdenEgresos() { return ordenEgresos; }
    public void setOrdenEgresos(TipoOrden ordenEgresos) { this.ordenEgresos = ordenEgresos; }

    public abstract void vincular(Entidad entidad);
}
