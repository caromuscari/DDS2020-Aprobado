package ar.edu.utn.frba.dds.Licitacion;

import ar.edu.utn.frba.dds.Categorizacion.Categoria;
import ar.edu.utn.frba.dds.Operaciones.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Presupuesto {
    private int id;
    private List<DocumentoComercial> documentos;
    private List<ItemOperacionPresupuesto> items;
    private Double precioTotal;
    private Proveedor proveedor;
    private List<Categoria> categorias;
    private String nombre;

    public Presupuesto(List<DocumentoComercial> documentos, List<ItemOperacionPresupuesto> items, Double precioTotal, Proveedor proveedor,String nombre) {
        this.documentos = documentos;
        this.items = items;
        this.precioTotal = precioTotal;
        this.proveedor = proveedor;
        this.categorias = new ArrayList<>();
        this.nombre = nombre;
        this.id = new Random().nextInt(1000);
    }

    public Presupuesto(List<ItemOperacionPresupuesto> items, Proveedor proveedor,String nombre) {
        this.items = items;
        this.proveedor = proveedor;
        this.categorias = new ArrayList<>();
        this.items = new ArrayList<>();
        this.nombre = nombre;
        this.id = new Random().nextInt(1000);
    }

    public List<DocumentoComercial> getDocumentos() { return documentos; }
    public void setDocumentos(List<DocumentoComercial> documentos) { this.documentos = documentos; }

    public List<ItemOperacionPresupuesto> getItems() { return items; }
    public void setItems(List<ItemOperacionPresupuesto> items) { this.items = items; }

    public Double getPrecioTotal() { calcularPrecio(); return precioTotal; }
    public void setPrecioTotal(Double precioTotal) { this.precioTotal = precioTotal; }

    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }

    public List<Categoria> getCategorias() { return categorias; }
    public void setCategorias(List<Categoria> categorias) { this.categorias = categorias; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    //Metodos
    public void asociarCategoria(Categoria categoria){
        this.categorias.add(categoria);
    }

    public boolean contieneCategoria(Categoria categoria){
        List<Boolean> resultados = this.categorias.stream().map(c -> {if (c.equals(categoria)) return true;
            return c.contieneCategoriaHija(categoria);}).collect(Collectors.toList());

        return resultados.stream().anyMatch(c -> c.equals(true));
    }

    public void calcularPrecio(){
        this.precioTotal = this.items.stream().mapToDouble(i -> i.precioTotal()).sum();
    }

    public boolean itemsIguales(List<ItemOperacionEgreso> itemsEgreso){
        List<CategoriaItem> categorias = this.items.stream().map(ItemOperacionPresupuesto::getItemPresupuesto).map(ItemPresupuesto::getCategoria).collect(Collectors.toList());
        categorias.removeAll(itemsEgreso.stream().map(ItemOperacionEgreso::getItemEgreso).map(ItemEgreso::getCategoria).collect(Collectors.toList()));
        if (categorias.size() == 0) return true;
        else return false;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
