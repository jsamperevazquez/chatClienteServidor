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
Aplicaci�n que representa una sala para chat, en la que se podr�n conectar hasta diez Clientes.
El cliente se conecta al servidor especificando direcci�n de este, puerto y su Nick.
El servidor recibe y reenv�a los mensajes a todo usuario que est� conectado.
Cada uno de los clientes ver� reflejado los mensajes enviados por los diferentes usuarios y ser�n mostrados en su respectiva interfaz.

## Ejemplo de Uso 

### <span style="color:red">Vista Cliente</span>
* Conexi�n con Server
  
![Conexi�n Cliente](https://github.com/jsamperevazquez/chatClienteServidor/blob/master/src/Media/clienteConex.png)

* Sala chat
  
![Sala chat cliente](https://github.com/jsamperevazquez/chatClienteServidor/blob/master/src/Media/clienteChat.png)

### <span style="color:yellow">Lado Servidor</span>

* Levantar servicio
  
![Conexi�n server](https://github.com/jsamperevazquez/chatClienteServidor/blob/master/src/Media/serverCon.png)

* Escuchar mensajes  


![Escucha Server](https://github.com/jsamperevazquez/chatClienteServidor/blob/master/src/Media/serverEsc.png)
