package bb;

import java.util.concurrent.Semaphore;

class Producer extends Thread{
	static BoundedBuffer sharedObj;
	  static { // initializaton of sharedObj - only one for classes Producer and Consumer
		  if (Consumer.sharedObj==null) sharedObj=new BoundedBuffer();
		  else sharedObj=Consumer.sharedObj;
	  }
	  private static Semaphore mutexP=new Semaphore(1);
	  private static int posId=0;
	  private int id=++posId;
	  public void run() {
		  try {
		  while (true) {
			  sharedObj.empty.acquire(); //wait for buf is empty
			  mutexP.acquire();//mutual exclusion for producers
			  int a= sharedObj.produce();
			  System.out.println("Producer "+id + " - element "+a);
			  sleep((long) (Math.random()*1000));
			  mutexP.release();// exit from critical section
			  sharedObj.full.release();//signal - buf is full;
		  }
	  }
		  catch(InterruptedException e) { }
	  }
}
