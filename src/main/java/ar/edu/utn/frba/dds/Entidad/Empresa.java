package ar.edu.utn.frba.dds.Entidad;

public class Empresa extends EntidadJuridica{

    private TipoEmpresa tipoEmpresa;

    public TipoEmpresa getTipoEmpresa() {
        return tipoEmpresa;
    }

    public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

    public void recategorizar(){
        this.tipoEmpresa = super.getTipoActividad().obtenerTipoEmpresa(super.getVentasPromedio());
    }
}
