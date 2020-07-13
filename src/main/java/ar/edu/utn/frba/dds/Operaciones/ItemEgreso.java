package ar.edu.utn.frba.dds.Operaciones;

public class ItemEgreso {
    private String descripcion;
    private CategoriaItem categoria;
    private Double precio;
    private TipoItem tipo;
    private String codigo;

    public ItemEgreso(String codigo, String descripcion, Double precio, TipoItem tipo, CategoriaItem categoria) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tipo = tipo;
        this.categoria = categoria;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public TipoItem getTipo() { return tipo; }
    public void setTipo(TipoItem tipo) { this.tipo = tipo; }

    public CategoriaItem getCategoria() { return categoria; }
    public void setCategoria(CategoriaItem categoria) { this.categoria = categoria; }
}