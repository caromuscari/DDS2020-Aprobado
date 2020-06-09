package ar.edu.utn.frba.dds.Egreso;

import java.util.List;

public class Egreso {
    private List<DocumentoComercial> documentos;
    private List<ItemOperacion> items;
    private MedioDePago medioDePago;
    private Double precioTotal;
    private Proveedor proveedor;

    public Egreso(List<ItemOperacion> items, Proveedor proveedor) {
        this.items = items;
        this.proveedor = proveedor;
        calcularPrecio();
    }

    public List<DocumentoComercial> getDocumentos() { return documentos; }

    public void setDocumentos(List<DocumentoComercial> documentos) { this.documentos = documentos; }

    public List<ItemOperacion> getItems() { return items; }

    public void setItems(List<ItemOperacion> items) { this.items = items; }

    public MedioDePago getMedioDePago() { return medioDePago; }

    public Double getPrecioTotal() { return precioTotal; }

    public void setPrecioTotal(Double precioTotal) { this.precioTotal = precioTotal; }

    public Proveedor getProveedor() { return proveedor; }

    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }

    public void registrarMedioDePago(MedioDePago medioDePago) {
        this.medioDePago = medioDePago;
    }

    public void adjuntarDocumento(DocumentoComercial documento){
        this.documentos.add(documento);
    }

    public void calcularPrecio(){
        this.precioTotal = this.items.stream().mapToDouble(i -> i.precioTotal()).sum();
    }
}
