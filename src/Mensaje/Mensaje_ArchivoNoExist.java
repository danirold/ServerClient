package Mensaje;

public class Mensaje_ArchivoNoExist extends Mensaje {
	private static final long serialVersionUID = 1L;
	
	public Mensaje_ArchivoNoExist(String origen, String destino) {
		super(MensajeTipo.ARCHIVO_NO_EXIST, origen, destino);
	}
	
}
