package Usuario;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
	private String nombreUsuario;
	private List<String> equiposUsuario;

	public Usuario(String nombreUsuario, List<String> equiposUsuario) {
		this.nombreUsuario = nombreUsuario;
		this.equiposUsuario = equiposUsuario;
	}
		
	public String getNombreUsuario() {
		return this.nombreUsuario;
	}

	public List<String> getEquiposUsuario() {
		return new ArrayList<String> (this.equiposUsuario);
	}
	
}