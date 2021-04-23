class input2 {
    public static void main(String[] args){
        try{
            A t1;
            B t2;
            int x;
            t1 = new A();
            t2 = new B();
            /* S0 : */ x = 1;
            t1.start();
            /* S1 : */ System.out.println(x);
            t2.start();
            /* S2 : */ x = 2;
            /* S3 : */ System.out.println(x);
        }catch(Exception e){ }
    }
}

class A extends Thread{
    public void run(){
        try{
            int x;
            /* S4 : */ x = 2;
        }catch(Exception e){}
    }
}

class B extends Thread{
    public void run(){
        try{
            int y;
            /* S5 : */ y = 2;
        }catch(Exception e){}
    }
}

/* S0 mhp? S4 */
/* S0 mhp? S5 */
/* S1 mhp? S4 */
/* S1 mhp? S5 */
/* S2 mhp? S4 */
/* S2 mhp? S5 */
/* S3 mhp? S4 */
/* S3 mhp? S5 */
