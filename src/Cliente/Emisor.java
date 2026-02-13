package Cliente;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import Socket.SocketImp;

public class Emisor extends Thread {

	private String equipo;
	private int puerto;
	private Cliente cliente;
	
	protected Emisor(int puerto, String equipo, Cliente cliente) {
		this.puerto = puerto;
		this.equipo = equipo;
		this.cliente = cliente;
	}
	
	public void run() {
		try {
			ServerSocket serv_socket = new ServerSocket(puerto);
			SocketImp socket = new SocketImp(serv_socket);
			Equipo e = new Equipo(this.equipo, System.getProperty("user.dir") + "/src/BaseDatos/" + cliente.getNombre() + "/Equipos/" + equipo);
			socket.write(e);
			serv_socket.close();
			socket.close();
			
		} catch (IOException e) {
			System.err.println("OCURRIO UN ERROR CUANDO SE EJECUTABA EL HILO DEL EMISOR... ");
			e.printStackTrace();
		}
	}
	
}