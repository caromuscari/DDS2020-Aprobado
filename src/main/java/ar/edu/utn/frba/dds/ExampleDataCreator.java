package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.BandejaDeMendajes.Mensaje;
import ar.edu.utn.frba.dds.Categorizacion.Categoria;
import ar.edu.utn.frba.dds.Categorizacion.CriterioCategoria;
import ar.edu.utn.frba.dds.Entidad.Empresa;
import ar.edu.utn.frba.dds.Entidad.Entidad;
import ar.edu.utn.frba.dds.Entidad.Organizacion;
import ar.edu.utn.frba.dds.Excepciones.UsuarioExistsException;
import ar.edu.utn.frba.dds.Licitacion.ItemOperacionPresupuesto;
import ar.edu.utn.frba.dds.Licitacion.ItemPresupuesto;
import ar.edu.utn.frba.dds.Licitacion.Licitacion;
import ar.edu.utn.frba.dds.Licitacion.Presupuesto;
import ar.edu.utn.frba.dds.Repositorios.*;
import ar.edu.utn.frba.dds.Entidad.TipoActividad;
import ar.edu.utn.frba.dds.Operaciones.*;
import ar.edu.utn.frba.dds.ResultadoLicitacion.EstadoValidacion;
import ar.edu.utn.frba.dds.ResultadoLicitacion.ResultadoValidacion;
import ar.edu.utn.frba.dds.Usuario.Hash;
import ar.edu.utn.frba.dds.Usuario.TipoPerfil;
import ar.edu.utn.frba.dds.Usuario.Usuario;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExampleDataCreator {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("db");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        inicializarUsuarios(entityManager);

//        List<Entidad> entidades = repoEntidades.obtenerEntidades();
//        List<Usuario> usuarios = repoUsuarios.getRegistrados();


        //repoUsuarios.buscarUsuario("gesoc").getOrganizacion().setEntidades(entidades);

        /*ItemPresupuesto p1 = new ItemPresupuesto(200.0, CategoriaItem.MONITOR, TipoItem.PRODUCTO);
        ItemPresupuesto p2 = new ItemPresupuesto(300.0, CategoriaItem.COMPUTADORA, TipoItem.PRODUCTO);
        ItemOperacionPresupuesto pp1 = new ItemOperacionPresupuesto(5,p1);
        ItemOperacionPresupuesto pp2 = new ItemOperacionPresupuesto(5,p2);

        DocumentoComercial prueba1 = new DocumentoComercial();
        List<DocumentoComercial> documents = new ArrayList<>();
        List<ItemOperacionPresupuesto> products = new ArrayList<>();*/

        // Egreso
//        ItemEgreso e1 = new ItemEgreso("210973","Monitor", 200.0, TipoItem.PRODUCTO, CategoriaItem.MONITOR);
//        ItemEgreso e2 = new ItemEgreso("274818","Computadora", 300.0, TipoItem.PRODUCTO, CategoriaItem.COMPUTADORA);
//        repoItemsOperacionEgreso.crearItem(5, e1);
//        repoItemsOperacionEgreso.crearItem(5, e2);
//
//        List<ItemOperacionEgreso> compras = new ArrayList<>();

    }

    private static void inicializarOrganizacion(EntityManager entityManager, Organizacion organizacion){
        List<Proveedor> proveedores = inicializarProveedores(entityManager);

        TipoActividad agropecuario = new TipoActividad(12890000, 48480000, 345430000,
                5, 10, 50, "Agropecuario");
        Empresa empresa1 = new Empresa("Empresa 1", "LME SRL", Long.parseLong("123456"),
                1111, 1, 1000, 10000.0, agropecuario, 1000.0);
        Empresa empresa2 = new Empresa("Empresa 2", "MercadoLibre SRL", Long.parseLong("234567"),
                1111, 1, 1000, 10000.0, agropecuario, 1000.0);

        ItemEgreso itemEgreso = new ItemEgreso("1234", "Computadora", 10.0, TipoItem.PRODUCTO, CategoriaItem.COMPUTADORA);
        ItemEgreso itemEgreso2 = new ItemEgreso("5678", "Monitor", 50.0, TipoItem.PRODUCTO, CategoriaItem.MONITOR);
        ItemOperacionEgreso itemOperacionEgreso = new ItemOperacionEgreso(1, itemEgreso);
        ItemOperacionEgreso itemOperacionEgreso2 = new ItemOperacionEgreso(2, itemEgreso2);

        List<ItemOperacionEgreso> itemsOperacion1 = new ArrayList<>();
        itemsOperacion1.add(itemOperacionEgreso);
        List<ItemOperacionEgreso> itemsOperacion2 = new ArrayList<>();
        itemsOperacion2.add(itemOperacionEgreso2);

        Egreso e1 = empresa1.generarEgreso(itemsOperacion1, proveedores.get(0),"Egreso 1");
        Egreso e2 = empresa1.generarEgreso(itemsOperacion2, proveedores.get(0),"Egreso 2");
        Egreso e3 = empresa2.generarEgreso(itemsOperacion2, proveedores.get(0),"Egreso 3");

        e1.setMedioDePago(new MedioDePago("Tarjeta Macro",100L,"CREDIT_CARD"));
        e2.setMedioDePago(new MedioDePago("Tarjeta Santander",90L,"CREDIT_CARD"));
        e3.setMedioDePago(new MedioDePago("Tarjeta Cabal",200L,"DEBIT_CARD"));

        //creo categoria
        List<Categoria> listaCategorias = new ArrayList<>();
        Categoria categoria1 = new Categoria("Peru");

        listaCategorias.add(categoria1);

        //Creo ingresos
        Ingreso ingreso1 = new Ingreso("Ingreso 1",100.0, LocalDate.now());

        ingreso1.setCategorias(listaCategorias);

        Ingreso ingreso2 = new Ingreso("Ingreso 2",200.0, LocalDate.now());
        List<Ingreso> ingresosEmpresa1 = new ArrayList<>();
        ingresosEmpresa1.add(ingreso1);
        ingresosEmpresa1.add(ingreso2);
        empresa1.setIngresos(ingresosEmpresa1);

        Ingreso ingreso3 = new Ingreso("Ingreso 3",150.0, LocalDate.now());
        List<Ingreso> ingresosEmpresa2 = new ArrayList<>();
        ingresosEmpresa2.add(ingreso3);

        Ingreso ingreso4 = new Ingreso("Ingreso 4",180.0, LocalDate.of(2020, 2,20));
        ingresosEmpresa2.add(ingreso4);
        empresa2.setIngresos(ingresosEmpresa2);

        //Creo licitaciones y presupuestos
        Licitacion licitacion1 = new Licitacion("Licitacion 1",3);
        Licitacion licitacion2 = new Licitacion("Licitacion 2",3);
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
        Presupuesto presupuesto1 = new Presupuesto(items1,new Proveedor("HP", 1L, "1234"),"Presupuesto 1");
        Presupuesto presupuesto2 = new Presupuesto(items2,new Proveedor("HP", 1L, "1234"),"Presupuesto 2");
//        licitacion1.addPresupuesto(presupuesto1);
//        licitacion2.addPresupuesto(presupuesto2);

        empresa1.addLicitacion(licitacion1);
        empresa2.addLicitacion(licitacion2);

        organizacion.addEntidad(empresa1);
        organizacion.addEntidad(empresa2);

    }

    private static List<Proveedor> inicializarProveedores(EntityManager entityManager){
        Proveedor prov1 = new Proveedor("HP", 1L, "1234");
        prov1.addItem(new ItemEgreso("3482","Computadora",20.0,TipoItem.PRODUCTO,CategoriaItem.COMPUTADORA));
        prov1.addItem(new ItemEgreso("348","Impresora",20.0,TipoItem.PRODUCTO,CategoriaItem.COMPUTADORA));
        prov1.addItem(new ItemEgreso("48347","Tinta para impresora",20.0,TipoItem.PRODUCTO,CategoriaItem.COMPUTADORA));

        Proveedor prov2 = new Proveedor("Sony", 2L, "1234");
        prov2.addItem(new ItemEgreso("8359","Computadora",80.0,TipoItem.PRODUCTO,CategoriaItem.COMPUTADORA));
        prov2.addItem(new ItemEgreso("29347","Auriculares",20.0,TipoItem.PRODUCTO,CategoriaItem.COMPUTADORA));

        Proveedor prov3 = new Proveedor("Samsung", 21314l, "666");
        prov3.addItem(new ItemEgreso("3473","Computadora",60.0,TipoItem.PRODUCTO,CategoriaItem.COMPUTADORA));
        prov3.addItem(new ItemEgreso("284","Monitor",20.0,TipoItem.PRODUCTO,CategoriaItem.MONITOR));

        List<Proveedor> proveedores = new ArrayList<>();
        proveedores.add(prov1);
        proveedores.add(prov2);
        proveedores.add(prov3);

        entityManager.getTransaction().begin();
        entityManager.persist(prov1);
        entityManager.persist(prov2);
        entityManager.persist(prov3);
        entityManager.getTransaction().commit();

        return proveedores;
    }

    private static void inicializarUsuarios(EntityManager entityManager){
        Hash encriptador = new Hash();

        List<ResultadoValidacion> resultadosValidacion1 = new ArrayList<>();
        List<ResultadoValidacion> resultadosValidacion2 = new ArrayList<>();

        ResultadoValidacion ResultadoValidacion1 = new ResultadoValidacion(EstadoValidacion.OK,new Licitacion("Licitacion 23", 2));
        ResultadoValidacion ResultadoValidacion2 = new ResultadoValidacion(EstadoValidacion.OK,new Licitacion("Licitacion 24", 23));
        ResultadoValidacion ResultadoValidacion3 = new ResultadoValidacion(EstadoValidacion.OK,new Licitacion("Licitacion 25", 24));

        resultadosValidacion1.add(ResultadoValidacion1);
        resultadosValidacion1.add(ResultadoValidacion2);
        resultadosValidacion1.add(ResultadoValidacion3);

        resultadosValidacion2.add(ResultadoValidacion2);
        resultadosValidacion2.add(ResultadoValidacion3);

        Mensaje mensaje1 = new Mensaje(resultadosValidacion1);
        Mensaje mensaje2 = new Mensaje(resultadosValidacion1);
        Mensaje mensaje3 = new Mensaje(resultadosValidacion2);

        List<Mensaje> listaMensajes1 = new ArrayList<>();
        List<Mensaje> listaMensajes2 = new ArrayList<>();

        listaMensajes1.add(mensaje1);
        listaMensajes1.add(mensaje2);
        listaMensajes1.add(mensaje3);

        listaMensajes2.add(mensaje1);
        listaMensajes2.add(mensaje2);

        Usuario usuario1;
        Usuario usuario2;
        Usuario usuario3;
        Usuario usuario4;

        try {
            usuario1 = new Usuario("pepe", encriptador.hashear("pepe"), TipoPerfil.ADMINISTRADOR, new Organizacion("Fabrica de medias","Hacemos medias"));
            usuario2 = new Usuario("carlitos", encriptador.hashear("carlitos"), TipoPerfil.ADMINISTRADOR, new Organizacion("Fabrica de helados","Hacemos helados"));
            usuario3 = new Usuario("ana", encriptador.hashear("ana"), TipoPerfil.ADMINISTRADOR, new Organizacion("Fabrica de palas","Hacemos palas"));
            usuario4 = new Usuario("gesoc", encriptador.hashear("prueba"), TipoPerfil.OPERADOR, new Organizacion("Grupo 7 S.R.L", "Compañía fabless de semiconductores"));

            usuario1.setBandejaDeMensajes(listaMensajes1);
            usuario2.setBandejaDeMensajes(listaMensajes2);
            usuario3.setBandejaDeMensajes(listaMensajes2);
            usuario4.setBandejaDeMensajes(listaMensajes1);

            crearCategorias(usuario4.getOrganizacion());
            inicializarOrganizacion(entityManager, usuario4.getOrganizacion());

            entityManager.getTransaction().begin();
            entityManager.persist(usuario1);
            entityManager.persist(usuario2);
            entityManager.persist(usuario3);
            entityManager.persist(usuario4);
            entityManager.getTransaction().commit();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void crearCategorias(Organizacion org){
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
}
