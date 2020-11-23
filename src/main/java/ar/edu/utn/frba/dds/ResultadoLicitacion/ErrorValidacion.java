package ar.edu.utn.frba.dds.ResultadoLicitacion;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipoError", discriminatorType = DiscriminatorType.STRING)
public abstract class ErrorValidacion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    protected int id;

    public abstract String obtenerMensaje();
}
