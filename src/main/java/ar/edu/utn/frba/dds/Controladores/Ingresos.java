package ar.edu.utn.frba.dds.Controladores;

import ar.edu.utn.frba.dds.DTO.EgresoDTO;
import ar.edu.utn.frba.dds.DTO.IngresoDTO;
import ar.edu.utn.frba.dds.Entidad.Entidad;
import ar.edu.utn.frba.dds.Operaciones.ItemOperacionEgreso;
import ar.edu.utn.frba.dds.Operaciones.Proveedor;
import ar.edu.utn.frba.dds.Repositorios.RepositorioEgresos;
import ar.edu.utn.frba.dds.Repositorios.RepositorioEntidades;
import ar.edu.utn.frba.dds.Repositorios.RepositorioItemsOperacionEgreso;
import ar.edu.utn.frba.dds.Repositorios.RepositorioProveedores;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ingresos {

    public static ModelAndView paginaIngresos(Request request, Response response) {
        if(request.session(false) == null) {
            return Login.paginaLogin(request,response);
        }
        else {
            List<IngresoDTO> ingresos = new ArrayList<>();
            List<Entidad> entidades = RepositorioEntidades.getInstance().obtenerEntidades();
            entidades.forEach(entidad -> entidad.getIngresos().forEach(ingreso -> ingresos.add(new IngresoDTO(ingreso,entidad))));
            Map<String, Object> map = new HashMap<>();
            map.put("ingresos",ingresos);
            return new ModelAndView(map, "ingresos.html");
        }
    }
}
