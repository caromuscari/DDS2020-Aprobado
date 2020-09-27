package ar.edu.utn.frba.dds.Controladores;

import ar.edu.utn.frba.dds.BandejaDeMendajes.Mensaje;
import ar.edu.utn.frba.dds.DTO.MensajeDTO;
import ar.edu.utn.frba.dds.Repositorios.LicitacionRepo;
import ar.edu.utn.frba.dds.Repositorios.RepoUsuarios;
import ar.edu.utn.frba.dds.ResultadoLicitacion.EstadoValidacion;
import ar.edu.utn.frba.dds.ResultadoLicitacion.ResultadoValidacion;
import ar.edu.utn.frba.dds.Usuario.Usuario;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ar.edu.utn.frba.dds.Licitacion.Licitacion;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        String nombreLicitacion = request.queryMap("nombreLicitacion").value();
        Optional<Licitacion> licitacion = LicitacionRepo.find(nombreLicitacion);
        if (licitacion.isPresent()){
            licitacion.get().validarLicitacion();
        }
        return null;
    }

    public static Object mostrarMensajes(Request request, Response response) throws JsonProcessingException {
        String licitacion = request.queryMap("nombreLicitacion").value();
        String nombreUsuario = request.queryMap("usuario").value();
        Usuario usuario = repo.buscarUsuario(nombreUsuario);
        if(usuario != null){
            List<MensajeDTO> listaMensajes = usuario.getBandejaDeMensajes().stream()
                    .filter(l -> l.getResultados().get(0).getLicitacion().getNombre().matches(licitacion))
                    .map(l -> new MensajeDTO(l.obtenerMensaje(),l.getLeido()))
                    .collect(Collectors.toList());

            Gson gson = new Gson();
            String mensajesJson = gson.toJson(listaMensajes);
            try {
                PrintWriter out = response.raw().getWriter();
                response.header("Content-Type","application/json");
                out.print(mensajesJson);
                out.flush();
            } catch (IOException e){
                System.out.println("error convirtiendo mensajes : " + e.getMessage());
            }
        }
        return null;
    }
}
