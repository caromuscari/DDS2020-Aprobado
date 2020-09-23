package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.Controladores.Egreso;
import ar.edu.utn.frba.dds.Controladores.Login;
import ar.edu.utn.frba.dds.Entidad.*;
import ar.edu.utn.frba.dds.Licitacion.Licitacion;
import ar.edu.utn.frba.dds.Licitacion.Presupuesto;
import ar.edu.utn.frba.dds.Operaciones.*;
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
    private static Organizacion organizacion = new Organizacion();
    private static List<MedioDePago> medioDePagos = new ArrayList<>();
    private static List<Proveedor> proveedores = new ArrayList<>();

    public static Organizacion getOrganizacion() {
        return organizacion;
    }

    public static void setOrganizacion(Organizacion organizacion) {
        App.organizacion = organizacion;
    }

    public static List<MedioDePago> getMedioDePagos() {
        return medioDePagos;
    }

    public static void setMedioDePagos(List<MedioDePago> medioDePagos) {
        App.medioDePagos = medioDePagos;
    }

    public static List<Proveedor> getProveedores() {
        return proveedores;
    }

    public static void setProveedores(List<Proveedor> proveedores) {
        App.proveedores = proveedores;
    }

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
        get("/egreso", Egreso::paginaEgresos, engine);
        get("/nuevo_egreso", Egreso::paginaNuevoEgreso, engine);
        get("/modificar_egreso", Egreso::paginaModificarEgreso, engine);
        // ===============================================================================
        Hash encriptador = new Hash();
        Scanner sn = new Scanner(System.in);

        //Inicializar datos de prueba
        inicializarProveedores();
        inicializarMediosDePago();
        inicializarOrganizacion();

        // Consola
        long inicio;
        long fin;
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

    private static void inicializarOrganizacion(){
        TipoActividad agropecuario = new TipoActividad(12890000, 48480000, 345430000,
                5, 10, 50, "Agropecuario");
        Empresa empresa1 = new Empresa("Empresa 1", "LME SRL", Long.parseLong("123456"),
                1111, 1, 1000, 10000.0, agropecuario, 1000.0);
        Empresa empresa2 = new Empresa("Empresa 2", "LME SRL", Long.parseLong("234567"),
                1111, 1, 1000, 10000.0, agropecuario, 1000.0);
        List<Entidad> entidades = new ArrayList<>();
        entidades.add(empresa1);
        entidades.add(empresa2);
        organizacion.setEntidades(entidades);

        ItemEgreso itemEgreso = new ItemEgreso("1234", "item 1", 10.0, TipoItem.PRODUCTO, CategoriaItem.COMPUTADORA);
        ItemEgreso itemEgreso2 = new ItemEgreso("5678", "item 2", 50.0, TipoItem.PRODUCTO, CategoriaItem.COMPUTADORA);
        ItemOperacionEgreso itemOperacionEgreso = new ItemOperacionEgreso(1, itemEgreso);
        ItemOperacionEgreso itemOperacionEgreso2 = new ItemOperacionEgreso(2, itemEgreso2);

        List<ItemOperacionEgreso> itemsOperacion1 = new ArrayList<>();
        itemsOperacion1.add(itemOperacionEgreso);
        List<ItemOperacionEgreso> itemsOperacion2 = new ArrayList<>();
        itemsOperacion2.add(itemOperacionEgreso2);

        empresa1.generarEgreso(itemsOperacion1, proveedores.get(0),"Egreso 1");
        empresa1.generarEgreso(itemsOperacion2, proveedores.get(0),"Egreso 2");
    }

    private static void inicializarMediosDePago(){
        MedioDePago medio1 = new MedioDePago("Tarjeta credito",100L,TipoPago.CREDIT_CARD);
        MedioDePago medio2 = new MedioDePago("Tarjeta debito",200L,TipoPago.CREDIT_CARD);
        medioDePagos.add(medio1);
        medioDePagos.add(medio2);
    }

    private static void inicializarProveedores(){
        Proveedor proveedor1 = new Proveedor("Proveedor 1", 1L,"1234");
        Proveedor proveedor2 = new Proveedor("Proveedor 2", 2L,"1234");
        proveedores.add(proveedor1);
        proveedores.add(proveedor2);
    }

}
