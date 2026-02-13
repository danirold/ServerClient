package Socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import Locks.*;
import Mensaje.Mensaje;
import Mensaje.Mensaje_ConexionConfirmada;

public class SocketImp {
	private final int n = 100;
	private Socket s;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private LockTicket lock;
	
	public SocketImp (String IP, int puerto) throws UnknownHostException, IOException {
		s = new Socket(IP, puerto);
		this.out = new ObjectOutputStream(s.getOutputStream());
		this.in = new ObjectInputStream(s.getInputStream());
		lock = new LockTicket(n);
	}
	
	public SocketImp (ServerSocket ss) throws UnknownHostException, IOException {
		s = ss.accept();
		this.out = new ObjectOutputStream(s.getOutputStream());
		this.in = new ObjectInputStream(s.getInputStream());
		lock = new LockTicket(n);
	}
	
	public Object read() throws ClassNotFoundException, IOException {
		lock.takeLock();
		Object o = (Object) in.readObject();
		lock.releaseLock();
		return o;
	}
	
	public void write(Object o) throws IOException {
		lock.takeLock();
		out.writeObject(o);
		out.flush();
		lock.releaseLock();
	}
	
	public void close() throws IOException {
		s.close();
	}
	
	public ObjectInputStream getInputStream() {
		return in;
	}
	
	public ObjectOutputStream getOutputStream() {
		return out;
	}
}
