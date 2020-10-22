package ar.edu.utn.frba.dds.Licitacion;

import ar.edu.utn.frba.dds.Operaciones.CategoriaItem;
import ar.edu.utn.frba.dds.Operaciones.ItemEgreso;
import ar.edu.utn.frba.dds.Operaciones.ItemOperacionEgreso;
import ar.edu.utn.frba.dds.ResultadoLicitacion.ErrorItemFaltante;
import ar.edu.utn.frba.dds.ResultadoLicitacion.EstadoValidacion;
import ar.edu.utn.frba.dds.ResultadoLicitacion.ResultadoValidacion;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@DiscriminatorValue("ItemsPrespuesto")
public class ItemsPresupuesto extends CriterioSeleccion{
    @Override
    public ResultadoValidacion validar(Licitacion licitacion) {
        ResultadoValidacion resultado;
        List<ItemOperacionEgreso> itemsEgreso = licitacion.getEgreso().getItems();
        if (licitacion.getPresupuestos().stream().anyMatch(p -> p.itemsIguales(itemsEgreso))){
            resultado = new ResultadoValidacion(EstadoValidacion.OK,licitacion);
        }
        else {
            resultado = new ResultadoValidacion(EstadoValidacion.ERROR,licitacion);
            //Asumo que todos los presupuestos tienen las mismas categorias de items
            List<CategoriaItem> categoriasPresupuesto = licitacion.getPresupuestos().get(0).getItems().stream().map(ItemOperacionPresupuesto::getItemPresupuesto).map(ItemPresupuesto::getCategoria).collect(Collectors.toList());
            List<CategoriaItem> categoriasEgreso = licitacion.getEgreso().getItems().stream().map(ItemOperacionEgreso::getItemEgreso).map(ItemEgreso::getCategoria).collect(Collectors.toList());
            if (categoriasPresupuesto.size() > categoriasEgreso.size()){
                categoriasPresupuesto.removeAll(categoriasEgreso);
                resultado.setError(new ErrorItemFaltante(1,categoriasPresupuesto));
            }
            else{
                categoriasEgreso.removeAll(categoriasPresupuesto);
                resultado.setError(new ErrorItemFaltante(2,categoriasEgreso));
            }
        }
        return resultado;
    }
}
