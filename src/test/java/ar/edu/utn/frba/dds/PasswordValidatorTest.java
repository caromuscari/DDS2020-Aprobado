package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.ValidacionesPassword.CantMinCaracteres;
import ar.edu.utn.frba.dds.ValidacionesPassword.Top10000;
import ar.edu.utn.frba.dds.Usuario.ValidadorPassword;
import org.junit.Assert;
import org.junit.Test;

public class PasswordValidatorTest {

    ValidadorPassword validador = ValidadorPassword.getInstance();

    @Test
    public void Top10000(){
        validador.getValidadores().add(new Top10000());
        Assert.assertEquals(true , validador.validarPassword("pasasfafasf"));
    }

    @Test
    public void CantMinCaracteresTrue(){
        validador.getValidadores().add(new CantMinCaracteres());
        Assert.assertEquals(true , validador.validarPassword("pasfasdasfaasf"));
    }

    @Test
    public void CantMinCaracteresFalse(){
        validador.getValidadores().add(new CantMinCaracteres());
        Assert.assertNotEquals(true , validador.validarPassword("asd"));
    }

    @Test
    public void MultipleValidations(){
        validador.getValidadores().add(new CantMinCaracteres());
        validador.getValidadores().add(new Top10000());
        Assert.assertEquals(true , validador.validarPassword("afasfasfaasfa"));
    }
}
