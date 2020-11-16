package ar.edu.utn.frba.dds.Repositorios;

import ar.edu.utn.frba.dds.Operaciones.ItemEgreso;
import ar.edu.utn.frba.dds.Operaciones.Proveedor;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
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
        this.entityManager.persist(proveedor);
        return proveedor;
    }

    public List<Proveedor> obtenerProveedores() {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Proveedor> consulta = cb.createQuery(Proveedor.class);
        Root<Proveedor> proveedores = consulta.from(Proveedor.class);
        return this.entityManager.createQuery(consulta.select(proveedores)).getResultList();
    }

    public Proveedor obtenerProveedorPorNombre(String nombre) {
        if (this.existsProveedorPorNombre(nombre)) {
            CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
            CriteriaQuery<Proveedor> consulta = cb.createQuery(Proveedor.class);
            Root<Proveedor> proveedores = consulta.from(Proveedor.class);
            Predicate condicion = cb.equal(proveedores.get("nombre"), nombre);
            CriteriaQuery<Proveedor> where = consulta.select(proveedores).where(condicion);
            List<Proveedor> listaProveedores = this.entityManager.createQuery(where).getResultList();
            return !listaProveedores.isEmpty() ? listaProveedores.get(0) : null;
        }
        return null;
    }

    private boolean existsProveedorPorNombre(String nombre) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> consulta = cb.createQuery(Long.class);
        Root<Proveedor> proveedores = consulta.from(Proveedor.class);
        Predicate condicion = cb.equal(proveedores.get("nombre"), nombre);
        CriteriaQuery<Long> select = consulta.where(condicion).select(cb.count(proveedores));
        return this.entityManager.createQuery(select).getSingleResult() > 0;
    }

    public void eliminarProveedor(Proveedor proveedor){ this.entityManager.remove(proveedor); }

}
