import java.util.concurrent.locks.*;
import java.util.concurrent.CountDownLatch;
class SerialCompute{
    int[] arr = new int[1000];
    int totalSum = 0;
    SerialCompute(){
        for(int i=0;i<1000;i++){
            arr[i] = i + 1;
        }
    }
    public void computeSum(){
        for(int x:arr){
            totalSum += x;
        }
    }
    public int getSum(){
        return totalSum;
    }
}

class ParallelCompute{
    int[] arr;
    int totalSum;
    ReentrantLock l;
    ParallelCompute(){
        arr = new int[1000];
        for(int i=0;i<1000;i++){
            arr[i] = i + 1;
        }
        totalSum = 0;
        l = new ReentrantLock();
    }
    public void computeSum(int startIdx,int endIdx){
        int localSum = 0;
        for(int i=startIdx;i<=endIdx;i++){
            localSum += arr[i];
        }
        l.lock();
        totalSum += localSum;
        l.unlock();
    }
    public int getSum(){
        return totalSum;
    }
}  
class myThread extends Thread{
    ParallelCompute pc;
    static Thread mainThread;
    CountDownLatch countDownLatch;
    int startIdx,endIdx;
    myThread(CountDownLatch countDownLatch,ParallelCompute pc,int startIdx,int endIdx){
        this.countDownLatch = countDownLatch;
        this.pc = pc;
        this.startIdx = startIdx;
        this.endIdx = endIdx;
    }
    public void run(){
        pc.computeSum(startIdx,endIdx);
        countDownLatch.countDown();
    }
}
public class Question2 {
    public static void main(String[] args)throws InterruptedException{
        SerialCompute sc = new SerialCompute();
        long startTimeSerial = System.nanoTime();
        sc.computeSum();
        System.out.println("Total Sum : "+sc.getSum());
        long endTimeSerial = System.nanoTime();
        double serialTime = endTimeSerial - startTimeSerial;
        System.out.println("Serial Program Took :"+serialTime+"ns");
        long times[] = new long[3];
        int k = 0;
        for(int i=2;i<=8;i=i*2){
            ParallelCompute pc = new ParallelCompute();
            CountDownLatch countDownLatch = new CountDownLatch(i);
            int chunks = 1000 / i;
            long startTimeParallel = System.nanoTime();
            for(int j=0;j<i;j++){
                int startIdx = j * chunks;
                int endIdx = startIdx + chunks - 1;
                (new myThread(countDownLatch,pc,startIdx,endIdx)).start();
            }
            countDownLatch.await();
            long endTimeParallel = System.nanoTime();
            times[k++] = endTimeParallel - startTimeParallel;
        }
        int l = 2;
        for(long time:times){
            System.out.printf("Time taken to execute the program with %d parallel compute units is: %d ns with speedup: %.2f\n",l,time,(serialTime / time));
            l = l * 2;
        }
    }
}
