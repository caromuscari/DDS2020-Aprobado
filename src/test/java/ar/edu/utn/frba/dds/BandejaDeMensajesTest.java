package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.BandejaDeMendajes.NotificadorValidador;
import ar.edu.utn.frba.dds.Entidad.Organizacion;
import ar.edu.utn.frba.dds.Licitacion.Licitacion;
import ar.edu.utn.frba.dds.ResultadoLicitacion.ErrorCantidadPresupuestos;
import ar.edu.utn.frba.dds.ResultadoLicitacion.ErrorValidacion;
import ar.edu.utn.frba.dds.ResultadoLicitacion.EstadoValidacion;
import ar.edu.utn.frba.dds.ResultadoLicitacion.ResultadoValidacion;
import ar.edu.utn.frba.dds.Usuario.TipoPerfil;
import ar.edu.utn.frba.dds.Usuario.Usuario;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BandejaDeMensajesTest {
    NotificadorValidador notificador;
    Usuario usuario;
    Usuario usuario2;
    List<ResultadoValidacion> resultados;
    Licitacion licitacion;

    @Before
    public void init(){
        notificador = new NotificadorValidador();
        usuario = new Usuario("Carlos", "Diseño2020", TipoPerfil.OPERADOR,new Organizacion("organizacion 1", "descripcion"));
        usuario2 = new Usuario("Roberto","Diseño2020",TipoPerfil.ADMINISTRADOR,new Organizacion("organizacion 1", "descripcion"));
        resultados = new ArrayList<>();
    }

    @Test
    public void cantidadMensajesEnUsuario(){
        notificador.suscribir(usuario);
        resultados.add(new ResultadoValidacion(EstadoValidacion.OK,new Licitacion("Licitacion",1)));
        notificador.notificar(resultados);

        Assert.assertEquals(1,usuario.getBandejaDeMensajes().size());
    }

    @Test
    public void cantidadDeSuscriptores(){
        notificador.suscribir(usuario);
        notificador.suscribir(usuario2);
        notificador.desuscribir(usuario);

        Assert.assertEquals(1,notificador.getRevisores().size());
    }

    @Test
    public void leerMensajeUsuario(){
        notificador.suscribir(usuario);
        resultados.add(new ResultadoValidacion(EstadoValidacion.OK,new Licitacion("Licitacion",1)));
        notificador.notificar(resultados);

        String mensaje = usuario.getBandejaDeMensajes().get(0).leerMensaje();

        Assert.assertTrue(mensaje.contains("La validacion se realizo correctamente"));
    }

    @Test
    public void leerMensajeError(){
        notificador.suscribir(usuario);
        resultados.add(new ResultadoValidacion(EstadoValidacion.ERROR,new ErrorCantidadPresupuestos(5,3),new Licitacion("Licitacion",1)));
        notificador.notificar(resultados);

        String mensaje = usuario.getBandejaDeMensajes().get(0).leerMensaje();

        Assert.assertFalse(mensaje.contains("No se registraron errores en la validación"));
    }
}
