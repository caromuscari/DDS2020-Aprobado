package ar.edu.utn.frba.dds.Controladores;

import ar.edu.utn.frba.dds.Categorizacion.Categoria;
import ar.edu.utn.frba.dds.DTO.EgresoDTO;
import ar.edu.utn.frba.dds.DTO.IngresoDTO;
import ar.edu.utn.frba.dds.Entidad.Entidad;
import ar.edu.utn.frba.dds.Operaciones.Egreso;
import ar.edu.utn.frba.dds.Operaciones.Ingreso;
import ar.edu.utn.frba.dds.Operaciones.ItemOperacionEgreso;
import ar.edu.utn.frba.dds.Operaciones.Proveedor;
import ar.edu.utn.frba.dds.Repositorios.*;
import ar.edu.utn.frba.dds.Usuario.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Ingresos {

    public static ModelAndView paginaIngresos(Request request, Response response) {
        if(request.session(false) == null) {
            return Login.paginaLogin(request,response);
        }
        else {
            List<IngresoDTO> ingresos = new ArrayList<>();
            List<Entidad> entidades = RepositorioEntidades.getInstance().obtenerEntidades();
            entidades.forEach(entidad -> entidad.getIngresos().forEach(ingreso -> ingresos.add(new IngresoDTO(ingreso,entidad))));
            Map<String, Object> map = new HashMap<>();
            map.put("ingresos",ingresos);
            return new ModelAndView(map, "ingresos.html");
        }
    }

    public static ModelAndView filtrarPorCategoria (Request request, Response response) {
        String categoria = request.queryParams("categoria");
        List<EgresoDTO> ingresos = new ArrayList<>();
        List<Entidad> entidades = RepositorioEntidades.getInstance().obtenerEntidades();
        Map<String, Object> map = new HashMap<>();
        map.put("ingresos", ingresos);
        return new ModelAndView(map, "ingresos.html");

    }

    public static ModelAndView modificarIngreso(Request request, Response response) {
        if(request.session(false) == null) {
            return Login.paginaLogin(request,response);
        }
        else {
            Usuario usuario = RepoUsuarios.getInstance().buscarUsuario(request.session().attribute("usuario"));
            ar.edu.utn.frba.dds.Operaciones.Ingreso ingreso = RepositorioEntidades.getInstance().obtenerIngresoPorId(request.params(":id"));
            Map<String, Object> map = new HashMap<>();
            map.put("ingreso",ingreso);
            map.put("categorias", usuario.getOrganizacion().getCriterios());
            map.put("categoriasIngreso", getCategoriasIngreso(ingreso));
            return new ModelAndView(map, "modificarIngreso.html");
        }
    }

    public static ModelAndView guardarIngreso(Request request, Response response) {
        if(request.session(false) == null) {
            return Login.paginaLogin(request, response);
        }

        Usuario usuario = RepoUsuarios.getInstance().buscarUsuario(request.session().attribute("usuario"));

        String[] nombreCategorias = request.queryParamsValues("categorias");
        String id = request.params(":id");

        Ingreso ingreso = RepositorioEntidades.instance.obtenerIngresoPorId(id);

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
        ingreso.setCategorias(categorias);

        response.redirect("/ingreso");
        return null;
    }

    public static List<String> getCategoriasIngreso(Ingreso ingreso){
        List<String> categorias = new ArrayList<>();
        String comilla = "\"";
        ingreso.getCategorias().forEach(c -> categorias.add(comilla + c.getNombre() + comilla));
        return categorias;
    }
}
