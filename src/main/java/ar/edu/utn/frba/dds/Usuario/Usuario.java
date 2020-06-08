package ar.edu.utn.frba.dds.Usuario;
import ar.edu.utn.frba.dds.BandejaDeMendajes.Mensaje;
import ar.edu.utn.frba.dds.ResultadoLicitacion.ResultadoValidacion;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private String usuario;
    private String password;
    private int cantidadIntentos;
    private TipoPerfil perfil;
    private Organizacion organizacion;
    private List<String> ultimasPasswords;
    private List<Mensaje> bandejaDeMensajes;

    private ValidadorPassword validar;

    public Usuario(String usuario, String password, TipoPerfil perfil, Organizacion organizacion){
        this.usuario = usuario;
        this.password = password;
        this.perfil = perfil;
        this.organizacion = organizacion;
        this.cantidadIntentos = 0;
        this.ultimasPasswords = new ArrayList<String>();
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

    public boolean modificarPassword(String passNueva) throws NoSuchAlgorithmException {
        Hash aux = new Hash();

        boolean seModifico = false;

        if (!password.equals(aux.hashear(passNueva)) && !new RotacionPassword().validarRotacion(ultimasPasswords, aux.hashear(passNueva))){
            if(validar.validarPassword(passNueva)) {
                ultimasPasswords.add(password);
                password = aux.hashear(passNueva);
                seModifico = !seModifico;
            }
        }
        return seModifico;
    }

    public void recibirMensaje(ResultadoValidacion resultado){
        Mensaje mensaje = new Mensaje(resultado);
        this.bandejaDeMensajes.add(mensaje);
    }

    public void eliminarMensaje(Mensaje mensaje){
        this.bandejaDeMensajes.remove(mensaje);
    }
}
