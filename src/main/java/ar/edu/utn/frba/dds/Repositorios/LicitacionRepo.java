package ar.edu.utn.frba.dds.Repositorios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ar.edu.utn.frba.dds.Licitacion.Licitacion;

public class LicitacionRepo {

    static List<Licitacion> licitaciones = new ArrayList<>();

    public static LicitacionRepo instance = null;

    public static Optional<Licitacion> find(String id){
        return licitaciones.stream()
                .filter(l -> l.getNombre().matches(id))
                .findFirst();
    }

    public static LicitacionRepo getInstance() {
        if (instance == null) {
            instance = new LicitacionRepo();
        }
        return instance;
    }

    public static void add(Licitacion licitacion){
        licitaciones.add(licitacion);
    }

    public static void remove(Licitacion licitacion){ licitaciones.remove(licitacion); }
}
