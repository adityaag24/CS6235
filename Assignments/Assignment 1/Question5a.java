class A{
    public synchronized void a1(B b){
        System.out.println("Hello from a1 in class A, executed by thread named "+Thread.currentThread().getName());
        Thread.yield();
        b.bye();
    }
    public synchronized void bye(){
        System.out.println("Bye from class A");
    }
}
class B{
    public synchronized void b1(A a){
        System.out.println("Hello from b1 in class B, executed by thread named "+Thread.currentThread().getName());
        Thread.yield();
        a.bye();
    }
    public synchronized void bye(){
        System.out.println("Bye from class B");
    }
}

public class Question5a extends Thread {
    A a = new A();
    B b = new B();
    public void runner(){
        this.start();//Starts the thread object
        a.a1(b);//This line is executed by the main thread
    }
    public void run(){
        b.b1(a);
    }
    public static void main(String[] args){
        Question5a t = new Question5a();
        t.runner();
    }
}
