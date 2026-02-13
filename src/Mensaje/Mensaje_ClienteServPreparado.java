package Mensaje;

public class Mensaje_ClienteServPreparado extends Mensaje {
	private static final long serialVersionUID = 1L;
	
	private int puerto;

	public Mensaje_ClienteServPreparado(String origen, String destino, int puerto) {
		super(MensajeTipo.CLIENTESERV_PREPARADO, origen, destino);
		this.puerto = puerto;
	}

	
	public int getPuerto() {
		return this.puerto;
	}
}
