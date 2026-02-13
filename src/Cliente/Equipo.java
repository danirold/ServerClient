package Cliente;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

public class Equipo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String nombreEquipo = "";
	private String plantilla = "";
	
	public Equipo(String nombreEquipo, String path) {
		this.nombreEquipo = nombreEquipo;
		try (BufferedReader br = new BufferedReader(new FileReader(path))){
			String linea;
			while((linea = br.readLine()) != null) {
				this.plantilla += linea + "\n";
			}
		} catch (IOException e) {
			System.err.println("EQUIPO " + nombreEquipo + " NO SE PUDO ABRIR " + e.getLocalizedMessage());
		}
	}
	
	public String getEquipo() {
		return nombreEquipo;
	}
	
	public String getPlantilla() {
		return plantilla;
	}
	
	public void print() throws UnsupportedEncodingException {
		System.out.print(plantilla);
	}
	
}
