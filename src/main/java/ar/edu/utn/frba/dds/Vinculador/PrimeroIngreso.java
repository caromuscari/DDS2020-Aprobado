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

public class PrimeroIngreso extends Vinculador{

    public PrimeroIngreso(TipoOrden orden) {
        if (orden == TipoOrden.FECHA) throw new IllegalArgumentException("El tipo de orden Fecha no esta permitido en este vinculador");
        else {
            this.setOrdenEgresos(orden);
            this.setOrdenIngresos(orden);
        }
    }

    @Override
    public ResultadoVinculacion vincular(Entidad entidad) {
        List<Ingreso> ingrOrdenados = ordenarIngresos(entidad.getIngresos());
        List<Egreso> egrOrdenados = ordenarEgresos(entidad.getEgresos());
        Iterator<Ingreso> ingresos;
        List <Ingreso> ingresosVinculados = new ArrayList<>();

        for(int i = 0; i<egrOrdenados.size(); i++) {
            Egreso egreso = egrOrdenados.get(i);
            ingresos = ingrOrdenados.iterator();
            while (!egreso.getVinculado() && ingresos.hasNext()) {
                Ingreso ingreso = ingresos.next();
                if (this.getCondiciones().stream().allMatch(c -> c.validarCondicion(egreso, ingreso)) && (ingreso.montoTotalEgresos() + egreso.getPrecioTotal() < ingreso.getMontoTotal())) {
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
