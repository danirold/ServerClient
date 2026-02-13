package Mensaje;

public class Mensaje_EmitirEquipo extends Mensaje {
	private static final long serialVersionUID = 1L;

	private String equipoSolicitado;
	
	public Mensaje_EmitirEquipo(String origen, String destino, String equipoSolicitado) {
		super(MensajeTipo.EMITIR_EQUIPO, origen, destino);
		this.equipoSolicitado = equipoSolicitado;
	}
	
	public String getEquipoSolicitado() {
		return this.equipoSolicitado;
	}
}
