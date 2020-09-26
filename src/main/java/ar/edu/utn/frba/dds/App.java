package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.Controladores.Egresos;
import ar.edu.utn.frba.dds.Controladores.LicitacionController;
import ar.edu.utn.frba.dds.Controladores.Login;
import ar.edu.utn.frba.dds.Usuario.Usuario;
import ar.edu.utn.frba.dds.Usuario.Hash;
import ar.edu.utn.frba.dds.Usuario.RepoUsuarios;

import java.security.NoSuchAlgorithmException;
import java.util.*;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.handlebars.HandlebarsTemplateEngine;



public class App
{
    private static RepoUsuarios repoUsuarios = new RepoUsuarios();

    public static void main(String[] args ) {

        // ===============================================================================
        // Server

        enableDebugScreen();
        port(4568);
        boolean localhost = true;
        if (localhost) {
            String projectDir = System.getProperty("user.dir");
            String staticDir = "/src/main/resources/static/";
            staticFiles.externalLocation(projectDir + staticDir);
        } else {
            staticFiles.location("/public");
        }

        // Acceso: http://localhost:4567/login
        HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();

        //Login
        get("/login", Login::paginaLogin, engine);
        post("/autenticacion", (request, response) -> {
            if (checkLogin(request.queryMap("usuario").value(), request.queryMap("pass").value())) {
                request.session(true);
                response.status(202);
                return "Bienvenido " + request.queryMap("usuario").value() + "!";
            }
            else {
                response.status(401);
                return null;
            }
        });
        post("/logout", (request,response) -> {
            request.session().invalidate();
            return null;
        });

        //Home
        get("/home", App::paginaHome, engine);

        //Ingresos
        get("/ingreso", App::paginaIngresos, engine);

        //Presupuestos
        get("/presupuesto", App::paginaPresupuestos, engine);

        //Vinculador
        get("/vinculador", App::paginaVinculador, engine);

        //Egresos
        get("/egreso", Egresos::paginaEgresos, engine);
        post("/egreso", Egresos::nuevoEgreso, engine);
        get("/nuevo_egreso", Egresos::paginaNuevoEgreso, engine);
        get("/modificar_egreso", Egresos::paginaModificarEgreso, engine);

        get("/proveedor", ar.edu.utn.frba.dds.Controladores.Proveedor::proveedores);

        //Licitaciones
        post("/licitacion", LicitacionController::crearLicitacion);
        post("/licitacion/:id", LicitacionController::validarLicitacion);
        get("/licitacion/:id", LicitacionController::mostrarMensajes);

        // ===============================================================================
        Hash encriptador = new Hash();
        Scanner sn = new Scanner(System.in);

        //Inicializar datos de prueba
        new ExampleDataCreator().createData();
    }

    public static ModelAndView paginaHome(Request request, Response response) {
        // Si no hay session creada por login, me redirige a la vista de Login
        if(request.session(false) == null) {
            return Login.paginaLogin(request,response);
        }
        else{
            Map<String, Object> map = new HashMap<>();
            return new ModelAndView(map, "home.html");
        }
    }


    public static ModelAndView paginaIngresos(Request request, Response response) {
        if(request.session(false) == null) {
            return Login.paginaLogin(request,response);
        }
        else {
            Map<String, Object> map = new HashMap<>();
            return new ModelAndView(map, "ingresos.html");
        }
    }

    public static ModelAndView paginaPresupuestos(Request request, Response response) {
        if(request.session(false) == null) {
            return Login.paginaLogin(request,response);
        }
        else {
            Map<String, Object> map = new HashMap<>();
            return new ModelAndView(map, "presupuestos.html");
        }
    }

    public static ModelAndView paginaVinculador(Request request, Response response) {
        if(request.session(false) == null) {
            return Login.paginaLogin(request,response);
        }
        else {
            Map<String, Object> map = new HashMap<>();
            return new ModelAndView(map, "vinculador.html");
        }
    }

    public static boolean checkLogin(String usuario, String pass) throws NoSuchAlgorithmException {
        Usuario user = repoUsuarios.getRegistrados().get(usuario);
        return user.getPassword().equals(repoUsuarios.getEncriptador().hashear(pass));
    }

}
