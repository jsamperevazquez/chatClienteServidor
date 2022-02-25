import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Clase ClienteChat
 * @version 0.1.1
 * @author joseangelsamperevazquez
 * Clase con Interfaz en Swing para crear conexion con servidor y sala de chat
 * Usa diferentes recursos de swing para facilitar al usuario el envio y recibo de mensajes
 */
public class ClienteChat {
    private JPanel panel1;
    private JLabel chatLabel;
    private JButton cerrarButton;
    private JTextArea textArea;
    private JTextField portField;
    private JTextField direcField;
    private JButton enviarButton;
    private JTextField nickField;
    private JButton conectarButton;
    private JTextField mensajeField;
    private JLabel titleLable;
    private JLabel msjLabel;
    private JLabel direcLabel;
    private JLabel puertoLabel;
    private JLabel nickLabel;
    private static JFrame frame;
    static boolean infintoC = true;
    static Socket skCliente;
    static Integer puerto = 0; //Numero de puerto para conexión con servidor
    static String servidor = ""; //IP o nombre DNS del servidor al que nos conectaremos
    static String nick = "";
    static String mensaje = ""; //Mensaje a enviar al servidor
    ConectarServer conn = new ConectarServer();

    /**
     * Constructor de ClienteChat
     * Se ocultan algunos elementos hasta que se establezca la conexion
     * Se generan los Listener para los botones de la interfaz
     */
    public ClienteChat() {
        textArea.setVisible(false);
        enviarButton.setVisible(false);
        chatLabel.setVisible(false);
        mensajeField.setVisible(false);
        msjLabel.setVisible(false);
        cerrarButton.setVisible(false);
        conectarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String direccion = direcField.getText();
                Integer puerto = Integer.parseInt(portField.getText());
                String nick = nickField.getText();
                try {
                    conn.conectarServer(direccion, puerto, nick);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        enviarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread hilo = new Thread(conn);
                mensaje = mensajeField.getText();
                try {
                    conn.enviarDatosServer(mensaje);
                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        cerrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    conn.cerrarConexion();
                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    /**
     * Metodo Principal
     * @param args
     * Se establece las caracteristicas del panel
     */
    public static void main(String[] args) {
        frame = new JFrame("ANGEL_MESSENGER");
        frame.setContentPane(new ClienteChat().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 400); // Le damos un tamano deseado porque el pack() lo pone demasiado pequeno
        frame.setVisible(true);
        frame.setLocationRelativeTo(null); //Situamos el frame en el centro de la pantalla

    }

    /**
     * Clase ConectarServer
     * @version 0.0.9
     * @author joseangelsamperevazquez
     * Clase que hereda de Thread encargada de flujos de conexion con server, entrada y salida de datos y cierre de conexion
     */
    class ConectarServer extends Thread {
        /**
         * Constante de tipo String para establecer condicion de cierre de conexion
         */
        private static final String FIN = "bye";
        /**
         * Variable de tipo String para introducir los mensajes del cliente
         */
        String mensajes = "";

        /**
         * Metodo run que ejecuta cada hilo
         * Gestiona los flujos de entrada y salida, y muestra en el textArea el mensaje
         */
        @Override
        public void run() {
            try {
                while (true) {
                    InputStream auxIn = skCliente.getInputStream();
                    DataInputStream infoEntrada = new DataInputStream(auxIn);
                    mensajes += infoEntrada.readUTF() + System.lineSeparator();
                    textArea.setText(mensajes);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        /**
         * Metodo para crear conexion con Servidor
         * @param address direccion de cliente
         * @param port puerto del servidor
         * @param nickName Nick del cliente
         * @throws IOException Excepcion de tipo Input Output
         */
        public void conectarServer(String address, Integer port, String nickName) throws IOException {
            frame.setSize(600, 500); // Le damos un tamano deseado porque el pack() lo pone demasiado pequeno
            textArea.setVisible(true);
            enviarButton.setVisible(true);
            chatLabel.setVisible(true);
            mensajeField.setVisible(true);
            msjLabel.setVisible(true);
            cerrarButton.setVisible(true);
            direcField.setVisible(false);
            portField.setVisible(false);
            nickField.setVisible(false);
            conectarButton.setVisible(false);
            direcLabel.setVisible(false);
            puertoLabel.setVisible(false);
            nickLabel.setVisible(false);
            titleLable.setForeground(Color.GREEN);
            titleLable.setText("Cliente: " + nickName + "  conectado");
            servidor = address;
            nick = nickName;
            try {
                puerto = port;
            } catch (Exception e) {
                textArea.setText(nickName + " introduce puerto valido");
                System.exit(1); //Cerramos aplicacion con codigo de salida 1
            }
            if (puerto > 1023) {
                try {
                    System.out.println("Conectando con servidor " + servidor + " por puerto: " + puerto + "...");
                    //Instanciamos clase Socket con servidor y puerto especificados
                    skCliente = new Socket(address, port);
                    System.out.println("Conectado a servidor: " + skCliente.getInetAddress());

                    //Cerramos la conexion con el servidor
                    //cerrarConexion();
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
            this.start();
        }

        /**
         * Metodo para envio de datos al servidor
         * @param mensaje Mensaje para enviar
         * @throws IOException Excepcion de tipo Input Output
         * @throws InterruptedException Excepcion de tipo Interrupted
         */
        public synchronized void enviarDatosServer(String mensaje) throws IOException, InterruptedException {
            //Establecemos el canal de comunicación
            OutputStream auxOut = skCliente.getOutputStream();
            DataOutputStream infoSalida = new DataOutputStream(auxOut);
            infoSalida.writeUTF(nick + ": " + mensaje);
            if (mensaje.equalsIgnoreCase(FIN)) {
                cerrarConexion();
                System.exit(1);
            }
            mensajeField.setText("");
        }

        /**
         * Metodo para cerrar la conexion con servidor y salida de aplicacion
         * @throws IOException Excepcion Input Output
         * @throws InterruptedException Excepcion Interrupted
         */
        public void cerrarConexion() throws IOException, InterruptedException {
            skCliente.close();
            textArea.setText("Cerrada conexión\n" +
                    "Que tengas buen día");
            sleep(2000);
            System.exit(1);
        }

    }


}