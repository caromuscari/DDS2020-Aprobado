package ar.edu.utn.frba.dds.Repositorios;

import ar.edu.utn.frba.dds.Operaciones.Egreso;
import ar.edu.utn.frba.dds.Operaciones.ItemEgreso;
import ar.edu.utn.frba.dds.Operaciones.ItemOperacionEgreso;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class RepositorioItemsOperacionEgreso {

    private List<ItemOperacionEgreso> items = new ArrayList<>();
    private EntityManager entityManager;

    public RepositorioItemsOperacionEgreso(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ItemOperacionEgreso crearItem(Integer cantidad, ItemEgreso itemEgreso) {
        ItemOperacionEgreso itemOperacionEgreso = new ItemOperacionEgreso(cantidad, itemEgreso);
        this.entityManager.persist(itemOperacionEgreso);
        return itemOperacionEgreso;
    }

    public List<ItemOperacionEgreso> obtenerItems() {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<ItemOperacionEgreso> consulta = cb.createQuery(ItemOperacionEgreso.class);
        Root<ItemOperacionEgreso> itemsOperacionEgreso = consulta.from(ItemOperacionEgreso.class);
        return this.entityManager.createQuery(consulta.select(itemsOperacionEgreso)).getResultList();
    }

    public ItemOperacionEgreso obtenerItemsPorId(Long id) {
        for (ItemOperacionEgreso item : obtenerItems()) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public void eliminarItem(ItemOperacionEgreso item){
        this.entityManager.remove(item);
    }
}
