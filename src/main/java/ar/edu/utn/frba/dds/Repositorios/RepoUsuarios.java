package ar.edu.utn.frba.dds.Repositorios;

import ar.edu.utn.frba.dds.Categorizacion.Categoria;
import ar.edu.utn.frba.dds.Categorizacion.CriterioCategoria;
import ar.edu.utn.frba.dds.Entidad.Organizacion;
import ar.edu.utn.frba.dds.Usuario.Hash;
import ar.edu.utn.frba.dds.Usuario.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepoUsuarios {

    Map<String, Usuario> registrados;
    Hash encriptador;
    public static RepoUsuarios instance = null;

    public RepoUsuarios(){
        encriptador = new Hash();
        registrados = new HashMap<>();

        /* crearCategorías hay que moverlo de acá, yo deje el usuario gesoc de prueba pero lo mude al
        *  ExampleDataCreator
        */

        //crearCategorias(organizacion);
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
