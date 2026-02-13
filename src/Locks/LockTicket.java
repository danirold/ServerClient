package Locks;

import java.util.concurrent.atomic.AtomicInteger;

public class LockTicket{
	
	private int tam;
	private AtomicInteger number;
	private Entero next;
	
	public LockTicket (int m) {
		tam = m;
		number = new AtomicInteger(1);
		next = new Entero(1);
	}
	
	public void takeLock() {
	
		Entero turno = new Entero(0);
		turno.set(number.getAndAdd(1)); 
		if (turno.get() == tam) {
			number.getAndAdd(-tam);
		}
		if (turno.get() > tam) {
			turno.set(turno.get() - tam);
		}
		while(turno.get() != next.get());
	}
	
	public void releaseLock() {
		next.set((next.get() % tam) + 1);
	}
}

