package ar.edu.utn.frba.dds.Egreso;

public class MedioDePago {
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
}
