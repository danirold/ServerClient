package Mensaje;

public class Mensaje_CerrarConexionConfirmada extends Mensaje {
	private static final long serialVersionUID = 1L;
	
	public Mensaje_CerrarConexionConfirmada(String origen, String destino) {
		super(MensajeTipo.CERRAR_CONEXION_CONFIRMADA, origen, destino);
	}
	
}