package ar.edu.utn.frba.dds.Repositorios;

import ar.edu.utn.frba.dds.Categorizacion.Categoria;
import ar.edu.utn.frba.dds.Entidad.Entidad;
import ar.edu.utn.frba.dds.Operaciones.Egreso;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class RepositorioCategorias {
    private List<Categoria> categorias = new ArrayList<>();
    private EntityManager entityManager;

    public RepositorioCategorias(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Categoria> getCategorias() {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Categoria> consulta = cb.createQuery(Categoria.class);
        Root<Categoria> categorias = consulta.from(Categoria.class);
        return this.entityManager.createQuery(consulta.select(categorias)).getResultList();
    }

    // Revisar si esto es válido
    public void setCategorias(List<Categoria> categorias) {
        this.entityManager.persist(categorias);
    }
}
