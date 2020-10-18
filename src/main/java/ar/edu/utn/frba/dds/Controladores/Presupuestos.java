package ar.edu.utn.frba.dds.Controladores;

import ar.edu.utn.frba.dds.App;
import ar.edu.utn.frba.dds.Categorizacion.Categoria;
import ar.edu.utn.frba.dds.DTO.IngresoDTO;
import ar.edu.utn.frba.dds.DTO.LicitacionDTO;
import ar.edu.utn.frba.dds.Entidad.Entidad;
import ar.edu.utn.frba.dds.Licitacion.Presupuesto;
import ar.edu.utn.frba.dds.Operaciones.Ingreso;
import ar.edu.utn.frba.dds.Repositorios.RepoUsuarios;
import ar.edu.utn.frba.dds.Repositorios.RepositorioEntidades;
import ar.edu.utn.frba.dds.Usuario.Usuario;
import com.google.gson.Gson;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Presupuestos {

    public static ModelAndView paginaPresupuestos(Request request, Response response) {
        if(request.session(false) == null) {
            return Login.paginaLogin(request,response);
        }
        else {
            List<LicitacionDTO> licitaciones = new ArrayList<>();
            List<Entidad> entidades = RepositorioEntidades.getInstance().obtenerEntidades();
            entidades.forEach(entidad -> entidad.getLicitaciones().forEach(licitacion -> licitaciones.add(new LicitacionDTO(licitacion,entidad))));
            Map<String, Object> map = new HashMap<>();
            int pagina = (request.queryParams("page") != null) ? Integer.parseInt(request.queryParams("page")) : 1;
            int elementoInicial = (pagina-1)* App.getPageSize();
            int elementoFinal = ((pagina*App.getPageSize()) < licitaciones.size()) ? (pagina*App.getPageSize()) : licitaciones.size();
            map.put("licitaciones",licitaciones.subList(elementoInicial,elementoFinal));
            List<Integer> range = IntStream.rangeClosed(1, ((licitaciones.size()-1)/App.getPageSize())+1).boxed().collect(Collectors.toList());
            map.put("pages",range);
            return new ModelAndView(map, "presupuestos.html");
        }
    }

    public static ModelAndView modificarPresupuesto(Request request, Response response) {
        if(request.session(false) == null) {
            return Login.paginaLogin(request,response);
        }
        else {
            Usuario usuario = RepoUsuarios.getInstance().buscarUsuario(request.session().attribute("usuario"));
            ar.edu.utn.frba.dds.Licitacion.Presupuesto presupuesto = RepositorioEntidades.getInstance().obtenerPresupuestoPorId(request.params(":id"));
            Map<String, Object> map = new HashMap<>();
            map.put("presupuesto",presupuesto);
            map.put("categorias", new Gson().toJson(usuario.getOrganizacion().getCriterios()));
            map.put("categoriasPresupuestos",getCategoriasPresupuestos(presupuesto));
            return new ModelAndView(map, "modificarPresupuesto.html");
        }
    }

    public static ModelAndView guardarPresupuesto(Request request, Response response) {
        if(request.session(false) == null) {
            return Login.paginaLogin(request, response);
        }

        Usuario usuario = RepoUsuarios.getInstance().buscarUsuario(request.session().attribute("usuario"));

        String[] nombreCategorias = request.queryParamsValues("categorias");
        String id = request.params(":id");

        Presupuesto presupuesto = RepositorioEntidades.instance.obtenerPresupuestoPorId(id);

        List<Categoria> categorias = new ArrayList<>();
        if(nombreCategorias != null) {
            List<Categoria> total = usuario.getOrganizacion().getCriterios().stream()
                    .map(c -> c.getCategorias()).flatMap(List::stream)
                    .collect(Collectors.toList());

            for (String cat : nombreCategorias) {
                categorias.add(total.stream()
                        .filter(c -> c.getNombre().matches(cat))
                        .findFirst().get());
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
}
