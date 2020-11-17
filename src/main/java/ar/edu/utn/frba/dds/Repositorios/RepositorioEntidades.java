package ar.edu.utn.frba.dds.Repositorios;

import ar.edu.utn.frba.dds.Entidad.Entidad;
import ar.edu.utn.frba.dds.Licitacion.Presupuesto;
import ar.edu.utn.frba.dds.Operaciones.Egreso;
import ar.edu.utn.frba.dds.Operaciones.Ingreso;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioEntidades {

    private EntityManager entityManager;

    // Declaro el EntityManager que va "actuar como repositorio"
    public RepositorioEntidades(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public void persistirEntidad(Entidad entidad) { this.entityManager.persist(entidad); }

    public void eliminarEntidad(Entidad entidad){ this.entityManager.remove(entidad); }

    public List<Entidad> obtenerEntidades(){;
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Entidad> consulta = cb.createQuery(Entidad.class);
        Root<Entidad> entidades = consulta.from(Entidad.class);
        return this.entityManager.createQuery(consulta.select(entidades)).getResultList();
    }

    public Entidad obtenerEntidadDeEgreso(Egreso egreso){
        return obtenerEntidades().stream().filter(e -> e.getEgresos().contains(egreso)).findFirst().get();
    }

    public Entidad obtenerEntidadPorNombre(String nombreEntidad){
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Entidad> consulta = cb.createQuery(Entidad.class);
        Root<Entidad> entidades = consulta.from(Entidad.class);
        Predicate condicion = cb.equal(entidades.get("nombre"), nombreEntidad);
        CriteriaQuery<Entidad> where = consulta.select(entidades).where(condicion);
        return this.entityManager.createQuery(where).getSingleResult();
    }

}
