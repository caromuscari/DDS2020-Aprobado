package ar.edu.utn.frba.dds.Operaciones;

import java.util.ArrayList;
import java.util.List;

public class RepositorioItemsOperacionEgreso {

    public static RepositorioItemsOperacionEgreso instance = null;
    private List<ItemOperacionEgreso> items = new ArrayList<>();

    private RepositorioItemsOperacionEgreso() {}

    public static RepositorioItemsOperacionEgreso getInstance() {
        if (instance == null) {
            instance = new RepositorioItemsOperacionEgreso();
        }
        return instance;
    }

    public ItemOperacionEgreso crearItem(Integer cantidad, ItemEgreso itemEgreso) {
        ItemOperacionEgreso itemOperacionEgreso = new ItemOperacionEgreso(cantidad, itemEgreso);
        items.add(itemOperacionEgreso);
        return itemOperacionEgreso;
    }

    public List<ItemOperacionEgreso> obtenerItems() {
        return items;
    }

    public ItemOperacionEgreso obtenerItemsPorId(Long id) {
        for (ItemOperacionEgreso item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }
}
