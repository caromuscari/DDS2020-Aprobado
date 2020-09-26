package ar.edu.utn.frba.dds.Repositorios;

import ar.edu.utn.frba.dds.Operaciones.MedioDePago;
import ar.edu.utn.frba.dds.Operaciones.TipoPago;

import java.util.ArrayList;
import java.util.List;

public class RepositorioMedioDePago {
    public static RepositorioMedioDePago instance = null;
    private List<MedioDePago> medios = new ArrayList<>();

    private RepositorioMedioDePago() {}

    public static RepositorioMedioDePago getInstance() {
        if (instance == null) {
            instance = new RepositorioMedioDePago();
        }
        return instance;
    }

    public MedioDePago crearMedioDePago(String identificador, Long numero, TipoPago tipo) {
        MedioDePago medio = new MedioDePago(identificador, numero, tipo);
        medios.add(medio);
        return medio;
    }

    public List<MedioDePago> obtenerMediosDePago() {
        return medios;
    }

    public MedioDePago obtenerItemsPorId(String id) {
        for (MedioDePago medio : medios) {
            if (medio.getIdentificador().equals(id)) {
                return medio;
            }
        }
        return null;
    }

    public void eliminarMedioDePago(MedioDePago medio){
        medios.remove(medio);
    }

}
