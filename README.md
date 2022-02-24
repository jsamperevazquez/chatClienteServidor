# <span style="color:green">CHAT JAVA SERVIDOR-MULTICLIENTE (SOCKETS-HILOS)</span>

## Recursos  
- Interfaz swing para clientes
- Sockets Servidor y Cliente
- Threads en Servidor y Cliente

## Estrucutura del proyecto
- Package servidor con Clase Main, Servidor e Hilo
- Package Cliente con Clase cliente e Interfaz
- Package entidad con Clase Datos para posible pivote a Object

![Imagen estructura app](https://github.com/jsamperevazquez/chatClienteServidor/blob/master/src/Media/estructura.png)

## Resumen app
Aplicación que representa una sala para chat, en la que se podrán conectar hasta diez Clientes.
El cliente se conecta al servidor especificando dirección de este, puerto y su Nick.
El servidor recibe y reenvía los mensajes a todo usuario que esté conectado.
Cada uno de los clientes verá reflejado los mensajes enviados por los diferentes usuarios y serán mostrados en su respectiva interfaz.

## Ejemplo de Uso 

### <span style="color:red">Vista Cliente</span>
* Conexión con Server
  
![Conexión Cliente](https://github.com/jsamperevazquez/chatClienteServidor/blob/master/src/Media/clienteConex.png)

* Sala chat
  
![Sala chat cliente](https://github.com/jsamperevazquez/chatClienteServidor/blob/master/src/Media/clienteChat.png)

### <span style="color:yellow">Lado Servidor</span>

* Levantar servicio
  
![Conexión server](https://github.com/jsamperevazquez/chatClienteServidor/blob/master/src/Media/serverCon.png)

* Escuchar mensajes  


![Escucha Server](https://github.com/jsamperevazquez/chatClienteServidor/blob/master/src/Media/serverEsc.png)
