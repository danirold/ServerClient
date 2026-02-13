package Locks;

public class LockRompeEmpate extends Lock {
	private Entero[] in;
	private Entero[] last;
    private int tam;

    public LockRompeEmpate(int m) {
    	tam = m;
    	in = new Entero[tam];
    	last = new Entero[tam];
    	for (int i = 0; i < tam; ++i) {
    		in[i] = new Entero(0);
    		last[i]= new Entero(0);
    	}
    }

    @Override
    public void takeLock(int I) {
        for (int j = 1; j < tam; j++) { 
        	last[j - 1].set(I);
        	in[I - 1].set(j);
            for (int k = 1; k < tam; k++) {
            	if (k != I) {
            		while (in[k - 1].get() >= in[I - 1].get() && last[j - 1].get() == I);
            	}
                
            }
        }
    }

    @Override
    public void releaseLock(int I) {
        in[I - 1].set(0); 
    }
    
}
