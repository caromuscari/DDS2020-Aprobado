package ar.edu.utn.frba.dds.Organizacion;

import ar.edu.utn.frba.dds.Egreso.*;
import ar.edu.utn.frba.dds.Entidad.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class OrganizacionTest {

    @Test
    public void egresosTest() {
        Organizacion organizacion = new Organizacion();

        Empresa empresa = new Empresa("La mejor empresa", "LME SRL", Long.parseLong("11123456789"),
                1111, 1, 1000, 10000.0, TipoActividad.AGROPECUARIO, 1000.0);
        EntidadBase entidadBase = new EntidadBase("La mejor entidad base", "Soy una descripcion", empresa);

        List<Entidad> entidades = new ArrayList<>();
        entidades.add(entidadBase);
        entidades.add(empresa);
        organizacion.setEntidades(entidades);

        Item item = new Item("1234", "item 1", 10.0, TipoItem.PRODUCTO);
        Item item2 = new Item("5678", "item 2", 50.0, TipoItem.PRODUCTO);
        ItemOperacion itemOperacion = new ItemOperacion(1, item);
        ItemOperacion itemOperacion2 = new ItemOperacion(2, item2);

        List<ItemOperacion> itemsOperacion1 = new ArrayList<>();
        itemsOperacion1.add(itemOperacion);
        List<ItemOperacion> itemsOperacion2 = new ArrayList<>();
        itemsOperacion2.add(itemOperacion2);
        Proveedor proveedor = new Proveedor("Provedor 1", Long.parseLong("123"), "1234");

        entidadBase.generarEgreso(itemsOperacion1, proveedor);
        empresa.generarEgreso(itemsOperacion2, proveedor);

        Assert.assertEquals(Double.valueOf(110.0), organizacion.obtenerEgresos());

    }

    @Test
    public void tipoEmpresaTest() {
        Empresa empresa = new Empresa("La mejor empresa", "LME SRL", Long.parseLong("11123456789"),
                1111, 1, 1000, 10000.0, TipoActividad.AGROPECUARIO, 1000.0);

        Assert.assertEquals(MicroEmpresa.class, empresa.getTipoEmpresa().getClass());
    }

    @Test
    public void recategorizarTest() {
        Empresa empresa = new Empresa("La mejor empresa", "LME SRL", Long.parseLong("11123456789"),
                1111, 1, 1000, 10000.0, TipoActividad.AGROPECUARIO, 1000.0);

        empresa.setVentasPromedio(15000000.0);
        empresa.recategorizar();

        Assert.assertEquals(PequenaEmpresa.class, empresa.getTipoEmpresa().getClass());
    }

}
