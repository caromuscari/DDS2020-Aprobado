package ar.edu.utn.frba.dds.Controladores;

import ar.edu.utn.frba.dds.Operaciones.RepositorioItemsOperacionEgreso;
import ar.edu.utn.frba.dds.Operaciones.RepositorioProveedores;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;
import spark.Response;

import java.util.List;

public class Proveedor {

    public static String proveedores(Request request, Response response) {
        /*if (request.session(false) == null) {
            return Login.paginaLogin(request, response);
        }*/

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(RepositorioProveedores.getInstance().obtenerProveedores());
        } catch (Exception e) {

        }

        return null;
        /*Map<String, Object> map = new HashMap<>();
        return new ModelAndView(map, "egresos.html");*/
    }
}
