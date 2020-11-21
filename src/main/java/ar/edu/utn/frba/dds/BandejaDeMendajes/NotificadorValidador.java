package ar.edu.utn.frba.dds.BandejaDeMendajes;

import ar.edu.utn.frba.dds.Usuario.Usuario;
import ar.edu.utn.frba.dds.ResultadoLicitacion.ResultadoValidacion;

import javax.persistence.*;
import java.util.*;

@Embeddable
public class NotificadorValidador {
    @ManyToMany
    @JoinTable(name = "Notificados_licitacion", joinColumns = @JoinColumn(name = "licitacion_id"), inverseJoinColumns = @JoinColumn(name = "usuario_id"))
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

    public void notificar(List<ResultadoValidacion> resultados){
        this.revisores.forEach(usuario -> usuario.recibirMensaje(resultados));
    }
}
