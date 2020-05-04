package ar.edu.utn.frba.dds.Usuario;

import ar.edu.utn.frba.dds.ValidacionesPassword.Validacion;
import java.util.ArrayList;
import java.util.List;

public class ValidadorPassword {

    private static ValidadorPassword instancia = null;

    private ValidadorPassword()
    {
    }

    public static ValidadorPassword getInstance()
    {
        if (instancia == null) {
            instancia = new ValidadorPassword();
        }
        return instancia;
    }

    private List<Validacion> validadores = new ArrayList<Validacion>();

    public List<Validacion> getValidadores() {
        return validadores;
    }
    public void setValidadores(List<Validacion> validadores) {
        validadores = validadores;
    }



    public boolean validarPassword(String password){
        return validadores.stream().allMatch(validacion -> validacion.validarPassword(password));
    }


}
