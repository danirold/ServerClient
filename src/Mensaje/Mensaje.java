package Mensaje;

import java.io.Serializable;

public abstract class Mensaje implements Serializable {
	private static final long serialVersionUID = 1L;

	private MensajeTipo tipo;
	private String origen;
	private String destino;

	public Mensaje(MensajeTipo tipo, String origen, String destino) {
		this.tipo = tipo;
		this.origen = origen;
		this.destino = destino;
	}
	
	public MensajeTipo getTipo() {
		return this.tipo;
	}
	
	public String getOrigen() {
		return this.origen;
	}

	public String getDestino() {
		return this.destino;
	}
}
