package ar.edu.utn.frba.dds.Egreso;

public class Item {
    private String codigo;
    private String descripcion;
    private Double precio;
    private TipoItem tipo;

    public Item(String codigo, String descripcion, Double precio, TipoItem tipo) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tipo = tipo;
    }

    public String getCodigo() { return codigo; }

    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getPrecio() { return precio; }

    public void setPrecio(Double precio) { this.precio = precio; }

    public TipoItem getTipo() { return tipo; }

    public void setTipo(TipoItem tipo) { this.tipo = tipo; }
}
