package ar.edu.utn.frba.dds.Licitacion;

import javax.persistence.*;

@Entity
public class ItemOperacionPresupuesto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Integer cantidad;

    @OneToOne
    @JoinColumn(name = "item_id")
    private ItemPresupuesto itemPresupuesto;

    public ItemOperacionPresupuesto(Integer cantidad, ItemPresupuesto itemPresupuesto) {
        this.cantidad = cantidad;
        this.itemPresupuesto = itemPresupuesto;
    }

    public Integer getCantidad(){return cantidad;}
    public void setCantidad(Integer cantidad){this.cantidad = cantidad;}

    public ItemPresupuesto getItemPresupuesto(){return itemPresupuesto;}
    public void setItemPresupuesto(ItemPresupuesto itemPresupuesto){this.itemPresupuesto = itemPresupuesto;}

    //Metodos
    public Double precioTotal(){return cantidad * itemPresupuesto.getPrecio();}
}
