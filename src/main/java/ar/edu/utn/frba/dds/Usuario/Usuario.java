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

    private ValidadorPassword validar = new ValidadorPassword();

    public Usuario(String usuario, String password, TipoPerfil perfil, Organizacion organizacion){
        this.usuario = usuario;
        this.password = password;
        this.perfil = perfil;
        this.organizacion = organizacion;
        this.cantidadIntentos = 0;
        this.ultimasPasswords = new ArrayList<String>();
        ultimasPasswords.add(password);
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

    public int getCantidadIntentos() { return cantidadIntentos; }
    public void setCantidadIntentos(int cantidadIntentos) { this.cantidadIntentos = cantidadIntentos; }

    public TipoPerfil getPerfil() { return perfil; }
    public void setPerfil(TipoPerfil perfil) { this.perfil = perfil; }

    public Organizacion getOrganizacion() { return organizacion; }
    public void setOrganizacion(Organizacion organizacion) { this.organizacion = organizacion; }

    public List<String> getUltimasPasswords() { return ultimasPasswords; }
    public void setUltimasPasswords(List<String> ultimasPasswords) { this.ultimasPasswords = ultimasPasswords; }

    public List<Mensaje> getBandejaDeMensajes() { return bandejaDeMensajes; }
    public void setBandejaDeMensajes(List<Mensaje> bandejaDeMensajes) { this.bandejaDeMensajes = bandejaDeMensajes; }

    public ValidadorPassword getValidar() { return validar; }
    public void setValidar(ValidadorPassword validar) { this.validar = validar; }

    public boolean modificarPassword(String passNueva) throws NoSuchAlgorithmException {

        RotacionPassword rotacionPassword = new RotacionPassword();
        Hash hashAlgorithm = new Hash();
        String passNuevaHasheada = hashAlgorithm.hashear(passNueva);
        boolean seModifico = false;

        if (!password.equals(passNuevaHasheada) && !rotacionPassword.validarRotacion(ultimasPasswords, passNuevaHasheada))
            if (validar.validarPassword(passNueva)) {
                ultimasPasswords.add(password);
                password = passNuevaHasheada;
                seModifico = true;
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
