package ar.edu.utn.frba.dds.Operaciones;

import ar.edu.utn.frba.dds.Categorizacion.Categoria;
import ar.edu.utn.frba.dds.DateConverter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Ingreso {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String descripcion;
    private Double montoTotal;

    @Column
    @Convert(converter = DateConverter.class)
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDate fecha;

    @OneToMany
    @JoinColumn(name = "ingreso_id")
    private List<Egreso> egresos;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "ingresos_categorias", joinColumns = @JoinColumn(name = "ingreso_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    private List<Categoria> categorias;

    public Ingreso(){
        // Para Hibernate
    }

    public Ingreso(String descripcion, Double montoTotal, LocalDate fecha) {
        this.descripcion = descripcion;
        this.montoTotal = montoTotal;
        this.fecha = fecha;
        this.egresos = new ArrayList<>();
        this.categorias = new ArrayList<>();
    }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(Double montoTotal) { this.montoTotal = montoTotal; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public List<Egreso> getEgresos() { return egresos; }
    public void setEgresos(List<Egreso> egresos) { this.egresos = egresos; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public List<Categoria> getCategorias() { return categorias; }
    public void setCategorias(List<Categoria> categorias) { this.categorias = categorias; }

    //Metodos
    public void asociarEgreso(Egreso egreso){
        this.egresos.add(egreso);
        egreso.vincular();
    }

    public boolean contieneCategoria(Categoria categoria){
        List<Boolean> resultados = this.categorias.stream().map(c -> {if (c.equals(categoria)) return true;
            return categoria.contieneCategoriaHija(c);}).collect(Collectors.toList());

        return resultados.stream().anyMatch(c -> c.equals(true));
    }

    public Double montoTotalEgresos()
    {
        return egresos.stream().mapToDouble(Egreso::getPrecioTotal).sum();
    }
}
