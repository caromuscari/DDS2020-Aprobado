package ar.edu.utn.frba.dds.Repositorios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ar.edu.utn.frba.dds.Licitacion.Licitacion;

import javax.persistence.EntityManager;

public class LicitacionRepo {

    private EntityManager entityManager;

    public LicitacionRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Licitacion obtenerLicitacionPorID(String id){
        return this.entityManager.find(Licitacion.class, Integer.parseInt(id));
    }

    public void remove(Licitacion licitacion){ this.entityManager.remove(licitacion); }

    public void persistir(Licitacion licitacion) {
        this.entityManager.persist(licitacion);
    }
}
