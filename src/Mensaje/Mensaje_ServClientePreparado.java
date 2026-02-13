package Mensaje;

public class Mensaje_ServClientePreparado extends Mensaje {
	private static final long serialVersionUID = 1L;

	private int puerto;
	
	public Mensaje_ServClientePreparado(String origen, String destino, int puerto) {
		super(MensajeTipo.SERVCLIENTE_PREPARADO, origen, destino);
		this.puerto = puerto;
	}
	
	public int getPuerto() {
		return this.puerto;
	}
}
