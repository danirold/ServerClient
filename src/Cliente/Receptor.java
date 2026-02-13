package Cliente;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import Locks.*;
import Socket.SocketImp;

public class Receptor extends Thread {
	private int puerto;
	private Lock lock;
	private String nombre;
	private Semaphore sem;

	public Receptor( int puerto, Lock lock, String nombre, Semaphore sem) {
		this.puerto = puerto;
		this.lock = lock;
		this.nombre = nombre;
		this.sem = sem;
	}
	
	public void run() {
		try {
			lock.takeLock(2);	
			SocketImp socket = new SocketImp("localhost", puerto);
			Equipo e = (Equipo) socket.read();
			System.out.println("*****************************************************************************************");
			System.out.println("JUGADORES DE " + e.getEquipo() + ": ");
			System.out.println("*****************************************************************************************");
			System.out.println("");
			e.print();
			System.out.println("");
			System.out.println("");
			System.out.println("*****************************************************************************************");
			System.out.println("");
			
			String rutaArchivo = System.getProperty("user.dir") + "/src/BaseDatos/" + nombre + "/Equipos.txt";
	        try (PrintWriter escritor = new PrintWriter(new FileWriter(rutaArchivo, true))) {
	        	//escritor.println();
	            escritor.println(e.getEquipo());
	        } catch (IOException e1) {
	            System.err.println("Error al escribir en el archivo: " + e1.getMessage());
	        }
	        

	        try {
	            PrintWriter escritor = new PrintWriter(System.getProperty("user.dir") + "/src/BaseDatos/" + nombre + "/Equipos/" + e.getEquipo());
	            escritor.println(e.getPlantilla());
	            escritor.close();
	        } catch (FileNotFoundException error) {
	            System.out.println("Error: No se pudo crear el archivo.");
	            error.printStackTrace();
	        }
			socket.close();
			lock.releaseLock(2);
			sem.release();
			
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("OCURRIO UN ERROR CUANDO SE EJECUTABA EL HILO DEL RECEPTOR... ");
			e.printStackTrace();
		}
	}
	
}