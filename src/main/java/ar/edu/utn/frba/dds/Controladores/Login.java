package ar.edu.utn.frba.dds.Controladores;

import ar.edu.utn.frba.dds.Repositorios.RepoUsuarios;
import ar.edu.utn.frba.dds.Usuario.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Login {


    public static ModelAndView paginaLogin(Request request, Response response) {
        Map<String, Object> map = new HashMap<>();
        return new ModelAndView(map, "login.html");
    }

    public static ModelAndView logout(Request request, Response response){
        request.cookies().clear();
        request.session().invalidate();
        return null;
    }

    public static boolean checkLogin(String usuario, String pass, EntityManager entityManager) throws NoSuchAlgorithmException {
        RepoUsuarios repoUsuarios = new RepoUsuarios(entityManager);
        if(repoUsuarios.existeUsuario(usuario)) {
            Usuario user = repoUsuarios.buscarUsuario(usuario);
            return user.getPassword().equals(repoUsuarios.getEncriptador().hashear(pass));
        }
        return false;
    }

    public static String autenticar(Request request, Response response, EntityManager entity) throws NoSuchAlgorithmException {
        String user = request.queryMap("usuario").value();
        String pass = request.queryMap("pass").value();

        if (checkLogin(user, pass, entity)) {
            request.session(true);
            request.session().attribute("usuario", user);
            response.cookie("usuario", user);
            response.status(202);
            return "Bienvenido " + user + "!";
        } else {
            response.status(401);
            return null;
        }
    }

}
