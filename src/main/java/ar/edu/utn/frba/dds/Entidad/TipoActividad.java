package ar.edu.utn.frba.dds.Entidad;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TipoActividad {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int limiteInferiorIngresos;
    private int limiteMedioIngresos;
    private int limiteSuperiorIngresos;

    private int limiteInferiorEmpleados;
    private int limiteMedioEmpleados;
    private int limiteSuperiorEmpleados;

    private String nombre;

    public int getLimiteInferiorIngresos() {
        return limiteInferiorIngresos;
    }
    public void setLimiteInferiorIngresos(int limiteInferiorIngresos) {
        this.limiteInferiorIngresos = limiteInferiorIngresos;
    }

    public int getLimiteMedioIngresos() {
        return limiteMedioIngresos;
    }
    public void setLimiteMedioIngresos(int limiteMedioIngresos) {
        this.limiteMedioIngresos = limiteMedioIngresos;
    }

    public int getLimiteSuperiorIngresos() {
        return limiteSuperiorIngresos;
    }
    public void setLimiteSuperiorIngresos(int limiteSuperiorIngresos) {
        this.limiteSuperiorIngresos = limiteSuperiorIngresos;
    }

    public int getLimiteInferiorEmpleados() {
        return limiteInferiorEmpleados;
    }
    public void setLimiteInferiorEmpleados(int limiteInferiorEmpleados) {
        this.limiteInferiorEmpleados = limiteInferiorEmpleados;
    }

    public int getLimiteMedioEmpleados() {
        return limiteMedioEmpleados;
    }
    public void setLimiteMedioEmpleados(int limiteMedioEmpleados) {
        this.limiteMedioEmpleados = limiteMedioEmpleados;
    }

    public int getLimiteSuperiorEmpleados() {
        return limiteSuperiorEmpleados;
    }
    public void setLimiteSuperiorEmpleados(int limiteSuperiorEmpleados) {
        this.limiteSuperiorEmpleados = limiteSuperiorEmpleados;
    }

    public TipoActividad(int limiteInferiorIngresos, int limiteMedioIngresos, int limiteSuperiorIngresos, int limiteInferiorEmpleados, int limiteMedioEmpleados, int limiteSuperiorEmpleados, String nombre) {
        this.limiteInferiorIngresos = limiteInferiorIngresos;
        this.limiteMedioIngresos = limiteMedioIngresos;
        this.limiteSuperiorIngresos = limiteSuperiorIngresos;
        this.limiteInferiorEmpleados = limiteInferiorEmpleados;
        this.limiteMedioEmpleados = limiteMedioEmpleados;
        this.limiteSuperiorEmpleados = limiteSuperiorEmpleados;
        this.nombre = nombre;
    }


    //Metodos
    public TipoEmpresa obtenerTipoEmpresa(Double ventasPromedio, int cantidadEmpleados) {
        if(ventasPromedio < limiteInferiorIngresos && cantidadEmpleados < limiteInferiorEmpleados){
            return TipoEmpresa.Micro;
        }else if(ventasPromedio < limiteMedioIngresos && cantidadEmpleados < limiteMedioEmpleados){
            return TipoEmpresa.Pequeña;
        }else if(ventasPromedio < limiteSuperiorIngresos && cantidadEmpleados < limiteSuperiorEmpleados){
            return TipoEmpresa.MedianaT1;
        }else{
            return TipoEmpresa.MedianaT2;
        }
    }


}
