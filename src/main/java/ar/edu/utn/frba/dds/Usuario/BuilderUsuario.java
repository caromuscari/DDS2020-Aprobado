package ar.edu.utn.frba.dds.Usuario;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BuilderUsuario {

    private String usuario;
    private String password;
    private TipoPerfil perfil;
    private Organizacion organizacion;

    private ValidadorPassword validadorPassword = ValidadorPassword.getInstance();

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

    // Hasheo para la persistencia seguro de las passwords
    private byte[] sha256(String password) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(password.getBytes(StandardCharsets.UTF_8));
    }
    private static String hash(byte[] bytes)
    {
        BigInteger number = new BigInteger(1, bytes);
        StringBuilder hexaString = new StringBuilder(number.toString(16));
        while (hexaString.length() < 32)
        {
            hexaString.insert(0, '0');
        }
        return hexaString.toString();
    }

    public Usuario registrar() throws Exception{

        if(usuario != null && validadorPassword.validarPassword(password) && perfil != null && organizacion != null)
        {
            return new Usuario(usuario,hash(sha256(password)),perfil,organizacion);
        }
        else
        {
            throw new Exception("No se pudo crear un usuario");
        }
    }
}
