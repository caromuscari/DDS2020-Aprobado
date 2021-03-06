package ar.edu.utn.frba.dds.Entidad;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("Empresa")
public class Empresa extends EntidadJuridica{ 

    @Enumerated(EnumType.STRING)
    private TipoEmpresa tipoEmpresa;
    @Transient
    private List<RegistroRecategorizacion> historial;

    public List<RegistroRecategorizacion> getHistorial() {
        return historial;
    }
    public void setHistorial(List<RegistroRecategorizacion> historial) {
        this.historial = historial;
    }

    public TipoEmpresa getTipoEmpresa() {
        return tipoEmpresa;
    }
    public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

    public Empresa(){}

    public Empresa(String nombre, String razonSocial, Long cuit, int codigoPostal, int codigoInscripcion, int cantidadPersonal, Double ventasProyectadas, TipoActividad tipoActividad, Double ventasPromedio) {
        super(nombre, razonSocial, cuit, codigoPostal, codigoInscripcion, cantidadPersonal, ventasProyectadas, tipoActividad, ventasPromedio);
        this.tipoEmpresa = super.getTipoActividad().obtenerTipoEmpresa(ventasPromedio, cantidadPersonal);
        this.historial = new ArrayList<>();
        Recategorizador.getInstance().agregarEmpresa(this);
    }

    //Metodos
    public void recategorizar(){
        TipoEmpresa tipo = getTipoActividad().obtenerTipoEmpresa(getVentasPromedio(), getCantidadPersonal());
        this.historial.add(new RegistroRecategorizacion(this.tipoEmpresa,tipo));
        this.tipoEmpresa = tipo;
    }


}
