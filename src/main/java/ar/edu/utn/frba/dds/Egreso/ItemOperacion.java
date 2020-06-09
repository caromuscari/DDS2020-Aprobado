package ar.edu.utn.frba.dds.Egreso;

public class ItemOperacion {
    private Integer cantidad;
    private Item item;

    public ItemOperacion(Integer cantidad, Item item) {
        this.cantidad = cantidad;
        this.item = item;
    }

    public Integer getCantidad() { return cantidad; }

    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Item getItem() { return item; }

    public void setItem(Item item) { this.item = item; }

    public Double precioTotal(){
        return cantidad * this.item.getPrecio();
    }
}
