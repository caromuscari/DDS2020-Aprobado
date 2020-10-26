package ar.edu.utn.frba.dds.Repositorios;

import ar.edu.utn.frba.dds.Operaciones.Egreso;
import ar.edu.utn.frba.dds.Operaciones.ItemEgreso;
import ar.edu.utn.frba.dds.Operaciones.Proveedor;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class RepositorioProveedores {

    private List<Proveedor> proveedores = new ArrayList<>();
    private EntityManager entityManager;

    public RepositorioProveedores(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Proveedor crearProveedor(String nombre, Long identificador, String direccionPostal, ItemEgreso item) {
        Proveedor proveedor = new Proveedor(nombre, identificador, direccionPostal);
        entityManager.getTransaction().begin();
        this.entityManager.persist(proveedor);
        entityManager.getTransaction().commit();
        return proveedor;
    }

    public List<Proveedor> obtenerProveedores() {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Proveedor> consulta = cb.createQuery(Proveedor.class);
        Root<Proveedor> proveedores = consulta.from(Proveedor.class);
        return this.entityManager.createQuery(consulta.select(proveedores)).getResultList();
    }

    public Proveedor obtenerProveedorPorNombre(String nombre) {
        for (Proveedor proveedor : obtenerProveedores()) {
            if (proveedor.getNombre().matches(nombre)) {
                return proveedor;
            }
        }
        return null;
    }

    public void eliminarProveedor(Proveedor proveedor){ this.entityManager.remove(proveedor); }

}
