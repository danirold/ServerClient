package Mensaje;

public class Mensaje_ListaUsuarios extends Mensaje {
	private static final long serialVersionUID = 1L;

	public Mensaje_ListaUsuarios(String origen, String destino) {
		super(MensajeTipo.LISTA_USUARIOS, origen, destino);
	}
}
