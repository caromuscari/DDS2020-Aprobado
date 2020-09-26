package ar.edu.utn.frba.dds.Operaciones;

import java.util.ArrayList;
import java.util.List;

public class RepositorioProveedores {

    public static RepositorioProveedores instance = null;
    private List<Proveedor> proveedores = new ArrayList<>();

    private RepositorioProveedores() {}

    public static RepositorioProveedores getInstance() {
        if (instance == null) {
            instance = new RepositorioProveedores();
        }
        return instance;
    }

    public Proveedor crearProveedor(String nombre, Long identificador, String direccionPostal) {
        Proveedor proveedor = new Proveedor(nombre, identificador, direccionPostal);
        proveedores.add(proveedor);
        return proveedor;
    }

    public List<Proveedor> obtenerProveedores() {
        return proveedores;
    }

    public Proveedor obtenerProveedorPorNombre(String nombre) {
        for (Proveedor proveedor : proveedores) {
            if (nombre.equals(proveedor.getNombre())) {
                return proveedor;
            }
        }
        return null;
    }
}
