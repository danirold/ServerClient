package Servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

import Cliente.OyenteServidor;
import Locks.Lock;
import Locks.LockBakery;
import Socket.SocketImp;

public class Servidor {

	private SocketImp socket;
	private ServerSocket servidor_socket;
	
    private final static int puerto = 1112;
    
    private TablaUsuario tablaUsuario;
    private TablaEntrada tablaEntrada;
    private TablaSalida tablaSalida;
	private Lock lock;
    
    protected Servidor() throws IOException {
		servidor_socket = new ServerSocket(puerto);
		tablaUsuario = new TablaUsuario();
		tablaEntrada = new TablaEntrada();
		tablaSalida = new TablaSalida();
		lock = new LockBakery(2);
    }
    
	protected void inicializarServidor() throws IOException, InterruptedException{  	
		System.out.println("SERVIDOR CREADO.");
		try {
			while(true) {
				
				socket = new SocketImp(servidor_socket);
				Thread oyente = new OyenteCliente(lock, socket, tablaUsuario, tablaEntrada, tablaSalida);
				oyente.start();
				int n = tablaUsuario.getListaUsuarios().size(); 
				lock.takeLock(1);
				n  = n + 1;
				if (n == 1) System.out.println("Hay " + n + " usuario conectado");
				else System.out.println("Hay " + n + " usuarios conectados");
				lock.releaseLock(1);
				
                
				
				
				


	    	}
		} catch(IOException e) {
			servidor_socket.close();
			System.out.println("CERRANDO SERVIDOR.");
		}
    	
    }

}