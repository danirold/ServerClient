package Mensaje;

import java.util.List;

public class Mensaje_ListaUsuariosConfirmada extends Mensaje {
	private static final long serialVersionUID = 1L;

	private List<String> listaUsuarios;
	private List<List<String>> equipos;
	
	public Mensaje_ListaUsuariosConfirmada(String origen, String destino, List<String> listaUsuarios, List<List<String>> equipos) {
		super(MensajeTipo.LISTA_USUARIOS_CONFIRMADA, origen, destino);
		this.listaUsuarios = listaUsuarios;	
		this.equipos = equipos;
	}
	
	public List<String> getListaUsuarios(){
		return this.listaUsuarios;
	}

	public List<List<String>> getEquipos(){
		return this.equipos;
	}
}
