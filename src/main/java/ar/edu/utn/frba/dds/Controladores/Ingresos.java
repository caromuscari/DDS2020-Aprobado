package ar.edu.utn.frba.dds.Controladores;

import ar.edu.utn.frba.dds.App;
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

public class Ingresos {

    public static ModelAndView paginaIngresos(Request request, Response response, EntityManager entity) {
        if(request.session(false) == null) {
            return Login.paginaLogin(request,response);
        }
        else {
            Usuario usuario = new RepoUsuarios(entity).buscarUsuario(request.session().attribute("usuario"));
            List<IngresoDTO> ingresos = new ArrayList<>();
            List<Entidad> entidades = usuario.getOrganizacion().getEntidades();
            entidades.forEach(entidad -> entidad.getIngresos().forEach(ingreso -> ingresos.add(new IngresoDTO(ingreso,entidad))));
            Map<String, Object> map = new HashMap<>();
            int pagina = (request.queryParams("page") != null) ? Integer.parseInt(request.queryParams("page")) : 1;
            int elementoInicial = (pagina-1)* App.getPageSize();
            int elementoFinal = ((pagina*App.getPageSize()) < ingresos.size()) ? (pagina*App.getPageSize()) : ingresos.size();
            map.put("ingresos",ingresos.subList(elementoInicial,elementoFinal));
            map.put("categorias",new Gson().toJson(usuario.getOrganizacion().getCriterios()));
            List<Integer> range = IntStream.rangeClosed(1, ((ingresos.size()-1)/App.getPageSize())+1).boxed().collect(Collectors.toList());
            map.put("pages",range);
            return new ModelAndView(map, "ingresos.html");
        }
    }

    public static ModelAndView modificarIngreso(Request request, Response response, EntityManager entity) {
        if(request.session(false) == null) {
            return Login.paginaLogin(request,response);
        }
        else {
            Usuario usuario = new RepoUsuarios(entity).buscarUsuario(request.session().attribute("usuario"));
            ar.edu.utn.frba.dds.Operaciones.Ingreso ingreso = new RepositorioIngreso(entity).obtenerIngresoPorId(request.params(":id"));
            Map<String, Object> map = new HashMap<>();
            map.put("ingreso",ingreso);
            map.put("categorias", new Gson().toJson(usuario.getOrganizacion().getCriterios()));
            map.put("categoriasIngreso", getCategoriasIngreso(ingreso));
            return new ModelAndView(map, "modificarIngreso.html");
        }
    }

    public static ModelAndView guardarIngreso(Request request, Response response, EntityManager entity) {
        if(request.session(false) == null) {
            return Login.paginaLogin(request, response);
        }

        Usuario usuario = new RepoUsuarios(entity).buscarUsuario(request.session().attribute("usuario"));

        String[] nombreCategorias = request.queryParamsValues("categorias");
        String id = request.params(":id");

        Ingreso ingreso = new RepositorioIngreso(entity).obtenerIngresoPorId(id);

        List<Categoria> categorias = new ArrayList<>();
        if(nombreCategorias != null) {
            List<Categoria> total = usuario.getOrganizacion().getCriterios().stream()
                    .map(c -> c.getCategorias()).flatMap(List::stream)
                    .collect(Collectors.toList());

            for (String cat : nombreCategorias) {
                categorias.add(total.stream()
                                    .filter(c -> c.getNombre().matches(cat))
                                    .findFirst()
                                    .orElse(null)
                );
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

    public static String filtrarPorCategoria (Request request, Response response, EntityManager entity){
        String categoria = request.queryParams("categoria");
        List<IngresoDTO> ingresos = new ArrayList<>();
        Usuario usuario = new RepoUsuarios(entity).buscarUsuario(request.session().attribute("usuario"));
        List<Entidad> entidades = usuario.getOrganizacion().getEntidades();
        entidades.forEach(entidad -> {
                    entidad.getIngresos().stream()
                            .filter(ingreso -> filtrar(ingreso, categoria))
                            .map(ingreso -> new IngresoDTO(ingreso, entidad))
                            .collect(Collectors.toCollection(() -> ingresos));
                }
        );
        return toJson(ingresos);
    }

    private static boolean filtrar(Ingreso ingreso, String categoria){
        return ingreso.getCategorias().stream()
                .filter(i -> i.getNombre().equals(categoria) || Ingresos.tieneCategoria(i,categoria))
                .count()>0;
    }

    private static boolean tieneCategoria(Categoria categoria, String nombreCategoria){
        Categoria categoriaHija = new Categoria(nombreCategoria);
        return categoria.contieneCategoriaHija(categoriaHija);
    }

    private static String toJson(List<IngresoDTO> listaIngresos){
        Gson gson = new Gson();
        String mensajeJson = gson.toJson(listaIngresos);
        return mensajeJson;
    }
}
