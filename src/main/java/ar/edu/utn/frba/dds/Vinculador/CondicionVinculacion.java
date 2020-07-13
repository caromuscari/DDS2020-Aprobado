package ar.edu.utn.frba.dds.Vinculador;

import ar.edu.utn.frba.dds.Operaciones.Egreso;
import ar.edu.utn.frba.dds.Operaciones.Ingreso;

public interface CondicionVinculacion {
    public boolean validarCondicion(Egreso egreso, Ingreso ingreso);
}
