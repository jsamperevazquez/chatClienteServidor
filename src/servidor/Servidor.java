package servidor;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clase Servidor.
 * @version 0.0.2
 * @author joseangelsamperevazquez
 * Clase Servidor encargada de crear 10 sockets para atender mensajes de clietes.
 * Solicita al usuario el número del puerto para levantar la conexion.
 *
 */
public class Servidor
{
    /**
     * Integer puerto para administrar el puerto del servidor
     */
    private static int puerto;
    /**
     * Varible de tipo Scanner encargada de leer por teclado el numero de puerto
     */
    private static Scanner sc;

    /**
     * Constructor de Servidor donde se establecen las condiciones del mismo
     */

    Object lock = new Object();

   List<Hilo> hilos = new ArrayList<>();


    public Servidor() {
        try {
            sc = new Scanner(System.in);
            System.out.println("Ingresa un puerto mayor de 1023 (bien conocido), o se asigna por defecto 6000: ");
            String puerto = sc.nextLine();
            if ( puerto.length() == 0 || Integer.parseInt(puerto) <= 1023 ) puerto = "6000";
            ServerSocket skServidor = new ServerSocket(Integer.parseInt(puerto));
            System.out.println("Escucho en el puerto " + puerto);
            int numeroCliente = 0;
            String nick = " ";

            while (true) {
                // Aceptamos conexiones
                Socket skcliente = skServidor.accept();

                // Creamos nuevo hilo para atender cliente
                Hilo hilo = new Hilo(this,skcliente,numeroCliente);
                synchronized (lock){
                    hilos.add(hilo);
                }
                new Thread(hilo).start();
                ++numeroCliente;
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
        void broadCast(String mensaje) throws IOException {
        List<Hilo> copiaHilos;
        synchronized (lock) {
            copiaHilos = new ArrayList<>(hilos);
        }
        for (Hilo hilo:copiaHilos
             ) {
            hilo.enviarMensaje(mensaje);
        }
    }
}
