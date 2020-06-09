package ar.edu.utn.frba.dds.Entidad;

public class Empresa extends EntidadJuridica{

    private TipoEmpresa tipoEmpresa;

    public TipoEmpresa getTipoEmpresa() {
        return tipoEmpresa;
    }

    public Empresa(String nombre, String razonSocial, int cuit, int codigoPostal, int codigoInscripcion, int cantidadPersonal, int ventasProyectadas, int ventasPromedio) {
        super(nombre, razonSocial, cuit, codigoPostal, codigoInscripcion, cantidadPersonal, ventasProyectadas, ventasPromedio);
        this.tipoEmpresa = super.getTipoActividad().obtenerTipoEmpresa(ventasPromedio);
    }

    public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

    public void recategorizar(){
        this.tipoEmpresa = super.getTipoActividad().obtenerTipoEmpresa(super.getVentasPromedio());
    }
}
