package ar.edu.utn.frba.dds.Egreso;

public class Proveedor {
    private String nombre;
    private Long identificador;
    private String direccionPostal;

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public Long getIdentificador() { return identificador; }

    public void setIdentificador(Long identificador) { this.identificador = identificador; }

    public String getDireccionPostal() { return direccionPostal; }

    public void setDireccionPostal(String direccionPostal) { this.direccionPostal = direccionPostal; }
}
