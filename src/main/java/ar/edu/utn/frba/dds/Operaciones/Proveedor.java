package ar.edu.utn.frba.dds.Operaciones;

import java.util.ArrayList;
import java.util.List;

public class Proveedor {
    private String nombre;
    private Long identificador;
    private String direccionPostal;
    private List<ItemEgreso> items;

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
}
