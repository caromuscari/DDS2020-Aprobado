package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.BandejaDeMendajes.NotificadorValidador;
import ar.edu.utn.frba.dds.Entidad.Organizacion;
import ar.edu.utn.frba.dds.ResultadoLicitacion.ErrorCantidadPresupuestos;
import ar.edu.utn.frba.dds.ResultadoLicitacion.ErrorValidacion;
import ar.edu.utn.frba.dds.ResultadoLicitacion.EstadoValidacion;
import ar.edu.utn.frba.dds.ResultadoLicitacion.ResultadoValidacion;
import ar.edu.utn.frba.dds.Usuario.TipoPerfil;
import ar.edu.utn.frba.dds.Usuario.Usuario;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BandejaDeMensajesTest {
    NotificadorValidador notificador;
    Usuario usuario;
    Usuario usuario2;
    ResultadoValidacion resultado;

    @Before
    public void init(){
        notificador = new NotificadorValidador();
        usuario = new Usuario("Carlos", "Diseño2020", TipoPerfil.OPERADOR,new Organizacion());
        usuario2 = new Usuario("Roberto","Diseño2020",TipoPerfil.ADMINISTRADOR,new Organizacion());
    }

    @Test
    public void cantidadMensajesEnUsuario(){
        notificador.suscribir(usuario);
        resultado = new ResultadoValidacion(EstadoValidacion.OK,1927594);
        notificador.notificar(resultado);

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
        resultado = new ResultadoValidacion(EstadoValidacion.OK,1927594);
        notificador.notificar(resultado);

        String mensaje = usuario.getBandejaDeMensajes().get(0).leerMensaje();

        Assert.assertTrue(mensaje.contains("No se registraron errores en la validación"));
    }

    @Test
    public void leerMensajeError(){
        notificador.suscribir(usuario);
        resultado = new ResultadoValidacion(EstadoValidacion.ERROR,new ErrorCantidadPresupuestos(5,3),1927594);
        notificador.notificar(resultado);

        String mensaje = usuario.getBandejaDeMensajes().get(0).leerMensaje();

        Assert.assertFalse(mensaje.contains("No se registraron errores en la validación"));
    }
}
