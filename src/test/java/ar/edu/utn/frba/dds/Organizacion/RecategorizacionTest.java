package ar.edu.utn.frba.dds.Organizacion;

import ar.edu.utn.frba.dds.Egreso.Item;
import ar.edu.utn.frba.dds.Egreso.ItemOperacion;
import ar.edu.utn.frba.dds.Egreso.Proveedor;
import ar.edu.utn.frba.dds.Egreso.TipoItem;
import ar.edu.utn.frba.dds.Entidad.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RecategorizacionTest {

    @Test
    public void recategorizacionEmpresas() {

        Empresa empresa = new Empresa("La mejor empresa", "LME SRL", Long.parseLong("11123456789"),
                1111, 1, 1000, 10000.0, TipoActividad.AGROPECUARIO, 1000.0);

        empresa.setVentasPromedio(15000000.0);

        Recategorizador.getInstance().recategorizarEmpresas();

        Assert.assertEquals(TipoEmpresa.Pequeña, empresa.getTipoEmpresa());
    }

    @Test
    public void historialRecategorizacionEmpresas() {

        Empresa empresa = new Empresa("La mejor empresa", "LME SRL", Long.parseLong("11123456789"),
                1111, 1, 1000, 10000.0, TipoActividad.AGROPECUARIO, 1000.0);

        empresa.setVentasPromedio(15000000.0);

        Recategorizador.getInstance().recategorizarEmpresas();

        Assert.assertEquals(1, empresa.getHistorial().size());
    }

}
