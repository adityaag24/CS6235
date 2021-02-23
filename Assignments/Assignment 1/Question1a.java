import java.util.Random;
import java.util.concurrent.CountDownLatch;
class TotalArr{
    int total = 0;
    int arr[] = new int[4];
    TotalArr(){
        for(int i=0;i<4;i++){
            arr[i] = i+1;
        }
    }
    void doJob(int idx)throws InterruptedException{
        int temp = total;
        Thread.sleep(1000);
        temp = temp + arr[idx];
        total = temp;
    }
    void printTotal(){
        System.out.println(total);
    }
}

class myThread extends Thread{
    TotalArr tot;
    CountDownLatch ctd;
    int idx;
    myThread(int idx,CountDownLatch ctd,TotalArr tot){
        this.tot = tot;
        this.idx = idx;
        this.ctd = ctd;
    }
    public void run(){
        try{
        tot.doJob(idx);
        }catch(InterruptedException e){}
        ctd.countDown();
    }
}

public class Question1a{
    public static void main(String[] args)throws InterruptedException{
        CountDownLatch ctd = new CountDownLatch(4);
        TotalArr tot = new TotalArr();
        for(int i=1;i<=4;i++){
            int idx = i-1;//Returns a random number between 0 (inclusive) and 5 (inclusive)
            (new myThread(idx,ctd,tot)).start();
        }
        ctd.await();
        tot.printTotal();
    }
}