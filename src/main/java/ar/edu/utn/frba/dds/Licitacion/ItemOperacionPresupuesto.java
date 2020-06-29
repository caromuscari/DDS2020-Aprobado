package ar.edu.utn.frba.dds.Licitacion;

public class ItemOperacionPresupuesto {
    private Integer cantidad;
    private ItemPresupuesto itemPresupuesto;

    public ItemOperacionPresupuesto(Integer cantidad, ItemPresupuesto itemPresupuesto) {
        this.cantidad = cantidad;
        this.itemPresupuesto = itemPresupuesto;
    }

    public Integer getCantidad(){return cantidad;}
    public void setCantidad(Integer cantidad){this.cantidad = cantidad;}

    public ItemPresupuesto getItemPresupuesto(){return itemPresupuesto;}
    public void setItemPresupuesto(ItemPresupuesto itemPresupuesto){this.itemPresupuesto = itemPresupuesto;}

    public Double precioTotal(){return cantidad * itemPresupuesto.getPrecio();}
}