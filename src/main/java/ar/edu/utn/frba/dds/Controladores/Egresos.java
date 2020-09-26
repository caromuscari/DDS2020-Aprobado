package ar.edu.utn.frba.dds.Controladores;

import ar.edu.utn.frba.dds.DTO.EgresoDTO;
import ar.edu.utn.frba.dds.Entidad.Entidad;
import ar.edu.utn.frba.dds.Repositorios.*;
import ar.edu.utn.frba.dds.Operaciones.*;
import ar.edu.utn.frba.dds.Operaciones.Proveedor;
import ar.edu.utn.frba.dds.Repositorios.RepoUsuarios;
import ar.edu.utn.frba.dds.Usuario.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Egresos {
    private static RepoUsuarios repoUsuarios = RepoUsuarios.getInstance();
    private static RepositorioProveedores proveedores = RepositorioProveedores.getInstance();
    private static RepositorioMedioDePago medios = RepositorioMedioDePago.getInstance();

    public static ModelAndView paginaEgresos(Request request, Response response) {
        if(request.session(false) == null) {
            return Login.paginaLogin(request,response);
        }
        else {
            List<EgresoDTO> egresos = new ArrayList<>();
            List<Entidad> entidades = RepositorioEntidades.getInstance().obtenerEntidades();
            entidades.forEach(entidad -> entidad.getEgresos().forEach(egreso -> egresos.add(new EgresoDTO(egreso,entidad))));
            Map<String, Object> map = new HashMap<>();
            map.put("egresos",egresos);
            return new ModelAndView(map, "egresos.html");
        }
    }

    public static ModelAndView paginaNuevoEgreso(Request request, Response response) {
        if(request.session(false) == null) {
            return Login.paginaLogin(request,response);
        }
        else {
            Usuario usuario = repoUsuarios.buscarUsuario(request.session().attribute("usuario"));
            Map<String, Object> map = new HashMap<>();
            List<Entidad> entidades = usuario.getOrganizacion().getEntidades();
            map.put("proveedores", proveedores.obtenerProveedores());
            map.put("mediosDePago", medios.obtenerMediosDePago());
            map.put("entidades", entidades);
            return new ModelAndView(map, "nuevoEgreso.html");
        }
    }

    public static ModelAndView paginaModificarEgreso(Request request, Response response) {
        if(request.session(false) == null) {
            return Login.paginaLogin(request,response);
        }
        else {
            Usuario usuario = repoUsuarios.buscarUsuario(request.session().attribute("usuario"));
            ar.edu.utn.frba.dds.Operaciones.Egreso egreso = RepositorioEntidades.getInstance().obtenerEgresoPorId(request.params(":id"));
            Map<String, Object> map = new HashMap<>();
            map.put("egreso",egreso);
            map.put("proveedores", proveedores.obtenerProveedores());
            map.put("mediosDePago", medios.obtenerMediosDePago());
            map.put("entidades", usuario.getOrganizacion().getEntidades());
            return new ModelAndView(map, "modificarEgreso.html");
        }
    }

    public static ModelAndView guardarEgreso(Request request, Response response) {
        if(request.session(false) == null) {
            return Login.paginaLogin(request, response);
        }

        Usuario usuario = repoUsuarios.buscarUsuario(request.session().attribute("usuario"));

        String nombreProveedor = request.queryParams("proveedor");
        String nombreEntidad = request.queryParams("entidad");
        String nombreEgreso = request.queryParams("nombre");
        String fechaEgreso = request.queryParams("fecha");
        String id = request.params(":id");

        System.out.print(request.body());

        List<ItemOperacionEgreso> items = new ArrayList<>();
        String itemsId = request.queryParams("items");
        if(itemsId != null) {
/*            for (String id : itemsId) {
                ItemOperacionEgreso itemOperacionEgreso = RepositorioItemsOperacionEgreso.getInstance().obtenerItemsPorId(Long.parseLong(id));
                if (itemOperacionEgreso != null) {
                    items.add(itemOperacionEgreso);
                }
            }*/
        }


        Proveedor proveedor = null;
        if (nombreProveedor != null) proveedor = RepositorioProveedores.getInstance().obtenerProveedorPorNombre(nombreProveedor);

        ar.edu.utn.frba.dds.Operaciones.Egreso egreso = null;

        if(id == null){
            egreso = RepositorioEgresos.getInstance().crearEgreso(items, proveedor, nombreEgreso);
        }
        else {
            egreso = RepositorioEntidades.getInstance().obtenerEgresoPorId(request.params(":id"));
            egreso.setNombre(nombreEgreso);
            egreso.setProveedor(proveedor);
            egreso.setItems(items);

            RepositorioEntidades.getInstance().obtenerEntidadDeEgreso(egreso).getEgresos().remove(egreso);
        }

        egreso.setFecha(LocalDate.parse(fechaEgreso));
        if(nombreEntidad != null) {
            Entidad entidad = usuario.getOrganizacion().getEntidades().stream().filter(e -> e.getNombre().matches(nombreEntidad)).findFirst().get();
            entidad.getEgresos().add(egreso);
        }
        //falta categorias
        //falta documentos
        //falta presupuesto

        response.redirect("/egreso");
        return null;
    }
}
