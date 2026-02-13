package Mensaje;

import java.io.Serializable;
import java.util.List;

public class Mensaje_Conexion extends Mensaje implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<String> equipos;

	public Mensaje_Conexion(String origen, String destino, List<String> equipos) {
		super(MensajeTipo.CONEXION, origen, destino);
		this.equipos = equipos;
	}
	
	public List<String> getEquipos(){
		return this.equipos;
	}

}
