package ar.edu.utn.frba.dds.Licitacion;

import ar.edu.utn.frba.dds.ResultadoLicitacion.ResultadoValidacion;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipoCriterioSeleccion", discriminatorType = DiscriminatorType.STRING)
public abstract class CriterioSeleccion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public abstract ResultadoValidacion validar(Licitacion licitacion);
}
