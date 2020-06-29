package ar.edu.utn.frba.dds.Licitacion;

import ar.edu.utn.frba.dds.Egreso.Categoria;
import ar.edu.utn.frba.dds.Egreso.TipoItem;

public class ItemPresupuesto {
    private Double precio;
    private Categoria categoria;
    private TipoItem tipo;

    public ItemPresupuesto(Double precio, Categoria categoria, TipoItem tipo) {
        this.precio = precio;
        this.categoria = categoria;
        this.tipo = tipo;
    }

    public Double getPrecio(){return precio;}
    public void setPrecio(Double precio){this.precio = precio;}

    public Categoria getCategoria(){return categoria;}
    public void setCategoria(Categoria categoria){this.categoria = categoria;}

    public TipoItem getTipo(){return tipo;}
    public void setTipo(TipoItem tipo){this.tipo = tipo;}
}
