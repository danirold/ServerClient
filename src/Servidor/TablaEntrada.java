package Servidor;

import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

import Locks.Entero;

public class TablaEntrada implements Tabla{
	
	private LectoresYEscritores lye;
	private HashMap<String,ObjectInputStream> tablaEntrada;
	
	public TablaEntrada() {
		this.tablaEntrada = new HashMap<String,ObjectInputStream>();
		
		this.lye = new SemaforoLectoresEscritores(new Semaphore(1), new Semaphore(0), new Semaphore(0), new Entero(0), new Entero(0), new Entero(0), new Entero(0));
	}
	public void anadir(String nombreUsuario, Object in) throws InterruptedException {
		lye.request_write();
		tablaEntrada.put(nombreUsuario,(ObjectInputStream) in);
		lye.release_write();
	}
	public void borrar(String nombreUsuario, Object in ) throws InterruptedException {
		lye.request_write();
		tablaEntrada.remove(nombreUsuario,(ObjectInputStream) in);
		lye.release_write();
	}
	public ObjectInputStream get(String nombreUsuario) throws InterruptedException {
		lye.request_read();
		ObjectInputStream in = tablaEntrada.get(nombreUsuario);
		lye.release_read();
		return in;
	}

	
}
