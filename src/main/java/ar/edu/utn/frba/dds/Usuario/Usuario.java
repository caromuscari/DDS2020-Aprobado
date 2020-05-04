package ar.edu.utn.frba.dds.Usuario;

import java.util.Hashtable;

public class Usuario {

    private String usuario;
    private String password;
    private TipoPerfil perfil;
    private Organizacion organizacion;
    private int cantidadIntentos;



    public Usuario(String usuario, String password, TipoPerfil perfil, Organizacion organizacion){
        this.usuario = usuario;
        this.password = password;
        this.perfil = perfil;
        this.organizacion = organizacion;
        this.cantidadIntentos = 0;
    }

    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public int getCantidadIntentos() {
        return cantidadIntentos;
    }
    public void setCantidadIntentos(int cantidadIntentos) {
        this.cantidadIntentos = cantidadIntentos;
    }

    public TipoPerfil getPerfil() {
        return perfil;
    }
    public void setPerfil(TipoPerfil perfil) {
        this.perfil = perfil;
    }

    public Organizacion getOrganizacion() {
        return organizacion;
    }
    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }
}
