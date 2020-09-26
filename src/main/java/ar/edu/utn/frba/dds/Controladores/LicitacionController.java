package ar.edu.utn.frba.dds.Controladores;

import ar.edu.utn.frba.dds.BandejaDeMendajes.Mensaje;
import ar.edu.utn.frba.dds.Repositorios.LicitacionRepo;
import ar.edu.utn.frba.dds.Repositorios.RepoUsuarios;
import ar.edu.utn.frba.dds.Usuario.Usuario;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ar.edu.utn.frba.dds.Licitacion.Licitacion;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.stream.Collectors;

public class LicitacionController {
    private static RepoUsuarios repo = RepoUsuarios.getInstance();

    public static Object crearLicitacion(Request request, Response response) {
        String jsonLicitacion = request.body();
        Licitacion licitacion = new ObjectMapper().convertValue(jsonLicitacion, Licitacion.class);
        LicitacionRepo.add(licitacion);
        response.body("Licitacion: " + licitacion.getNombre());
        return null;
    }

    public static Object validarLicitacion(Request request, Response response) {
        String licitacion = request.params(":id");
        LicitacionRepo.find(licitacion).validarLicitacion();
        return null;
    }

    public static Object mostrarMensajes(Request request, Response response) throws JsonProcessingException {
        String licitacion = request.params(":id");
        String nombre = request.session().attribute("usuario");
        Usuario usuario = repo.buscarUsuario(nombre);
        List<String> listaMensajes = usuario.getBandejaDeMensajes().stream()
                .filter(l -> l.getResultados().get(0).getLicitacion().getNombre().matches(licitacion))
                .map(Mensaje::leerMensaje).collect(Collectors.toList());
        String mensajes = new ObjectMapper().writeValueAsString(listaMensajes);
        response.body(mensajes);
        return null;
    }
}
