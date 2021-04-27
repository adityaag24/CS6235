class input3 {
    public static void main(String[] args){
        try{
            A a;
            B b;
            A temp;
            int x;
            a = new A();
            b = new B();
            /* S0 : */ b.f0 = a;
            b.start();
            /* S1 : */synchronized(b){
                /* S2 : */ b.wait();
            }
            /* S3 : */ temp = b.f0;
            /* S4 : */ x = temp.f1;
            /* S5 : */ System.out.println(x);
        }catch(Exception e){}
    }
}

class A{
    int f1;
}

class B extends Thread{
    A f0;
    public void run(){
        try{
            int x;
            B b;
            A a;
            f0 = new A();
            x = 2;
            b = this;
            synchronized(b){
                /* S6 : */ f0.f1 = x;
                /* S7 : */ b.notify();
            }
            /* S8 : */ x = 10;
            /* S9 : */ System.out.println(x);
        }catch(Exception e){}
    }
}

/* S0 mhp? S6 */
/* S0 mhp? S8 */
/* S1 mhp? S6 */
/* S1 mhp? S8 */
/* S2 mhp? S6 */
/* S2 mhp? S9 */
/* S3 mhp? S8 */