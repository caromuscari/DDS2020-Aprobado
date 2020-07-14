package ar.edu.utn.frba.dds.Vinculador;

import ar.edu.utn.frba.dds.Operaciones.Egreso;
import ar.edu.utn.frba.dds.Operaciones.Ingreso;
import ar.edu.utn.frba.dds.Entidad.Entidad;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Mix extends Vinculador{

    public Mix(TipoOrden ordenIngresos, TipoOrden ordenEgresos) {
        this.setOrdenIngresos(ordenIngresos);
        this.setOrdenEgresos(ordenEgresos);
    }

    @Override
    public void vincular(Entidad entidad) {
        Iterator<Egreso> egrOrdenados = ordenarEgresos(entidad.getEgresos()).iterator();
        List <Ingreso> ingrOrdenados = ordenarIngresos(entidad.getIngresos());

        for (int i=0; ingrOrdenados.size()>i; i++){
            Ingreso ingreso = ingrOrdenados.get(i);
            while(ingreso.montoTotalEgresos()< ingreso.getMontoTotal() && egrOrdenados.hasNext()){
                Egreso egreso = egrOrdenados.next();
                if(this.getCondiciones().stream().allMatch(c -> c.validarCondicion(egreso,ingreso)) && (egreso.getPrecioTotal() + ingreso.montoTotalEgresos() <= ingreso.getMontoTotal())){
                    ingreso.asociarEgreso(egreso);
                }
            }
        }
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
            case FECHA:
                egresosOrdenados.sort(Comparator.comparing(Egreso::getFecha));
                break;
        }
        return egresosOrdenados;
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
            case FECHA:
                ingresosOrdenados.sort(Comparator.comparing(Ingreso::getFecha));
                break;
        }
        return ingresosOrdenados;
    }
}
