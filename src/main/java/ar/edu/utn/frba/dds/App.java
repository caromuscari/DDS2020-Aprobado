package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.Controladores.*;
import ar.edu.utn.frba.dds.Usuario.Usuario;
import ar.edu.utn.frba.dds.Repositorios.RepoUsuarios;

import java.util.*;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

import com.google.gson.Gson;
import spark.*;
import spark.template.handlebars.HandlebarsTemplateEngine;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class App {
    static EntityManagerFactory entityManagerFactory;

    private static int pageSize = 2;
    private static Gson gson = new Gson();

    public static void main(String[] args) {

        // ===============================================================================
        // Server

        enableDebugScreen();
        port(4568);
        initRoutes();
        initPersistence();

    }

    public static void initPersistence(){
        entityManagerFactory = Persistence.createEntityManagerFactory("db");
    }

    public static void initRoutes(){
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
        post("/autenticacion", RouteWithTransaction(Login::autenticar));
        post("/logout", Login::logout);

        //Home
        get("/", App::redireccion, engine);
        get("/home", TemplWithTransaction(App::paginaHome), engine);

        //Ingresos
        get("/ingreso", TemplWithTransaction(Ingresos::paginaIngresos), engine);
        get("/ingreso/:id", TemplWithTransaction(Ingresos::modificarIngreso), engine);
        post("/ingreso/:id", TemplWithTransaction(Ingresos::guardarIngreso), engine);

        //Presupuestos
        get("/presupuesto", TemplWithTransaction(Presupuestos::paginaPresupuestos), engine);
        get("/presupuesto/:id", TemplWithTransaction(Presupuestos::modificarPresupuesto), engine);
        post("/presupuesto/:id", TemplWithTransaction(Presupuestos::guardarPresupuesto), engine);

        //Vinculador
        get("/vinculador", TemplWithTransaction(VinculadorController::paginaVinculador), engine);
        post("/vincular", RouteWithTransaction(VinculadorController::vincular));

        //Egresos
        get("/egreso", TemplWithTransaction(Egresos::paginaEgresos), engine);
        post("/egreso", TemplWithTransaction(Egresos::guardarEgreso), engine);
        get("/nuevo_egreso", TemplWithTransaction(Egresos::paginaNuevoEgreso), engine);
        get("/egreso/:id", TemplWithTransaction(Egresos::paginaModificarEgreso), engine);
        get("/detalle_egreso/:id", TemplWithTransaction(Egresos::paginaDetalleEgreso), engine);
        get("/egreso/:id/presupuesto", TemplWithTransaction(Egresos::paginaAgregarPresupuesto), engine);
        post("/egreso/:id/presupuesto", TemplWithTransaction(Egresos::agregarPresupuesto), engine);
        post("/egreso/:id", TemplWithTransaction(Egresos::guardarEgreso), engine);
        delete("/egreso/:id", RouteWithTransaction(Egresos::borrarEgreso));

        get("/proveedor", RouteWithTransaction(ar.edu.utn.frba.dds.Controladores.Proveedor::proveedores));

        //Licitaciones
        post("/licitacion", RouteWithTransaction(LicitacionController::crearLicitacion));
        post("/validarLicitacion", RouteWithTransaction(LicitacionController::validarLicitacion));
        get("/licitacion", RouteWithTransaction(LicitacionController::mostrarMensajes), gson::toJson);

        //Filtros
        get("/egreso/filtrar/", RouteWithTransaction(Egresos::filtrarPorCategoria));
        get("/ingreso/filtrar/", RouteWithTransaction(Ingresos::filtrarPorCategoria));

        // ===============================================================================
    }

    public static ModelAndView paginaHome(Request request, Response response, EntityManager entity) {
        // Si no hay session creada por login, me redirige a la vista de Login
        if (request.session(false) == null) {
            return Login.paginaLogin(request, response);
        } else {
            Usuario usuario = new RepoUsuarios(entity).buscarUsuario(request.session().attribute("usuario"));
            Map<String, Object> map = new HashMap<>();
            map.put("organizacion", usuario.getOrganizacion());
            return new ModelAndView(map, "home.html");
        }
    }

    public static ModelAndView redireccion(Request request, Response response){
        response.redirect("/home");
        return null;
    }

    public static int getPageSize() {
        return pageSize;
    }

    public static void setPageSize(int pageSize) {
        App.pageSize = pageSize;
    }

    private static TemplateViewRoute TemplWithTransaction(dds.WithTransaction<ModelAndView> fn) {
        TemplateViewRoute r = (req, res) -> {
            EntityManager em = entityManagerFactory.createEntityManager();
            em.getTransaction().begin();
            try {
                ModelAndView result = fn.method(req, res, em);
                em.getTransaction().commit();
                return result;
            } catch (Exception ex) {
                em.getTransaction().rollback();
                throw ex;
            }
        };
        return r;
    }
    private static Route RouteWithTransaction(dds.WithTransaction<Object> fn) {
        Route r = (req, res) -> {
            EntityManager em = entityManagerFactory.createEntityManager();
            em.getTransaction().begin();
            try {
                Object result = fn.method(req, res, em);
                em.getTransaction().commit();
                return result;
            } catch (Exception ex) {
                em.getTransaction().rollback();
                throw ex;
            }
        };
        return r;
    }
}
