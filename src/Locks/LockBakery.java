package Locks;

public class LockBakery extends Lock {
	private Entero[] turno;
	private int tam;
	
	public LockBakery (int m) {
		tam = m;
		turno = new Entero[tam];
		for (int i = 0; i < tam; ++i) {
			turno[i] = new Entero(0);
		}
	}
	
	private boolean comparador(int a, int b, int c, int d) {
		return (a > c || (a == c && b > d));
	}
	
	private int maximo() {
		int maximo = turno[0].get();
		for(int i = 1; i < tam; ++i) {
			if(turno[i].get() > maximo) maximo = turno[i].get();
		}
		return maximo;
	}
	
	public void takeLock(int I) {
		turno[I - 1].set(1);
		turno[I - 1].set(maximo() + 1);
		for(int j = 1; j < tam; ++j) {
			if (j != I) {
				while(turno[j - 1].get() != 0 && comparador(turno[I - 1].get(), I, turno[j - 1].get(), j));
			}
		}
	}
	
	public void releaseLock(int I) {
		turno[I - 1].set(0);
	}
}
