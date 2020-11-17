package ar.edu.utn.frba.dds.Vinculador;

import ar.edu.utn.frba.dds.Operaciones.Egreso;
import ar.edu.utn.frba.dds.Operaciones.Ingreso;

import java.time.LocalDate;

public class CondicionFecha implements CondicionVinculacion{
    private int diasAnteriores;
    private int diasPosteriores;

    public int getDiasAnteriores() { return diasAnteriores; }
    public void setDiasAnteriores(int diasAnteriores) { this.diasAnteriores = diasAnteriores; }

    public int getDiasPosteriores() { return diasPosteriores; }
    public void setDiasPosteriores(int diasPosteriores) { this.diasPosteriores = diasPosteriores; }

    //Metodos
    @Override
    public boolean validarCondicion(Egreso egreso, Ingreso ingreso) {
        long diasAnteriores = this.diasAnteriores;
        long diasPosteriores = this.diasPosteriores;
        LocalDate fechaDesde = ingreso.getFecha().minusDays(diasAnteriores);
        LocalDate fechaHasta = ingreso.getFecha().plusDays(diasPosteriores);
        return (egreso.getFecha().isAfter(fechaDesde) && egreso.getFecha().isBefore(fechaHasta));
    }
}
