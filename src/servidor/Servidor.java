package servidor;
import java.net.*;
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
    public Servidor() {
        try {
            Socket[] listaSockets = new Socket[10];
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
                listaSockets[numeroCliente] = skServidor.accept();
                // Creamos nuevo hilo para atender cliente
                (new Thread(new Hilo(listaSockets[numeroCliente], numeroCliente))).start();
                ++numeroCliente;
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
