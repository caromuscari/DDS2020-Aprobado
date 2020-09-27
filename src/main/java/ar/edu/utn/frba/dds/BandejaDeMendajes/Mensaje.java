package ar.edu.utn.frba.dds.BandejaDeMendajes;

import ar.edu.utn.frba.dds.ResultadoLicitacion.ResultadoValidacion;

import java.util.List;
import java.util.stream.Collectors;

public class Mensaje {
    private List<ResultadoValidacion> resultados;
    private Boolean leido;

    public Mensaje(List<ResultadoValidacion> resultados) {
        this.resultados = resultados;
        this.leido = false;
    }

    public List<ResultadoValidacion> getResultados() { return resultados; }

    public Boolean getLeido() { return leido; }

    public String leerMensaje(){
        this.leido = true;
        return obtenerMensaje();
    }

    public String obtenerMensaje(){
        //String mensaje = String.format("Resultados de la licitacion {0}", resultados.get(0).getLicitacion().getNombre());
        List<String> msjResultados = this.resultados.stream().map(r -> r.obtenerMensaje()).collect(Collectors.toList());
        return msjResultados.toString();
    }
}
