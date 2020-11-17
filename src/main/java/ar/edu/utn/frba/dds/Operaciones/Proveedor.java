package ar.edu.utn.frba.dds.Operaciones;

import ar.edu.utn.frba.dds.Categorizacion.Categoria;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nombre;
    private Long identificador;
    private String direccionPostal;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "proveedor_id")
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ItemEgreso> items;

    @OneToMany
    @JoinTable(name = "proveedor_categorias", joinColumns = @JoinColumn(name = "proveedor_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    private List<Categoria> categorias;

    public Proveedor(){
        // Para Hibernate
    }

    public Proveedor(String nombre, Long identificador, String direccionPostal) {
        this.nombre = nombre;
        this.identificador = identificador;
        this.direccionPostal = direccionPostal;
        this.items = new ArrayList<>();
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Long getIdentificador() { return identificador; }
    public void setIdentificador(Long identificador) { this.identificador = identificador; }

    public String getDireccionPostal() { return direccionPostal; }
    public void setDireccionPostal(String direccionPostal) { this.direccionPostal = direccionPostal; }

    public List<ItemEgreso> getItems() { return items; }
    public void setItems(List<ItemEgreso> items) { this.items = items; }

    public List<Categoria> getCategorias() { return categorias; }
    public void setCategorias(List<Categoria> categorias) { this.categorias = categorias; }

    public void addItem(ItemEgreso item){
        this.items.add(item);
    }
}
