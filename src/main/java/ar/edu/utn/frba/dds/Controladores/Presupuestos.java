package ar.edu.utn.frba.dds.Controladores;

import ar.edu.utn.frba.dds.App;
import ar.edu.utn.frba.dds.Categorizacion.Categoria;
import ar.edu.utn.frba.dds.DTO.LicitacionDTO;
import ar.edu.utn.frba.dds.Entidad.Entidad;
import ar.edu.utn.frba.dds.Licitacion.Licitacion;
import ar.edu.utn.frba.dds.Licitacion.Presupuesto;
import ar.edu.utn.frba.dds.Repositorios.RepoUsuarios;
import ar.edu.utn.frba.dds.Repositorios.RepositorioPresupuesto;
import ar.edu.utn.frba.dds.Usuario.Usuario;
import ar.edu.utn.frba.dds.audit.Audit;
import com.google.gson.Gson;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Presupuestos {

    public static ModelAndView paginaPresupuestos(Request request, Response response, EntityManager entity) {
        if(request.session(false) == null) {
            return Login.paginaLogin(request,response);
        }
        else {
            Usuario usuario = new RepoUsuarios(entity).buscarUsuario(request.session().attribute("usuario"));
            List<LicitacionDTO> licitaciones = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            String page = request.queryParams("page");
            String filtro = request.queryParams("filter");
            int pagina = (page != null) ? Integer.parseInt(page) : 1;
            int elementoInicial = (pagina-1)* App.getPageSize();

            usuario.getOrganizacion().getEntidades().forEach(entidad -> entidad.getLicitaciones()
                    .forEach(licitacion -> licitaciones.add(new LicitacionDTO(licitacion,entidad))));

            if (filtro != null) licitaciones.forEach(l -> l.getPresupuestos().removeIf(p -> !(p.contieneCategoria(filtro))));

            int elementoFinal = ((pagina*App.getPageSize()) < licitaciones.size()) ? (pagina*App.getPageSize()) : licitaciones.size();
            map.put("licitaciones",licitaciones.subList(elementoInicial,elementoFinal));
            List<Integer> range = IntStream.rangeClosed(1, ((licitaciones.size()-1)/App.getPageSize())+1).boxed().collect(Collectors.toList());
            map.put("pages",range);
            map.put("categorias", new Gson().toJson(usuario.getOrganizacion().getCriterios()));
            map.put("actualPage", page);
            map.put("filter", filtro);
            return new ModelAndView(map, "presupuestos.html");
        }
    }

    private static void getLicitacionesFromEntidades(Map<String, Object> map, List<Entidad> entidades, int elementoInicial, int elementoFinal){
        List<Licitacion> listaLicitaciones = new ArrayList<>();
        List<LicitacionDTO> licitaciones = new ArrayList<>();
        entidades.forEach(entidad -> entidad.getLicitaciones().forEach(licitacion -> {
            licitaciones.add(new LicitacionDTO(licitacion,entidad));
            listaLicitaciones.add(licitacion);
        }));
        map.put("licitaciones",licitaciones.subList(elementoInicial,elementoFinal));
    }

    public static ModelAndView modificarPresupuesto(Request request, Response response, EntityManager entity) {
        if(request.session(false) == null) {
            return Login.paginaLogin(request,response);
        }
        else {
            Usuario usuario = new RepoUsuarios(entity).buscarUsuario(request.session().attribute("usuario"));
            Audit.crearAuditoria("MODIFICAR","Presupuesto",usuario.getUsuario());
            ar.edu.utn.frba.dds.Licitacion.Presupuesto presupuesto = new RepositorioPresupuesto(entity).obtenerPresupuestoPorId(request.params(":id"));
            Map<String, Object> map = new HashMap<>();
            map.put("presupuesto",presupuesto);
            map.put("categorias", new Gson().toJson(usuario.getOrganizacion().getCriterios()));
            map.put("categoriasPresupuestos",getCategoriasPresupuestos(presupuesto));
            return new ModelAndView(map, "modificarPresupuesto.html");
        }
    }

    public static ModelAndView guardarPresupuesto(Request request, Response response, EntityManager entity) {
        if(request.session(false) == null) {
            return Login.paginaLogin(request, response);
        }

        Usuario usuario = new RepoUsuarios(entity).buscarUsuario(request.session().attribute("usuario"));
        Audit.crearAuditoria("ALTA","Presupuesto",usuario.getUsuario());
        String[] nombreCategorias = request.queryParamsValues("categorias");
        String id = request.params(":id");

        Presupuesto presupuesto = new RepositorioPresupuesto(entity).obtenerPresupuestoPorId(id);

        List<Categoria> categorias = new ArrayList<>();
        if(nombreCategorias != null) {
            for (String cat : nombreCategorias) {
                categorias.add(usuario.getOrganizacion().obtenerCategoria(cat));
            }
        }
        presupuesto.setCategorias(categorias);

        response.redirect("/presupuesto");
        return null;
    }

    private static List<String> getCategoriasPresupuestos(Presupuesto presupuesto) {
        List<String> categorias = new ArrayList<>();
        String comilla = "\"";

        presupuesto.getCategorias().forEach(c -> categorias.add(comilla + c.getNombre() + comilla));

        return categorias;
    }
    private static String toJson(List<LicitacionDTO> listaEgresos) {
        Gson gson = new Gson();
        String mensajeJson = gson.toJson(listaEgresos);
        return mensajeJson;
    }

    private static List<Categoria> getCategoriasFromLicitaciones(List<Licitacion> licitaciones){
        List<Categoria> categorias = new ArrayList<>();
        licitaciones.stream()
                    .forEach(l -> l.getPresupuestos().stream()
                    .forEach(p -> categorias.addAll(p.getCategorias())));
        return categorias;
    }
}
