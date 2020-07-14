package ar.edu.utn.frba.dds.Licitacion;

import ar.edu.utn.frba.dds.BandejaDeMendajes.NotificadorValidador;
import ar.edu.utn.frba.dds.Operaciones.Egreso;
import ar.edu.utn.frba.dds.ResultadoLicitacion.ResultadoValidacion;
import ar.edu.utn.frba.dds.Usuario.Usuario;
import ar.edu.utn.frba.dds.Vinculador.CriterioEjecucion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Licitacion {
    private String nombre;
    private List<Presupuesto> presupuestos;
    private Egreso egreso;
    private Integer presupuestosRequeridos;
    private List<CriterioSeleccion> criterios;
    private NotificadorValidador notificador;

    public Licitacion(String nombre, Integer presupuestosRequeridos) {
        this.nombre = nombre;
        this.presupuestosRequeridos = presupuestosRequeridos;
        this.presupuestos = new ArrayList<Presupuesto>();
        this.criterios = new ArrayList<CriterioSeleccion>();
        this.notificador = new NotificadorValidador();
    }

    //Setters y Getters
    public List<Presupuesto> getPresupuestos() { return presupuestos; }
    public void setPresupuestos(List<Presupuesto> presupuestos) { this.presupuestos = presupuestos; }

    public Egreso getEgreso() { return egreso; }
    public void setEgreso(Egreso egreso) { this.egreso = egreso; }

    public Integer getPresupuestosRequeridos() { return presupuestosRequeridos; }
    public void setPresupuestosRequeridos(Integer presupuestosRequeridos) { this.presupuestosRequeridos = presupuestosRequeridos; }

    public List<CriterioSeleccion> getCriterios() { return criterios; }
    public void setCriterios(List<CriterioSeleccion> criterios) { this.criterios = criterios; }

    public NotificadorValidador getNotificador() { return notificador; }
    public void setNotificador(NotificadorValidador notificador) { this.notificador = notificador; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }


    //Metodos
    public void agregarPresupuesto(Presupuesto presupuesto){
        this.presupuestos.add(presupuesto);
    }

    public void agregarRevisor(Usuario usuario){
        this.notificador.suscribir(usuario);
    }

    public void quitarRevisor(Usuario usuario){
        this.notificador.desuscribir(usuario);
    }

    public void validarLicitacion(){
        List<ResultadoValidacion> resultados = this.criterios.stream().map(c -> c.validar(this)).collect(Collectors.toList());
        this.notificador.notificar(resultados);
    }

    public void agregarCriterio(CriterioSeleccion criterio){
        this.criterios.add(criterio);
    }
}
