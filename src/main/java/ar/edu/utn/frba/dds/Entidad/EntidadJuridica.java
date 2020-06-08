package ar.edu.utn.frba.dds.Entidad;

public class EntidadJuridica {
    private String razonSocial;
    private int cuit;
    private int codigoPostal;
    private int codigoInscripcion;
    private int cantidadPersonal;
    private double ventasProyectadas;
    private TipoActividad tipoActividad;


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

    public int getCuit() {
        return cuit;
    }

    public void setCuit(int cuit) {
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

    public double getVentasProyectadas() {
        return ventasProyectadas;
    }

    public void setVentasProyectadas(double ventasProyectadas) {
        this.ventasProyectadas = ventasProyectadas;
    }
}