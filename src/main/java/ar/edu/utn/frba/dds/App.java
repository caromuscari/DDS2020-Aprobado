package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.Entidad.Organizacion;
import ar.edu.utn.frba.dds.Usuario.BuilderUsuario;
import ar.edu.utn.frba.dds.Usuario.TipoPerfil;
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
    private static int limiteIntentos = 2;
    private static RepoUsuarios repoUsuarios = new RepoUsuarios();

    public static void main( String[] args ) {

        // ===============================================================================
        // Server

        enableDebugScreen();
        port(4567);
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
        get("/login", App::paginaPrueba, engine);
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
        get("/home", App::paginaHome, engine);
        get("/egreso", App::paginaEgresos, engine);
        get("/nuevo_egreso", App::nuevoEgreso, engine);
        get("/modificar_egreso", App::modificarEgreso, engine);
        get("/ingreso", App::paginaIngresos, engine);
        get("/presupuesto", App::paginaPresupuestos, engine);
        get("/vinculador", App::paginaVinculador, engine);

        // ===============================================================================
        // Consola

        Hash encriptador = new Hash();
        Scanner sn = new Scanner(System.in);

        long inicio;
        long fin;

        // Consola
        try
        {
            String usuario;
            String pass;
            int opcion;
            Usuario user;
            int intentosFallidos = 0;
            Map<String, Usuario> users = repoUsuarios.getRegistrados();

            System.out.println("");
            System.out.println("\u001B[36m" + "Bienvenido a GeSoc!" + "\u001B[0m");
            do {
                System.out.println("Elija una opción:");
                System.out.println(">> 1. Registrar Operador");
                System.out.println(">> 2. Registrar Administrador");
                System.out.println(">> 3. Iniciar Sesión");
                System.out.println(">> 4. Salir");

                opcion = sn.nextInt();

                switch(opcion) {
                    case 1: {
                        // Registrar Operador
                        System.out. println("Ingrese usuario: ");
                        usuario = sn.next();
                        System.out.println("Ingrese contraseña: ");
                        pass = sn.next();

                        BuilderUsuario nuevoOperador = new BuilderUsuario();
                        nuevoOperador.setUsuario(usuario);
                        nuevoOperador.setPassword(pass);
                        nuevoOperador.setOrganizacion(new Organizacion());
                        nuevoOperador.setPerfil(TipoPerfil.OPERADOR);
                        user = nuevoOperador.registrar();

                        users.put(usuario,user);
                        System.out.println("Operador " + usuario + " registrado exitosamente");
                        break;
                    }
                    case 2:
                        // Registrar Administrador
                        System.out.println("Ingrese usuario: ");
                        usuario = sn.next();
                        System.out.println("Ingrese contraseña: ");
                        pass = sn.next();

                        BuilderUsuario nuevoAdministador = new BuilderUsuario();
                        nuevoAdministador.setUsuario(usuario);
                        nuevoAdministador.setPassword(pass);
                        nuevoAdministador.setOrganizacion(new Organizacion());
                        nuevoAdministador.setPerfil(TipoPerfil.ADMINISTRADOR);
                        user = nuevoAdministador.registrar();

                        users.put(usuario,user);
                        System.out.println("Administrador " + usuario + " registrado exitosamente");
                        break;
                    case 3:
                        // Login.
                        System.out.println("Ingrese usuario: ");
                        usuario = sn.next();
                        System.out.println("Ingrese contraseña: ");
                        pass = sn.next();

                        if(users.get(usuario) != null) {
                            user = users.get(usuario);
                            if(encriptador.hashear(pass).equals(user.getPassword()))
                            {
                                System.out.println("\u001B[32m" + "Ha iniciado sesión. Bienvenido " + user.getUsuario() + "\u001B[0m");
                                sn.reset();
                                user.setCantidadIntentos(0);

                                System.out.println("----------TODO----------");
                            }
                            else {
                                System.out.println("\u001B[31m" + "Contraseña inválida." + "\u001B[0m");
                                if(users.get(usuario).getCantidadIntentos() < limiteIntentos) {
                                    intentosFallidos++;
                                    users.get(usuario).setCantidadIntentos(intentosFallidos);
                                    System.out.println("Intentos fallidos: " + intentosFallidos + "/" + limiteIntentos);
                                }
                                else {
                                    System.out.println("Bloqueado por 15 segundos");
                                    inicio = System.currentTimeMillis();
                                    fin = inicio + 15*1000;
                                    while(System.currentTimeMillis() < fin)
                                    {
                                        // NO hago nada -- Bloqueado --
                                    }
                                }
                            }
                        }
                        else
                        {
                            System.out.println("\u001B[31m" + "Usuario inexistente." + "\u001B[0m");
                        }
                        break;
                    default:
                        System.out.println("Inserte un número válido para seleccionar una opción");
                }

            }while (!(opcion == 4));
        }

        catch (InputMismatchException e) {
            System.out.println("Inserte un número para seleccionar una opción");
            sn.nextInt();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ModelAndView paginaPrueba(Request request, Response response) {
            Map<String, Object> map = new HashMap<>();
            return new ModelAndView(map, "login.html");
    }

    public static ModelAndView paginaHome(Request request, Response response) {

        // Si no hay session creada por login, me redirige a la vista de Login
        if(request.session(false) == null) {
            return paginaPrueba(request,response);
        }
        else{
            Map<String, Object> map = new HashMap<>();
            return new ModelAndView(map, "home.html");
        }
    }

    public static ModelAndView paginaEgresos(Request request, Response response) {
        if(request.session(false) == null) {
            return paginaPrueba(request,response);
        }
        else {
            Map<String, Object> map = new HashMap<>();
            return new ModelAndView(map, "egresos.html");
        }
    }

    public static ModelAndView nuevoEgreso(Request request, Response response) {
        Map<String, Object> map = new HashMap<>();
        return new ModelAndView(map, "nuevoEgreso.html");
    }

    public static ModelAndView modificarEgreso(Request request, Response response) {
        Map<String, Object> map = new HashMap<>();
        return new ModelAndView(map, "modificarEgreso.html");
    }

    public static ModelAndView paginaIngresos(Request request, Response response) {
        Map<String, Object> map = new HashMap<>();
        return new ModelAndView(map, "ingresos.html");
    }

    public static ModelAndView paginaPresupuestos(Request request, Response response) {
        Map<String, Object> map = new HashMap<>();
        return new ModelAndView(map, "presupuestos.html");
    }

    public static ModelAndView paginaVinculador(Request request, Response response) {
        Map<String, Object> map = new HashMap<>();
        return new ModelAndView(map, "vinculador.html");
    }

    public static boolean checkLogin(String usuario, String pass) throws NoSuchAlgorithmException {
        Usuario user = repoUsuarios.getRegistrados().get(usuario);
        return user.getPassword().equals(repoUsuarios.getEncriptador().hashear(pass));
    }
}
