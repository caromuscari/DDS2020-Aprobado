package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.Usuario.BuilderUsuario;
import ar.edu.utn.frba.dds.Usuario.Organizacion;
import ar.edu.utn.frba.dds.Usuario.TipoPerfil;
import ar.edu.utn.frba.dds.Usuario.Usuario;
import ar.edu.utn.frba.dds.Usuario.Hash;
import java.util.*;


public class App
{
    public static int limiteIntentos = 2;

    public static void main( String[] args ) {

        Hash encriptador = new Hash();
        Scanner sn = new Scanner(System.in);
        Map<String, Usuario> registrados = new HashMap<String, Usuario>();

        long inicio;
        long fin;

        // Consola
        try
        {
            String usuario;
            String pass;
            int opcion;
            Usuario user;
            int intentosFallidos = 0;

            System.out.println("");
            System.out.println("\u001B[36m" + "Bienvenido a GeSoc!" + "\u001B[0m");
            do {
                System.out.println("Elija una opción:");
                System.out.println(">> 1. Registrar Operador");
                System.out.println(">> 2. Registrar Administrador");
                System.out.println(">> 3. Iniciar Sesión");
                System.out.println(">> 4. Salir");

                opcion = sn.nextInt();

                switch(opcion) {
                    case 1: {
                        // Registrar Operador
                        System.out.println("Ingrese usuario: ");
                        usuario = sn.next();
                        System.out.println("Ingrese contraseña: ");
                        pass = sn.next();

                        BuilderUsuario nuevoOperador = new BuilderUsuario();
                        nuevoOperador.setUsuario(usuario);
                        nuevoOperador.setPassword(pass);
                        nuevoOperador.setOrganizacion(new Organizacion());
                        nuevoOperador.setPerfil(TipoPerfil.OPERADOR);
                        user = nuevoOperador.registrar();

                        registrados.put(usuario,user);
                        System.out.println("Operador " + usuario + " registrado exitosamente");
                        break;
                    }
                    case 2:
                        // Registrar Administrador
                        System.out.println("Ingrese usuario: ");
                        usuario = sn.next();
                        System.out.println("Ingrese contraseña: ");
                        pass = sn.next();

                        BuilderUsuario nuevoAdministador = new BuilderUsuario();
                        nuevoAdministador.setUsuario(usuario);
                        nuevoAdministador.setPassword(pass);
                        nuevoAdministador.setOrganizacion(new Organizacion());
                        nuevoAdministador.setPerfil(TipoPerfil.ADMINISTRADOR);
                        user = nuevoAdministador.registrar();

                        registrados.put(usuario,user);
                        System.out.println("Administrador " + usuario + " registrado exitosamente");
                        break;
                    case 3:
                        // Login.
                        System.out.println("Ingrese usuario: ");
                        usuario = sn.next();
                        System.out.println("Ingrese contraseña: ");
                        pass = sn.next();

                        if(registrados.get(usuario) != null) {
                            user = registrados.get(usuario);
                            if(encriptador.hashear(pass).equals(user.getPassword()))
                            {
                                System.out.println("\u001B[32m" + "Ha iniciado sesión. Bienvenido " + user.getUsuario() + "\u001B[0m");
                                sn.reset();
                                user.setCantidadIntentos(0);

                                System.out.println("----------TODO----------");
                            }
                            else {
                                System.out.println("\u001B[31m" + "Contraseña inválida." + "\u001B[0m");
                                if(registrados.get(usuario).getCantidadIntentos() < limiteIntentos) {
                                    intentosFallidos++;
                                    registrados.get(usuario).setCantidadIntentos(intentosFallidos);
                                    System.out.println("Intentos fallidos: " + intentosFallidos + "/" + limiteIntentos);
                                }
                                else {
                                    System.out.println("Bloqueado por 15 segundos");
                                    inicio = System.currentTimeMillis();
                                    fin = inicio + 15*1000;
                                    while(System.currentTimeMillis() < fin)
                                    {
                                        // NO hago nada -- Bloqueado --
                                    }
                                }
                            }
                        }
                        else
                        {
                            System.out.println("\u001B[31m" + "Usuario inexistente." + "\u001B[0m");
                        }
                        break;
                    default:
                        System.out.println("Inserte un número válido para seleccionar una opción");
                }

            }while (!(opcion == 4));
        }

        catch (InputMismatchException e) {
            System.out.println("Inserte un número para seleccionar una opción");
            sn.nextInt();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}
