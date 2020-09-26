package ar.edu.utn.frba.dds.Controladores;

import ar.edu.utn.frba.dds.Operaciones.*;
import ar.edu.utn.frba.dds.Operaciones.Proveedor;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Egreso {

    public static ModelAndView paginaEgresos(Request request, Response response) {
        if(request.session(false) == null) {
            return Login.paginaLogin(request,response);
        }
        else {
            Map<String, Object> map = new HashMap<>();
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
