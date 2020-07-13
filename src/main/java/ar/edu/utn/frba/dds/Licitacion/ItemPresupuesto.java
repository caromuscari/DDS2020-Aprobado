package ar.edu.utn.frba.dds.Licitacion;

import ar.edu.utn.frba.dds.Operaciones.CategoriaItem;
import ar.edu.utn.frba.dds.Operaciones.TipoItem;

public class ItemPresupuesto {
    private Double precio;
    private CategoriaItem categoria;
    private TipoItem tipo;

    public ItemPresupuesto(Double precio, CategoriaItem categoria, TipoItem tipo) {
        this.precio = precio;
        this.categoria = categoria;
        this.tipo = tipo;
    }

    public Double getPrecio(){return precio;}
    public void setPrecio(Double precio){this.precio = precio;}

    public CategoriaItem getCategoria(){return categoria;}
    public void setCategoria(CategoriaItem categoria){this.categoria = categoria;}

    public TipoItem getTipo(){return tipo;}
    public void setTipo(TipoItem tipo){this.tipo = tipo;}
}
