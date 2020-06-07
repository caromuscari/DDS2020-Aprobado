package ar.edu.utn.frba.dds.Usuario;
import ar.edu.utn.frba.dds.Usuario.Hash;

public class BuilderUsuario {

    private String usuario;
    private String password;
    private TipoPerfil perfil;
    private Organizacion organizacion;

    private ValidadorPassword validadorPassword = new ValidadorPassword();
    private Hash encriptador = new Hash();

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

    public Usuario registrar() throws Exception{

        if(usuario != null && validadorPassword.validarPassword(password) && perfil != null && organizacion != null)
            return new Usuario(usuario, encriptador.hashear(password),perfil,organizacion);
        else
            throw new Exception("No se pudo crear un usuario");
    }
}
