package Locks;

public class Entero {
	private volatile int cont;
	
	public Entero (int ini) {
		cont = ini;
	}
	
	public int get() {
		return cont;
	}
	
	public void set (int value) {
		cont = value;
	}
	
	public void inc() {
		cont++;
	}
	
	public void dec() {
		cont--;
	}

}
