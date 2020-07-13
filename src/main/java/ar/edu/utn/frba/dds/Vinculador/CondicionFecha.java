package ar.edu.utn.frba.dds.Vinculador;

import ar.edu.utn.frba.dds.Operaciones.Egreso;
import ar.edu.utn.frba.dds.Operaciones.Ingreso;

import java.time.LocalDate;

public class CondicionFecha implements CondicionVinculacion{
    private Integer diasAntes;
    private Integer diasDespues;

    private LocalDate fechadesde;
    private LocalDate fechahasta;

    public Integer getDiasAntes() { return diasAntes; }
    public void setDiasAntes(Integer diasAntes) { this.diasAntes = diasAntes; }

    public Integer getDiasDespues() { return diasDespues; }
    public void setDiasDespues(Integer diasDespues) { this.diasDespues = diasDespues; }

    //Metodos
    public void calcularFechas(LocalDate fechaIngreso){
        this.fechadesde =  fechaIngreso.minusDays(this.diasAntes);
        this.fechahasta = fechaIngreso.plusDays(this.diasDespues);
    }

    @Override
    public boolean validarCondicion(Egreso egreso, Ingreso ingreso) {
        if (egreso.getFecha().isAfter(this.fechadesde) && egreso.getFecha().isBefore(this.fechahasta)) return true;
        else return false;
    }
}
