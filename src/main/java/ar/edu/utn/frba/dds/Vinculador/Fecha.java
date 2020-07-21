package ar.edu.utn.frba.dds.Vinculador;

import ar.edu.utn.frba.dds.Operaciones.Egreso;
import ar.edu.utn.frba.dds.Operaciones.Ingreso;
import ar.edu.utn.frba.dds.Entidad.Entidad;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Fecha extends Vinculador{

    public Fecha() {
        this.setOrdenEgresos(TipoOrden.FECHA);
        this.setOrdenIngresos(TipoOrden.FECHA);
    }
    
    @Override
    public ResultadoVinculacion vincular(Entidad entidad) {
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

        return new ResultadoVinculacion(entidad, LocalDate.now(), ingresosNoCompletos(entidad.getIngresos()), egresosNoVinculados(entidad.getEgresos()));
    }

    private List<Ingreso> ordenarIngresos(List<Ingreso> ingresos) {
        List<Ingreso> ingresosOrdenados = ingresosNoCompletos(ingresos);
        ingresosOrdenados.sort(Comparator.comparing(Ingreso::getFecha));
        return ingresosOrdenados;
    }

    private List<Egreso> ordenarEgresos(List<Egreso> egresos) {
        List<Egreso> egresosOrdenados = egresosNoVinculados(egresos);
        egresosOrdenados.sort(Comparator.comparing(Egreso::getFecha));
        return egresosOrdenados;
    }
}
