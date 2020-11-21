package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.Controladores.*;
import ar.edu.utn.frba.dds.Repositorios.RepoUsuarios;

import java.util.*;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

import ar.edu.utn.frba.dds.audit.Audit;
import ar.edu.utn.frba.dds.Usuario.Usuario;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import spark.*;
import spark.template.handlebars.HandlebarsTemplateEngine;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;


public class App {
    static EntityManagerFactory entityManagerFactory;
    static Datastore datastore;
    static Morphia morphia;
    private static int pageSize = 2;
    private static Gson gson = new Gson();

    public static void main(String[] args) {

        enableDebugScreen();
        port(getHerokuAssignedPort());
        initRoutes();
        initPersistence();
        initNoSQL();

    }

    public static Datastore getDatastore() {
        return datastore;
    }

    public static void setDatastore(Datastore datastore) {
        App.datastore = datastore;
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }

    public static void initPersistence() {
        try {
            String strUri = System.getenv("JAWSDB_URL");
            if (strUri != null && !strUri.equals("")) {
                URI dbUri = new URI(strUri);
                Map<String, Object> configOverrides = new HashMap<String, Object>();
                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String dbUrl = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();
                configOverrides.put("hibernate.connection.url", dbUrl);
                configOverrides.put("hibernate.connection.username", username);
                configOverrides.put("hibernate.connection.password", password);
                entityManagerFactory = Persistence.createEntityManagerFactory("db", configOverrides);
            }else{
                entityManagerFactory = Persistence.createEntityManagerFactory("db");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initRoutes() {
        String localhostValue = System.getenv("LOCALHOST");
        boolean localhost;

        if (localhostValue != null && !"".equals(localhostValue)) {
            localhost = false;
        } else {
            localhost = true;
        }

        if (localhost) {
            String projectDir = System.getProperty("user.dir");
            String staticDir = "/src/main/resources/static/";
            staticFiles.externalLocation(projectDir + staticDir);
        } else {
            staticFiles.location("/static");
        }

        // Acceso: http://localhost:4568/login
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

        //Licitaciones
        post("/licitacion", RouteWithTransaction(LicitacionController::crearLicitacion));
        post("/validarLicitacion", RouteWithTransaction(LicitacionController::validarLicitacion));
        get("/licitacion", RouteWithTransaction(LicitacionController::mostrarMensajes), gson::toJson);

        //Audit
        get("/audit", Audit::list);

        //Initializer
        post("/initializer", RouteWithTransaction(ExampleDataCreator::inicializarSistema));
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

    public static ModelAndView redireccion(Request request, Response response) {
        response.redirect("/home");
        return null;
    }

    public static int getPageSize() {
        return pageSize;
    }

    public static void setPageSize(int pageSize) {
        App.pageSize = pageSize;
    }

    private static TemplateViewRoute TemplWithTransaction(WithTransaction<ModelAndView> fn) {
        TemplateViewRoute r = (req, res) -> {
            EntityManager em = entityManagerFactory.createEntityManager();
            em.getTransaction().begin();
            try {
                ModelAndView result = fn.method(req, res, em);
                em.getTransaction().commit();
                em.close();
                return result;
            } catch (Exception ex) {
                em.getTransaction().rollback();
                em.close();
                throw ex;
            }
        };
        return r;
    }

    private static Route RouteWithTransaction(WithTransaction<Object> fn) {
        Route r = (req, res) -> {
            EntityManager em = entityManagerFactory.createEntityManager();
            em.getTransaction().begin();
            try {
                Object result = fn.method(req, res, em);
                em.getTransaction().commit();
                em.close();
                return result;
            } catch (Exception ex) {
                em.getTransaction().rollback();
                em.close();
                throw ex;
            }
        };
        return r;
    }

    private static void initNoSQL() {
        morphia = new Morphia();
        morphia.mapPackage("ar.edu.utn.frba.dds.audit");
        MongoClientURI uri = new MongoClientURI("mongodb://root:gesoc@cluster0-shard-00-00.bdiyw.mongodb.net:27017,cluster0-shard-00-02.bdiyw.mongodb.net:27017,cluster0-shard-00-01.bdiyw.mongodb.net:27017/admin?ssl=true&replicaSet=atlas-neih4m-shard-0&readPreference=primary&connectTimeoutMS=10000&authSource=admin&authMechanism=SCRAM-SHA-1");
        MongoClient mongoClient = new MongoClient(uri);
        datastore = morphia.createDatastore(mongoClient, "gesoc");
        datastore.ensureIndexes();
    }
}
