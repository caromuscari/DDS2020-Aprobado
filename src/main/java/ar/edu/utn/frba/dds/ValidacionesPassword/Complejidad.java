package ar.edu.utn.frba.dds.ValidacionesPassword;

public class Complejidad implements Validacion{

    private boolean valido = false;

    @Override
    public boolean validarPassword(String password) {
        ///// Política de complejidad de la contraseña /////
        /*
            @ Debe contener al menos una mayúscula
            @ No debe contener simbolos?
         */

        // Si contiene mayúsculas
        for(int i = 0; i<password.length(); i++)
        {
            if(Character.isUpperCase(password.charAt(i))) {
                valido = !valido;
                return valido;
            }
        }
        return valido;
    }
}
