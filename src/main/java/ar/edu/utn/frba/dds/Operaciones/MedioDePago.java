package ar.edu.utn.frba.dds.Operaciones;

public class MedioDePago {
    private String identificador;
    private Long numero;
    private TipoPago tipo;

    public Long getNumero() {
        return numero;
    }
    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public TipoPago getTipo() {
        return tipo;
    }
    public void setTipo(TipoPago tipo) {
        this.tipo = tipo;
    }

    public String getIdentificador() {
        return identificador;
    }
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public MedioDePago(String identificador, Long numero, TipoPago tipo) {
        this.identificador = identificador;
        this.numero = numero;
        this.tipo = tipo;
    }
}
