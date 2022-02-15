import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;


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
    private JLabel ipCliLab;
    private JTextField ipCliTextField;
    private static boolean infintoC = true;
    private static Socket skCliente;
    private static Integer puerto = 0; //Numero de puerto para conexión con servidor
    private static String servidor = ""; //IP o nombre DNS del servidor al que nos conectaremos
    private static String nick = "";
    private static String mensaje = ""; //Mensaje a enviar al servidor
    private static JFrame frame;
    ConectarServer conn = new ConectarServer();

    public ClienteChat() {
        textArea.setVisible(false);
        enviarButton.setVisible(false);
        chatLabel.setVisible(false);
        mensajeField.setVisible(false);
        msjLabel.setVisible(false);
        nickField.setVisible(false);
        nickLabel.setVisible(false);
        ipCliLab.setVisible(false);
        ipCliTextField.setVisible(false);
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
                Thread hilo = new Thread(new Thread(() -> {
                    try {
                        mensaje = mensajeField.getText();
                        conn.enviarDatosServer(mensaje);
                    } catch (IOException | InterruptedException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }));
                hilo.start();
            }
        });
        cerrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    conn.cerrarConexion();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        frame = new JFrame("ANGEL_MESSENGER");
        frame.setContentPane(new ClienteChat().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400); // Le damos un tamano deseado porque el pack() lo pone demasiado pequeno
        frame.setVisible(true);
        frame.setLocationRelativeTo(null); //Situamos el frame en el centro de la pantalla

    }

    class ConectarServer extends Thread implements Serializable{
        private static final String FIN = "bye";

        public void conectarServer(String address, Integer port, String nickName) throws IOException {
            frame.setSize(600,600);
            textArea.setVisible(true);
            enviarButton.setVisible(true);
            chatLabel.setVisible(true);
            mensajeField.setVisible(true);
            msjLabel.setVisible(true);
            cerrarButton.setVisible(true);
            ipCliLab.setVisible(true);
            ipCliTextField.setVisible(true);
            nickField.setVisible(true);
            nickLabel.setVisible(true);
            direcField.setVisible(false);
            portField.setVisible(false);
            conectarButton.setVisible(false);
            direcLabel.setVisible(false);
            puertoLabel.setVisible(false);


            servidor = address;
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

        }

        public synchronized void enviarDatosServer(String mensaje) throws IOException, InterruptedException, ClassNotFoundException {


            //Establecemos el canal de comunicación
            ObjectOutputStream objAux = new ObjectOutputStream(skCliente.getOutputStream());
            Datos datosEnviar = new Datos(nickField.getText(),ipCliTextField.getText(),mensaje);
            objAux.writeObject(datosEnviar);
            //infoSalida.writeUTF(mensaje);
            if (mensaje.equalsIgnoreCase(FIN)) {
                cerrarConexion();
                System.exit(1);
            }
            mensajeField.setText("");
            recibirDatosServer();


        }

        public void recibirDatosServer() throws IOException, ClassNotFoundException {
            // Recibo el objeto que envía el server
            ObjectInputStream datosRecibidos = new ObjectInputStream(skCliente.getInputStream());
            Object objetoLectura = datosRecibidos.readObject();
            textArea.setText(objetoLectura.toString());
        }

        public void cerrarConexion() throws IOException {
            skCliente.close();
        }

    }
}