package ar.edu.utn.frba.dds.Repositorios;

import ar.edu.utn.frba.dds.Categorizacion.Categoria;
import ar.edu.utn.frba.dds.Categorizacion.CriterioCategoria;
import ar.edu.utn.frba.dds.Entidad.Organizacion;
import ar.edu.utn.frba.dds.Usuario.Hash;
import ar.edu.utn.frba.dds.Usuario.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepoUsuarios {

    Hash encriptador;
    private EntityManager entityManager;

    public RepoUsuarios(EntityManager entityManager){
        this.entityManager = entityManager;
        encriptador = new Hash();
    }

    public Hash getEncriptador() { return encriptador; }

    public List<Usuario> getRegistrados() {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Usuario> consulta = cb.createQuery(Usuario.class);
        Root<Usuario> usuarios = consulta.from(Usuario.class);
        return this.entityManager.createQuery(consulta.select(usuarios)).getResultList();
    }

    public Usuario buscarUsuario(String nombre){
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Usuario> consulta = cb.createQuery(Usuario.class);
        Root<Usuario> usuario = consulta.from(Usuario.class);
        Predicate condicion = cb.equal(usuario.get("usuario"), nombre);
        CriteriaQuery<Usuario> where = consulta.select(usuario).where(condicion);
        return this.entityManager.createQuery(where).getSingleResult();
    }

    public void agregarUsuario(Usuario usuario) {
        this.entityManager.persist(usuario);
    }

}
