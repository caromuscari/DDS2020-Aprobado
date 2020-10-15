package ar.edu.utn.frba.dds.Vinculador;

import ar.edu.utn.frba.dds.Entidad.Entidad;
import ar.edu.utn.frba.dds.Operaciones.Egreso;
import ar.edu.utn.frba.dds.Operaciones.Ingreso;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class ResultadoVinculacion {
    private String usuario;
    private String fechaVinculacion; // Dia de vinculacion (para el json)
    private String horaVinculacion; // Hora de la vinculacion
    private LocalDate fecha;
    private Entidad entidad;
    private List<Ingreso> ingresosVinculados;
    private List<Egreso> egresosNoVinculados;
    private List<Ingreso> ingresosNoCompletos;

    public ResultadoVinculacion(Entidad entidad, LocalDate fecha, List<Ingreso> ingresosNoCompletos, List<Egreso> egresosNoVinculados, List<Ingreso> ingresosVinculados) {
        this.fecha = fecha;
        this.fechaVinculacion = fecha.toString();
        this.entidad = entidad;
        this.egresosNoVinculados = egresosNoVinculados;
        this.ingresosNoCompletos = ingresosNoCompletos;
        this.ingresosVinculados = ingresosVinculados;
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

    public String getFechaVinculacion() { return fechaVinculacion; }
    public void setFechaVinculacion(String fechaVinculacion) { this.fechaVinculacion = fechaVinculacion; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public List<Ingreso> getIngresosVinculados() { return ingresosVinculados; }
    public void setIngresosVinculados(List<Ingreso> ingresosVinculados) { this.ingresosVinculados = ingresosVinculados; }
}
