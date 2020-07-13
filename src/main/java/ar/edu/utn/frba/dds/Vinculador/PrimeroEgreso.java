package ar.edu.utn.frba.dds.Vinculador;

import ar.edu.utn.frba.dds.Operaciones.Egreso;
import ar.edu.utn.frba.dds.Operaciones.Ingreso;
import ar.edu.utn.frba.dds.Entidad.Entidad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class PrimeroEgreso implements CriterioEjecucion{

    @Override
    public void procesarEjecucion(List<Egreso> egresos, List<Ingreso> ingresos, List<CondicionVinculacion> condiciones) {

        List<Egreso> egrOrdenados = new ArrayList<>();
        List <Ingreso> igrOrdenados = new ArrayList<>();
        Collections.sort(igrOrdenados, (i1,i2) -> comparadorIngresos(i1,i2));
        Collections.sort(egrOrdenados, (e1,e2) -> comparadorEgresos(e1,e2));

        Iterator<CondicionVinculacion> verificador = condiciones.listIterator();
        CondicionVinculacion condicion;
        double total = 0;

        // Itero
        for(int i = 0; i<igrOrdenados.size();i++){
            Ingreso ingr = igrOrdenados.get(i);
            for(int k = 0; k<egrOrdenados.size();k++){
                Egreso egr = egrOrdenados.get(k);
                while(verificador.hasNext()){
                    condicion = verificador.next();
                    if(condicion.validarCondicion(egr,ingr))
                       if(total + egr.getPrecioTotal() < ingr.getMontoTotal())
                           ingr.asociarEgreso(egr);
                }
            }
            total = 0;
        }

    }

    private int comparadorIngresos(Ingreso i1, Ingreso i2){
        return i1.getMontoTotal().compareTo(i2.getMontoTotal());
    }
    private int comparadorEgresos(Egreso e1, Egreso e2){
        return e1.getPrecioTotal().compareTo(e2.getPrecioTotal());
    }


}
