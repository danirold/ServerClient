package Servidor;

import java.io.IOException;

public class ServidorMain {
	public static void main(String[] args) throws InterruptedException {
		try {
			Servidor s = new Servidor();
			s.inicializarServidor();
		} catch (IOException e) {
			System.err.println("OCURRIO UN ERROR MIENTRAS SE EJECUTABA EL MAIN DEL SERVIDOR... ");
			e.printStackTrace();
		}
	}
}
