package ar.edu.utn.frba.dds.Usuario;

import ar.edu.utn.frba.dds.Entidad.Organizacion;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class RepoUsuarios {

    Map<String, Usuario> registrados;
    Hash encriptador;

    public RepoUsuarios(){
        registrados = new HashMap<>();
        encriptador = new Hash();

        Usuario user = null;
        try{
            user = new Usuario("gesoc", encriptador.hashear("prueba"), TipoPerfil.OPERADOR, null);
        }
        catch (NoSuchAlgorithmException e) { e.printStackTrace(); }

        registrados.put("gesoc",user);
    }

    // Getters
    public Map<String, Usuario> getRegistrados() { return registrados; }
    public Hash getEncriptador() { return encriptador; }

    public Usuario buscarUsuario(String nombre){
        return registrados.get(nombre);
    }
}
