package Servidor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorLectoresEscritores implements LectoresYEscritores{  //El monitor lo hacemos con el esquema Lectores-Escritores
	private int nr, nw = 0;
	private final Condition oktoread;
	private final Condition oktowrite;
	private final ReentrantLock lock;
	
	public MonitorLectoresEscritores() {
		lock = new ReentrantLock();
		oktoread = lock.newCondition();
		oktowrite = lock.newCondition();
	}
	
	public void request_read() throws InterruptedException {
		lock.lock();
		while(nw > 0) {
			oktoread.await();
		}
		nr++;
		lock.unlock();
	}
	
	public void release_read() throws InterruptedException {
		lock.lock();
		nr--;
		if(nr == 0) {
			oktowrite.signal();
		}
		lock.unlock();
	}
	
	public void request_write() throws InterruptedException {
		lock.lock();
		while(nr > 0 || nw > 0) {
			oktowrite.await();
		}	
		nw++;
		lock.unlock();
	}
	
	public void release_write() throws InterruptedException {
		lock.lock();
		nw--;
		oktowrite.signal();
		oktoread.signalAll();
		lock.unlock();
	}
}
