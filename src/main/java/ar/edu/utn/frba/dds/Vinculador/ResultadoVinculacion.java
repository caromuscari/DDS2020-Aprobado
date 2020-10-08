package ar.edu.utn.frba.dds.Vinculador;

import ar.edu.utn.frba.dds.Entidad.Entidad;
import ar.edu.utn.frba.dds.Operaciones.Egreso;
import ar.edu.utn.frba.dds.Operaciones.Ingreso;

import java.time.LocalDate;
import java.util.List;

public class ResultadoVinculacion {
    private String usuario;
    private String fechaVinculacion; // Dia de vinculacion (para el json)
    private String horaVinculacion; // Hora de la vinculacion
    private LocalDate fecha;
    private Entidad entidad;
    private List<Ingreso> ingresosNoCompletos;
    private List<Egreso> egresosNoVinculados;

    public ResultadoVinculacion(Entidad entidad, LocalDate fecha, List<Ingreso> ingresosNoCompletos, List<Egreso> egresosNoVinculados) {
        this.fecha = fecha;
        this.fechaVinculacion = fecha.toString();
        this.entidad = entidad;
        this.ingresosNoCompletos = ingresosNoCompletos;
        this.egresosNoVinculados = egresosNoVinculados;
    }

    public Entidad getEntidad() { return entidad; }
    public void setEntidad(Entidad entidad) { this.entidad = entidad; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public List<Ingreso> getIngresosNoCompletos() { return ingresosNoCompletos; }
    public void setIngresosNoCompletos(List<Ingreso> ingresosNoCompletos) { this.ingresosNoCompletos = ingresosNoCompletos; }

    public List<Egreso> getEgresosNoVinculados() { return egresosNoVinculados; }
    public void setEgresosNoVinculados(List<Egreso> egresosNoVinculados) { this.egresosNoVinculados = egresosNoVinculados; }

    public String getUser() { return usuario; }
    public void setUser(String usuario) { this.usuario = usuario; }

    public String getHoraVinculacion() { return horaVinculacion; }
    public void setHoraVinculacion(String horaVinculacion) { this.horaVinculacion = horaVinculacion; }
}
