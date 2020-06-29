package ar.edu.utn.frba.dds.Egreso;

import ar.edu.utn.frba.dds.Licitacion.ItemOperacionPresupuesto;
import ar.edu.utn.frba.dds.Licitacion.ItemPresupuesto;
import ar.edu.utn.frba.dds.Licitacion.Presupuesto;

import java.util.Iterator;
import java.util.List;

public class Egreso {
    private List<DocumentoComercial> documentos;
    private List<ItemOperacionEgreso> items;
    private MedioDePago medioDePago;
    private Double precioTotal;
    private Proveedor proveedor;
    private Presupuesto presupuesto;

    public Egreso(List<ItemOperacionEgreso> items, Proveedor proveedor) {
        this.items = items;
        this.proveedor = proveedor;
        calcularPrecio();
    }

    public List<DocumentoComercial> getDocumentos() { return documentos; }
    public void setDocumentos(List<DocumentoComercial> documentos) { this.documentos = documentos; }

    public List<ItemOperacionEgreso> getItems() { return items; }
    public void setItems(List<ItemOperacionEgreso> items) { this.items = items; }

    public MedioDePago getMedioDePago() { return medioDePago; }

    public Double getPrecioTotal() { return precioTotal; }
    public void setPrecioTotal(Double precioTotal) { this.precioTotal = precioTotal; }

    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }

    public Presupuesto getPresupuesto() { return presupuesto; }
    public void setPresupuesto(Presupuesto presupuesto) { this.presupuesto = presupuesto; }

    //Metodos
    public void registrarMedioDePago(MedioDePago medioDePago) {
        this.medioDePago = medioDePago;
    }

    public void adjuntarDocumento(DocumentoComercial documento){
        this.documentos.add(documento);
    }

    public void calcularPrecio(){
        this.precioTotal = this.items.stream().mapToDouble(i -> i.precioTotal()).sum();
    }

    public boolean verificarCantidadItems(){
        return items.stream().mapToInt(i -> i.getCantidad()).sum() == presupuesto.
                getItems().stream().mapToInt(i -> i.getCantidad()).sum();
    }

    private boolean compararItems(ItemOperacionEgreso egr, ItemOperacionPresupuesto pres){
        return egr.getItemEgreso().getCategoria() == pres.getItemPresupuesto().getCategoria()
                && egr.getItemEgreso().getTipo() == pres.getItemPresupuesto().getTipo();
    }

    public boolean verificarEgreso(){
        boolean validez = false;
        Iterator<ItemOperacionEgreso> egreso = items.iterator();
        while(egreso.hasNext()) {
            ItemOperacionEgreso e = egreso.next();
            if (!presupuesto.getItems().stream().anyMatch(i -> compararItems(e,i))) {
                return validez;
            }
        }
        return !validez;
    }
}
