package ar.edu.utn.frba.dds.Controladores;

import ar.edu.utn.frba.dds.App;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;
import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Egresos {
    private static RepoUsuarios repoUsuarios = RepoUsuarios.getInstance();
    private static RepositorioProveedores proveedores = RepositorioProveedores.getInstance();
    private static RepositorioMedioDePago medios = RepositorioMedioDePago.getInstance();

    public static ModelAndView paginaEgresos(Request request, Response response, EntityManager entity) {
        if (request.session(false) == null) {
            return Login.paginaLogin(request, response);
        } else {
            List<EgresoDTO> egresos = new ArrayList<>();
            List<Entidad> entidades = RepositorioEntidades.getInstance().obtenerEntidades();
            Usuario usuario = repoUsuarios.buscarUsuario(request.session().attribute("usuario"));
            entidades.forEach(entidad -> entidad.getEgresos().forEach(egreso -> egresos.add(new EgresoDTO(egreso, entidad))));
            Map<String, Object> map = new HashMap<>();
            int pagina = (request.queryParams("page") != null) ? Integer.parseInt(request.queryParams("page")) : 1;
            int elementoInicial = (pagina-1)* App.getPageSize();
            int elementoFinal = ((pagina*App.getPageSize()) < egresos.size()) ? (pagina*App.getPageSize()) : egresos.size();
            map.put("egresos",  egresos.subList(elementoInicial,elementoFinal));
            map.put("categorias", new Gson().toJson(usuario.getOrganizacion().getCriterios()));
            List<Integer> range = IntStream.rangeClosed(1, ((egresos.size()-1)/App.getPageSize())+1).boxed().collect(Collectors.toList());
            map.put("pages",range);
            return new ModelAndView(map, "egresos.html");
        }
    }

    public static ModelAndView paginaNuevoEgreso(Request request, Response response, EntityManager entity) {
        if (request.session(false) == null) {
            return Login.paginaLogin(request, response);
        } else {
            Usuario usuario = repoUsuarios.buscarUsuario(request.session().attribute("usuario"));
            Map<String, Object> map = new HashMap<>();
            List<Entidad> entidades = usuario.getOrganizacion().getEntidades();
            map.put("proveedores", proveedores.obtenerProveedores());
            map.put("mediosDePago", medios.obtenerMediosDePago());
            map.put("entidades", entidades);
            map.put("categorias", new Gson().toJson(usuario.getOrganizacion().getCriterios()));
            return new ModelAndView(map, "nuevoEgreso.html");
        }
    }

    public static ModelAndView paginaDetalleEgreso(Request request, Response response, EntityManager entity) {
        if (request.session(false) == null) {
            return Login.paginaLogin(request, response);
        } else {
            Usuario usuario = repoUsuarios.buscarUsuario(request.session().attribute("usuario"));
            ar.edu.utn.frba.dds.Operaciones.Egreso egreso = RepositorioEntidades.getInstance().obtenerEgresoPorId(request.params(":id"));
            Map<String, Object> map = new HashMap<>();
            map.put("egreso", egreso);
            map.put("nombreEntidad", RepositorioEntidades.getInstance().obtenerEntidadDeEgreso(egreso).getNombre());
            map.put("categoriasEgreso", getCategoriasArray(egreso.getCategorias()));
            if (egreso.getPresupuesto() != null)
                map.put("categoriasPresupuesto", getCategoriasArray(egreso.getPresupuesto().getCategorias()));
            return new ModelAndView(map, "detalleEgreso.html");
        }
    }

    public static ModelAndView paginaAgregarPresupuesto(Request request, Response response, EntityManager entity) {
        if (request.session(false) == null) {
            return Login.paginaLogin(request, response);
        } else {
            Map<String, Object> map = new HashMap<>();
            Egreso egreso = RepositorioEntidades.getInstance().obtenerEgresoPorId(request.params("id"));
            map.put("egreso", egreso);
            return new ModelAndView(map, "nuevoPresupuestoEnEgreso.html");
        }
    }

    public static ModelAndView agregarPresupuesto(Request request, Response response, EntityManager entity) {
        if (request.session(false) == null) {
            return Login.paginaLogin(request, response);
        } else {
            Path tempFile;
            File uploadDir = new File("upload");
            uploadDir.mkdir();
            try {
                tempFile = Files.createTempFile(uploadDir.toPath(), "", "");

                request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

                InputStream input = request.raw().getPart("uploaded_file").getInputStream();
                Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);

                ObjectMapper mapper = new ObjectMapper();
                PresupuestoDTO presupuestoDTO = mapper.readValue(tempFile.toFile(), PresupuestoDTO.class);

                ar.edu.utn.frba.dds.Operaciones.Egreso egreso = RepositorioEntidades.getInstance().obtenerEgresoPorId(request.params("id"));
                egreso.setPresupuesto(new Presupuesto(new ArrayList<>(), presupuestoDTO.getProveedor(), presupuestoDTO.getNombre()));

                Files.deleteIfExists(tempFile);
            } catch (Exception e) {

            } finally {
                uploadDir.delete();
            }

            response.redirect("/egreso");
            return null;
        }
    }

    public static ModelAndView paginaModificarEgreso(Request request, Response response, EntityManager entity) {
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
            map.put("categorias", new Gson().toJson(usuario.getOrganizacion().getCriterios()));
            map.put("nombreEntidad", RepositorioEntidades.getInstance().obtenerEntidadDeEgreso(egreso).getNombre());
            map.put("categoriasEgreso", getCategoriasArray(egreso.getCategorias()));
            return new ModelAndView(map, "modificarEgreso.html");
        }
    }

    public static ModelAndView borrarEgreso(Request request, Response response, EntityManager entity) {
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

    public static ModelAndView guardarEgreso(Request request, Response response, EntityManager entity) {
        if (request.session(false) == null) {
            return Login.paginaLogin(request, response);
        }

        Usuario usuario = repoUsuarios.buscarUsuario(request.session().attribute("usuario"));

        String nombreProveedor = request.queryParams("proveedor");
        String nombreEntidad = request.queryParams("entidad");
        String nombreEgreso = request.queryParams("nombre");
        String fechaEgreso = request.queryParams("fecha");
        String[] nombreCategorias = request.queryParamsValues("categorias");
        String descrMedioDePago = request.queryParams("descrMedioDePago");
        String nombreMedioDePago = request.queryParams("medioDePago");
        String numeroMedioDePago = request.queryParams("numeroMedioDePago");
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
            for (String cat : nombreCategorias) {
                categorias.add(usuario.getOrganizacion().obtenerCategoria(cat));
            }
        }

        egreso.setCategorias(categorias);

        if(numeroMedioDePago != null && nombreMedioDePago != null) {
            MedioDePago medio = new MedioDePago(descrMedioDePago, Long.parseLong(numeroMedioDePago), TipoPago.valueOf(nombreMedioDePago));
            egreso.setMedioDePago(medio);
        }

        response.redirect("/egreso");
        return null;
    }

    public static List<String> getCategoriasArray(List<Categoria> categoriasList) {
        List<String> categorias = new ArrayList<>();
        String comilla = "\"";

        categoriasList.forEach(c -> categorias.add(comilla + c.getNombre() + comilla));

        return categorias;
    }

    public static String filtrarPorCategoria(Request request, Response response, EntityManager entity) {
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

    private static boolean filtrar(Egreso egreso, String categoria) {
        return egreso.getCategorias().stream()
                .filter(c -> c.getNombre().equals(categoria) || Egresos.tieneCategoria(c, categoria))
                .count() > 0;
    }

    private static boolean tieneCategoria(Categoria categoria, String nombreCategoria) {
        Categoria categoriaHija = new Categoria(nombreCategoria);
        return categoria.contieneCategoriaHija(categoriaHija);
    }

    private static String toJson(List<EgresoDTO> listaEgresos) {
        Gson gson = new Gson();
        String mensajeJson = gson.toJson(listaEgresos);
        return mensajeJson;
    }

}
