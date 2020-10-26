package ar.edu.utn.frba.dds.Repositorios;

import ar.edu.utn.frba.dds.Excepciones.UsuarioExistsException;
import ar.edu.utn.frba.dds.Usuario.Hash;
import ar.edu.utn.frba.dds.Usuario.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

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

    public boolean existeUsuario(String usuario){
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> consulta = cb.createQuery(Long.class);
        Root<Usuario> user = consulta.from(Usuario.class);
        Predicate condicion = cb.equal(user.get("usuario"), usuario);
        CriteriaQuery<Long> where = consulta.where(condicion).select(cb.count(user));
        return this.entityManager.createQuery(where).getSingleResult() > 0;
    }

    public void agregarUsuario(Usuario usuario) throws UsuarioExistsException {
        if(!existeUsuario(usuario.getUsuario()))
            this.entityManager.persist(usuario);
        else
            throw new UsuarioExistsException(usuario.getUsuario());
    }

}
