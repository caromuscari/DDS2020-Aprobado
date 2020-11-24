package ar.edu.utn.frba.dds.DTO;

import ar.edu.utn.frba.dds.Categorizacion.Categoria;
import ar.edu.utn.frba.dds.Licitacion.ItemOperacionPresupuesto;
import ar.edu.utn.frba.dds.Licitacion.Presupuesto;
import ar.edu.utn.frba.dds.Operaciones.DocumentoComercial;
import ar.edu.utn.frba.dds.Operaciones.Proveedor;

import java.util.List;

public class PresupuestoDTO {
    private Double precioTotal;
    private Proveedor proveedor;
    private String nombre;
    private int id;
    private List<ItemOperacionPresupuesto> items;
    private List<DocumentoComercial> documentos;
    private List<Categoria> categorias;

    public PresupuestoDTO() {
    }

    public PresupuestoDTO(Presupuesto presupuesto) {
        this.precioTotal = presupuesto.getPrecioTotal();
        this.proveedor = presupuesto.getProveedor();
        this.nombre = presupuesto.getNombre();
        this.id = presupuesto.getId();
        this.items = presupuesto.getItems();
        this.documentos = presupuesto.getDocumentos();
        this.categorias = presupuesto.getCategorias();
    }

    public Double getPrecioTotal() {
        return precioTotal;
    }
    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }
    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public List<ItemOperacionPresupuesto> getItems() { return items; }
    public void setItems(List<ItemOperacionPresupuesto> items) { this.items = items; }

    public List<DocumentoComercial> getDocumentos() { return documentos; }
    public void setDocumentos(List<DocumentoComercial> documentos) { this.documentos = documentos; }

    public List<Categoria> getCategorias() { return categorias; }
    public void setCategorias(List<Categoria> categorias) { this.categorias = categorias; }
}
