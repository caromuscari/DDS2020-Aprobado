package ar.edu.utn.frba.dds.Repositorios;

import ar.edu.utn.frba.dds.Entidad.Entidad;
import ar.edu.utn.frba.dds.Operaciones.Egreso;
import ar.edu.utn.frba.dds.Operaciones.ItemOperacionEgreso;
import ar.edu.utn.frba.dds.Operaciones.Proveedor;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class RepositorioEgresos {

    private EntityManager entityManager;

    public RepositorioEgresos(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Egreso crearEgreso(List<ItemOperacionEgreso> items, Proveedor proveedor, String nombre) {
        Egreso egreso = new Egreso(items, proveedor, nombre);
        this.entityManager.persist(egreso);
        return egreso;
    }

    public List<Egreso> obtenerEgresos() {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Egreso> consulta = cb.createQuery(Egreso.class);
        Root<Egreso> egresos = consulta.from(Egreso.class);
        return this.entityManager.createQuery(consulta.select(egresos)).getResultList();
    }

    public Egreso obtenerEgresoPorNombre(String nombre) {
        for (Egreso egreso : obtenerEgresos()) {
            if (nombre.equals(egreso.getNombre())) {
                return egreso;
            }
        }
        return null;
    }

    public void eliminarEgreso(Egreso egreso){
        this.entityManager.remove(egreso);
    }

    public void borrarEgreso(String id) {
        Egreso e = this.entityManager.find(Egreso.class, Integer.parseInt(id));
        this.entityManager.remove(e);
    }

    public Egreso obtenerEgresoPorId(String id) {
        return this.entityManager.find(Egreso.class, Integer.parseInt(id));
    }

}
