package ar.edu.utn.frba.dds.Repositorios;

import ar.edu.utn.frba.dds.Entidad.Entidad;
import ar.edu.utn.frba.dds.Licitacion.Presupuesto;
import ar.edu.utn.frba.dds.Operaciones.Egreso;

import javax.persistence.EntityManager;

public class RepositorioPresupuesto {

    private EntityManager entityManager;

    // Declaro el EntityManager que va "actuar como repositorio"
    public RepositorioPresupuesto(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public void crearPresupuesto(Presupuesto presupuesto) { this.entityManager.persist(presupuesto); }

    public void eliminarPresupuesto(Presupuesto presupuesto){ this.entityManager.remove(presupuesto); }

    public Presupuesto obtenerPresupuestoPorId(String id) {
        return this.entityManager.find(Presupuesto.class, Integer.parseInt(id));
    }
}
