package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.Usuario.BuilderUsuario;
import ar.edu.utn.frba.dds.Usuario.Organizacion;
import ar.edu.utn.frba.dds.Usuario.TipoPerfil;
import ar.edu.utn.frba.dds.Usuario.Usuario;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;


public class AppTest
{
    Map<String, Usuario> registrados = new HashMap<String, Usuario>();

    @Test
    public void registroUsuarios() throws Exception {
        BuilderUsuario builderUsuario = new BuilderUsuario();
        builderUsuario.setUsuario("Enrique");
        builderUsuario.setPassword("Diseño2020");
        builderUsuario.setPerfil(TipoPerfil.ADMINISTRADOR);
        builderUsuario.setOrganizacion(new Organizacion());
        Usuario user = builderUsuario.registrar();
        registrados.put(user.getUsuario(),user);

        builderUsuario.setUsuario("Alberto");
        builderUsuario.setPassword("Diseño2021");
        builderUsuario.setPerfil(TipoPerfil.ADMINISTRADOR);
        builderUsuario.setOrganizacion(new Organizacion());
        user = builderUsuario.registrar();
        registrados.put(user.getUsuario(), user);

        Assert.assertTrue(registrados.get("Enrique").equals(user));
    }
}
