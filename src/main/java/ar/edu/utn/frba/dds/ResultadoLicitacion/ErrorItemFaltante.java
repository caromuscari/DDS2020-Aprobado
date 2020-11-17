package ar.edu.utn.frba.dds.ResultadoLicitacion;

import ar.edu.utn.frba.dds.Operaciones.CategoriaItem;

import javax.persistence.CollectionTable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.List;

@Entity
@DiscriminatorValue("ErrorItemFaltante")
public class ErrorItemFaltante extends ErrorValidacion {
    @ElementCollection
    @CollectionTable(name = "ErrorItemFaltante_Categorias")
    private List<CategoriaItem> categoriasItems;
    private String mensaje;

    public ErrorItemFaltante(Integer error,List<CategoriaItem> categoriaItem) {
        this.categoriasItems = categoriaItem;
        if (error == 1) this.mensaje = "Al egreso registrado le falta el/los item/s de categoria {0}";
        else this.mensaje = "El egreso tiene mas items que el presupuesto de categoria {0}";
    }

    public ErrorItemFaltante() {
        // Para Hibernate
    }

    @Override
    public String obtenerMensaje() {
        return String.format(this.mensaje, this.categoriasItems.toString());
    }
}
