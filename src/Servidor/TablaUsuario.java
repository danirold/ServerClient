package Servidor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Usuario.*;

public class TablaUsuario {
	private LectoresYEscritores lye;
	private HashMap<String,Usuario> tablaUsuario;
	
	public TablaUsuario() {
		this.tablaUsuario = new HashMap<String,Usuario>();
		this.lye = new MonitorLectoresEscritores();
	}
	
	protected void anadirUsuario(Usuario usuario) throws InterruptedException {
		lye.request_write();
		tablaUsuario.put(usuario.getNombreUsuario(), usuario);
		lye.release_write();
	}
	
    protected void borrarUsuario(String usuario) throws InterruptedException {
    	lye.request_write();
    	tablaUsuario.remove(usuario);
    	lye.release_write();
    }
    
    protected List<String> getListaUsuarios() throws InterruptedException{
    	lye.request_read();
		List<String> listaUsuarios = new ArrayList<String>();
		for (Usuario usuario : tablaUsuario.values()) {
			listaUsuarios.add(usuario.getNombreUsuario());
		}
		lye.release_read();
		return listaUsuarios;
	}
    
    protected List<List<String>> getListaEquiposxUsuario() throws InterruptedException {
    	lye.request_read();
		List<List<String>> equiposUsuarios = new ArrayList<List<String>>();
		for (Usuario usuario : tablaUsuario.values()) {
			List<String> listaEquipos = new ArrayList<String>();
			for(String equipo : usuario.getEquiposUsuario()) {
				listaEquipos.add(equipo);
			}
			equiposUsuarios.add(listaEquipos);
		}
		lye.release_read();
		return equiposUsuarios;
	}
    
    protected List<String> getListaEquiposDeUsuario(String nombre) throws InterruptedException {
    	lye.request_read();
		List<String> equipos = new ArrayList<String>();
		for (Usuario usuario : tablaUsuario.values()) {
			if (usuario.getNombreUsuario().equals(nombre)) {
				for(String equipo : usuario.getEquiposUsuario()) {
					equipos.add(equipo);
				}
				break;
			}
		}
		lye.release_read();
		return equipos;
	}
    
	protected String getUsuarioConEquipo(String equipo) throws InterruptedException {
		lye.request_read();
		String nombre = null;
		for(Usuario usuario : tablaUsuario.values()) {
			for(String equipos : usuario.getEquiposUsuario()) {
				if(equipo.equals(equipos)) {
					nombre = usuario.getNombreUsuario();
					break;
				}
			}
			if(nombre != null) {
				break;
			}
		}
		lye.release_read();
		return nombre;
	}
}