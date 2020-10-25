package ar.edu.utn.frba.dds.Entidad;

import javax.persistence.*;

@Entity
public abstract class EntidadJuridica extends Entidad {
    private String razonSocial;
    private Long cuit;
    private int codigoPostal;
    private int codigoInscripcion;
    private int cantidadPersonal;
    private Double ventasProyectadas;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tipoActividad_id")
    private TipoActividad tipoActividad;
    private Double ventasPromedio;

    public EntidadJuridica(String nombre, String razonSocial, Long cuit, int codigoPostal, int codigoInscripcion, int cantidadPersonal, Double ventasProyectadas, TipoActividad tipoActividad, Double ventasPromedio) {
        super(nombre);
        this.razonSocial = razonSocial;
        this.cuit = cuit;
        this.codigoPostal = codigoPostal;
        this.codigoInscripcion = codigoInscripcion;
        this.cantidadPersonal = cantidadPersonal;
        this.ventasProyectadas = ventasProyectadas;
        this.tipoActividad = tipoActividad;
        this.ventasPromedio = ventasPromedio;
    }

    public EntidadJuridica() {
    }

    public TipoActividad getTipoActividad() {
        return tipoActividad;
    }

    public void setTipoActividad(TipoActividad tipoActividad) {
        this.tipoActividad = tipoActividad;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Long getCuit() {
        return cuit;
    }

    public void setCuit(Long cuit) {
        this.cuit = cuit;
    }

    public int getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(int codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public int getCodigoInscripcion() {
        return codigoInscripcion;
    }

    public void setCodigoInscripcion(int codigoInscripcion) {
        this.codigoInscripcion = codigoInscripcion;
    }

    public int getCantidadPersonal() {
        return cantidadPersonal;
    }

    public void setCantidadPersonal(int cantidadPersonal) {
        this.cantidadPersonal = cantidadPersonal;
    }

    public Double getVentasProyectadas() {
        return ventasProyectadas;
    }

    public void setVentasProyectadas(Double ventasProyectadas) {
        this.ventasProyectadas = ventasProyectadas;
    }

    public Double getVentasPromedio() {
        return ventasPromedio;
    }

    public void setVentasPromedio(Double ventasPromedio) {
        this.ventasPromedio = ventasPromedio;
    }
}
