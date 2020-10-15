package ar.edu.utn.frba.dds.Vinculador;

import ar.edu.utn.frba.dds.Entidad.Empresa;
import ar.edu.utn.frba.dds.Entidad.TipoActividad;
import ar.edu.utn.frba.dds.Operaciones.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VinculadorTest {
    ItemEgreso item1, item2, item3;
    ItemOperacionEgreso itemOp1, itemOp2;
    List<ItemOperacionEgreso> itemsOperacion, itemsOperacion2;
    Ingreso i1, i2, i3;
    Egreso e1, e2;
    List<Egreso> egresos;
    List<Ingreso> ingresos;
    Vinculador vinculador;
    List<CondicionVinculacion> condiciones;
    CondicionFecha condicionFecha;
    Empresa empresa;

    // Criterios de vinculacion
    Fecha fecha;
    PrimeroIngreso primeroIngreso;
    PrimeroEgreso primeroEgreso;
    Mix mix;


    private TipoActividad agropecuario = new TipoActividad(12890000, 48480000, 345430000,
            5, 10, 50, "Agropecuario");

    @Before
    public void init() {

        //Operaciones
        i1 = new Ingreso("Venta Servicio", 200.0, LocalDate.of(2020,05,30));
        i2 = new Ingreso("Venta Servicio", 350.0, LocalDate.of(2020,04,23));
        i3 = new Ingreso("Venta VALIDA", 330.5, LocalDate.of(2020,06,14));
        item1= new ItemEgreso("274818","Computadora", 310.0, TipoItem.PRODUCTO, CategoriaItem.COMPUTADORA);
        item2= new ItemEgreso("274816","Computadora", 300.0, TipoItem.PRODUCTO, CategoriaItem.COMPUTADORA);
        item3= new ItemEgreso("274817","Monitor", 250.0, TipoItem.PRODUCTO, CategoriaItem.MONITOR);

        itemOp1 = new ItemOperacionEgreso(1,item1);
        itemsOperacion = new ArrayList<>();
        itemsOperacion.add(itemOp1);
        e1 = new Egreso(itemsOperacion, null,"Egreso"); // Proveedor no me interesa

        ingresos = new ArrayList<>();
        egresos = new ArrayList<>();

        //Vinculador y condiciones
        condicionFecha = new CondicionFecha();
        condicionFecha.setFinPeriodo(LocalDate.parse("2020-04-15"));
        condicionFecha.setInicioPeriodo(LocalDate.parse("2020-05-15"));
        condiciones = new ArrayList<>();
        condiciones.add(condicionFecha);

        // Entidad
        empresa = new Empresa("La mejor empresa", "LME SRL", Long.parseLong("11123456789"),
                1111, 1, 1000, 10000.0, agropecuario, 1000.0);
        empresa.setEgresos(egresos);
        empresa.setIngresos(ingresos);
    }

    @Test
    public void vinculacionXPrimeroEgreso(){

        e1.setFecha(LocalDate.of(2020,06, 20));
        egresos.add(e1);
        ingresos.add(i3);

        primeroEgreso = new PrimeroEgreso(TipoOrden.ORDENDESCENDENTE);
        primeroEgreso.setCondiciones(condiciones);
        primeroEgreso.vincular(empresa);
        Assert.assertEquals(empresa.getIngresos().get(0).montoTotalEgresos(),e1.getPrecioTotal());

        // El Ingreso i3 es el que valido para la vinculacion
    }

    @Test
    public void vinculacionXPrimeroEgresoFallida(){

        e1.setFecha(LocalDate.of(2020,6, 20));
        egresos.add(e1);
        ingresos.add(i2);
        ingresos.add(i3);

        primeroEgreso = new PrimeroEgreso(TipoOrden.ORDENDESCENDENTE);
        primeroEgreso.setCondiciones(condiciones);
        primeroEgreso.vincular(empresa);
        Assert.assertNotEquals(empresa.getIngresos().get(1).montoTotalEgresos(),e1.getPrecioTotal());

        // El Ingreso i3 es el que valido para la vinculacion pero el matcheo es el primer egreso con el primer ingreso
        // el segundo egreso con el primer ingreso y asi sucesivamente y solo hay un egreso.
    }

    @Test
    public void vinculacionXPrimeroIngreso(){

        e1.setFecha(LocalDate.of(2020,04, 17));
        egresos.add(e1);
        ingresos.add(i1);
        ingresos.add(i2);

        primeroIngreso = new PrimeroIngreso(TipoOrden.ORDENDESCENDENTE);
        primeroIngreso.setCondiciones(condiciones);
        primeroIngreso.vincular(empresa);
        Assert.assertEquals(i2.montoTotalEgresos(),e1.getPrecioTotal());

        // El Ingreso i2 es valido para la vinculacion con el egreso. Cambiando de orden a Ascendente,
        // el test falla (Demuestra que ordena correctamente)
    }

    @Test
    public void vinculacionXPrimeroIngresoFallido(){

        e1.setFecha(LocalDate.of(2020,04, 17));
        egresos.add(e1);
        ingresos.add(i1);
        ingresos.add(i2);

        primeroIngreso = new PrimeroIngreso(TipoOrden.ORDENASCENDENTE);
        primeroIngreso.setCondiciones(condiciones);
        primeroIngreso.vincular(empresa);
        Assert.assertNotEquals(empresa.getIngresos().get(1).montoTotalEgresos(),e1.getPrecioTotal());

        // El Ingreso i2 es valido para la vinculacion con el egreso pero solo va iterar una vez porque un
        // egreso no puede estar asignado a mas de un ingreso
    }

    @Test
    public void vinculacionXFecha(){

        // Agrego un nuevo egreso
        itemsOperacion2 = new ArrayList<>();
        itemOp2 = new ItemOperacionEgreso(1,item2);
        itemsOperacion2.add(itemOp2);
        e2 = new Egreso(itemsOperacion2,null,"Egreso");

        e1.setFecha(LocalDate.of(2020,05, 17));
        e2.setFecha(LocalDate.of(2020,04, 19));
        egresos.add(e1);
        egresos.add(e2);
        ingresos.add(i2); // 350
        ingresos.add(i1); // 200

        fecha = new Fecha();
        fecha.setCondiciones(condiciones);
        fecha.vincular(empresa);
        Assert.assertEquals(empresa.getIngresos().get(0).montoTotalEgresos(),e2.getPrecioTotal());

        // El Ingreso i2 es valido para la vinculacion con el egreso por el precio 350 admite este egreso. El egreso
        // e2 es el valido por estar dentro de la fecha de aceptabilidad de egresos
    }
}
