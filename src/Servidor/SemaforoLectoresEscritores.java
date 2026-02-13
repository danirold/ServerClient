package Servidor;
import java.util.concurrent.Semaphore;

import Locks.Entero;

public class SemaforoLectoresEscritores implements LectoresYEscritores{
	private Semaphore e;
	private Semaphore r;
	private Semaphore w;
	private Entero nr;
	private Entero nw;
	private Entero dr;
	private Entero dw;
	  
	public SemaforoLectoresEscritores(Semaphore e, Semaphore r, Semaphore w, Entero nr, Entero nw, Entero dr, Entero dw) {
		this.e = e;
		this.r = r;
		this.w = w;
		this.nr = nr;
		this.nw = nw;
		this.dr = dr;
		this.dw = dw;
	}
  
  
    public void request_read() {
    	while (nw.get() != 0);
			try {
				e.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		if (nw.get() > 0) {
			dr.inc();
			e.release();
			try {
				r.acquire();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		nr.inc();
		if (dr.get() > 0) {
			dr.dec();
			r.release();
		}
		else e.release();
   }
  
   public void release_read() {
   	try {
			e.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		nr.dec();
		if (nr.get() == 0 && dw.get() > 0) {
			dw.dec();
			w.release();
		}
		else e.release();
   }
  
   public void read() {
   	
   }
  
   public void request_write() {
   	while (nr.get() != 0 || nw.get() != 0);
		try {
			e.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (nr.get() > 0 || nw.get() > 0) {
			dw.inc();
			e.release();
			try {
				w.acquire();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		nw.inc();
		e.release();
   }
  
   public void release_write() {
   	try {
			e.acquire();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		nw.dec();
		if (dr.get() > 0) {
			dr.dec();
			r.release();
		}
		else if (dw.get() > 0){
			dw.dec();
			w.release();
		}
		else e.release();
   }
}

