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
    Socket skCliente;

    // Número de clientes que se conectan a servidor
    /**
     * Variable integer para contabilizar el numero de clientes conectados
     */
    int numeroCliente;
    /**
     * Variable de tipo DataOutput para flujo de salida de datos hacia clientes
     */
    static DataOutputStream salidaDatos;
    /**
     * Variable de tipo DataInput para flujo de entrada de datos de cliente
     */
    static DataInputStream entradaDatos;
    /**
     * Variable String inicializada en null para almacenar mensaje
     */
    static String datoRecibido = null;

    /**
     * Constructor de Hilo
     * @param skEnviado Socket recibido de Servidor
     * @param num Numero de cliente
     */
    public Hilo(Socket skEnviado, int num) {
        skCliente = skEnviado;
        numeroCliente = num;
        System.out.println("Conexion con cliente: " + num);
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
                    //Escritura datos
                    salidaDatos = new DataOutputStream(skCliente.getOutputStream());
                    // Lectura de datos
                    entradaDatos = new DataInputStream(skCliente.getInputStream());
                    datoRecibido = entradaDatos.readUTF();
                    System.out.println("Mensaje de cliente " + numeroCliente + ": " + datoRecibido);
                    // Enviamos objeto a cliente
                    salidaDatos.writeUTF(datoRecibido);
                }
            } else
                System.out.println("Servidor ocupado");
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
}