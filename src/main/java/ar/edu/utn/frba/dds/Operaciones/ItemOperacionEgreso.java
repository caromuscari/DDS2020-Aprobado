package ar.edu.utn.frba.dds.Operaciones;

public class ItemOperacionEgreso {
    private Long id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //Metodos
    public Double precioTotal(){
        return cantidad * this.itemEgreso.getPrecio();
    }
}
