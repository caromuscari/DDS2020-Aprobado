package ar.edu.utn.frba.dds.Categorizacion;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class CriterioCategoria {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "criterioPadre_id")
    private List<Categoria> categorias;

    public CriterioCategoria(String nombre, List<Categoria> categorias) {
        this.nombre = nombre;
        this.categorias = categorias;
    }

    public CriterioCategoria() {
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<Categoria> getCategorias() { return categorias; }
    public void setCategorias(List<Categoria> categorias) { this.categorias = categorias; }

    public List<Categoria> getTotalCategorias() {
        List<Categoria> categorias = this.categorias.stream().map(c -> c.getCategoriasHijas()).flatMap(List::stream).collect(Collectors.toList());
        categorias.addAll(this.categorias);
        return categorias;
    }
}
