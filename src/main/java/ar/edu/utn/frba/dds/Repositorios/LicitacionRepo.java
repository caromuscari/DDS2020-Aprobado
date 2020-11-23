package ar.edu.utn.frba.dds.Repositorios;

import ar.edu.utn.frba.dds.Entidad.Entidad;
import ar.edu.utn.frba.dds.Licitacion.Licitacion;
import ar.edu.utn.frba.dds.Operaciones.Egreso;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.invoke.LambdaConversionException;
import java.util.List;
import java.util.stream.Collectors;

public class LicitacionRepo {

    private EntityManager entityManager;

    public LicitacionRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Licitacion obtenerLicitacionPorID(String id){
        return this.entityManager.find(Licitacion.class, Integer.parseInt(id));
    }

    public List<Licitacion> obtenerLicitaciones(){;
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Licitacion> consulta = cb.createQuery(Licitacion.class);
        Root<Licitacion> licitaciones = consulta.from(Licitacion.class);
        return this.entityManager.createQuery(consulta.select(licitaciones)).getResultList();
    }

    public List<Licitacion> obtenerLicitacionesConEgresoAsociado(Integer idEgreso){
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Licitacion> consulta = cb.createQuery(Licitacion.class);
        Root<Licitacion> licitaciones = consulta.from(Licitacion.class);
        List<Licitacion> result = this.entityManager.createQuery(consulta.select(licitaciones)).getResultList();
        return result.stream().filter(l -> comparador(l,idEgreso.intValue())).collect(Collectors.toList());
    }

    public void remove(Licitacion licitacion){ this.entityManager.remove(licitacion); }

    public void persistir(Licitacion licitacion) {
        this.entityManager.persist(licitacion);
    }

    public void desasociarLicitacion(Licitacion licitacion){ licitacion.setEgreso(null); }

    private boolean comparador(Licitacion a, int b){
        if(a.getEgreso() != null)
            return a.getEgreso().getId() == b;
        return false;
    }
}
