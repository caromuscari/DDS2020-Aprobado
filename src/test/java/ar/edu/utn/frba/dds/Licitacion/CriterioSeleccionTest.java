package ar.edu.utn.frba.dds.Licitacion;

import ar.edu.utn.frba.dds.Entidad.Organizacion;
import ar.edu.utn.frba.dds.Operaciones.*;
import ar.edu.utn.frba.dds.ResultadoLicitacion.EstadoValidacion;
import ar.edu.utn.frba.dds.ResultadoLicitacion.ResultadoValidacion;
import ar.edu.utn.frba.dds.Usuario.TipoPerfil;
import ar.edu.utn.frba.dds.Usuario.Usuario;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CriterioSeleccionTest {
    Licitacion licitacion;
    Presupuesto p1, p2, p3;
    Egreso e1;
    Usuario usuario;

    @Before
    public void init(){
        licitacion = new Licitacion("Licitacion1", 3);
        usuario = new Usuario("Carlos", "Diseño2020", TipoPerfil.OPERADOR,new Organizacion("organizacion 1", "descripcion"));

        ItemEgreso itemEgreso = new ItemEgreso("1234", "item 1", 1234.0, TipoItem.PRODUCTO, CategoriaItem.COMPUTADORA);
        ItemEgreso itemEgreso2 = new ItemEgreso("5678", "item 2", 5678.0, TipoItem.PRODUCTO, CategoriaItem.MONITOR);
        ItemOperacionEgreso itemOperacionEgreso = new ItemOperacionEgreso(1, itemEgreso);
        ItemOperacionEgreso itemOperacionEgreso2 = new ItemOperacionEgreso(2, itemEgreso2);
        List<ItemOperacionEgreso> items = new ArrayList<>();
        items.add(itemOperacionEgreso);
        items.add(itemOperacionEgreso2);

        e1 = new Egreso(items,new Proveedor("Jose", (long) 1739345,"kdsfkjnsdv"),"Egreso");


        ItemPresupuesto itemP = new ItemPresupuesto(1234.0, CategoriaItem.COMPUTADORA, TipoItem.PRODUCTO);
        ItemPresupuesto itemP2 = new ItemPresupuesto(5678.0, CategoriaItem.MONITOR, TipoItem.PRODUCTO);
        ItemOperacionPresupuesto itemOperacionP = new ItemOperacionPresupuesto(1, itemP);
        ItemOperacionPresupuesto itemOperacionP2 = new ItemOperacionPresupuesto(2, itemP2);
        List<ItemOperacionPresupuesto> itemsP = new ArrayList<>();

        itemsP.add(itemOperacionP);
        itemsP.add(itemOperacionP2);

        p1 = new Presupuesto(itemsP, new Proveedor("Jose", (long) 1739345,"kdsfkjnsdv"),"Presupuesto 1");

        itemOperacionP.setCantidad(2);
        p2 = new Presupuesto(itemsP, new Proveedor("Carlos", (long) 1739349,"kdsfkjnsdv"),"Presupuesto 2");

        p3 = new Presupuesto(itemsP, new Proveedor("Carlos", (long) 1739349,"kdsfkjnsdv"),"Presupuesto 3");


        licitacion.agregarPresupuesto(p1);
        licitacion.agregarPresupuesto(p2);
        licitacion.agregarPresupuesto(p3);
        licitacion.setEgreso(e1);
        licitacion.agregarRevisor(usuario);
    }

    @Test
    public void CriterioCantidadPresupuestosVerdadero(){
        CantidadPresupuestos criterio = new CantidadPresupuestos();
        ResultadoValidacion resultado = criterio.validar(licitacion);

        Assert.assertEquals(EstadoValidacion.OK, resultado.getEstado());
    }

    @Test
    public void CriterioCantidadPresupuestosFalso(){
        CantidadPresupuestos criterio = new CantidadPresupuestos();
        licitacion.setPresupuestosRequeridos(6);
        ResultadoValidacion resultado = criterio.validar(licitacion);

        Assert.assertEquals(EstadoValidacion.ERROR, resultado.getEstado());
    }

    @Test
    public void CriterioMenorValorVerdadero(){
        MenorValor criterio = new MenorValor();
        licitacion.getPresupuestos().get(0).getItems().get(0).setCantidad(1);
        ResultadoValidacion resultado = criterio.validar(licitacion);

        Assert.assertEquals(EstadoValidacion.OK, resultado.getEstado());
    }

    @Test
    public void CriterioMenorValorFalso(){
        MenorValor criterio = new MenorValor();
        ResultadoValidacion resultado = criterio.validar(licitacion);

        Assert.assertEquals(EstadoValidacion.ERROR, resultado.getEstado());
    }

    @Test
    public void CriterioItemsPresupuestoVerdadero(){
        ItemsPresupuesto criterio = new ItemsPresupuesto();
        ResultadoValidacion resultado = criterio.validar(licitacion);

        Assert.assertEquals(EstadoValidacion.OK, resultado.getEstado());
    }

    @Test
    public void CriterioItemsPresupuestoFalso(){
        ItemsPresupuesto criterio = new ItemsPresupuesto();
        licitacion.getEgreso().getItems().remove(1);
        ResultadoValidacion resultado = criterio.validar(licitacion);

        Assert.assertEquals(EstadoValidacion.ERROR, resultado.getEstado());
    }

}
