package ar.edu.utn.frba.dds.Vinculador;

import ar.edu.utn.frba.dds.Operaciones.Egreso;
import ar.edu.utn.frba.dds.Operaciones.Ingreso;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CondicionFecha implements CondicionVinculacion{
    private LocalDate inicioPeriodo;
    private LocalDate finPeriodo;

    public LocalDate getInicioPeriodo() { return inicioPeriodo; }
    public void setInicioPeriodo(LocalDate diasAntes) { this.inicioPeriodo = diasAntes; }

    public LocalDate getFinPeriodo() { return finPeriodo; }
    public void setFinPeriodo(LocalDate finPeriodo) { this.finPeriodo = finPeriodo; }

    //Metodos
    @Override
    public boolean validarCondicion(Egreso egreso, Ingreso ingreso) {
        long diasAnteriores = ChronoUnit.DAYS.between(inicioPeriodo,ingreso.getFecha());
        long diasPosteriores = ChronoUnit.DAYS.between(ingreso.getFecha(), finPeriodo);
        LocalDate fechaDesde = ingreso.getFecha().minusDays(diasAnteriores);
        LocalDate fechaHasta = ingreso.getFecha().plusDays(diasPosteriores);
        return (egreso.getFecha().isAfter(fechaDesde) && egreso.getFecha().isBefore(fechaHasta));
    }
}
