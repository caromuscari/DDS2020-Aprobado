package ar.edu.utn.frba.dds.Vinculador;

import ar.edu.utn.frba.dds.Operaciones.Egreso;
import ar.edu.utn.frba.dds.Operaciones.Ingreso;

import java.time.LocalDate;

public class CondicionFecha implements CondicionVinculacion{
    private Integer diasAntes;
    private Integer diasDespues;

    public Integer getDiasAntes() { return diasAntes; }
    public void setDiasAntes(Integer diasAntes) { this.diasAntes = diasAntes; }

    public Integer getDiasDespues() { return diasDespues; }
    public void setDiasDespues(Integer diasDespues) { this.diasDespues = diasDespues; }

    //Metodos
    @Override
    public boolean validarCondicion(Egreso egreso, Ingreso ingreso) {
        LocalDate fechadesde =  ingreso.getFecha().minusDays(this.diasAntes);
        LocalDate fechahasta = ingreso.getFecha().plusDays(this.diasDespues);
        if (egreso.getFecha().isAfter(fechadesde) && egreso.getFecha().isBefore(fechahasta)) return true;
        else return false;
    }
}
