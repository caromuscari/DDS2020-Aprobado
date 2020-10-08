package ar.edu.utn.frba.dds.Controladores;

import ar.edu.utn.frba.dds.Entidad.Entidad;
import ar.edu.utn.frba.dds.Repositorios.RepoUsuarios;
import ar.edu.utn.frba.dds.Repositorios.RepositorioEntidades;
import ar.edu.utn.frba.dds.Vinculador.*;
import ar.edu.utn.frba.dds.Vinculador.PrimeroEgreso;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.*;

public class VinculadorController {

    private static RepoUsuarios repoUsuarios = RepoUsuarios.getInstance();
    private static RepositorioEntidades repoEntidades = RepositorioEntidades.getInstance();
    private static List<CondicionVinculacion> condiciones;

    public static ModelAndView paginaVinculador(Request request, Response response) {
        if (request.session(false) == null) {
            return Login.paginaLogin(request, response);
        } else {

            List<Entidad> entidades = repoEntidades.obtenerEntidades();
            Map<String, Object> map = new HashMap<>();
            map.put("entidades",entidades);
            return new ModelAndView(map, "vinculador.html");
        }
    }

    public static String vincular(Request request, Response response){
        //Inicializaciones
        condiciones = new ArrayList<>();
        CondicionFecha condicionFecha = new CondicionFecha();
        Integer diasAntes;
        Integer diasDespues;
        ResultadoVinculacion resultadoVinculacion;
        String seleccionado = request.queryMap("seleccionado").value();
        String orden = request.queryMap("orden").value();
        String ent = request.queryMap("entidad").value();
        Entidad entidad = repoEntidades.obtenerEntidadPorNombre(ent);

        // Periodo de aceptabilidad de ingresos
        String fechaInicio = request.queryMap("fechaInicio").value();
        String fechaFin = request.queryMap("fechaFin").value();
        diasAntes = Period.between(LocalDate.parse(fechaInicio), LocalDate.now()).getDays();
        diasDespues = Period.between(LocalDate.parse(fechaFin), LocalDate.now()).getDays();

        condicionFecha.setDiasAntes(diasAntes);
        condicionFecha.setDiasDespues(diasDespues);
        condiciones.add(condicionFecha);

            if (seleccionado.equals("fecha")) {
                Fecha procFecha = new Fecha();
                procFecha.setCondiciones(condiciones);
                resultadoVinculacion = procFecha.vincular(entidad);
                resultadoVinculacion.setUser(request.session().attribute("usuario"));
                resultadoVinculacion.setHoraVinculacion(LocalTime.now().withNano(0).toString());

            } else if (seleccionado.equals("mix")) {
                Mix procMix = mixSegunOrdenes(orden);
                procMix.setCondiciones(condiciones);
                resultadoVinculacion = procMix.vincular(entidad);
                resultadoVinculacion.setUser(request.session().attribute("usuario"));
                resultadoVinculacion.setHoraVinculacion(LocalTime.now().withNano(0).toString());

            } else if (seleccionado.equals("primeroEgreso")) {
                PrimeroEgreso procEgreso = null;
                if (orden.equals("asc")) {
                    procEgreso = new PrimeroEgreso(TipoOrden.ORDENASCENDENTE);
                } else {
                    procEgreso = new PrimeroEgreso(TipoOrden.ORDENDESCENDENTE);
                }
                procEgreso.setCondiciones(condiciones);
                resultadoVinculacion = procEgreso.vincular(entidad);
                resultadoVinculacion.setUser(request.session().attribute("usuario"));
                resultadoVinculacion.setHoraVinculacion(LocalTime.now().withNano(0).toString());

            } else {
                PrimeroIngreso procIngreso = null;
                if (orden.equals("asc")) {
                    procIngreso = new PrimeroIngreso(TipoOrden.ORDENASCENDENTE);
                } else {
                    procIngreso = new PrimeroIngreso(TipoOrden.ORDENDESCENDENTE);
                }
                procIngreso.setCondiciones(condiciones);
                resultadoVinculacion = procIngreso.vincular(entidad);
                resultadoVinculacion.setUser(request.session().attribute("usuario"));
                resultadoVinculacion.setHoraVinculacion(LocalTime.now().withNano(0).toString());
            }

        // Creo el JSON
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String result = gson.toJson(resultadoVinculacion);

        // Retorno JSON
        return result;
    }

    private static Mix mixSegunOrdenes(String orden){
        if(orden.equals("asc-asc")) { return new Mix(TipoOrden.ORDENASCENDENTE,TipoOrden.ORDENASCENDENTE); }
        else if(orden.equals("asc-desc")){ return new Mix(TipoOrden.ORDENASCENDENTE,TipoOrden.ORDENDESCENDENTE);}
        else if(orden.equals("desc-asc")){ return new Mix(TipoOrden.ORDENDESCENDENTE, TipoOrden.ORDENASCENDENTE);}
        else { return new Mix(TipoOrden.ORDENDESCENDENTE, TipoOrden.ORDENDESCENDENTE);}
    }
}
