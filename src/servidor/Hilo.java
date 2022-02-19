package servidor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Hilo extends Thread {
    // Socket cliente
    Socket skCliente;

    // Número de clientes que se conectan a servidor
    int numeroCliente;

    static DataOutputStream salidaDatos;
    static DataInputStream entradaDatos;
    static String datoRecibido = null;


    public Hilo(Socket skEnviado, int num) {
        skCliente = skEnviado;
        numeroCliente = num;
        System.out.println("Conexion con cliente: " + num);
    }

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