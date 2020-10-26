package ar.edu.utn.frba.dds.Excepciones;

import ar.edu.utn.frba.dds.Usuario.Usuario;

public class UsuarioExistsException extends Exception{

    private String usuario;

    public UsuarioExistsException(){
        super();
    }

    public UsuarioExistsException(String usuario){
        super("Usuario " + usuario + " existente en la base de Datos.");
    }
}
