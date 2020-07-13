package ar.edu.utn.frba.dds.Operaciones;

public class DocumentoComercial {
    private Long id;
    private TipoDocumento tipo;
    private String url;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public TipoDocumento getTipo() { return tipo; }
    public void setTipo(TipoDocumento tipo) { this.tipo = tipo; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}
