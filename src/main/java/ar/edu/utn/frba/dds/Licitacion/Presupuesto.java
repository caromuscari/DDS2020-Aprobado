package ar.edu.utn.frba.dds.Licitacion;

import ar.edu.utn.frba.dds.Egreso.DocumentoComercial;
import ar.edu.utn.frba.dds.Egreso.ItemOperacionEgreso;
import ar.edu.utn.frba.dds.Egreso.Proveedor;

import java.util.List;

public class Presupuesto {
    private List<DocumentoComercial> documentos;
    private List<ItemOperacionPresupuesto> items;
    private Double precioTotal;
    private Proveedor proveedor;

    public Presupuesto(List<DocumentoComercial> documentos, List<ItemOperacionPresupuesto> items, Double precioTotal, Proveedor proveedor) {
        this.documentos = documentos;
        this.items = items;
        this.precioTotal = precioTotal;
        this.proveedor = proveedor;
    }

    public List<DocumentoComercial> getDocumentos() { return documentos; }
    public void setDocumentos(List<DocumentoComercial> documentos) { this.documentos = documentos; }

    public List<ItemOperacionPresupuesto> getItems() { return items; }
    public void setItems(List<ItemOperacionPresupuesto> items) { this.items = items; }

    public Double getPrecioTotal() { return precioTotal; }
    public void setPrecioTotal(Double precioTotal) { this.precioTotal = precioTotal; }

    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }
}
