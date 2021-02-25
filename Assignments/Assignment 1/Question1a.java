import java.util.concurrent.CountDownLatch;

class Greeter{
    public void greet(String name){
        System.out.print("Hello there, ");//S3
        System.out.println(name);//S4
    }
}
class myThread extends Thread{
    CountDownLatch ctd;
    Greeter obj;
    myThread(Greeter obj,CountDownLatch ctd){
        this.ctd = ctd;//S8
        this.obj = obj;//S9
    }
    public void run(){
        obj.greet(Thread.currentThread().getName());//S1
        ctd.countDown();//S2
    }
}

public class Question1a{
    public static void main(String[] args)throws InterruptedException{
        CountDownLatch ctd = new CountDownLatch(10);//S6
        Greeter obj = new Greeter();//S7
        for(int i=1;i<=10;i++){
            (new myThread(obj,ctd)).start(); //S5
        }
        ctd.await();//S10
        System.out.println("End of Main Thread");//S11
    }
}