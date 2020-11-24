package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.BandejaDeMendajes.Mensaje;
import ar.edu.utn.frba.dds.Categorizacion.Categoria;
import ar.edu.utn.frba.dds.Categorizacion.CriterioCategoria;
import ar.edu.utn.frba.dds.Entidad.Empresa;
import ar.edu.utn.frba.dds.Entidad.Organizacion;
import ar.edu.utn.frba.dds.Licitacion.*;
import ar.edu.utn.frba.dds.Entidad.TipoActividad;
import ar.edu.utn.frba.dds.Operaciones.*;
import ar.edu.utn.frba.dds.ResultadoLicitacion.*;
import ar.edu.utn.frba.dds.Usuario.Hash;
import ar.edu.utn.frba.dds.Usuario.TipoPerfil;
import ar.edu.utn.frba.dds.Usuario.Usuario;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExampleDataCreator {

    public static Object inicializarSistema(Request request, Response response, EntityManager entity) {
        inicializarUsuarios(entity);

        try {
            PrintWriter writer = response.raw().getWriter();
            response.header("Content-Type", "application/json");
            ObjectNode ob = JsonNodeFactory.instance.objectNode();
            ob.put("Status", "Creación exitosa.");
            writer.print(ob);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void inicializarUsuarios(EntityManager entityManager){
        Hash encriptador = new Hash();

        List<ResultadoValidacion> resultadosValidacion0 = new ArrayList<>();
        List<ResultadoValidacion> resultadosValidacion1 = new ArrayList<>();
        List<ResultadoValidacion> resultadosValidacion2 = new ArrayList<>();
        List<ResultadoValidacion> resultadosValidacion3 = new ArrayList<>();
        List<ResultadoValidacion> resultadosValidacion4 = new ArrayList<>();


        ErrorCantidadPresupuestos errorCantidadPresupuestos1 =  new ErrorCantidadPresupuestos(5, 2);
        ErrorCantidadPresupuestos errorCantidadPresupuestos2 =  new ErrorCantidadPresupuestos(7, 3);
        Licitacion licitacionPrueba =  new Licitacion("Licitacion prueba", 10);
        licitacionPrueba.agregarCriterio(new CantidadPresupuestos());
        licitacionPrueba.agregarCriterio(new MenorValor());
        ResultadoValidacion resultadoValidacion0 = new ResultadoValidacion(EstadoValidacion.OK,licitacionPrueba);
        ResultadoValidacion resultadoValidacion1 = new ResultadoValidacion(EstadoValidacion.OK,licitacionPrueba);
        ResultadoValidacion resultadoValidacion2 = new ResultadoValidacion(EstadoValidacion.OK,licitacionPrueba);
        ResultadoValidacion resultadoValidacion3 = new ResultadoValidacion(EstadoValidacion.OK,new Licitacion("Licitacion 25", 24));
        ResultadoValidacion resultadoValidacion4 = new ResultadoValidacion(EstadoValidacion.OK,new Licitacion("prueba2", 2));
        ResultadoValidacion resultadoValidacion5 = new ResultadoValidacion(EstadoValidacion.OK,new Licitacion("prueba3", 3));

        entityManager.persist(errorCantidadPresupuestos1);
        entityManager.persist(errorCantidadPresupuestos2);

        ResultadoValidacion resultadoValidacion7 = new ResultadoValidacion(EstadoValidacion.ERROR,errorCantidadPresupuestos2,licitacionPrueba);

        resultadosValidacion0.add(resultadoValidacion0);

        resultadosValidacion1.add(resultadoValidacion1);
        resultadosValidacion1.add(resultadoValidacion2);

        resultadosValidacion2.add(resultadoValidacion3);

        resultadosValidacion3.add(resultadoValidacion4);
        resultadosValidacion3.add(resultadoValidacion5);

        resultadosValidacion4.add(resultadoValidacion7);

        Mensaje mensaje1 = new Mensaje(resultadosValidacion1);
        Mensaje mensaje2 = new Mensaje(resultadosValidacion2);
        Mensaje mensaje3 = new Mensaje(resultadosValidacion3);
        Mensaje mensaje4 = new Mensaje(resultadosValidacion4);
        Mensaje mensaje5 = new Mensaje(resultadosValidacion0);

        List<Mensaje> listaMensajes1 = new ArrayList<>();
        List<Mensaje> listaMensajes2 = new ArrayList<>();
        List<Mensaje> listaMensajes3 = new ArrayList<>();

        listaMensajes1.add(mensaje1);
        listaMensajes1.add(mensaje2);
        listaMensajes1.add(mensaje3);
        listaMensajes1.add(mensaje4);
        listaMensajes1.add(mensaje5);

        Usuario usuario1;
        Usuario usuario2;
        Usuario usuario3;
        Usuario usuario4;

        try {
            usuario1 = new Usuario("pepe", encriptador.hashear("pepe"), TipoPerfil.ADMINISTRADOR, new Organizacion("Grupo MediaTek S.A","Hacemos medias"));
            usuario2 = new Usuario("carlitos", encriptador.hashear("carlitos"), TipoPerfil.ADMINISTRADOR, new Organizacion("Fabrica de helados","Hacemos helados"));
            usuario3 = new Usuario("ana", encriptador.hashear("ana"), TipoPerfil.ADMINISTRADOR, new Organizacion("Fabrica de palas S.A","Hacemos palas"));
            usuario4 = new Usuario("gesoc", encriptador.hashear("prueba"), TipoPerfil.OPERADOR, new Organizacion("Grupo 7 S.R.L", "Compañía fabless de semiconductores"));

            usuario1.setBandejaDeMensajes(listaMensajes3);
            usuario4.setBandejaDeMensajes(listaMensajes2);
            usuario4.setBandejaDeMensajes(listaMensajes1);

            crearCategorias(usuario4.getOrganizacion());
            crearCategoriasPepe(usuario1.getOrganizacion());
            inicializarOrganizacion(entityManager, usuario4.getOrganizacion());
            inicializarOrganizacionPepe(entityManager, usuario1.getOrganizacion());
            //crearCategorias(usuario2.getOrganizacion());
            //inicializarOrganizacion(entityManager, usuario2.getOrganizacion());

            entityManager.persist(usuario1);
            entityManager.persist(usuario2);
            entityManager.persist(usuario3);
            entityManager.persist(usuario4);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static void inicializarOrganizacion(EntityManager entityManager, Organizacion organizacion){
        List<Proveedor> proveedores = inicializarProveedores(entityManager);

        TipoActividad agropecuario = new TipoActividad(12890000, 48480000, 345430000,
                5, 10, 50, "Agropecuario");
        Empresa empresa1 = new Empresa("Empresa 1", "LME SRL", Long.parseLong("123456"),
                1111, 1, 1000, 10000.0, agropecuario, 1000.0);
        Empresa empresa2 = new Empresa("Empresa 2", "MercadoLibre SRL", Long.parseLong("234567"),
                1111, 1, 1000, 10000.0, agropecuario, 1000.0);

        ItemEgreso itemEgreso = new ItemEgreso("1234", "Computadora", 40.0, TipoItem.PRODUCTO, CategoriaItem.COMPUTADORA);
        ItemEgreso itemEgreso2 = new ItemEgreso("5678", "Monitor", 50.0, TipoItem.PRODUCTO, CategoriaItem.MONITOR);
        ItemEgreso itemEgreso3 = new ItemEgreso("5673", "Monitores", 30.0, TipoItem.PRODUCTO, CategoriaItem.MONITOR);
        ItemEgreso itemEgreso4 = new ItemEgreso("5671", "Computadora", 50.0, TipoItem.PRODUCTO, CategoriaItem.COMPUTADORA);
        ItemOperacionEgreso itemOperacionEgreso = new ItemOperacionEgreso(1, itemEgreso);
        ItemOperacionEgreso itemOperacionEgreso2 = new ItemOperacionEgreso(1, itemEgreso2);
        ItemOperacionEgreso itemOperacionEgreso3 = new ItemOperacionEgreso(2, itemEgreso3);
        ItemOperacionEgreso itemOperacionEgreso4 = new ItemOperacionEgreso(3, itemEgreso4);

        List<ItemOperacionEgreso> itemsOperacion1 = new ArrayList<>();
        itemsOperacion1.add(itemOperacionEgreso);
        List<ItemOperacionEgreso> itemsOperacion2 = new ArrayList<>();
        itemsOperacion2.add(itemOperacionEgreso2);
        List<ItemOperacionEgreso> itemsOperacion3 = new ArrayList<>();
        itemsOperacion3.add(itemOperacionEgreso3);
        List<ItemOperacionEgreso> itemsOperacion4 = new ArrayList<>();
        itemsOperacion4.add(itemOperacionEgreso4);

        Egreso e1 = empresa1.generarEgreso(itemsOperacion1, proveedores.get(0), "Egreso 1");
        Egreso e2 = empresa1.generarEgreso(itemsOperacion2, proveedores.get(0), "Egreso 2");
        Egreso e3 = empresa2.generarEgreso(itemsOperacion3, proveedores.get(0), "Egreso 3");
        Egreso e4 = empresa2.generarEgreso(itemsOperacion4, proveedores.get(0), "Egreso 4");



        // Tengo que setear la fecha porque en la creacion se setea LocalDate.now()
        e1.setFecha(LocalDate.of(2020, 6, 11));
        e2.setFecha(LocalDate.of(2020, 6, 30));
        e3.setFecha(LocalDate.of(2020, 6, 01));
        e4.setFecha(LocalDate.of(2020, 6, 04));


        e1.setMedioDePago(new MedioDePago("Tarjeta Macro", 100L, "CREDIT_CARD"));
        e2.setMedioDePago(new MedioDePago("Tarjeta Santander", 90L, "CREDIT_CARD"));
        e3.setMedioDePago(new MedioDePago("Tarjeta Cabal", 200L, "DEBIT_CARD"));
        e4.setMedioDePago(new MedioDePago("Tarjeta Visa", 210L, "DEBIT_CARD"));


        //creo categoria
        List<Categoria> listaCategorias = new ArrayList<>();
        Categoria categoria1 = new Categoria("Peru");

        listaCategorias.add(categoria1);

        //Creo ingresos
        Ingreso ingreso1 = new Ingreso("Ingreso 1", 100.0, LocalDate.of(2020, 6, 19));

        ingreso1.setCategorias(listaCategorias);

        Ingreso ingreso2 = new Ingreso("Ingreso 2", 200.0, LocalDate.of(2020, 10, 30));
        List<Ingreso> ingresosEmpresa1 = new ArrayList<>();
        ingresosEmpresa1.add(ingreso1);
        ingresosEmpresa1.add(ingreso2);
        empresa1.setIngresos(ingresosEmpresa1);

        Ingreso ingreso3 = new Ingreso("Ingreso 3", 150.0, LocalDate.of(2020, 6, 17));
        List<Ingreso> ingresosEmpresa2 = new ArrayList<>();
        ingresosEmpresa2.add(ingreso3);

        Ingreso ingreso4 = new Ingreso("Ingreso 4", 180.0, LocalDate.of(2020, 6, 20));
        ingresosEmpresa2.add(ingreso4);
        empresa2.setIngresos(ingresosEmpresa2);

        //Creo licitaciones y presupuestos
        Licitacion licitacion1 = new Licitacion("Licitacion 1", 3);
        Licitacion licitacion2 = new Licitacion("Licitacion 2", 3);
        ItemPresupuesto ip1 = new ItemPresupuesto(100.0, CategoriaItem.COMPUTADORA, TipoItem.PRODUCTO);
        ItemPresupuesto ip2 = new ItemPresupuesto(200.0, CategoriaItem.MONITOR, TipoItem.PRODUCTO);
        ItemPresupuesto ip3 = new ItemPresupuesto(900.0, CategoriaItem.COMPUTADORA, TipoItem.PRODUCTO);
        ItemOperacionPresupuesto iop1 = new ItemOperacionPresupuesto(2, ip1);
        ItemOperacionPresupuesto iop2 = new ItemOperacionPresupuesto(2, ip2);
        ItemOperacionPresupuesto iop3 = new ItemOperacionPresupuesto(3, ip3);
        List<ItemOperacionPresupuesto> items1 = new ArrayList<>();
        items1.add(iop1);
        items1.add(iop2);
        List<ItemOperacionPresupuesto> items2 = new ArrayList<>();
        items2.add(iop3);
        Presupuesto presupuesto1 = new Presupuesto(items1, proveedores.get(0), "Presupuesto 1");
        Presupuesto presupuesto2 = new Presupuesto(items2, proveedores.get(0), "Presupuesto 2");
        licitacion1.addPresupuesto(presupuesto1);
        licitacion2.addPresupuesto(presupuesto2);

        licitacion1.setEgreso(e1);
        licitacion2.setEgreso(e2);

        licitacion1.agregarCriterio(new CantidadPresupuestos());
        //licitacion1.agregarCriterio(new ItemsPresupuesto());


        empresa1.addLicitacion(licitacion1);
        empresa2.addLicitacion(licitacion2);

        organizacion.addEntidad(empresa1);
        organizacion.addEntidad(empresa2);

    }

    private static void inicializarOrganizacionPepe(EntityManager entityManager, Organizacion organizacion){
        List<Proveedor> proveedores = inicializarProveedoresPepe(entityManager);

        TipoActividad electrónico = new TipoActividad(12890000, 48480000, 345430000,
                5, 10, 50, "Electrónico");
        Empresa satelite = new Empresa("Empresa SATELITE", "SATELITE SRL", Long.parseLong("123456"),
                1111, 1, 1000, 10000.0, electrónico, 1000.0);
        Empresa impac = new Empresa("Empresa IMPAC", "IMPAC S.A", Long.parseLong("234567"),
                1111, 1, 1000, 10000.0, electrónico, 1000.0);

        ItemEgreso itemEgreso = new ItemEgreso("1234", "Computadora", 10.0, TipoItem.PRODUCTO, CategoriaItem.COMPUTADORA);
        ItemEgreso itemEgreso2 = new ItemEgreso("5678", "Monitor", 50.0, TipoItem.PRODUCTO, CategoriaItem.MONITOR);
        ItemOperacionEgreso itemOperacionEgreso = new ItemOperacionEgreso(1, itemEgreso);
        ItemOperacionEgreso itemOperacionEgreso2 = new ItemOperacionEgreso(2, itemEgreso2);

        List<ItemOperacionEgreso> itemsOperacion1 = new ArrayList<>();
        itemsOperacion1.add(itemOperacionEgreso);
        List<ItemOperacionEgreso> itemsOperacion2 = new ArrayList<>();
        itemsOperacion2.add(itemOperacionEgreso2);

        Egreso e1 = satelite.generarEgreso(itemsOperacion1, proveedores.get(0),"PC Intel");
        Egreso e2 = satelite.generarEgreso(itemsOperacion2, proveedores.get(0),"Monitores DELL");
        Egreso e3 = impac.generarEgreso(itemsOperacion2, proveedores.get(0),"Monitores LG");


        // Tengo que setear la fecha porque en la creacion se setea LocalDate.now()
        e1.setFecha(LocalDate.of(2020,5,18));
        e2.setFecha(LocalDate.of(2020,5,3));


        e1.setMedioDePago(new MedioDePago("Tarjeta Macro",100L,"CREDIT_CARD"));
        e2.setMedioDePago(new MedioDePago("Tarjeta Santander",90L,"CREDIT_CARD"));
        e3.setMedioDePago(new MedioDePago("Tarjeta Cabal",200L,"DEBIT_CARD"));



        //Creo ingresos
        Ingreso ingreso1 = new Ingreso("Ingreso 1",100.0, LocalDate.of(2020,6,02));


        Ingreso ingreso2 = new Ingreso("Ingreso 2",200.0, LocalDate.of(2020,10,30));
        List<Ingreso> ingresosEmpresa1 = new ArrayList<>();
        ingresosEmpresa1.add(ingreso1);
        ingresosEmpresa1.add(ingreso2);
        satelite.setIngresos(ingresosEmpresa1);

        Ingreso ingreso3 = new Ingreso("Ingreso 3",150.0, LocalDate.of(2020,6,17));
        List<Ingreso> ingresosEmpresa2 = new ArrayList<>();
        ingresosEmpresa2.add(ingreso3);

        Ingreso ingreso4 = new Ingreso("Ingreso 4",180.0, LocalDate.of(2020, 4,20));
        ingresosEmpresa2.add(ingreso4);
        impac.setIngresos(ingresosEmpresa2);

        //Creo licitaciones y presupuestos
        Licitacion licitacion1 = new Licitacion("Licitacion por computadoras",3);
        Licitacion licitacion2 = new Licitacion("Licitacion por monitores",3);
        ItemPresupuesto ip1 = new ItemPresupuesto(100.0,CategoriaItem.COMPUTADORA,TipoItem.PRODUCTO);
        ItemPresupuesto ip2 = new ItemPresupuesto(200.0,CategoriaItem.MONITOR,TipoItem.PRODUCTO);
        ItemPresupuesto ip3 = new ItemPresupuesto(900.0,CategoriaItem.COMPUTADORA,TipoItem.PRODUCTO);
        ItemOperacionPresupuesto iop1 = new ItemOperacionPresupuesto(2,ip1);
        ItemOperacionPresupuesto iop2 = new ItemOperacionPresupuesto(2,ip2);
        ItemOperacionPresupuesto iop3 = new ItemOperacionPresupuesto(3,ip3);
        List<ItemOperacionPresupuesto> items1 = new ArrayList<>();
        items1.add(iop1);
        items1.add(iop2);
        List<ItemOperacionPresupuesto> items2 = new ArrayList<>();
        items2.add(iop3);
        Presupuesto presupuesto1 = new Presupuesto(items1,proveedores.get(0),"Presupuesto 613");
        Presupuesto presupuesto2 = new Presupuesto(items2,proveedores.get(0),"Presupuesto 215");
        licitacion1.addPresupuesto(presupuesto1);
        licitacion2.addPresupuesto(presupuesto2);

        licitacion1.setEgreso(e1);
        licitacion2.setEgreso(e2);

        licitacion1.agregarCriterio(new CantidadPresupuestos());
        //licitacion1.agregarCriterio(new ItemsPresupuesto());

        satelite.addLicitacion(licitacion1);
        impac.addLicitacion(licitacion2);

        organizacion.addEntidad(satelite);
        organizacion.addEntidad(impac);

    }

    private static List<Proveedor> inicializarProveedores(EntityManager entityManager){
        Proveedor prov1 = new Proveedor("HP", 1L, "1234");
        prov1.addItem(new ItemEgreso("3482", "Computadora", 20.0, TipoItem.PRODUCTO, CategoriaItem.COMPUTADORA));
        prov1.addItem(new ItemEgreso("348", "Impresora", 20.0, TipoItem.PRODUCTO, CategoriaItem.COMPUTADORA));
        prov1.addItem(new ItemEgreso("48347", "Tinta para impresora", 20.0, TipoItem.PRODUCTO, CategoriaItem.COMPUTADORA));

        Proveedor prov2 = new Proveedor("Sony", 2L, "1234");
        prov2.addItem(new ItemEgreso("8359", "Computadora", 80.0, TipoItem.PRODUCTO, CategoriaItem.COMPUTADORA));
        prov2.addItem(new ItemEgreso("29347", "Auriculares", 20.0, TipoItem.PRODUCTO, CategoriaItem.COMPUTADORA));

        Proveedor prov3 = new Proveedor("Samsung", 21314l, "666");
        prov3.addItem(new ItemEgreso("3473", "Computadora", 60.0, TipoItem.PRODUCTO, CategoriaItem.COMPUTADORA));
        prov3.addItem(new ItemEgreso("284", "Monitor", 20.0, TipoItem.PRODUCTO, CategoriaItem.MONITOR));

        List<Proveedor> proveedores = new ArrayList<>();
        proveedores.add(prov1);
        proveedores.add(prov2);
        proveedores.add(prov3);

        entityManager.persist(prov1);
        entityManager.persist(prov2);
        entityManager.persist(prov3);

        return proveedores;
    }

    private static List<Proveedor> inicializarProveedoresPepe(EntityManager entityManager){
        Proveedor prov1 = new Proveedor("LG", 4L, "8847");
        prov1.addItem(new ItemEgreso("3482", "Monitor LG", 300.0, TipoItem.PRODUCTO, CategoriaItem.COMPUTADORA));
        prov1.addItem(new ItemEgreso("3483", "Smart TV", 600.0, TipoItem.PRODUCTO, CategoriaItem.COMPUTADORA));
        prov1.addItem(new ItemEgreso("7756", "Minicomponentes", 200.0, TipoItem.PRODUCTO, CategoriaItem.COMPUTADORA));

        Proveedor prov2 = new Proveedor("Apple", 8L, "122134");
        prov2.addItem(new ItemEgreso("8359", "iPad", 450.0, TipoItem.PRODUCTO, CategoriaItem.COMPUTADORA));
        prov2.addItem(new ItemEgreso("02303", "iMac", 900.0, TipoItem.PRODUCTO, CategoriaItem.COMPUTADORA));

        Proveedor prov3 = new Proveedor("ASUS", 213l, "66632");
        prov3.addItem(new ItemEgreso("8458", "Notebook ASUS", 450.0, TipoItem.PRODUCTO, CategoriaItem.COMPUTADORA));
        prov3.addItem(new ItemEgreso("221884", "Tarjeta Grafica", 300.0, TipoItem.PRODUCTO, CategoriaItem.MONITOR));

        List<Proveedor> proveedores = new ArrayList<>();
        proveedores.add(prov1);
        proveedores.add(prov2);
        proveedores.add(prov3);

        entityManager.persist(prov1);
        entityManager.persist(prov2);
        entityManager.persist(prov3);

        return proveedores;
    }

    public static void crearCategorias(Organizacion org) {
        Categoria america = new Categoria("America");
        Categoria asia = new Categoria("Asia");

        List<Categoria> categoriasPaises = new ArrayList<>();
        Categoria argentina = new Categoria("Argentina");
        categoriasPaises.add(argentina);
        categoriasPaises.add(new Categoria("Peru"));

        List<Categoria> categoriasContinente = new ArrayList<>();
        categoriasContinente.add(america);
        categoriasContinente.add(asia);

        CriterioCategoria criterioContinente = new CriterioCategoria("Continente", categoriasContinente);
        CriterioCategoria criterioPais = new CriterioCategoria("Pais", categoriasPaises);

        america.setCriterioHijo(criterioPais);

        org.asociarCriterio(criterioContinente);
    }

    public static void crearCategoriasPepe(Organizacion org){
        Categoria caba = new Categoria("Cap. Federal - CABA");
        Categoria pba = new Categoria("Provincia - PBA");

        List<Categoria> categoriasBarrios = new ArrayList<>();
        categoriasBarrios.add(new Categoria("La Boca"));
        categoriasBarrios.add(new Categoria("Barracas"));
        categoriasBarrios.add(new Categoria("Once"));
        categoriasBarrios.add(new Categoria("Villa Devoto"));

        List<Categoria> categoriasProvincias = new ArrayList<>();
        categoriasProvincias.add(caba);
        categoriasProvincias.add(pba);

        CriterioCategoria criterioContinente = new CriterioCategoria("Provincias", categoriasProvincias);
        CriterioCategoria criterioBarrio = new CriterioCategoria("Barrios", categoriasBarrios);

        caba.setCriterioHijo(criterioBarrio);

        org.asociarCriterio(criterioContinente);
    }
}
