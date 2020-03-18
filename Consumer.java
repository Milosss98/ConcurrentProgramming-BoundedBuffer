package bb;

import java.util.concurrent.Semaphore;

public class Consumer extends Thread{
	static BoundedBuffer sharedObj;
	  static {//initializaton of sharedObj - only one object for classes Producer and Consumer
		  if (Producer.sharedObj==null) sharedObj=new BoundedBuffer();
		  else sharedObj=Producer.sharedObj;
	  }
	  private static Semaphore mutexC=new Semaphore(1);
	  
	  private static int posId=0;
	  private int id=++posId;
	  
	  public void run() {
		  try {
			  while (true) {
				 sharedObj.full.acquire();//wait for buf is full
				 mutexC.acquire();
				 int a=sharedObj.consume();
				 System.out.println("Consumer "+id + " - element "+a);
				 sleep((long) (Math.random()*1000));
				 mutexC.release();
				 sharedObj.empty.release();//signal - buf is empty;
			  }
		  }
		  catch(InterruptedException e) { }
	  }
}
