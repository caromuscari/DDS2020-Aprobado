package ar.edu.utn.frba.dds.Repositorios;

import ar.edu.utn.frba.dds.Entidad.Entidad;
import ar.edu.utn.frba.dds.Licitacion.Presupuesto;
import ar.edu.utn.frba.dds.Operaciones.Egreso;
import ar.edu.utn.frba.dds.Operaciones.Ingreso;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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

    public void crearEntidad(Entidad entidad) { this.entityManager.persist(entidad); }

    public void eliminarEntidad(Entidad entidad){ this.entityManager.remove(entidad); }

    public List<Entidad> obtenerEntidades(){;
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Entidad> consulta = cb.createQuery(Entidad.class);
        Root<Entidad> entidades = consulta.from(Entidad.class);
        return this.entityManager.createQuery(consulta.select(entidades)).getResultList();
    }

    public Egreso obtenerEgresoPorId(String id) {
        return this.entityManager.find(Egreso.class, Integer.parseInt(id));
    }

    public Egreso obtenerEgresoPorNombre(String nombre) {
        for (Egreso egreso : obtenerEntidades().stream().map(e -> e.getEgresos()).flatMap(List::stream).collect(Collectors.toList())) {
            if (nombre.equals(egreso.getNombre())) {
                return egreso;
            }
        }
        return null;
    }

    public Entidad obtenerEntidadDeEgreso(Egreso egreso){
        return obtenerEntidades().stream().filter(e -> e.getEgresos().contains(egreso)).findFirst().get();
    }

    public Entidad obtenerEntidadPorNombre(String nombreEntidad){
        return obtenerEntidades().stream().filter(e -> e.getNombre().equals(nombreEntidad)).findFirst().get();
    }

    public void borrarEgreso(String id) {
        Egreso e = this.entityManager.find(Egreso.class, Integer.parseInt(id));
        this.entityManager.remove(e);
    }

    public Ingreso obtenerIngresoPorId(String id) {
        for (Ingreso ingreso : obtenerEntidades().stream().map(e -> e.getIngresos()).flatMap(List::stream).collect(Collectors.toList())) {
            if (Integer.parseInt(id) == ingreso.getId()) {
                return ingreso;
            }
        }
        return null;
    }

    public Presupuesto obtenerPresupuestoPorId(String id) {
        for (Presupuesto presupuesto : obtenerEntidades().stream().map(e -> e.getLicitaciones()).flatMap(List::stream).map(l -> l.getPresupuestos()).flatMap(List::stream).collect(Collectors.toList())) {
            if (Integer.parseInt(id) == presupuesto.getId()) {
                return presupuesto;
            }
        }
        return null;
    }

}
