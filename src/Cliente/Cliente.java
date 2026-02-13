package Cliente;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

import Mensaje.*;
import Socket.SocketImp;
import Locks.*;

public class Cliente {
	final String servidorIP = "localhost";
	final int puerto = 1112;
	
	private Lock Lock;
	
	
	
	private String nombreCliente;
	private static Scanner scanner = new Scanner(System.in);
	private List<String> equipos;
	
	
	private SocketImp socket;
	
	
	private Semaphore sem = new Semaphore(1);
	
	public Cliente() throws IOException, InterruptedException {
		this.equipos = new ArrayList<String>();
		this.socket = new SocketImp(servidorIP, puerto);
		this.Lock = new LockRompeEmpate(2);
	}

	protected void inicializarCliente() throws IOException, InterruptedException {
		preguntarNombre();
		
		Thread oyente = new OyenteServidor(Lock, this, sem, socket);
		oyente.start();
		
		leerArchivo();
		
		int option = 1; 
		
		Mensaje_Conexion mensaje = new Mensaje_Conexion(nombreCliente, "servidor", equipos);
		sem.acquire();
		socket.write(mensaje);
		
		while(option != 0) {
			sem.acquire();
			Lock.takeLock(1);
			option = getOption();
			if (option == 1) {
				socket.write(new Mensaje_ListaUsuarios(nombreCliente, "servidor"));
			}
			else if (option == 2){
				System.out.print("INTRODUCE EL NOMBRE DEL EQUIPO (equipo.txt): ");
				String equipo = scanner.next();
				socket.write(new Mensaje_PedirEquipo(nombreCliente, "servidor", equipo));
			}
			Lock.releaseLock(1);
		}
		System.out.println("SALIENDO...");
		socket.write(new Mensaje_CerrarConexion(nombreCliente, "servidor"));
		oyente.join();
		scanner.close();
		socket.close();
	}
	
	private void preguntarNombre() {
		System.out.println("INTRODUZCA EL NOMBRE DEL CLIENTE: ");
		String input = scanner.nextLine();
		File f = null;
		do {
			f = new File(System.getProperty("user.dir") + "/src/BaseDatos/" + input + "/Equipos.txt");
			if(!f.exists()) {
				System.out.println("");
				System.out.println("ERROR. NO HAY ARCHIVO EN " + System.getProperty("user.dir") + "/src/BaseDatos CON NOMBRE "+ input );
				System.out.print("INTRODUCE EL NOMBRE DEL CLIENTE: ");
				input = scanner.nextLine();
			}
		} while(!f.exists());
		
		this.nombreCliente = input;
		System.out.println("WELCOME " + this.nombreCliente);
	}
	
	private void leerArchivo() throws IOException {
	     FileReader fr = new FileReader (new File(System.getProperty("user.dir") + "/src/BaseDatos/" + this.nombreCliente + "/Equipos.txt"));
	     BufferedReader br = new BufferedReader(fr);
         String linea;
         while((linea=br.readLine())!=null)
            this.equipos.add(linea);
	}
	
	private int getOption() {
		int op = -1;
		boolean exception;
		System.out.println("\n");
		System.out.println("*****************************************************************************************");
		System.out.println(":: ELIGE UNA ACCION:							");
		System.out.println(":: 											");
		System.out.println(":: 1 - CONSULTA DE LOS USUARIOS CONECTADOS Y SUS EQUIPOS.	");
		System.out.println(":: 2 - DESCARGAR EQUIPO.								");
		System.out.println(":: 											");
		System.out.println(":: 0 - EXIT.										");
		System.out.println("::											");
		System.out.println("*****************************************************************************************");
		System.out.print(" >> OPCION: ");
		do {
			exception = false;
			try {
				 op = Integer.parseInt(scanner.next()); 
			} catch(NumberFormatException e) {
				exception = true;
			}
			if(exception || op < 0 || op > 2) {
				System.out.println("\nERROR, LA OPCION DEBE SER 0,1 O 2");
				System.out.print(" >> OPCION: ");
			}
			
		} while(exception || op < 0 || op > 2);
		
		
		return op;
	}
	
	
	protected String getNombre() {
		return this.nombreCliente;
	}
	
	protected ObjectInputStream getInputStream() {
		return socket.getInputStream();
	}
	
	protected ObjectOutputStream getOutputStream() {
		return socket.getOutputStream();
	}
	
	public SocketImp getSocket() {
		return socket;
	}
	
}