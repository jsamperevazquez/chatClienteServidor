package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
            // Objeto recibido del cliente
            Object dato;
            ObjectOutputStream salidaDatos = new ObjectOutputStream(skCliente.getOutputStream());
            // Lectura de datos
            ObjectInputStream entradaDatos = new ObjectInputStream(skCliente.getInputStream());
            while (true) {
                dato = entradaDatos.readObject();
                System.out.println("Mensaje de cliente " + numeroCliente + ": " + dato);
                // Enviamos objeto a cliente
                salidaDatos.writeObject(dato);
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