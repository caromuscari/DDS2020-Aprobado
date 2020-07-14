package ar.edu.utn.frba.dds.Vinculador;

import ar.edu.utn.frba.dds.Operaciones.Egreso;
import ar.edu.utn.frba.dds.Operaciones.Ingreso;
import ar.edu.utn.frba.dds.Entidad.Entidad;

import java.util.Iterator;
import java.util.List;

public class PrimeroIngreso extends Vinculador{

    public PrimeroIngreso(TipoOrden orden) {
        if (orden == TipoOrden.FECHA) throw new IllegalArgumentException("El tipo de orden Fecha no esta permitido en este vinculador");
        else {
            this.setOrdenEgresos(orden);
            this.setOrdenIngresos(orden);
        }
    }

    @Override
    public void vincular(Entidad entidad) {
        Iterator<Ingreso> ingrOrdenados = ordenarIngresos(entidad.getIngresos()).iterator();
        List<Egreso> egrOrdenados = ordenarEgresos(entidad.getEgresos());

        for(int i = 0; i<egrOrdenados.size(); i++) {
            Egreso egreso = egrOrdenados.get(i);
            while (ingrOrdenados.hasNext()) {
                Ingreso ingreso = ingrOrdenados.next();
                while (ingreso.montoTotalEgresos() < ingreso.getMontoTotal()) {
                    if(this.getCondiciones().stream().allMatch(c -> c.validarCondicion(egreso,ingreso)) && (ingreso.montoTotalEgresos() + egreso.getPrecioTotal() < ingreso.getMontoTotal())){
                        ingreso.asociarEgreso(egreso);
                    }
                }
            }
        }
    }

    private List<Ingreso> ordenarIngresos(List<Ingreso> ingresos) {
        List<Ingreso> ingresosOrdenados = ingresos;
        switch (this.getOrdenIngresos()){
            case ORDENASCENDENTE:
                ingresosOrdenados.sort((i1,i2) -> (int) (i1.getMontoTotal() - i2.getMontoTotal()));
                break;
            case ORDENDESCENDENTE:
                ingresosOrdenados.sort((i1,i2) -> (int) (i2.getMontoTotal() - i1.getMontoTotal()));
                break;
        }
        return ingresosOrdenados;
    }

    private List<Egreso> ordenarEgresos(List<Egreso> egresos) {
        List<Egreso> egresosOrdenados = egresos;
        switch (this.getOrdenIngresos()){
            case ORDENASCENDENTE:
                egresosOrdenados.sort((e1,e2) -> (int) (e1.getPrecioTotal() - e2.getPrecioTotal()));
                break;
            case ORDENDESCENDENTE:
                egresosOrdenados.sort((e1,e2) -> (int) (e2.getPrecioTotal() - e1.getPrecioTotal()));
                break;
        }
        return egresosOrdenados;
    }
}
