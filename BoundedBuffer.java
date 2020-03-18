package bb;

import java.util.concurrent.Semaphore;

public class BoundedBuffer extends Thread{
	   private static int N=5;
	   int[] buf=new int[N];
	   private int head,tail;
	   Semaphore full=new Semaphore(0), empty=new Semaphore(N);
	   
	   public int produce() {
		   buf[tail]=(int) (Math.random()*100);
		   int tmp=buf[tail];
		   tail=(tail+1)%N;
		   return tmp;
	   }
	   public int consume() {
		   int tmp=buf[head];
		   head=(head+1)%N;
		   return tmp;
	   }
	   public static void main(String[] args) {
		   Producer[] p1=new Producer[3];
		   for (int i=0;i<3;i++) {
			   p1[i]=new Producer();
			   p1[i].start();
		   }
		   Consumer[] c1=new Consumer[5];
		   for (int i=0;i<5;i++) {
			   c1[i]=new Consumer();
			   c1[i].start();
		   }
		   try {
			sleep((long)10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   for (int i=0;i<3;i++) {
			   p1[i].interrupt();
		   }
		   for (int i=0;i<5;i++) {
			   c1[i].interrupt();
		   }  
	   }
}
