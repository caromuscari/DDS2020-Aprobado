package ar.edu.utn.frba.dds.Controladores;

import ar.edu.utn.frba.dds.DTO.IngresoDTO;
import ar.edu.utn.frba.dds.DTO.LicitacionDTO;
import ar.edu.utn.frba.dds.Entidad.Entidad;
import ar.edu.utn.frba.dds.Repositorios.RepositorioEntidades;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Presupuestos {

    public static ModelAndView paginaPresupuestos(Request request, Response response) {
        if(request.session(false) == null) {
            return Login.paginaLogin(request,response);
        }
        else {
            List<LicitacionDTO> licitaciones = new ArrayList<>();
            List<Entidad> entidades = RepositorioEntidades.getInstance().obtenerEntidades();
            entidades.forEach(entidad -> entidad.getLicitaciones().forEach(licitacion -> licitaciones.add(new LicitacionDTO(licitacion,entidad))));
            Map<String, Object> map = new HashMap<>();
            map.put("licitaciones",licitaciones);
            return new ModelAndView(map, "presupuestos.html");
        }
    }

}
