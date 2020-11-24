package ar.edu.utn.frba.dds.Controladores;

import ar.edu.utn.frba.dds.Entidad.Entidad;
import ar.edu.utn.frba.dds.Repositorios.RepoUsuarios;
import ar.edu.utn.frba.dds.Repositorios.RepositorioEntidades;
import ar.edu.utn.frba.dds.Usuario.Usuario;
import ar.edu.utn.frba.dds.Vinculador.*;
import ar.edu.utn.frba.dds.Vinculador.PrimeroEgreso;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class VinculadorController {

    private static List<CondicionVinculacion> condiciones;

    public static ModelAndView paginaVinculador(Request request, Response response, EntityManager entity) {
        if (request.session(false) == null) {
            return Login.paginaLogin(request, response);
        } else {
            Usuario usuario = new RepoUsuarios(entity).buscarUsuario(request.session().attribute("usuario"));
            List<Entidad> entidades = usuario.getOrganizacion().getEntidades();
            Map<String, Object> map = new HashMap<>();
            map.put("entidades",entidades);
            return new ModelAndView(map, "vinculador.html");
        }
    }

    public static String vincular(Request request, Response response, EntityManager entityManager){
        //Inicializaciones
        condiciones = new ArrayList<>();
        CondicionFecha condicionFecha = new CondicionFecha();
        ResultadoVinculacion resultadoVinculacion;
        String seleccionado = request.queryMap("seleccionado").value();
        String orden = request.queryMap("orden").value();
        String idEntidad = request.queryMap("entidad").value();
        int idEntity;

        try{
            idEntity = Integer.parseInt(idEntidad);
        }catch (Exception e){
            return e.getMessage();
        }

        //=====================================================
        // Busco la entidad en la DB

        Entidad entidad = new RepositorioEntidades(entityManager).getEntidadById(idEntity);

        //=====================================================

        // Periodo de aceptabilidad de ingresos

        //LocalDate fechaInicio = LocalDate.parse(request.queryMap("fechaInicio").value());
        //LocalDate fechaFin = LocalDate.parse(request.queryMap("fechaFin").value());

        int inicioRango = Integer.parseInt(request.queryMap("inicioRango").value());
        int finRango = Integer.parseInt(request.queryMap("finRango").value());

        condicionFecha.setDiasAnteriores(inicioRango);
        condicionFecha.setDiasPosteriores(finRango);
        condiciones.add(condicionFecha);

            if (seleccionado.equals("fecha")) {
                Fecha procFecha = new Fecha();
                procFecha.setCondiciones(condiciones);
                resultadoVinculacion = procFecha.vincular(entidad);

            } else if (seleccionado.equals("mix")) {
                Mix procMix = mixSegunOrdenes(orden);
                procMix.setCondiciones(condiciones);
                resultadoVinculacion = procMix.vincular(entidad);

            } else if (seleccionado.equals("primeroEgreso")) {
                PrimeroEgreso procEgreso = null;
                if (orden.equals("asc")) {
                    procEgreso = new PrimeroEgreso(TipoOrden.ORDENASCENDENTE);
                } else {
                    procEgreso = new PrimeroEgreso(TipoOrden.ORDENDESCENDENTE);
                }
                procEgreso.setCondiciones(condiciones);
                resultadoVinculacion = procEgreso.vincular(entidad);

            } else {
                PrimeroIngreso procIngreso = null;
                if (orden.equals("asc")) {
                    procIngreso = new PrimeroIngreso(TipoOrden.ORDENASCENDENTE);
                } else {
                    procIngreso = new PrimeroIngreso(TipoOrden.ORDENDESCENDENTE);
                }
                procIngreso.setCondiciones(condiciones);
                resultadoVinculacion = procIngreso.vincular(entidad);
            }

        resultadoVinculacion.setUsuario(request.session().attribute("usuario"));
        resultadoVinculacion.setHoraVinculacion(LocalTime.now().withNano(0).toString());

        // Creo el JSON (con Jackson)
        String result;

        try {
            ObjectMapper objMapper = new ObjectMapper();
            ObjectWriter objectWriter = objMapper.writerWithDefaultPrettyPrinter();
            result = objectWriter.writeValueAsString(resultadoVinculacion);
        }
        catch(JsonProcessingException e){
            return e.getMessage();
        }

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
