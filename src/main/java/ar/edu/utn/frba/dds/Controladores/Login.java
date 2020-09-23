package ar.edu.utn.frba.dds.Controladores;

import ar.edu.utn.frba.dds.Usuario.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Login {

    public static ModelAndView paginaLogin(Request request, Response response) {
        Map<String, Object> map = new HashMap<>();
        return new ModelAndView(map, "login.html");
    }

}
