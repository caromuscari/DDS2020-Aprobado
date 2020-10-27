package ar.edu.utn.frba.dds.Repositorios;

import ar.edu.utn.frba.dds.Licitacion.Presupuesto;
import ar.edu.utn.frba.dds.Operaciones.Ingreso;

import javax.persistence.EntityManager;

public class RepositorioIngreso {
    private EntityManager entityManager;

    // Declaro el EntityManager que va "actuar como repositorio"
    public RepositorioIngreso(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public void crearIngreso(Ingreso ingreso) { this.entityManager.persist(ingreso); }

    public void eliminarIngreso(Ingreso ingreso){ this.entityManager.remove(ingreso); }

    public Ingreso obtenerIngresoPorId(String id) {
        return this.entityManager.find(Ingreso.class, Integer.parseInt(id));
    }

}
