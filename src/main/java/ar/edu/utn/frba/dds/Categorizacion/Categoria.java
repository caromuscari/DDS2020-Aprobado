package ar.edu.utn.frba.dds.Categorizacion;

import java.util.List;
import java.util.stream.Collectors;

public class Categoria {
    private String nombre;
    private CriterioCategoria criterioHijo;

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public CriterioCategoria getCriterioHijo() { return criterioHijo; }
    public void setCriterioHijo(CriterioCategoria criterioHijo) { this.criterioHijo = criterioHijo; }

    //Metodos
    public boolean contieneCategoriaHija(Categoria categoria){
        if (this.criterioHijo == null) return false;
        List<Boolean> resultados = this.criterioHijo.getCategorias().stream().map(c -> {if (c.equals(categoria)) return true;
                                                        return c.contieneCategoriaHija(categoria);}).collect(Collectors.toList());
        return resultados.stream().anyMatch(c -> c.equals(true));
    }
}
