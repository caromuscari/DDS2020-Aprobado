package ar.edu.utn.frba.dds.Licitacion;

import ar.edu.utn.frba.dds.Operaciones.CategoriaItem;
import ar.edu.utn.frba.dds.Operaciones.TipoItem;

import javax.persistence.*;

@Entity
public class ItemPresupuesto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Double precio;

    @Enumerated(EnumType.STRING)
    private CategoriaItem categoria;

    @Enumerated(EnumType.STRING)
    private TipoItem tipo;

    public ItemPresupuesto(Double precio, CategoriaItem categoria, TipoItem tipo) {
        this.precio = precio;
        this.categoria = categoria;
        this.tipo = tipo;
    }

    public ItemPresupuesto() {
    }

    public Double getPrecio(){return precio;}
    public void setPrecio(Double precio){this.precio = precio;}

    public CategoriaItem getCategoria(){return categoria;}
    public void setCategoria(CategoriaItem categoria){this.categoria = categoria;}

    public TipoItem getTipo(){return tipo;}
    public void setTipo(TipoItem tipo){this.tipo = tipo;}
}
