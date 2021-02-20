class IncrementVar{
    int total = 0;
    public void increment(){
        total += 1;
    }
    public int getValue(){
        return total;
    }
}
class myThread implements Runnable{
    IncrementVar var;
    myThread(IncrementVar inc){
        this.var = inc;
    }
    public void run(){
        var.increment();
    }
}
public class Question1a{
    public static void main(String[] args)throws InterruptedException{
        IncrementVar var = new IncrementVar();
        myThread []threads = new myThread[1000];
        for(int i=0;i<1000;i++){
            threads[i] = new myThread(var);
            new Thread(threads[i]).start();
        }
        Thread.sleep(1);
        System.out.println("Value of Increment Variable : "+var.getValue());
    }
}