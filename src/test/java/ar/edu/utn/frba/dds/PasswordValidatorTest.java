package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.Usuario.*;
import ar.edu.utn.frba.dds.ValidacionesPassword.Longitud;
import ar.edu.utn.frba.dds.ValidacionesPassword.Complejidad;
import ar.edu.utn.frba.dds.ValidacionesPassword.Top10000;
import org.junit.Assert;
import org.junit.Test;

public class PasswordValidatorTest {

    ValidadorPassword validador = ValidadorPassword.getInstance();

    @Test
    public void Top10000(){
        validador.getValidadores().clear();
        validador.getValidadores().add(new Top10000());
        Assert.assertEquals(true , validador.validarPassword("pasfasdasfaasf"));
    }

    @Test
    public void CantMinCaracteresTrue(){
        validador.getValidadores().clear();
        validador.getValidadores().add(new Longitud());
        Assert.assertEquals(true , validador.validarPassword("pasfasdasfaasf"));
    }

    @Test
    public void CantMinCaracteresFalse(){
        validador.getValidadores().clear();
        validador.getValidadores().add(new Longitud());
        Assert.assertNotEquals(true , validador.validarPassword("asd"));
    }

    @Test
    public void ContieneMayuscula(){
        validador.getValidadores().clear();
        validador.getValidadores().add(new Complejidad());
        Assert.assertEquals(true , validador.validarPassword("afasfAfaasf"));
    }

    @Test
    public void MultipleValidations(){
        validador.getValidadores().clear();
        validador.getValidadores().add(new Longitud());
        validador.getValidadores().add(new Top10000());
        validador.getValidadores().add(new Complejidad());
        Assert.assertEquals(true , validador.validarPassword("afasfasAaasfa"));
    }

    @Test
    public void CreacionDeAdminsitradorYHasheoDePass() throws Exception {
        BuilderUsuario builderUsuario = new BuilderUsuario();
        builderUsuario.setUsuario("pepito");
        builderUsuario.setPassword("Pepito1234");
        builderUsuario.setPerfil(TipoPerfil.ADMINISTRADOR);
        builderUsuario.setOrganizacion(new Organizacion());
        Usuario u = builderUsuario.registrar();
        Assert.assertTrue(u.getPassword() != "Pepito1234");
    }

    /*
    @Test
    public void CreacionDeOperador(){

    }
     */
}
