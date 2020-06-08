package ar.edu.utn.frba.dds.BandejaDeMendajes;

import ar.edu.utn.frba.dds.ResultadoLicitacion.ResultadoValidacion;

public class Mensaje {
    private ResultadoValidacion resultado;
    private Boolean leido;

    public Mensaje(ResultadoValidacion resultado) {
        this.resultado = resultado;
        this.leido = false;
    }

    public ResultadoValidacion getResultado() { return resultado; }

    public Boolean getLeido() { return leido; }

    public String leerMensaje(){
        this.leido = true;
        return obtenerMensaje();
    }

    public String obtenerMensaje(){
        return this.resultado.obtenerMensaje();
    }
}
