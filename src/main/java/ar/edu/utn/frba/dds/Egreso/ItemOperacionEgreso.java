package ar.edu.utn.frba.dds.Egreso;

public class ItemOperacionEgreso {
    private Integer cantidad;
    private ItemEgreso itemEgreso;

    public ItemOperacionEgreso(Integer cantidad, ItemEgreso itemEgreso) {
        this.cantidad = cantidad;
        this.itemEgreso = itemEgreso;
    }

    public Integer getCantidad() { return cantidad; }

    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public ItemEgreso getItemEgreso() { return itemEgreso; }

    public void setItemEgreso(ItemEgreso itemEgreso) { this.itemEgreso = itemEgreso; }

    public Double precioTotal(){
        return cantidad * this.itemEgreso.getPrecio();
    }
}
