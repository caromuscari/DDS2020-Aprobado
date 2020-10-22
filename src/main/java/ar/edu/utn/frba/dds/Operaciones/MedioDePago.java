package ar.edu.utn.frba.dds.Operaciones;

import javax.persistence.*;

@Entity
public class MedioDePago {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String identificador;
    private Long numero;
    private String tipo;

    public Long getNumero() {
        return numero;
    }
    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIdentificador() {
        return identificador;
    }
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public MedioDePago(String identificador, Long numero, String tipo) {
        this.identificador = identificador;
        this.numero = numero;
        this.tipo = tipo;
    }
}
