package ar.edu.utn.frba.dds.Entidad;

import java.util.ArrayList;
import java.util.List;

public class Recategorizador {
    private List<Empresa> empresas;
    private static Recategorizador instancia = null;

    public static Recategorizador getInstance() {
        if(instancia == null) {
            instancia = new Recategorizador();
        }
        return instancia;
    }

    public Recategorizador() {
        this.empresas = new ArrayList<>();
    }

    public void recategorizarEmpresas(){
        empresas.forEach(empresa -> empresa.recategorizar());
    }

    public void agregarEmpresa(Empresa empresa){
        this.empresas.add(empresa);
    }

    public void quitarEmpresa(Empresa empresa){
        this.empresas.remove(empresa);
    }
}
