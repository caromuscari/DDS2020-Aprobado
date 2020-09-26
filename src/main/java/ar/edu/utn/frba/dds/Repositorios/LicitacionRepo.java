package ar.edu.utn.frba.dds.Repositorios;

import java.util.List;
import java.util.Optional;

import ar.edu.utn.frba.dds.Licitacion.Licitacion;

public class LicitacionRepo {
    static List<Licitacion> licitaciones;

    public static Licitacion find(String id){
        return licitaciones.stream().filter(l -> l.getNombre().matches(id)).findFirst().get();
    }

    public static void add(Licitacion licitacion){
        licitaciones.add(licitacion);
    }

    public static void remove(Licitacion licitacion){ licitaciones.remove(licitacion); }
}
