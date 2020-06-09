package ar.edu.utn.frba.dds.BandejaDeMendajes;

import ar.edu.utn.frba.dds.Usuario.Usuario;
import ar.edu.utn.frba.dds.ResultadoLicitacion.ResultadoValidacion;

import java.util.*;

public class NotificadorValidador {
    private List<Usuario> revisores;

    public NotificadorValidador() {
        this.revisores = new ArrayList<Usuario>();
    }

    public List<Usuario> getRevisores() { return revisores; }
    public void setRevisores(List<Usuario> revisores) { this.revisores = revisores; }

    public void suscribir(Usuario usuario){
        this.revisores.add(usuario);
    }

    public void desuscribir(Usuario usuario){
        this.revisores.remove(usuario);
    }

    public void notificar(ResultadoValidacion resultado){
        this.revisores.forEach(usuario -> usuario.recibirMensaje(resultado));
    }
}
