package ar.edu.utn.frba.dds.Controladores;

import ar.edu.utn.frba.dds.Categorizacion.Categoria;
import ar.edu.utn.frba.dds.DTO.EgresoDTO;
import ar.edu.utn.frba.dds.DTO.PresupuestoDTO;
import ar.edu.utn.frba.dds.Entidad.Entidad;
import ar.edu.utn.frba.dds.Licitacion.Presupuesto;
import ar.edu.utn.frba.dds.Repositorios.*;
import ar.edu.utn.frba.dds.Operaciones.*;
import ar.edu.utn.frba.dds.Operaciones.Proveedor;
import ar.edu.utn.frba.dds.Repositorios.RepoUsuarios;
import ar.edu.utn.frba.dds.Usuario.Usuario;
import com.google.gson.Gson;
import com.sun.org.apache.xpath.internal.operations.Mod;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.cert.CollectionCertStoreParameters;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Egresos {
    private static RepoUsuarios repoUsuarios = RepoUsuarios.getInstance();
    private static RepositorioProveedores proveedores = RepositorioProveedores.getInstance();
    private static RepositorioMedioDePago medios = RepositorioMedioDePago.getInstance();

    public static ModelAndView paginaEgresos(Request request, Response response) {
        if (request.session(false) == null) {
            return Login.paginaLogin(request, response);
        } else {
            List<EgresoDTO> egresos = new ArrayList<>();
            List<Entidad> entidades = RepositorioEntidades.getInstance().obtenerEntidades();
            List<Categoria> categorias = RepositorioCategorias.getInstance().getCategorias();
            entidades.forEach(entidad -> entidad.getEgresos().forEach(egreso -> egresos.add(new EgresoDTO(egreso, entidad))));
            Map<String, Object> map = new HashMap<>();
            map.put("egresos",egresos);
            map.put("categorias",categorias);
            return new ModelAndView(map, "egresos.html");
        }
    }

    public static ModelAndView paginaNuevoEgreso(Request request, Response response) {
        if (request.session(false) == null) {
            return Login.paginaLogin(request, response);
        } else {
            Usuario usuario = repoUsuarios.buscarUsuario(request.session().attribute("usuario"));
            Map<String, Object> map = new HashMap<>();
            List<Entidad> entidades = usuario.getOrganizacion().getEntidades();
            map.put("proveedores", proveedores.obtenerProveedores());
            map.put("mediosDePago", medios.obtenerMediosDePago());
            map.put("entidades", entidades);
            map.put("categorias", usuario.getOrganizacion().getCriterios());
            return new ModelAndView(map, "nuevoEgreso.html");
        }
    }

    public static ModelAndView paginaAgregarPresupuesto(Request request, Response response) {
        if (request.session(false) == null) {
            return Login.paginaLogin(request, response);
        } else {
            Map<String, Object> map = new HashMap<>();
            Egreso egreso = RepositorioEntidades.getInstance().obtenerEgresoPorId(request.params("id"));
            map.put("egreso", egreso);
            return new ModelAndView(map, "nuevoPresupuestoEnEgreso.html");
        }
    }

    public static ModelAndView agregarPresupuesto(Request request, Response response) {
        if (request.session(false) == null) {
            return Login.paginaLogin(request, response);
        } else {
            try {
                String presupuesto = request.queryParams("presupuesto");
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
                JsonNode jsonNode = mapper.readTree(presupuesto);
                PresupuestoDTO presupuestoDTO = mapper.convertValue(jsonNode, PresupuestoDTO.class);

                ar.edu.utn.frba.dds.Operaciones.Egreso egreso = RepositorioEntidades.getInstance().obtenerEgresoPorId(request.params("id"));
                egreso.setPresupuesto(new Presupuesto(new ArrayList<>(), presupuestoDTO.getProveedor(), presupuestoDTO.getNombre()));
            } catch (Exception e) {

            }

            response.redirect("/egreso");
            return null;
        }
    }

    public static ModelAndView paginaModificarEgreso(Request request, Response response) {
        if (request.session(false) == null) {
            return Login.paginaLogin(request, response);
        } else {
            Usuario usuario = repoUsuarios.buscarUsuario(request.session().attribute("usuario"));
            ar.edu.utn.frba.dds.Operaciones.Egreso egreso = RepositorioEntidades.getInstance().obtenerEgresoPorId(request.params(":id"));
            Map<String, Object> map = new HashMap<>();
            map.put("egreso", egreso);
            map.put("proveedores", proveedores.obtenerProveedores());
            map.put("mediosDePago", medios.obtenerMediosDePago());
            map.put("entidades", usuario.getOrganizacion().getEntidades());
            map.put("categorias", usuario.getOrganizacion().getCriterios());
            map.put("nombreEntidad", RepositorioEntidades.getInstance().obtenerEntidadDeEgreso(egreso).getNombre());
            map.put("categoriasEgreso", getCategoriasEgreso(egreso));
            return new ModelAndView(map, "modificarEgreso.html");
        }
    }

    public static ModelAndView borrarEgreso(Request request, Response response) {
        if (request.session(false) == null) {
            return Login.paginaLogin(request, response);
        }

        Usuario usuario = repoUsuarios.buscarUsuario(request.session().attribute("usuario"));
        String id = request.params(":id");
        RepositorioEntidades.getInstance().borrarEgreso(request.params(":id"));
        List<EgresoDTO> egresos = new ArrayList<>();
        List<Entidad> entidades = RepositorioEntidades.getInstance().obtenerEntidades();
        entidades.forEach(entidad -> entidad.getEgresos().forEach(egreso -> egresos.add(new EgresoDTO(egreso, entidad))));
        Map<String, Object> map = new HashMap<>();
        map.put("egresos", egresos);
        return new ModelAndView(map, "egresos.html");
    }

    public static ModelAndView guardarEgreso(Request request, Response response) {
        if (request.session(false) == null) {
            return Login.paginaLogin(request, response);
        }

        Usuario usuario = repoUsuarios.buscarUsuario(request.session().attribute("usuario"));

        String nombreProveedor = request.queryParams("proveedor");
        String nombreEntidad = request.queryParams("entidad");
        String nombreEgreso = request.queryParams("nombre");
        String fechaEgreso = request.queryParams("fecha");
        String[] nombreCategorias = request.queryParamsValues("categorias");
        String nombreMedioDePago = request.queryParams("medioDePago");
        String id = request.params(":id");

        Proveedor proveedor = RepositorioProveedores.getInstance().obtenerProveedorPorNombre(nombreProveedor);

        String cantItem = null;
        List<ItemOperacionEgreso> items = new ArrayList<>();
        for (ItemEgreso item : proveedor.getItems()) {
            cantItem = request.queryParams(item.getDescripcion());
            if (cantItem != null) {
                ItemOperacionEgreso itemOperacionEgreso = new ItemOperacionEgreso(Integer.parseInt(cantItem), item);
                items.add(itemOperacionEgreso);
            }
        }

        ar.edu.utn.frba.dds.Operaciones.Egreso egreso = null;

        if (id == null) {
            egreso = RepositorioEgresos.getInstance().crearEgreso(items, proveedor, nombreEgreso);
        } else {
            egreso = RepositorioEntidades.getInstance().obtenerEgresoPorId(request.params(":id"));
            egreso.setNombre(nombreEgreso);
            egreso.setProveedor(proveedor);
            egreso.setItems(items);

            RepositorioEntidades.getInstance().obtenerEntidadDeEgreso(egreso).getEgresos().remove(egreso);
        }

        egreso.setFecha(LocalDate.parse(fechaEgreso));
        if (nombreEntidad != null) {
            Entidad entidad = usuario.getOrganizacion().getEntidades().stream().filter(e -> e.getNombre().matches(nombreEntidad)).findFirst().get();
            entidad.getEgresos().add(egreso);
        }

        List<Categoria> categorias = new ArrayList<>();
        if (nombreCategorias != null) {
            List<Categoria> total = usuario.getOrganizacion().getCriterios().stream()
                    .map(c -> c.getCategorias()).flatMap(List::stream)
                    .collect(Collectors.toList());

            for (String cat : nombreCategorias) {
                categorias.add(total.stream()
                        .filter(c -> c.getNombre().matches(cat))
                        .findFirst().get());
            }
        }
        egreso.setCategorias(categorias);

        MedioDePago medio = RepositorioMedioDePago.getInstance().obtenerItemsPorId(nombreMedioDePago);
        egreso.setMedioDePago(medio);

        response.redirect("/egreso");
        return null;
    }

    public static List<String> getCategoriasEgreso(Egreso egreso) {
        List<String> categorias = new ArrayList<>();
        String comilla = "\"";

        egreso.getCategorias().forEach(c -> categorias.add(comilla + c.getNombre() + comilla));

        return categorias;
    }

    private static List<String> getItemsEgreso(Egreso egreso) {
        List<String> items = new ArrayList<>();
        String comilla = "\"";

        egreso.getItems().forEach(c -> items.add(comilla + c.getItemEgreso().getDescripcion() + comilla));

        return items;
    }

    public static String filtrarPorCategoria (Request request, Response response){
        String categoria = request.queryParams("categoria");
        List<EgresoDTO> egresos = new ArrayList<>();
        List<Entidad> entidades = RepositorioEntidades.getInstance().obtenerEntidades();
        entidades.forEach(entidad -> {
                entidad.getEgresos().stream()
                            .filter(egreso -> filtrar(egreso, categoria))
                            .map(egreso -> new EgresoDTO(egreso, entidad))
                            .collect(Collectors.toCollection(() -> egresos));
                }
            );
        return toJson(egresos);
    }

    private static boolean filtrar(Egreso egreso, String categoria){
        return egreso.getCategorias().stream()
                                       .filter(c -> c.getNombre().equals(categoria) || Egresos.tieneCategoria(c,categoria))
                                        .count()>0;
    }

    private static boolean tieneCategoria(Categoria categoria, String nombreCategoria){
        Categoria categoriaHija = new Categoria(nombreCategoria);
        return categoria.contieneCategoriaHija(categoriaHija);
    }

    private static String toJson(List<EgresoDTO> listaEgresos){
        Gson gson = new Gson();
        String mensajeJson = gson.toJson(listaEgresos);
        return mensajeJson;
    }
}
