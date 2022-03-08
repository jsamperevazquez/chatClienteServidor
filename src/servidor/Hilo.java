package servidor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Clase Hilo
 * @version 0.0.9
 * @author joseangelsamperevazquez
 * Clase Hilo que hereda de Thread para gestionar cada hilo que envia la clase Servidor.
 * Uso de DataInput y DataOutput para la gestion de los flujos de mensajes.
 */
public class Hilo extends Thread {
    // Socket cliente
    /**
     * Variable de tipo Socket para conexion entre servidor y cliente
     */
    private Socket skCliente;

    // Número de clientes que se conectan a servidor
    /**
     * Variable integer para contabilizar el numero de clientes conectados
     */
    private static int numeroCliente;
    /**
     * Variable de tipo DataOutput para flujo de salida de datos hacia clientes
     */
    private static DataOutputStream salidaDatos;
    /**
     * Variable de tipo DataInput para flujo de entrada de datos de cliente
     */
    private static DataInputStream entradaDatos;
    /**
     * Variable String inicializada en null para almacenar mensaje
     */
    private static String datoRecibido = null;

    /**
     * Objeto de la clase Servidor
     */
    private Servidor servidor;
    /**
     * ArrayList para acumular sockets
     */
    private static ArrayList<Socket> listaH = new ArrayList<>();
    /**
     * Constructor de Hilo
     * @param server Clase Servidor que recibe
     * @param skEnviado Socket recibido de Servidor
     * @param num Numero de cliente
     */
    public Hilo(Servidor server,Socket skEnviado, int num) throws IOException {
        this.servidor = server;
        skCliente = skEnviado;
        numeroCliente = num;
        System.out.println("Conexion con cliente: " + num);
        listaH.add(skCliente);
    }

    /**
     * Metodo Run que ejecuta cada hilo instanciado
     * Gestiona el numero de clientes
     * Crea instancias de tipo Stream para flujo de datos
     * Envia los datos a los diferentes clientes
     * Cierra conexion con clientes
     */

    public void run() {
        try {
            if (numeroCliente < 11) {

                while (true) {


                        // Lectura de datos
                        entradaDatos = new DataInputStream(new BufferedInputStream(skCliente.getInputStream()));
                        datoRecibido = entradaDatos.readUTF();
                        System.out.println("Mensaje de cliente " + numeroCliente + ": " + datoRecibido);
                        // Enviamos objeto a cliente
                        enviarMensaje(datoRecibido);
                }
            } else{
                salidaDatos = new DataOutputStream(new BufferedOutputStream(skCliente.getOutputStream()));
                salidaDatos.writeUTF("Sala llena");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            skCliente.close();
            numeroCliente--;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Método para realizar broadcast a todos los clientes
     * @param mensaje Mensaje que recibe como parámetro que es la lectura de los mensajes de los clientes
     * @throws IOException
     */
    public void enviarMensaje(String mensaje) throws IOException {
        for (Socket s:listaH
        ) {
            DataOutputStream output = new DataOutputStream(s.getOutputStream());
            output.writeUTF(mensaje);
        }
    }
}