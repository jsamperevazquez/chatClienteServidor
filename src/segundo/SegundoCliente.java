package segundo;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class SegundoCliente {
    static boolean infintoC = true;
    static Socket skCliente;
    public static void main(String[] args) {

        SegundoCliente clienteChat = new SegundoCliente();
        int puerto = 0; //Numero de puerto para conexión con servidor
        String servidor = ""; //IP o nombre DNS del servidor al que nos conectaremos
        String nickName = "";
        String mensaje = ""; //Mensaje a enviar al servidor

        Scanner lecturaTeclado = new Scanner(System.in);
        System.out.print("Introduce la direccion del servidor: ");
        servidor = lecturaTeclado.nextLine();
        System.out.print("Introduce el puerto de conexion con el servidor: ");
        try {
            puerto = Integer.parseInt(lecturaTeclado.nextLine());
        } catch (Exception e) {
            System.out.println("Introduce puerto valido");
            System.exit(1); //Cerramos aplicacion con codigo de salida 1
        }


        if (puerto > 0) {
            try {
                System.out.println("Conectando con servidor " + servidor + " por puerto: " + puerto + "...");
                //Instanciamos clase Socket con servidor y puerto especificados
                skCliente = new Socket(servidor, puerto);
                System.out.println("Conectado a servidor: " + skCliente.getInetAddress());

                while (!mensaje.equals("bye")) {
                    //Establecemos el canal de comunicación
                    OutputStream auxOut = skCliente.getOutputStream();
                    DataOutputStream infoSalida = new DataOutputStream(auxOut);
                    System.out.print("Cliente--> ");
                    mensaje = lecturaTeclado.nextLine();
                    infoSalida.writeUTF(mensaje);
                    clienteChat.recibirDatosServer();

                }
                //Cerramos la conexion con el servidor
                skCliente.close();
            } catch (UnknownHostException ex) {
                System.out.println("Servidor no encontrado: " + ex.getMessage());
                System.exit(1); //Salimos del programa con código de salida 1
            } catch (IOException ex) {
                System.out.println("Error al conectar al servidor: " + ex.getMessage());
                System.exit(2); //Salimos del programa con código de salida 2
            }
        } else {
            System.out.println("Introduce puerto, una IP  y mensaje");
            System.exit(1); //Cerramos aplicación con código de salida 1
        }
    }

    public void recibirDatosServer() throws IOException {
        // Recibo e imprimo en pantalla el msje q me envía el server
        // infoEntrada -> informcion que ingresa al cliente
        InputStream auxIn = skCliente.getInputStream();
        DataInputStream infoEntrada = new DataInputStream(auxIn);
        String lectura = infoEntrada.readUTF();
        System.out.println("Servidor--> " + lectura);
    }
}
