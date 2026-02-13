package Cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.concurrent.Semaphore;

import Mensaje.*;
import Socket.SocketImp;
import Locks.*;

public class OyenteServidor extends Thread {
	private static final int n = 100;
	private Cliente cliente;
	private Lock lock;
	
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private SocketImp socket;
	private Semaphore sem;
	
	private ProductorConsumidorPuertos gestion_puertos = new ProductorConsumidorPuertos (n) ;

	
	protected OyenteServidor(Lock lock, Cliente cliente, Semaphore sem, SocketImp socket) {
		this.lock = lock;
		this.cliente = cliente;
		this.socket = socket;
		this.sem = sem;
		this.out = cliente.getOutputStream();
		this.in = cliente.getInputStream();
	}
	
	public void run() {
		try {
			boolean fin = false;
			while(!fin) {
				Mensaje mensaje = (Mensaje) in.readObject();
				switch(mensaje.getTipo()) {
				  	case CONEXION_CONFIRMADA:
				  		lock.takeLock(2);		
				  		System.out.println("OYENTE CLIENTE Y OYENTE SERVIDOR CONECTADOS");
				  		lock.releaseLock(2);
				  		sem.release();
						break;
				  	
				  	case LISTA_USUARIOS_CONFIRMADA:
				  		lock.takeLock(2);		
				  		Mensaje_ListaUsuariosConfirmada mensaje_LUConf = (Mensaje_ListaUsuariosConfirmada) mensaje;
				  		System.out.println("*****************************************************************************************");
						System.out.println("USUARIOS CONECTADOS:  ");
						System.out.println("*****************************************************************************************");
						System.out.println("");
				  		List<String> usuarios = mensaje_LUConf.getListaUsuarios();
				  		List<List<String>> equipos = mensaje_LUConf.getEquipos();
				  		for(int i = 0; i < usuarios.size(); i++) {
				  			System.out.print("USUARIO: " + usuarios.get(i) + "\nEQUIPOS: ");
				  			for(int j = 0;  j < equipos.get(i).size(); j++) {
				  				System.out.print(equipos.get(i).get(j));
				  				if(j != equipos.get(i).size() - 1) {
				  					System.out.print(", ");
				  				}
				  			}
				  			System.out.println("");
				  			System.out.println("");
				  			System.out.println("*****************************************************************************************");
				  			System.out.println("");
				  		}
				  		lock.releaseLock(2);
				  		sem.release();
				  		break;
				  		
				  	case EMITIR_EQUIPO:
				  		Mensaje_EmitirEquipo mensaje_emitirEquipo = (Mensaje_EmitirEquipo) mensaje;
				  		int puerto_1 = gestion_puertos.consumir();
				  		socket.write(new Mensaje_ClienteServPreparado(cliente.getNombre(), mensaje.getOrigen(), puerto_1));
				  		(new Emisor(puerto_1, mensaje_emitirEquipo.getEquipoSolicitado(), cliente)).start();	
				  		gestion_puertos.producir();
				  		break;
				  		
				  	case SERVCLIENTE_PREPARADO:
				  		Mensaje_ServClientePreparado mensaje_SCPrep = (Mensaje_ServClientePreparado) mensaje;
				  		(new Receptor(mensaje_SCPrep.getPuerto(), lock, mensaje.getDestino(), sem)).start();
				  		break;
				  		
				  	case CERRAR_CONEXION_CONFIRMADA:
				  		lock.takeLock(2);		
				  		System.out.println("LA CONEXION FUE CERRADA CORRECTAMENTE.");
				  		lock.releaseLock(2);		
				  		fin = true;
				  		sem.release();
				  		break;
				  	
				  	case ARCHIVO_NO_EXIST:
				  		lock.takeLock(2);		
				  		System.err.println("NO EXISTE NINGUN ARCHIVO CON ESE NOMBRE.");
				  		lock.releaseLock(2);
				  		sem.release();
				  		break;
				  		
				  	case PEDIR_EQUIPOAUSUARIO:
				  		Mensaje_PedirEquipoAUsuario mensaje_pedirUsu = (Mensaje_PedirEquipoAUsuario) mensaje;	
				  	    System.out.println("\n" + mensaje.getOrigen() +  " SOLICTA EQUIPO " + mensaje_pedirUsu.getEquipoSolicitado() + ".");
		   				System.out.print(" >> OPCION: ");
		   				int puerto_2 = gestion_puertos.consumir();
					  	socket.write(new Mensaje_ClienteServPreparado(cliente.getNombre(), mensaje.getOrigen(), puerto_2));
					  	(new Emisor(puerto_2, mensaje_pedirUsu.getEquipoSolicitado(), cliente)).start();
					  	gestion_puertos.producir();
				  		break;
				  		
				  	default:
				  		break;
			  }
				
			}
		} catch(IOException | ClassNotFoundException e) {
			System.err.println("OCURRIO UN ERROR AL EJECUTAR EL HILO DEL OYENTE SERVIDOR...");
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
