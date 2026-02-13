package Cliente;

import java.util.concurrent.Semaphore;

public class ProductorConsumidorPuertos {
	
	private int[] puertos; 
	private int ini, fin = 0;
	private int count;
	private int n;
	
	public ProductorConsumidorPuertos(int n) {
		this.n = n;
		this.puertos = new int[n];
		for (int i = 0; i < n; ++i) {
	    	puertos[i] = 3000 + i;
	    }
		this.count = n;
	}
	
	public synchronized void producir() throws InterruptedException {
		while(count == n) {
			wait();
		}
		puertos[fin] = fin + 1;
		fin = (fin + 1) % n;
		count++;
		notifyAll();
	}
	
	public synchronized int consumir() throws InterruptedException {
		while(count == 0) {
			wait();
		}
		int temp = puertos[ini];
		puertos[ini] = 0;
		ini = (ini + 1) % n;
		count--;
		notifyAll();
		return temp;
	}
    
    
    

}
