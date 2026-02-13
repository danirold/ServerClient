package Servidor;

import java.io.ObjectOutputStream;

public interface Tabla {
	
	public void anadir(String nombreUsuario, Object o) throws InterruptedException;
	public void borrar(String nombreUsuario,Object o ) throws InterruptedException;
	public Object get(String nombreUsuario) throws InterruptedException;

}
