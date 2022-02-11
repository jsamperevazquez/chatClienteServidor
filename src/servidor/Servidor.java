package servidor;

import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 * Clase Servidor.
 * Servidor que permanece a la escucha para atender peticiones de clientes.
 * @author joseangelsamperevazquez
 * @since 11/02/2022
 * @version 0.0.5
 *
 */
public class Servidor {
    /**
     * Variable de instancia de tipo Socket para establecer conexión.
     */
    private Socket socket;
    /**
     * Variable de instancia de tipo ServerSocket para socket de servidor.
     */
    private ServerSocket serverSocket;
    /**
     * Variable de instancia de tipo DataInput para flujo de entrada.
     */
    private DataInputStream entradaDatos = null;
    /**
     * Variable de instancia de tipo DataOutput para flujo de salida.
     */
    private DataOutputStream salidaDatos = null;
    /**
     * Variable de instancia para entrada por teclado.
     */
    Scanner escaner = new Scanner(System.in);
    /**
     * Constante para poner fin a conversación.
     */
    final String SALIR = "bye";

    /**
     * Método para recibir datos de los posibles clientes
     */
    public void recibirDatos() {
        String st = "";
        try {
            do {
                st = entradaDatos.readUTF();
                System.out.println("\nCliente --> " + st);
                System.out.print("\nServidor --> ");
            } while (!st.equals(SALIR));
        } catch (IOException e) {
            cerrarConexion();
        }
    }


    public void enviar(String s) {
        try {
            salidaDatos.writeUTF(s);
            salidaDatos.flush();
        } catch (IOException e) {
            System.out.println("Error enviando datos: " + e.getMessage());
        }
    }

    public void escirbir() {
        while (true) {
            System.out.print("Servidor --> ");
            enviar(escaner.nextLine());
        }
    }

    public void cerrarConexion() {
        try {
            entradaDatos.close();
            salidaDatos.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Error cerrando conexión: " + e.getMessage());
        } finally {
            System.out.println("Cerrado chat");
            System.exit(0);

        }
    }

    public void conexion(int puerto) {
        Thread hilo = new Thread(new Thread(() -> {
            while (true) {
                try {
                    serverSocket = new ServerSocket(puerto);
                    System.out.println("Esperando conexión entrante en el puerto " + String.valueOf(puerto) + "...");
                    socket = serverSocket.accept();
                    System.out.println("Conexión establecida con: " + socket.getInetAddress().getHostName() + "\n\n\n");
                    entradaDatos = new DataInputStream(socket.getInputStream());
                    salidaDatos = new DataOutputStream(socket.getOutputStream());
                    salidaDatos.flush();
                    recibirDatos();
                } catch (Exception e) {
                    System.out.println("Error en conexion: " + e.getMessage());
                    System.exit(0);
                } finally{
                cerrarConexion();
            }
        }
    }));
        hilo.start();
}

    public static void main(String[] args) throws IOException {
        Servidor servidor = new Servidor();
        Scanner sc = new Scanner(System.in);

        System.out.println("Ingresa un puerto mayor de 1023 (bien conocido), o se asigna por defecto 6000: ");
        String puerto = sc.nextLine();
        if (puerto.length() <= 1023) puerto = "6000";
        servidor.conexion(Integer.parseInt(puerto));
        servidor.escirbir();
    }
}