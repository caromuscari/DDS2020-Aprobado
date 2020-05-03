package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.Usuario.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        BuilderUsuario builderUsuario = new BuilderUsuario();
        builderUsuario.setUsuario("pepito");
        builderUsuario.setPassword("Pepito1234");
        builderUsuario.setPerfil(new TipoPerfil());
        builderUsuario.setOrganizacion(new Organizacion());

        try {
            Usuario usuario = builderUsuario.registrar();
            System.out.println("Se creo efectivamente el usuario : "+usuario.getUsuario());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
