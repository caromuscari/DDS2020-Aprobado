package ar.edu.utn.frba.dds.Controladores;

import ar.edu.utn.frba.dds.Repositorios.RepoUsuarios;
import ar.edu.utn.frba.dds.Vinculador.PrimeroEgreso;
import org.json.JSONObject;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class Vinculador {

    private static RepoUsuarios repoUsuarios = RepoUsuarios.getInstance();

    public static ModelAndView paginaVinculador(Request request, Response response) {
        if (request.session(false) == null) {
            return Login.paginaLogin(request, response);
        } else {
            Map<String, Object> map = new HashMap<>();
            return new ModelAndView(map, "vinculador.html");
        }
    }

    public static String vincular(Request request, Response response) {
        String seleccionado = request.queryMap("seleccionado").value();

        if(seleccionado == "fecha"){
        }
        else if(seleccionado == "mix"){
            return seleccionado;
        }
        else {
            return seleccionado;
        }
    }
}
