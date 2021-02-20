import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
class myThread extends Thread{
    CyclicBarrier barrier;
    myThread(CyclicBarrier barrier){
        this.barrier=barrier;
    }
    public void run(){
        System.out.println("Hello from "+Thread.currentThread().getName());
        try{
            barrier.await();
        }catch(Exception e){
            System.out.println("Interrupted.");
        }
    }
}
public class Question5b {
    public static void main(String[] args){
        CyclicBarrier barrier = new CyclicBarrier(12);
        for(int i=0;i<10;i++){
            (new myThread(barrier)).start();
        }
        try{
            barrier.await();
        }catch(Exception e){
            System.out.println("Main Interrupted");
        }
        System.out.println("Bye from Main Thread");
    }
}
