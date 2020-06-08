package ar.edu.utn.frba.dds.BandejaDeMendajes;

import ar.edu.utn.frba.dds.Usuario.Usuario;
import ar.edu.utn.frba.dds.ResultadoLicitacion.ResultadoValidacion;

import java.util.List;

public class NotificadorValidador {
    private List<Usuario> revisores;

    public NotificadorValidador() {
    }

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
