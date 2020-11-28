package ar.edu.utn.frba.dds.Vinculador;

import ar.edu.utn.frba.dds.Operaciones.Egreso;
import ar.edu.utn.frba.dds.Operaciones.Ingreso;
import ar.edu.utn.frba.dds.Entidad.Entidad;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Fecha extends Vinculador{

    public Fecha() {
        this.setOrdenEgresos(TipoOrden.FECHA);
        this.setOrdenIngresos(TipoOrden.FECHA);
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
        ingresosOrdenados.sort(Comparator.comparing(Ingreso::getFecha));
        return ingresosOrdenados;
    }

    private List<Egreso> ordenarEgresos(List<Egreso> egresos) {
        List<Egreso> egresosOrdenados = egresosNoVinculados(egresos);
        egresosOrdenados.sort(Comparator.comparing(Egreso::getFecha));
        return egresosOrdenados;
    }
}
