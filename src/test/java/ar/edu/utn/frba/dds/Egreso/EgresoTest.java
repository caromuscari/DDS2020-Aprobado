package ar.edu.utn.frba.dds.Egreso;

import ar.edu.utn.frba.dds.Licitacion.ItemOperacionPresupuesto;
import ar.edu.utn.frba.dds.Licitacion.ItemPresupuesto;
import ar.edu.utn.frba.dds.Licitacion.Presupuesto;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class EgresoTest {

    // Presupuesto
    Proveedor prov = new Proveedor("Alejandro",21314l,"666");
    ItemPresupuesto p1 = new ItemPresupuesto(200.0,Categoria.MONITOR,TipoItem.PRODUCTO);
    ItemPresupuesto p2 = new ItemPresupuesto(300.0, Categoria.COMPUTADORA, TipoItem.PRODUCTO);
    ItemOperacionPresupuesto pp1 = new ItemOperacionPresupuesto(5,p1);
    ItemOperacionPresupuesto pp2 = new ItemOperacionPresupuesto(5,p2);
    DocumentoComercial prueba1 = new DocumentoComercial();
    List<DocumentoComercial> documents = new ArrayList<>();
    List<ItemOperacionPresupuesto> products = new ArrayList<>();

    // Egreso
    ItemEgreso e1 = new ItemEgreso("210973","Monitor", 200.0, TipoItem.PRODUCTO, Categoria.MONITOR);
    ItemEgreso e2 = new ItemEgreso("274818","Computadora", 300.0, TipoItem.PRODUCTO, Categoria.COMPUTADORA);
    ItemOperacionEgreso ee1 = new ItemOperacionEgreso(5,e1);
    ItemOperacionEgreso ee2 = new ItemOperacionEgreso(5,e2);
    List<ItemOperacionEgreso> compras = new ArrayList<>();

    @Test
    public void verificarCantItems() {

        documents.add(prueba1);
        products.add(pp1);
        products.add(pp2);
        compras.add(ee1);
        compras.add(ee2);

        Presupuesto presupuesto = new Presupuesto(documents,products,500.0, prov);
        Egreso egreso = new Egreso(compras,prov);
        egreso.setPresupuesto(presupuesto);

        Assert.assertTrue(egreso.verificarCantidadItems());
    }

    @Test
    public void verificarEgreso() {

        documents.add(prueba1);
        products.add(pp1);
        products.add(pp2);
        compras.add(ee1);
        compras.add(ee2);

        Presupuesto presupuesto = new Presupuesto(documents,products,500.0, prov);
        Egreso egreso = new Egreso(compras,prov);
        egreso.setPresupuesto(presupuesto);

        Assert.assertTrue(egreso.verificarEgreso());
    }

    @Test
    public void verificarEgresoFallido() {

        documents.add(prueba1);
        pp1.getItemPresupuesto().setTipo(TipoItem.SERVICIO);
        products.add(pp1);
        products.add(pp2);
        compras.add(ee1);
        compras.add(ee2);

        Presupuesto presupuesto = new Presupuesto(documents,products,500.0, prov);
        Egreso egreso = new Egreso(compras,prov);
        egreso.setPresupuesto(presupuesto);

        Assert.assertFalse(egreso.verificarEgreso());
    }

    @Test
    public void verificarCantItemsFallido() {

        documents.add(prueba1);
        products.add(pp1);
        products.add(pp2);

        compras.add(ee1);
        compras.add(ee1);
        compras.add(ee2);

        Presupuesto presupuesto = new Presupuesto(documents,products,500.0, prov);
        Egreso egreso = new Egreso(compras,prov);
        egreso.setPresupuesto(presupuesto);

        Assert.assertFalse(egreso.verificarCantidadItems());
    }
}
