package ar.edu.utn.frba.dds.Controladores;

import ar.edu.utn.frba.dds.App;
import ar.edu.utn.frba.dds.Categorizacion.Categoria;
import ar.edu.utn.frba.dds.DTO.EgresoDTO;
import ar.edu.utn.frba.dds.DTO.PresupuestoDTO;
import ar.edu.utn.frba.dds.Entidad.Entidad;
import ar.edu.utn.frba.dds.Licitacion.ItemOperacionPresupuesto;
import ar.edu.utn.frba.dds.Licitacion.Licitacion;
import ar.edu.utn.frba.dds.Licitacion.Presupuesto;
import ar.edu.utn.frba.dds.Repositorios.*;
import ar.edu.utn.frba.dds.Operaciones.*;
import ar.edu.utn.frba.dds.Operaciones.Proveedor;
import ar.edu.utn.frba.dds.Repositorios.RepoUsuarios;
import ar.edu.utn.frba.dds.Usuario.Usuario;
import ar.edu.utn.frba.dds.audit.Audit;
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


    public static ModelAndView paginaEgresos(Request request, Response response, EntityManager entity) {
        if (request.session(false) == null) {
            return Login.paginaLogin(request, response);
        } else {
            Usuario usuario = new RepoUsuarios(entity).buscarUsuario(request.session().attribute("usuario"));
            List<EgresoDTO> egresos = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            String page = request.queryParams("page");
            String filtro = request.queryParams("filter");
            int pagina = (page != null) ? Integer.parseInt(page) : 1;
            int elementoInicial = (pagina - 1) * App.getPageSize();

            usuario.getOrganizacion().getEntidades().forEach(entidad -> entidad.getEgresos().forEach(egreso -> egresos.add(new EgresoDTO(egreso, entidad))));

            if (filtro != null)  egresos.removeIf(dto -> !(dto.getEgreso().contieneCategoria(filtro)));

            int elementoFinal = ((pagina * App.getPageSize()) < egresos.size()) ? (pagina * App.getPageSize()) : egresos.size();
            map.put("egresos", egresos.subList(elementoInicial, elementoFinal));
            map.put("categorias", new Gson().toJson(usuario.getOrganizacion().getCriterios()));
            List<Integer> range = IntStream.rangeClosed(1, ((egresos.size() - 1) / App.getPageSize()) + 1).boxed().collect(Collectors.toList());
            map.put("pages", range);
            map.put("actualPage", page);
            map.put("filter", filtro);
            return new ModelAndView(map, "egresos.html");
        }
    }

    public static ModelAndView paginaNuevoEgreso(Request request, Response response, EntityManager entity) {
        if (request.session(false) == null) {
            return Login.paginaLogin(request, response);
        } else {
            Usuario usuario = new RepoUsuarios(entity).buscarUsuario(request.session().attribute("usuario"));
            Map<String, Object> map = new HashMap<>();
            List<Entidad> entidades = usuario.getOrganizacion().getEntidades();
            map.put("proveedores", new RepositorioProveedores(entity).obtenerProveedores());
            map.put("entidades", entidades);
            map.put("categorias", new Gson().toJson(usuario.getOrganizacion().getCriterios()));
            return new ModelAndView(map, "nuevoEgreso.html");
        }
    }

    public static ModelAndView paginaDetalleEgreso(Request request, Response response, EntityManager entity) {
        if (request.session(false) == null) {
            return Login.paginaLogin(request, response);
        } else {
            Egreso egreso = new RepositorioEgresos(entity).obtenerEgresoPorId(request.params(":id"));
            Map<String, Object> map = new HashMap<>();
            map.put("egreso", egreso);
            map.put("nombreEntidad", new RepositorioEntidades(entity).obtenerEntidadDeEgreso(egreso).getNombre());
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
            Egreso egreso = new RepositorioEgresos(entity).obtenerEgresoPorId(request.params("id"));
            map.put("egreso", egreso);
            return new ModelAndView(map, "nuevoPresupuestoEnEgreso.html");
        }
    }

    public static ModelAndView agregarPresupuesto(Request request, Response response, EntityManager entity) {
        if (request.session(false) == null) {
            return Login.paginaLogin(request, response);
        } else {
            Usuario usuario = new RepoUsuarios(entity).buscarUsuario(request.session().attribute("usuario"));
            Audit.crearAuditoria("AGREGAR_PRESUPUESTO","Ingresos",usuario.getUsuario());
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

                RepositorioProveedores repositorioProveedores = new RepositorioProveedores(entity);
                Proveedor proveedor = repositorioProveedores.obtenerProveedorPorNombre(presupuestoDTO.getProveedor().getNombre());

                Presupuesto presupuesto = new Presupuesto(new ArrayList<>(), proveedor, presupuestoDTO.getNombre());
                new RepositorioPresupuesto(entity).crearPresupuesto(presupuesto);

                Egreso egreso = new RepositorioEgresos(entity).obtenerEgresoPorId(request.params("id"));
                Entidad entidad = new RepositorioEntidades(entity).obtenerEntidadDeEgreso(egreso);
                List<Licitacion> licitaciones = entidad.getLicitaciones().stream().filter(licitacion -> {
                    if (licitacion.getEgreso() != null) {
                        return licitacion.getEgreso().getId() == egreso.getId();
                    }
                    return false;
                }).collect(Collectors.toList());
                licitaciones.forEach(licitacion -> licitacion.getPresupuestos().add(presupuesto));

                egreso.setPresupuesto(presupuesto);

                Files.deleteIfExists(tempFile);
            } catch (Exception e) {
                e.printStackTrace();
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
            Usuario usuario = new RepoUsuarios(entity).buscarUsuario(request.session().attribute("usuario"));
            Egreso egreso = new RepositorioEgresos(entity).obtenerEgresoPorId(request.params(":id"));
            Map<String, Object> map = new HashMap<>();
            map.put("egreso", egreso);
            map.put("proveedores", new RepositorioProveedores(entity).obtenerProveedores());
            map.put("entidades", usuario.getOrganizacion().getEntidades());
            map.put("categorias", new Gson().toJson(usuario.getOrganizacion().getCriterios()));
            map.put("nombreEntidad", new RepositorioEntidades(entity).obtenerEntidadDeEgreso(egreso).getNombre());
            map.put("categoriasEgreso", getCategoriasArray(egreso.getCategorias()));
            return new ModelAndView(map, "modificarEgreso.html");
        }
    }

    public static Object borrarEgreso(Request request, Response response, EntityManager entity) {

        if (request.session(false) == null) {
            return Login.paginaLogin(request, response);
        }
        Usuario usuario = new RepoUsuarios(entity).buscarUsuario(request.session().attribute("usuario"));
        Audit.crearAuditoria("BAJA","Egreso",usuario.getUsuario());
        String id = request.params(":id");

        new RepositorioEgresos(entity).borrarEgreso(id);

        return "Borrado exitoso";
    }

    public static ModelAndView guardarEgreso(Request request, Response response, EntityManager entity) {

        RepositorioEntidades repoEntidades = new RepositorioEntidades(entity);

        if (request.session(false) == null) {
            return Login.paginaLogin(request, response);
        }

        Usuario usuario = new RepoUsuarios(entity).buscarUsuario(request.session().attribute("usuario"));
        String nombreProveedor = request.queryParams("proveedor");
        String nombreEntidad = request.queryParams("entidad");
        String nombreEgreso = request.queryParams("nombre");
        String fechaEgreso = request.queryParams("fecha");
        String[] nombreCategorias = request.queryParamsValues("categorias");
        String descrMedioDePago = request.queryParams("descrMedioDePago");
        String nombreMedioDePago = request.queryParams("medioDePago");
        String numeroMedioDePago = request.queryParams("numeroMedioDePago");
        String id = request.params(":id");

        Proveedor proveedor = new RepositorioProveedores(entity).obtenerProveedorPorNombre(nombreProveedor);

        String cantItem = null;
        List<ItemOperacionEgreso> items = new ArrayList<>();
        for (ItemEgreso item : proveedor.getItems()) {
            cantItem = request.queryParams(item.getDescripcion());
            if (cantItem != null && Integer.parseInt(cantItem) != 0) {
                ItemOperacionEgreso itemOperacionEgreso = new ItemOperacionEgreso(Integer.parseInt(cantItem), item);
                items.add(itemOperacionEgreso);
            }
        }

        Egreso egreso = null;

        if (id == null) {
            Audit.crearAuditoria("ALTA","Egreso",usuario.getUsuario());
            egreso = new RepositorioEgresos(entity).crearEgreso(items, proveedor, nombreEgreso);
        } else {
            Audit.crearAuditoria("MODIFICAR","Egreso",usuario.getUsuario());
            egreso = new RepositorioEgresos(entity).obtenerEgresoPorId(request.params(":id"));
            egreso.setNombre(nombreEgreso);
            egreso.setProveedor(proveedor);
            egreso.setItems(items);

            repoEntidades.obtenerEntidadDeEgreso(egreso).getEgresos().remove(egreso);
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

        if (numeroMedioDePago != null && nombreMedioDePago != null) {
            MedioDePago medio = new MedioDePago(descrMedioDePago, Long.parseLong(numeroMedioDePago), nombreMedioDePago);
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

    private static String toJson(List<EgresoDTO> listaEgresos) {
        Gson gson = new Gson();
        String mensajeJson = gson.toJson(listaEgresos);
        return mensajeJson;
    }

}
