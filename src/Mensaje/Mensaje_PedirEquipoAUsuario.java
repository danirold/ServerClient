package Mensaje;

public class Mensaje_PedirEquipoAUsuario extends Mensaje {
	private static final long serialVersionUID = 1L;

	private String equipoSolicitado;
	
	public Mensaje_PedirEquipoAUsuario(String origen, String destino, String equipoSolicitado) {
		super(MensajeTipo.PEDIR_EQUIPOAUSUARIO, origen, destino);
		this.equipoSolicitado = equipoSolicitado;
	}
	
	public String getEquipoSolicitado() {
		return this.equipoSolicitado;
	}
}
