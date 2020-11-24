package ar.edu.utn.frba.dds.ResultadoLicitacion;

import ar.edu.utn.frba.dds.Operaciones.CategoriaItem;
import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.List;

@Entity(name = "ErrorItemFaltante")
@DiscriminatorValue("ErrorItemFaltante")
public class ErrorItemFaltante extends ErrorValidacion {

    @Expose(serialize = true)
    @Column
    private String mensaje;

    public ErrorItemFaltante(Integer error,List<CategoriaItem> categoriaItem) {
        if (error == 1) this.mensaje = "Al egreso registrado le falta el/los item/s de categoria" + categoriaItem.toString();
        else this.mensaje = "El egreso tiene mas items que el presupuesto de categoria" + categoriaItem.toString();
    }

    public ErrorItemFaltante() {
        // Para Hibernate
    }

    @Override
    public String obtenerMensaje() {
        return mensaje;
    }
}
