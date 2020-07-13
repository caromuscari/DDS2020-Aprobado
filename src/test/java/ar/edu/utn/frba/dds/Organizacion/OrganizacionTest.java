package ar.edu.utn.frba.dds.Organizacion;

import ar.edu.utn.frba.dds.Operaciones.*;
import ar.edu.utn.frba.dds.Entidad.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class OrganizacionTest {

    private TipoActividad construccion = new TipoActividad(15230000, 90310000, 503880000,
            12, 45, 200, "Construccion");
    private TipoActividad servicios = new TipoActividad(8500000, 50950000, 425170000,
            7, 30, 165, "Servicios");
    private TipoActividad comercio = new TipoActividad(29740000, 178860000, 1502750000,
            7, 35, 125, "Comercio");
    private TipoActividad industriaYMineria = new TipoActividad(26540000, 190410000, 1190330000,
            15, 60, 235, "Industria y Mineria");
    private TipoActividad agropecuario = new TipoActividad(12890000, 48480000, 345430000,
            5, 10, 50, "Agropecuario");

    @Test
    public void egresosTest() {
        Organizacion organizacion = new Organizacion();

        Empresa empresa = new Empresa("La mejor empresa", "LME SRL", Long.parseLong("11123456789"),
                1111, 1, 1000, 10000.0, agropecuario, 1000.0);
        EntidadBase entidadBase = new EntidadBase("La mejor entidad base", "Soy una descripcion", empresa);

        List<Entidad> entidades = new ArrayList<>();
        entidades.add(entidadBase);
        entidades.add(empresa);
        organizacion.setEntidades(entidades);

        ItemEgreso itemEgreso = new ItemEgreso("1234", "item 1", 10.0, TipoItem.PRODUCTO, CategoriaItem.COMPUTADORA);
        ItemEgreso itemEgreso2 = new ItemEgreso("5678", "item 2", 50.0, TipoItem.PRODUCTO, CategoriaItem.COMPUTADORA);
        ItemOperacionEgreso itemOperacionEgreso = new ItemOperacionEgreso(1, itemEgreso);
        ItemOperacionEgreso itemOperacionEgreso2 = new ItemOperacionEgreso(2, itemEgreso2);

        List<ItemOperacionEgreso> itemsOperacion1 = new ArrayList<>();
        itemsOperacion1.add(itemOperacionEgreso);
        List<ItemOperacionEgreso> itemsOperacion2 = new ArrayList<>();
        itemsOperacion2.add(itemOperacionEgreso2);
        Proveedor proveedor = new Proveedor("Provedor 1", Long.parseLong("123"), "1234");

        entidadBase.generarEgreso(itemsOperacion1, proveedor);
        empresa.generarEgreso(itemsOperacion2, proveedor);

        Assert.assertEquals(Double.valueOf(110.0), organizacion.obtenerTotalEgresos());

    }

    @Test
    public void tipoEmpresaTest() {
        Empresa empresa = new Empresa("La mejor empresa", "LME SRL", Long.parseLong("11123456789"),
                1111, 1, 4, 10000.0, agropecuario, 1000.0);

        Assert.assertEquals(TipoEmpresa.Micro, empresa.getTipoEmpresa());
    }

    @Test
    public void recategorizarEmpresaTest() {
        Empresa empresa = new Empresa("La mejor empresa", "LME SRL", Long.parseLong("11123456789"),
                1111, 1, 4, 10000.0, agropecuario, 1000.0);

        empresa.setVentasPromedio(15000000.0);
        empresa.recategorizar();

        Assert.assertEquals(TipoEmpresa.Pequeña, empresa.getTipoEmpresa());
    }

}
