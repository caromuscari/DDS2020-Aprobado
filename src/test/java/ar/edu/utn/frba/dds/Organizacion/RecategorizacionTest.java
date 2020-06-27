package ar.edu.utn.frba.dds.Organizacion;

import ar.edu.utn.frba.dds.Entidad.*;
import org.junit.Assert;
import org.junit.Test;

public class RecategorizacionTest {

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
    public void recategorizacionEmpresas() {

        Empresa empresa = new Empresa("La mejor empresa", "LME SRL", Long.parseLong("11123456789"),
                1111, 1, 4, 10000.0, agropecuario, 1000.0);

        empresa.setVentasPromedio(15000000.0);

        Recategorizador.getInstance().recategorizarEmpresas();

        Assert.assertEquals(TipoEmpresa.Pequeña, empresa.getTipoEmpresa());
    }

    @Test
    public void historialRecategorizacionEmpresas() {

        Empresa empresa = new Empresa("La mejor empresa", "LME SRL", Long.parseLong("11123456789"),
                1111, 1, 4, 10000.0, agropecuario, 1000.0);

        empresa.setVentasPromedio(15000000.0);

        Recategorizador.getInstance().recategorizarEmpresas();

        Assert.assertEquals(1, empresa.getHistorial().size());
    }

}
