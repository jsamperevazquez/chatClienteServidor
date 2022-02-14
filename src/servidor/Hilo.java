package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Hilo extends Thread
{
    // Socket cliente
    Socket skCliente;
    int numeroCliente;

    public Hilo(Socket skEnviado, int num)
    {
        skCliente = skEnviado;
        numeroCliente = num;
        System.out.println("Conexion con cliente: " + num);
    }

    public void run() {
        try {
            // Mensajes recibidos de clientes
            String mensaje;
            // variable DataoutputStream para enviar mensajes a clientes
            DataOutputStream salidaDatos = new DataOutputStream(skCliente.getOutputStream());
            // Lectura de datos
            DataInputStream entradaDatos = new DataInputStream(skCliente.getInputStream());
            while (true) {
                mensaje = entradaDatos.readUTF();
                System.out.println("Mensaje de cliente " + numeroCliente + ": " + mensaje);
                // Enviamos mensaje a cliente
                salidaDatos.writeUTF("Recibido mensaje: " + mensaje);
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            skCliente.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}