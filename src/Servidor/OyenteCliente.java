package Servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

import Locks.Lock;
import Locks.LockBakery;
import Mensaje.*;
import Socket.SocketImp;
import Usuario.*;

public class OyenteCliente extends Thread {
	
	private static final int n = 100;
	
	private SocketImp socket = null;
	
	private TablaUsuario tablaUsuario;
	private Tabla tablaSalida;
	private Tabla tablaEntrada;
	private Lock lock; 
	
	
	protected OyenteCliente(Lock lock, SocketImp socket, TablaUsuario tablaUsuario, TablaEntrada tablaEntrada, TablaSalida tablaSalida) throws IOException {
		this.socket = socket;
		this.tablaUsuario = tablaUsuario;
		this.tablaEntrada = tablaEntrada;
		this.tablaSalida = tablaSalida;
		this.lock = lock;
	}
	
	public void run() {
		try {
			boolean fin = false;
			while (!fin) {
				
				Mensaje mensaje = (Mensaje) socket.read();
				
				switch(mensaje.getTipo()) {
				case CONEXION:
					Mensaje_Conexion mensaje_conex = (Mensaje_Conexion) mensaje;
		   			Usuario usuario = new Usuario(mensaje_conex.getOrigen(), mensaje_conex.getEquipos());
		   			anadirUsuario(usuario, socket.getOutputStream(), socket.getInputStream());
		   			socket.write(new Mensaje_ConexionConfirmada("server", mensaje.getOrigen()));
		   			lock.takeLock(2);
		   			System.out.println("CONEXION CREADA " + usuario.getNombreUsuario() + ".");
		   			lock.releaseLock(2);
					break;
		  	
		   		case LISTA_USUARIOS:
		   			socket.write(new Mensaje_ListaUsuariosConfirmada("server", mensaje.getOrigen(), tablaUsuario.getListaUsuarios(), tablaUsuario.getListaEquiposxUsuario()));
			  		break;
		  		
		   		case CERRAR_CONEXION:
		   			Mensaje_CerrarConexion mensaje_cerrar = (Mensaje_CerrarConexion) mensaje;
		   			borrarUsuario(mensaje_cerrar.getOrigen());
		   			socket.write(new Mensaje_CerrarConexionConfirmada("server", mensaje.getOrigen()));
		   			lock.takeLock(2);
		   			System.out.println("CONEXION CERRADA " + mensaje_cerrar.getOrigen() + ".");
		   			lock.releaseLock(2);
		   			fin = true;
			  		break;
		  		
		   		case PEDIR_EQUIPO:
		   			Mensaje_PedirEquipo mensaje_pedir = (Mensaje_PedirEquipo) mensaje;
		   			String equipoSolicitado = mensaje_pedir.getEquipo();
		   			String nombre = tablaUsuario.getUsuarioConEquipo(equipoSolicitado);

		   			if(nombre != null) {
		   				ObjectOutputStream out2 = (ObjectOutputStream) tablaSalida.get(nombre);
		   				if (nombre == mensaje.getOrigen()) {
			   				out2.writeObject(new Mensaje_EmitirEquipo(mensaje.getOrigen(), nombre, equipoSolicitado));
			   				out2.flush();
			   				lock.takeLock(2);
					  		System.out.println(mensaje.getOrigen() + " SOLICITA " + equipoSolicitado + " AL SERVIDOR.");
					  		lock.releaseLock(2);
		   				}
		   				else {
		   					out2.writeObject(new Mensaje_PedirEquipoAUsuario(mensaje.getOrigen(), nombre, equipoSolicitado));
		   					out2.flush();
		   					List<String> equipos = tablaUsuario.getListaEquiposDeUsuario(mensaje.getOrigen());
		   					equipos.add(equipoSolicitado);
				   			borrarUsuario(mensaje.getOrigen());
					  		anadirUsuario(new Usuario(mensaje.getOrigen(), equipos), socket.getOutputStream(), socket.getInputStream());
					  		lock.takeLock(2);
					  		System.out.println(mensaje.getOrigen() + " SOLICITA " + equipoSolicitado + " AL USUARIO " + nombre);
					  		lock.releaseLock(2);
		   				}
		   			}
		   			else {
		   				socket.write(new Mensaje_ArchivoNoExist("server", mensaje.getOrigen()));
		   				lock.takeLock(2);
		   				System.err.println("EL ARCHIVO" + mensaje_pedir.getEquipo() + "NO EXISTE ");
		   				lock.releaseLock(2);
		   			}
			  		break;
		  		
		   		case CLIENTESERV_PREPARADO:
		   			Mensaje_ClienteServPreparado mensaje_CSPrep = (Mensaje_ClienteServPreparado) mensaje;		 
		   			ObjectOutputStream out3 = (ObjectOutputStream) tablaSalida.get(mensaje.getDestino()); 
		   			out3.writeObject(new Mensaje_ServClientePreparado(mensaje.getOrigen(), mensaje.getDestino(), mensaje_CSPrep.getPuerto()));
		   			out3.flush();
		   			break;

		   		default:
		   			break;
				}
				
			}
		} catch(IOException | ClassNotFoundException | InterruptedException e) {
			System.err.println("OCURRIO UN ERROR AL EJECUTAR EL HILO DEL OYENTE CLIENTE");
		}
	}
	
	protected void anadirUsuario(Usuario usuario, ObjectOutputStream out, ObjectInputStream in) throws InterruptedException {
		tablaUsuario.anadirUsuario(usuario);
		tablaSalida.anadir(usuario.getNombreUsuario(), out);
		tablaEntrada.anadir(usuario.getNombreUsuario(), in);
    }
    
    protected void borrarUsuario(String nombreUsuario) throws InterruptedException {
    	tablaUsuario.borrarUsuario(nombreUsuario);
		tablaSalida.borrar(nombreUsuario, socket.getOutputStream());
		tablaEntrada.borrar(nombreUsuario, socket.getInputStream());
    }
}
