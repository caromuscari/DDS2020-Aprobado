package ar.edu.utn.frba.dds.Controladores;

import ar.edu.utn.frba.dds.DTO.MensajeDTO;
import ar.edu.utn.frba.dds.Repositorios.LicitacionRepo;
import ar.edu.utn.frba.dds.Repositorios.RepoUsuarios;
import ar.edu.utn.frba.dds.ResultadoLicitacion.ResultadoValidacion;
import ar.edu.utn.frba.dds.Usuario.Usuario;
import ar.edu.utn.frba.dds.audit.Audit;
import com.fasterxml.jackson.databind.ObjectMapper;
import ar.edu.utn.frba.dds.Licitacion.Licitacion;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import com.google.gson.Gson;

public class  LicitacionController {


    public static Object crearLicitacion(Request request, Response response, EntityManager entity) {
        Usuario usuario = new RepoUsuarios(entity).buscarUsuario(request.session().attribute("usuario"));
        String jsonLicitacion = request.body();
        Licitacion licitacion = new ObjectMapper().convertValue(jsonLicitacion, Licitacion.class);
        new LicitacionRepo(entity).persistir(licitacion);
        response.body("Licitacion: " + licitacion.getNombre());
        Audit.crearAuditoria("ALTA","Licitacion",usuario.getUsuario(),licitacion.getId());
        return null;
    }

    public static Object validarLicitacion(Request request, Response response, EntityManager entitys) {
        String idLicitacion = request.queryMap("idLicitacion").value();
        Licitacion licitacion = new LicitacionRepo(entitys).obtenerLicitacionPorID(idLicitacion);
        response.header("Content-Type", "application/json");
        try {
            if (licitacion != null) {
                List<ResultadoValidacion> resultadoValidacions = licitacion.validarLicitacion();
                Gson gson = new GsonBuilder()
                        .excludeFieldsWithoutExposeAnnotation()
                        .create();
                return gson.toJson(resultadoValidacions);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Object mostrarMensajes(Request request, Response response, EntityManager entity) {
        String licitacion = request.queryMap("idLicitacion").value();
        String nombreUsuario = request.queryMap("usuario").value();
        int idLicitacion;
        try {
            PrintWriter writer = response.raw().getWriter();
            response.header("Content-Type", "application/json");

            if (licitacion == null || nombreUsuario == null) {
                ObjectNode ob = JsonNodeFactory.instance.objectNode();
                ob.put("Error", "No se envio la licitacion o el usuario como queryParam");
                writer.print(ob);
                writer.flush();
                return null;
            }

            Usuario usuario = new RepoUsuarios(entity).buscarUsuario(nombreUsuario);

            if (usuario != null) {
                try{
                    idLicitacion = Integer.parseInt(licitacion);
                }catch (Exception e){
                    ObjectNode ob = JsonNodeFactory.instance.objectNode();
                    ob.put("Error", "el id de licitacion tiene que ser un numero");
                    writer.print(ob);
                    writer.flush();
                    return null;
                }
                List<MensajeDTO> listaMensajes = usuario.getBandejaDeMensajes().stream()
                        .filter(l -> l.getResultados().get(0).getLicitacion().getId() == idLicitacion)
                        .map(l -> new MensajeDTO(l.obtenerMensaje(), l.getLeido()))
                        .collect(Collectors.toList());

                Gson gson = new Gson();
                String mensajesJson = gson.toJson(listaMensajes);
                writer.print(mensajesJson);
                writer.flush();
                return null;
            } else {
                ObjectNode ob = JsonNodeFactory.instance.objectNode();
                ob.put("Erorr", "No se encontro el usuario " + nombreUsuario);
                writer.print(ob);
                writer.flush();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
