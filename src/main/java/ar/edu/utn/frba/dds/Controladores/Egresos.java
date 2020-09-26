package ar.edu.utn.frba.dds.Controladores;

import ar.edu.utn.frba.dds.DTO.EgresoDTO;
import ar.edu.utn.frba.dds.Entidad.Entidad;
import ar.edu.utn.frba.dds.Repositorios.RepositorioEntidades;
import ar.edu.utn.frba.dds.Operaciones.*;
import ar.edu.utn.frba.dds.Operaciones.Proveedor;
import ar.edu.utn.frba.dds.Repositorios.RepositorioEgresos;
import ar.edu.utn.frba.dds.Repositorios.RepositorioItemsOperacionEgreso;
import ar.edu.utn.frba.dds.Repositorios.RepositorioProveedores;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Egresos {

    public static ModelAndView paginaEgresos(Request request, Response response) {
        if(request.session(false) == null) {
            return Login.paginaLogin(request,response);
        }
        else {
            List<EgresoDTO> egresos = new ArrayList<>();
            List<Entidad> entidades = RepositorioEntidades.getInstance().obtenerEntidades();
            entidades.forEach(entidad -> entidad.getEgresos().forEach(egreso -> egresos.add(new EgresoDTO(egreso,entidad))));
            Map<String, Object> map = new HashMap<>();
            map.put("egresos",egresos);
            return new ModelAndView(map, "egresos.html");
        }
    }

    public static ModelAndView paginaNuevoEgreso(Request request, Response response) {
        if(request.session(false) == null) {
            return Login.paginaLogin(request,response);
        }
        else {
            Map<String, Object> map = new HashMap<>();
            return new ModelAndView(map, "nuevoEgreso.html");
        }
    }

    public static ModelAndView paginaModificarEgreso(Request request, Response response) {
        if(request.session(false) == null) {
            return Login.paginaLogin(request,response);
        }
        else {
            Map<String, Object> map = new HashMap<>();
            return new ModelAndView(map, "modificarEgreso.html");
        }
    }

    public static ModelAndView nuevoEgreso(Request request, Response response) {
        if(request.session(false) == null) {
            return Login.paginaLogin(request, response);
        }

        List<ItemOperacionEgreso> items = new ArrayList<>();
        String[] itemsId = request.queryParamsValues("items");
        for (String id : itemsId) {
            ItemOperacionEgreso itemOperacionEgreso = RepositorioItemsOperacionEgreso.getInstance().obtenerItemsPorId(Long.parseLong(id));
            if (itemOperacionEgreso != null) {
                items.add(itemOperacionEgreso);
            }
        }

        String nombreProveedor = request.queryParams("nombreProveedor");
        String nombreEgreso = request.queryParams("nombreEgreso");

        Proveedor proveedor = RepositorioProveedores.getInstance().obtenerProveedorPorNombre(nombreProveedor);

        RepositorioEgresos.getInstance().crearEgreso(items, proveedor, nombreEgreso);

        response.redirect("/egreso");
        return null;
    }
}
