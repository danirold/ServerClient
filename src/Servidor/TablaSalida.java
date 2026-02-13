package Servidor;

import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

import Locks.Entero;

public class TablaSalida implements Tabla{
	
	private LectoresYEscritores lye;
	private HashMap<String,ObjectOutputStream> tablaSalida;
	
	public TablaSalida() {
		this.tablaSalida = new HashMap<String,ObjectOutputStream>();
		this.lye = new SemaforoLectoresEscritores(new Semaphore(1), new Semaphore(0), new Semaphore(0), new Entero(0), new Entero(0), new Entero(0), new Entero(0));
	}
	public void anadir(String nombreUsuario, Object out) throws InterruptedException {
		lye.request_write();
		tablaSalida.put(nombreUsuario,(ObjectOutputStream) out);
		lye.release_write();
	}
	public void borrar(String nombreUsuario,Object out ) throws InterruptedException {
		lye.request_write();
		tablaSalida.remove(nombreUsuario, (ObjectOutputStream) out);
		lye.release_write();
	}
	public ObjectOutputStream get(String nombreUsuario) throws InterruptedException {
		lye.request_read();
		ObjectOutputStream out = tablaSalida.get(nombreUsuario);
		lye.release_read();
		return out;
	}
	
}
