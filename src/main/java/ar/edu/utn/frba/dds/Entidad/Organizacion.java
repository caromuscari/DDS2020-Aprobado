package ar.edu.utn.frba.dds.Entidad;

import ar.edu.utn.frba.dds.Egreso.ItemOperacion;
import ar.edu.utn.frba.dds.Egreso.Proveedor;

import java.util.List;

public class Organizacion {

    private List<Entidad> entidades;

    public List<Entidad> getEntidades() {
        return entidades;
    }

    public void setEntidades(List<Entidad> entidades) {
        this.entidades = entidades;
    }

    public Double obtenerEgresos(){
        return entidades.stream().
                mapToDouble(entidad -> entidad.obtenerTotalEgresos()).
                sum();
    }
}
