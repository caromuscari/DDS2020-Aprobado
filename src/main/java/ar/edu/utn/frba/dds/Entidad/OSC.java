package ar.edu.utn.frba.dds.Entidad;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("OSC")
public class OSC extends EntidadJuridica {

    public OSC(String nombre, String razonSocial, Long cuit, int codigoPostal, int codigoInscripcion, int cantidadPersonal, Double ventasProyectadas, TipoActividad tipoActividad, Double ventasPromedio) {
        super(nombre, razonSocial, cuit, codigoPostal, codigoInscripcion, cantidadPersonal, ventasProyectadas, tipoActividad, ventasPromedio);
    }
}
