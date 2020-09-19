package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.Categorizacion.Categoria;
import ar.edu.utn.frba.dds.Categorizacion.CriterioCategoria;
import ar.edu.utn.frba.dds.Entidad.Empresa;
import ar.edu.utn.frba.dds.Entidad.Entidad;
import ar.edu.utn.frba.dds.Entidad.Organizacion;
import ar.edu.utn.frba.dds.Entidad.TipoActividad;
import ar.edu.utn.frba.dds.Operaciones.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TipoCategoriaTest {

    private Organizacion organizacion = new Organizacion();
    private TipoActividad agropecuario = new TipoActividad(12890000, 48480000, 345430000,
            5, 10, 50, "Agropecuario");
    private Empresa empresa = new Empresa("La mejor empresa", "LME SRL", Long.parseLong("11123456789"),
            1111, 1, 1000, 10000.0, agropecuario, 1000.0);
    private Categoria america = new Categoria("America");
    private Categoria asia = new Categoria("Asia");

    @Before
    public void inicializacion() {
        //Creacion de dos egresos y asociacion a la entidad
        ItemEgreso itemEgreso = new ItemEgreso("1234", "item 1", 10.0, TipoItem.PRODUCTO, CategoriaItem.COMPUTADORA);
        ItemEgreso itemEgreso2 = new ItemEgreso("5678", "item 2", 50.0, TipoItem.PRODUCTO, CategoriaItem.COMPUTADORA);
        ItemOperacionEgreso itemOperacionEgreso = new ItemOperacionEgreso(1, itemEgreso);
        ItemOperacionEgreso itemOperacionEgreso2 = new ItemOperacionEgreso(2, itemEgreso2);

        List<ItemOperacionEgreso> itemsOperacion1 = new ArrayList<>();
        itemsOperacion1.add(itemOperacionEgreso);
        List<ItemOperacionEgreso> itemsOperacion2 = new ArrayList<>();
        itemsOperacion2.add(itemOperacionEgreso2);
        Proveedor proveedor = new Proveedor("Provedor 1", Long.parseLong("123"), "1234");

        empresa.generarEgreso(itemsOperacion1, proveedor,"Egreso 1");
        empresa.generarEgreso(itemsOperacion2, proveedor,"Egreso 2");

        //Creacion de la empresa
        List<Entidad> entidades = new ArrayList<>();
        entidades.add(empresa);
        organizacion.setEntidades(entidades);

        //Creacion de criterios
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

        organizacion.asociarCriterio(criterioContinente);
        organizacion.asociarCriterio(criterioPais);

        List<Egreso> egresos = empresa.getEgresos();
        egresos.get(0).asociarCategoria(argentina);
    }


    @Test
    public void filtroEgresosPorAmerica() {
        List<Egreso> egresos = empresa.filtrarEgresos(america);
        Assert.assertEquals(1, egresos.size());
    }

    @Test
    public void filtroEgresosPorArgentina() {
        List<Egreso> egresos = empresa.filtrarEgresos(new Categoria("Argentina"));
        Assert.assertEquals(1, egresos.size());
    }

    @Test
    public void filtroEgresosPorAsia() {
        List<Egreso> egresos = empresa.filtrarEgresos(asia);
        Assert.assertEquals(0, egresos.size());
    }

    @Test
    public void filtroEgresosPorBrasil() {
        List<Egreso> egresos = empresa.filtrarEgresos(new Categoria("Brasil"));
        Assert.assertEquals(0, egresos.size());
    }

}
