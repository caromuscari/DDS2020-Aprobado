package ar.edu.utn.frba.dds.Repositorios;

import ar.edu.utn.frba.dds.Categorizacion.Categoria;
import ar.edu.utn.frba.dds.Categorizacion.CriterioCategoria;
import ar.edu.utn.frba.dds.Entidad.Organizacion;
import ar.edu.utn.frba.dds.Operaciones.Proveedor;
import ar.edu.utn.frba.dds.Usuario.Hash;
import ar.edu.utn.frba.dds.Usuario.TipoPerfil;
import ar.edu.utn.frba.dds.Usuario.Usuario;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepoUsuarios {

    Map<String, Usuario> registrados;
    Hash encriptador;
    public static RepoUsuarios instance = null;

    public RepoUsuarios(){
        registrados = new HashMap<>();
        encriptador = new Hash();

        Usuario user = null;
        try{
            Organizacion organizacion = new Organizacion("organizacion 1", "descripcion");
            crearCategorias(organizacion);
            user = new Usuario("gesoc", encriptador.hashear("prueba"), TipoPerfil.OPERADOR, organizacion);
        }
        catch (NoSuchAlgorithmException e) { e.printStackTrace(); }

        registrados.put("gesoc",user);
    }

    public static RepoUsuarios getInstance() {
        if (instance == null) {
            instance = new RepoUsuarios();
        }
        return instance;
    }

    // Getters
    public Map<String, Usuario> getRegistrados() { return registrados; }
    public Hash getEncriptador() { return encriptador; }

    public Usuario buscarUsuario(String nombre){
        return registrados.get(nombre);
    }

    public void agregarUsuario(Usuario usuario) {
        registrados.put(usuario.getUsuario(),usuario);
    }

    public void crearCategorias(Organizacion org){
        Categoria america = new Categoria("America");
        Categoria asia = new Categoria("Asia");

        List<Categoria> categoriasPaises = new ArrayList<>();
        Categoria argentina = new Categoria("Argentina");
        categoriasPaises.add(argentina);
        categoriasPaises.add(new Categoria("Peru"));

        List<Categoria> categoriasContinente = new ArrayList<>();
        categoriasContinente.add(america);
        categoriasContinente.add(asia);

        CriterioCategoria criterioContinente = new CriterioCategoria("Continente", categoriasContinente);
        CriterioCategoria criterioPais = new CriterioCategoria("Pais", categoriasPaises);

        america.setCriterioHijo(criterioPais);

        org.asociarCriterio(criterioContinente);
        org.asociarCriterio(criterioPais);
    }
}
