package Mensaje;

public class Mensaje_CerrarConexion extends Mensaje {
	private static final long serialVersionUID = 1L;
	
	public Mensaje_CerrarConexion(String origen, String destino) {
		super(MensajeTipo.CERRAR_CONEXION, origen, destino);
	}
	
}