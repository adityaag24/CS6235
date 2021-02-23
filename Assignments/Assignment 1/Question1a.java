import java.util.concurrent.CountDownLatch;

class Greeter{
    public void greet(String name){
        System.out.print("Hello there, ");
        System.out.println(name);
    }
}
class myThread extends Thread{
    CountDownLatch ctd;
    Greeter obj;
    myThread(Greeter obj,CountDownLatch ctd){
        this.ctd = ctd;
        this.obj = obj;
    }
    public void run(){
        obj.greet(Thread.currentThread().getName());
        ctd.countDown();
    }
}

public class Question1a{
    public static void main(String[] args)throws InterruptedException{
        CountDownLatch ctd = new CountDownLatch(10);
        Greeter obj = new Greeter();
        for(int i=1;i<=10;i++){
            (new myThread(obj,ctd)).start();
        }
        ctd.await();
    }
}