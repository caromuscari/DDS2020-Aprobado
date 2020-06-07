package ar.edu.utn.frba.dds.Usuario;
import java.util.List;

public class RotacionPassword{

    public boolean validarRotacion(List<String> contraseñas, String passActual)
    {
        return contraseñas.contains(passActual);
    }
}
