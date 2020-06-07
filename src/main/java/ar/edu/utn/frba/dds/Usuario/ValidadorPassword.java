package ar.edu.utn.frba.dds.Usuario;

import ar.edu.utn.frba.dds.ValidacionesPassword.Complejidad;
import ar.edu.utn.frba.dds.ValidacionesPassword.Longitud;
import ar.edu.utn.frba.dds.ValidacionesPassword.Top10000;
import ar.edu.utn.frba.dds.ValidacionesPassword.Validacion;
import java.util.ArrayList;
import java.util.List;

public class ValidadorPassword {

    public ValidadorPassword()
    {
        setValidadores(new Top10000());
        setValidadores(new Complejidad());
        setValidadores(new Longitud());
    }

    private List<Validacion> validadores = new ArrayList<Validacion>();

    public List<Validacion> getValidadores() {
        return validadores;
    }
    public void setValidadores(Validacion v) {
        this.validadores.add(v);
    }

    public boolean validarPassword(String password){
        return validadores.stream().allMatch(validacion -> validacion.validarPassword(password));
    }


}
