package ar.edu.utn.frba.dds.ValidacionesPassword;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

public class Top10000 implements Validacion {

    private File malasPasswords = null;
    private FileReader fr = null;
    private BufferedReader br = null;
    private String linea;
    boolean valido = true;

    @Override
    public boolean validarPassword(String password) {

        try{
            malasPasswords = new File("top10000.txt");
            fr = new FileReader(malasPasswords);
            br = new BufferedReader(fr);
            while((linea = br.readLine()) != null)
            {
                if(password.equals(linea))
                    valido = false;
            }
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return valido;
    }
}
