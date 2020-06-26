package ar.edu.utn.frba.dds.Entidad;

import ar.edu.utn.frba.dds.Egreso.Egreso;
import ar.edu.utn.frba.dds.Egreso.ItemOperacion;
import ar.edu.utn.frba.dds.Egreso.Proveedor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Organizacion {

    private List<Entidad> entidades;

    public List<Entidad> getEntidades() {
        return entidades;
    }

    public void setEntidades(List<Entidad> entidades) {
        this.entidades = entidades;
    }

    public Double obtenerTotalEgresos(){
        return entidades.stream().
                mapToDouble(entidad -> entidad.obtenerTotalEgresos()).
                sum();
    }

    public List<Egreso> obtenerEgresos(){
        return this.entidades.stream().map(e -> e.getEgresos()).
                flatMap(Collection::stream).collect(Collectors.toList());
    }
}
