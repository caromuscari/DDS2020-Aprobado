package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.Entidad.Empresa;
import ar.edu.utn.frba.dds.Licitacion.ItemOperacionPresupuesto;
import ar.edu.utn.frba.dds.Licitacion.ItemPresupuesto;
import ar.edu.utn.frba.dds.Licitacion.Licitacion;
import ar.edu.utn.frba.dds.Licitacion.Presupuesto;
import ar.edu.utn.frba.dds.Repositorios.RepositorioEntidades;
import ar.edu.utn.frba.dds.Entidad.TipoActividad;
import ar.edu.utn.frba.dds.Operaciones.*;
import ar.edu.utn.frba.dds.Repositorios.RepositorioItemsOperacionEgreso;
import ar.edu.utn.frba.dds.Repositorios.RepositorioMedioDePago;
import ar.edu.utn.frba.dds.Repositorios.RepositorioProveedores;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExampleDataCreator {

    public void createData() {
        inicializarProveedores();
        inicializarMediosDePago();
        inicializarOrganizacion();

        /*ItemPresupuesto p1 = new ItemPresupuesto(200.0, CategoriaItem.MONITOR, TipoItem.PRODUCTO);
        ItemPresupuesto p2 = new ItemPresupuesto(300.0, CategoriaItem.COMPUTADORA, TipoItem.PRODUCTO);
        ItemOperacionPresupuesto pp1 = new ItemOperacionPresupuesto(5,p1);
        ItemOperacionPresupuesto pp2 = new ItemOperacionPresupuesto(5,p2);

        DocumentoComercial prueba1 = new DocumentoComercial();
        List<DocumentoComercial> documents = new ArrayList<>();
        List<ItemOperacionPresupuesto> products = new ArrayList<>();*/

        // Egreso
        ItemEgreso e1 = new ItemEgreso("210973","Monitor", 200.0, TipoItem.PRODUCTO, CategoriaItem.MONITOR);
        ItemEgreso e2 = new ItemEgreso("274818","Computadora", 300.0, TipoItem.PRODUCTO, CategoriaItem.COMPUTADORA);
        RepositorioItemsOperacionEgreso.getInstance().crearItem(5, e1);
        RepositorioItemsOperacionEgreso.getInstance().crearItem(5, e2);

        List<ItemOperacionEgreso> compras = new ArrayList<>();

        //Usuario usuario = RepositorioUsuarios.getInstance().createUsuario(new Usuario("Julian", null, TipoUsuario.PREMIUM, "password"));

    }

    private static void inicializarOrganizacion(){
        TipoActividad agropecuario = new TipoActividad(12890000, 48480000, 345430000,
                5, 10, 50, "Agropecuario");
        Empresa empresa1 = new Empresa("Empresa 1", "LME SRL", Long.parseLong("123456"),
                1111, 1, 1000, 10000.0, agropecuario, 1000.0);
        Empresa empresa2 = new Empresa("Empresa 2", "LME SRL", Long.parseLong("234567"),
                1111, 1, 1000, 10000.0, agropecuario, 1000.0);

        ItemEgreso itemEgreso = new ItemEgreso("1234", "item 1", 10.0, TipoItem.PRODUCTO, CategoriaItem.COMPUTADORA);
        ItemEgreso itemEgreso2 = new ItemEgreso("5678", "item 2", 50.0, TipoItem.PRODUCTO, CategoriaItem.COMPUTADORA);
        ItemOperacionEgreso itemOperacionEgreso = new ItemOperacionEgreso(1, itemEgreso);
        ItemOperacionEgreso itemOperacionEgreso2 = new ItemOperacionEgreso(2, itemEgreso2);

        List<ItemOperacionEgreso> itemsOperacion1 = new ArrayList<>();
        itemsOperacion1.add(itemOperacionEgreso);
        List<ItemOperacionEgreso> itemsOperacion2 = new ArrayList<>();
        itemsOperacion2.add(itemOperacionEgreso2);

        empresa1.generarEgreso(itemsOperacion1, RepositorioProveedores.getInstance().obtenerProveedores().get(0),"Egreso 1");
        empresa1.generarEgreso(itemsOperacion2, RepositorioProveedores.getInstance().obtenerProveedores().get(0),"Egreso 2");
        RepositorioEntidades.getInstance().crearEntidad(empresa1);
        RepositorioEntidades.getInstance().crearEntidad(empresa2);

        //Creo ingresos
        Ingreso ingreso1 = new Ingreso("Ingreso 1",100.0, LocalDate.now());
        Ingreso ingreso2 = new Ingreso("Ingreso 2",200.0, LocalDate.now());
        List<Ingreso> ingresosEmpresa1 = new ArrayList<>();
        ingresosEmpresa1.add(ingreso1);
        ingresosEmpresa1.add(ingreso2);
        empresa1.setIngresos(ingresosEmpresa1);

        Ingreso ingreso3 = new Ingreso("Ingreso 3",150.0, LocalDate.now());
        List<Ingreso> ingresosEmpresa2 = new ArrayList<>();
        ingresosEmpresa2.add(ingreso3);
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
        Presupuesto presupuesto1 = new Presupuesto(items1,RepositorioProveedores.getInstance().obtenerProveedores().get(0),"Presupuesto 1");
        Presupuesto presupuesto2 = new Presupuesto(items2,RepositorioProveedores.getInstance().obtenerProveedores().get(0),"Presupuesto 2");
        licitacion1.setPresupuestos(Arrays.asList(presupuesto1));
        licitacion2.setPresupuestos(Arrays.asList(presupuesto2));
        RepositorioEntidades.getInstance().obtenerEntidades().get(0).setLicitaciones(Arrays.asList(licitacion1));
        RepositorioEntidades.getInstance().obtenerEntidades().get(1).setLicitaciones(Arrays.asList(licitacion2));
    }

    private static void inicializarMediosDePago(){
        RepositorioMedioDePago.getInstance().crearMedioDePago("Tarjeta credito",100L,TipoPago.CREDIT_CARD);
        RepositorioMedioDePago.getInstance().crearMedioDePago("Tarjeta debito",200L,TipoPago.CREDIT_CARD);
    }

    private static void inicializarProveedores(){
        RepositorioProveedores.getInstance().crearProveedor("Proveedor 1", 1L, "1234");
        RepositorioProveedores.getInstance().crearProveedor("Proveedor 2", 2L, "1234");
        RepositorioProveedores.getInstance().crearProveedor("Alejandro", 21314l, "666");
    }
}
