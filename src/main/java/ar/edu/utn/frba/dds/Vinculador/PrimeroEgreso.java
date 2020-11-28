package ar.edu.utn.frba.dds.Vinculador;

import ar.edu.utn.frba.dds.Operaciones.Egreso;
import ar.edu.utn.frba.dds.Operaciones.Ingreso;
import ar.edu.utn.frba.dds.Entidad.Entidad;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class PrimeroEgreso extends Vinculador{

    public PrimeroEgreso(TipoOrden orden) {
        if (orden == TipoOrden.FECHA) throw new IllegalArgumentException("El tipo de orden Fecha no esta permitido en este vinculador");
        else {
            this.setOrdenEgresos(orden);
            this.setOrdenIngresos(orden);
        }
    }

    @Override
    public ResultadoVinculacion vincular(Entidad entidad) {
        List<Egreso> egrOrdenados = ordenarEgresos(entidad.getEgresos());
        Iterator<Egreso> egresos = egrOrdenados.iterator();
        List <Ingreso> ingrOrdenados = ordenarIngresos(entidad.getIngresos());
        List <Ingreso> ingresosVinculados = new ArrayList<>();

        for (int i=0; ingrOrdenados.size()>i; i++){
             Ingreso ingreso = ingrOrdenados.get(i);
             if (!egresos.hasNext()) egresos = egrOrdenados.iterator();
             while(ingreso.montoTotalEgresos()< ingreso.getMontoTotal() && egresos.hasNext()){
                 Egreso egreso = egresos.next();
                 if(this.getCondiciones().stream().allMatch(c -> c.validarCondicion(egreso,ingreso)) && (egreso.getPrecioTotal() + ingreso.montoTotalEgresos() <= ingreso.getMontoTotal()) && !egreso.getVinculado()){
                     ingreso.asociarEgreso(egreso);
                     if (!ingresosVinculados.contains(ingreso)) ingresosVinculados.add(ingreso);
                 }
             }
        }

        return new ResultadoVinculacion(entidad, LocalDate.now(), ingresosNoCompletos(entidad.getIngresos()), egresosNoVinculados(entidad.getEgresos()), ingresosVinculados);
    }

    private List<Ingreso> ordenarIngresos(List<Ingreso> ingresos) {
        List<Ingreso> ingresosOrdenados = ingresosNoCompletos(ingresos);
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
        List<Egreso> egresosOrdenados = egresosNoVinculados(egresos);
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
