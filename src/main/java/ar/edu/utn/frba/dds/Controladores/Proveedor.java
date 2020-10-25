package ar.edu.utn.frba.dds.Controladores;

import ar.edu.utn.frba.dds.Repositorios.RepositorioProveedores;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;

public class Proveedor {

    public static String proveedores(Request request, Response response, EntityManager entity) {
        /*if (request.session(false) == null) {
            return Login.paginaLogin(request, response);
        }*/

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(new RepositorioProveedores(entity).obtenerProveedores());
        } catch (Exception e) {

        }

        return null;
        /*Map<String, Object> map = new HashMap<>();
        return new ModelAndView(map, "egresos.html");*/
    }
}
