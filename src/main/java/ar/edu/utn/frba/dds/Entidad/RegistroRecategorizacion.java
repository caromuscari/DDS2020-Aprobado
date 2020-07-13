package ar.edu.utn.frba.dds.Entidad;

import java.util.Date;

public class RegistroRecategorizacion {
    private TipoEmpresa tipoPrevio;
    private TipoEmpresa tipoNuevo;
    private Date fecha;

    public TipoEmpresa getTipoPrevio() {
        return tipoPrevio;
    }
    public void setTipoPrevio(TipoEmpresa tipoPrevio) {
        this.tipoPrevio = tipoPrevio;
    }

    public TipoEmpresa getTipoNuevo() {
        return tipoNuevo;
    }
    public void setTipoNuevo(TipoEmpresa tipoNuevo) {
        this.tipoNuevo = tipoNuevo;
    }

    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public RegistroRecategorizacion(TipoEmpresa tipoPrevio, TipoEmpresa tipoNuevo) {
        this.tipoPrevio = tipoPrevio;
        this.tipoNuevo = tipoNuevo;
        this.fecha = new Date();
    }
}
