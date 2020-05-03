package ar.edu.utn.frba.dds.ValidacionesPassword;

public class Longitud implements Validacion {

    @Override
    public boolean validarPassword(String password){
        return password.length() >= 8;
    }
}
