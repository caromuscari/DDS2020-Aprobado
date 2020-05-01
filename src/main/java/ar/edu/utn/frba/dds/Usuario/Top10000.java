package ar.edu.utn.frba.dds.Usuario;

public class Top10000 implements Validador{
    private String password;

    public Top10000(String password) {
        this.password = password;
    }

    @Override
    public boolean validar(){


        return false;
    }
}
