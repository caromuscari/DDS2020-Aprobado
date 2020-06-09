package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.Entidad.Organizacion;
import ar.edu.utn.frba.dds.Usuario.BuilderUsuario;
import ar.edu.utn.frba.dds.Usuario.TipoPerfil;
import ar.edu.utn.frba.dds.Usuario.Usuario;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;


public class AppTest
{
    Map<String, Usuario> registrados = new HashMap<String, Usuario>();

    // Como "persistencia" usamos provisoriamente un HashMap

    @Test
    public void registroExitoso() throws Exception {

        registrados.clear();

        BuilderUsuario builderUsuario = new BuilderUsuario();
        builderUsuario.setUsuario("Enrique");
        builderUsuario.setPassword("Diseño2020");
        builderUsuario.setPerfil(TipoPerfil.ADMINISTRADOR);
        builderUsuario.setOrganizacion(new Organizacion());
        Usuario user = builderUsuario.registrar();
        registrados.put(user.getUsuario(),user);

        Assert.assertTrue(registrados.get("Enrique").equals(user));
    }

    @Test(expected = Exception.class)
    public void registroFallido() throws Exception {

        registrados.clear();

        BuilderUsuario builderUsuario = new BuilderUsuario();
        builderUsuario.setUsuario("Enrique");
        builderUsuario.setPassword("diseño2020");               // Contraseña sin mayusculas
        builderUsuario.setPerfil(TipoPerfil.ADMINISTRADOR);
        builderUsuario.setOrganizacion(new Organizacion());
        Usuario user = builderUsuario.registrar();
        registrados.put(user.getUsuario(),user);
    }


    @Test
    public void registroDeUsuarios() throws Exception {

        registrados.clear();

        BuilderUsuario builderUsuario = new BuilderUsuario();
        builderUsuario.setUsuario("Enrique");
        builderUsuario.setPassword("Diseño2020");
        builderUsuario.setPerfil(TipoPerfil.ADMINISTRADOR);
        builderUsuario.setOrganizacion(new Organizacion());
        Usuario user = builderUsuario.registrar();
        registrados.put(user.getUsuario(),user);

        BuilderUsuario builderUsuario1 = new BuilderUsuario();
        builderUsuario1.setUsuario("Alberto");
        builderUsuario1.setPassword("Diseño2021");
        builderUsuario1.setPerfil(TipoPerfil.OPERADOR);
        builderUsuario1.setOrganizacion(new Organizacion());
        user = builderUsuario1.registrar();
        registrados.put(user.getUsuario(),user);

        Assert.assertTrue(registrados.size() == 2);
    }
}
