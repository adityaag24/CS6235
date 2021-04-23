class input2 {
    public static void main(String[] args){
        try{
            A t1;
            B t2;
            int x;
            t1 = new A();
            t2 = new B();
            /* S0 : */ x = 1;
            /* S1 : */ t1.start();
            /* S2 : */ System.out.println(x);
            /* S3 : */ t2.start();
            /* S4 : */ x = 2;
            /* S5 : */ System.out.println(x);
        }catch(Exception e){ }
    }
}

class A extends Thread{
    public void run(){
        try{
            int x;
            /* S
        }catch(Exception e){}
    }
}

class B extends Thread{
    public void run(){
        try{
            int y;
        }catch(Exception e){}
    }
}
