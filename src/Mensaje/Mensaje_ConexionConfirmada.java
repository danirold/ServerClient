package Mensaje;

public class Mensaje_ConexionConfirmada extends Mensaje {
	private static final long serialVersionUID = 1L;

	public Mensaje_ConexionConfirmada(String origen, String destino) {
		super(MensajeTipo.CONEXION_CONFIRMADA, origen, destino);
	}

}
