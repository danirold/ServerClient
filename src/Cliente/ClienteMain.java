package Cliente;

import java.io.IOException;

public class ClienteMain {
	public static void main(String[] args) {
		Cliente cliente;
		try {
			cliente = new Cliente();
			cliente.inicializarCliente();
		}
		catch(IOException | InterruptedException e) {
			System.err.println("OCURRIO UN ERROR MIENTRAS SE EJECUTABA EL HILO DEL CLIENTE");
			e.printStackTrace();
		}

	}
}
