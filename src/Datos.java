import java.io.Serializable;

public class Datos implements Serializable {
    private String nick;
    private String mensaje;
    private String ip;


    public Datos(String nick, String ip, String mensaje) {
        this.nick = nick;
        this.ip = ip;
        this.mensaje = mensaje;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return nick + ": " + mensaje;

    }
}
