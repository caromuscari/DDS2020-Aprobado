package ar.edu.utn.frba.dds.Usuario;
import java.util.List;

public class RotacionPassword{

    public boolean validarRotacion(List<String> passwords, String passActual)
    {
        return passwords.contains(passActual);
    }
}
