class Wisher{
    public void greet(String name){
        System.out.print("Hello, ");
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            System.out.println("I got interrupted.");
        }
        System.out.println(name);
    }
}

class WisherThread extends Thread{
    Wisher w;
    String name;
    WisherThread(Wisher w,String name){
        this.w = w;
        this.name = name;
    }
    public void run(){
        w.greet(name);
    }
}

class Question3{
    public static void main(String[] args){
        Wisher w = new Wisher();//This object is allocated in the heap space.
        WisherThread t1 = new WisherThread(w,"Aditya");
        WisherThread t2 = new WisherThread(w,"Krishna");
        t1.start();
        t2.start();
    }
}