package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.Controladores.*;
import ar.edu.utn.frba.dds.Repositorios.RepositorioEntidades;
import ar.edu.utn.frba.dds.Usuario.Usuario;
import ar.edu.utn.frba.dds.Repositorios.RepoUsuarios;

import java.util.*;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.handlebars.HandlebarsTemplateEngine;



public class App {
    private static RepoUsuarios repoUsuarios = RepoUsuarios.getInstance();

    public static void main(String[] args) {

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
        post("/autenticacion", Login::autenticar);
        post("/logout", Login::logout);

        //Home
        get("/", App::redireccion, engine);
        get("/home", App::paginaHome, engine);

        //Ingresos
        get("/ingreso", Ingresos::paginaIngresos, engine);
        get("/ingreso/:id", Ingresos::modificarIngreso, engine);
        post("/ingreso/:id", Ingresos::guardarIngreso, engine);

        //Presupuestos
        get("/presupuesto", Presupuestos::paginaPresupuestos, engine);
        get("/presupuesto/:id", Presupuestos::modificarPresupuesto, engine);
        post("/presupuesto/:id", Presupuestos::guardarPresupuesto, engine);

        //Vinculador
        get("/vinculador", VinculadorController::paginaVinculador, engine);
        post("/vincular", VinculadorController::vincular);

        //Egresos
        get("/egreso", Egresos::paginaEgresos, engine);
        post("/egreso", Egresos::guardarEgreso, engine);
        get("/nuevo_egreso", Egresos::paginaNuevoEgreso, engine);
        get("/egreso/:id", Egresos::paginaModificarEgreso, engine);
        post("/egreso/:id", Egresos::guardarEgreso, engine);
        delete("/egreso/:id", Egresos::borrarEgreso, engine);

        get("/proveedor", ar.edu.utn.frba.dds.Controladores.Proveedor::proveedores);

        //Licitaciones
        post("/licitacion", LicitacionController::crearLicitacion);
        post("/validarLicitacion", LicitacionController::validarLicitacion);
        get("/licitacion", LicitacionController::mostrarMensajes);

        // ===============================================================================

        //Inicializar datos de prueba
        new ExampleDataCreator().createData();
        repoUsuarios.getRegistrados().get("gesoc").getOrganizacion().setEntidades(RepositorioEntidades.getInstance().obtenerEntidades());
    }

    public static ModelAndView paginaHome(Request request, Response response) {
        // Si no hay session creada por login, me redirige a la vista de Login
        if (request.session(false) == null) {
            return Login.paginaLogin(request, response);
        } else {
            Usuario usuario = repoUsuarios.buscarUsuario(request.session().attribute("usuario"));
            Map<String, Object> map = new HashMap<>();
            map.put("organizacion", usuario.getOrganizacion());
            return new ModelAndView(map, "home.html");
        }
    }

    public static ModelAndView redireccion(Request request, Response response){
        response.redirect("/home");
        return null;
    }
}
