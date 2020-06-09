package ar.edu.utn.frba.dds.Usuario;

import ar.edu.utn.frba.dds.Egreso.Egreso;
import ar.edu.utn.frba.dds.Egreso.ItemOperacion;
import ar.edu.utn.frba.dds.Egreso.Proveedor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Organizacion {
    private List<Entidad> entidad;

    public List<Egreso> obtenerEgresos(){
        return this.entidad.stream().map(e -> e.getEgresos()).flatMap(Collection::stream).collect(Collectors.toList());
    }
}
