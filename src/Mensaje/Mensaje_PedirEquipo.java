package Mensaje;

public class Mensaje_PedirEquipo extends Mensaje {
	private static final long serialVersionUID = 1L;

	private String equipo;
	
	public Mensaje_PedirEquipo (String origen, String destino, String equipo) {
		super(MensajeTipo.PEDIR_EQUIPO, origen, destino);
		this.equipo = equipo;
	}

	public String getEquipo() {
		return this.equipo;
	}
	
}
