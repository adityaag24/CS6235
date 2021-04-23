class input4 {
    public static void main(String[] args){
        try{
            A a;
            int x;
            /* S0 : */ a = new A();
            a.start();
            /* S1 : */ x = 2;
            /* S2 : */ System.out.println(x);
        }catch(Exception e){}
    }
}

class A extends Thread{
    public void run(){
        try{
            B b;
            int y;
            b.start();
            /* S3 : */ y = 3;
            /* S4 : */ System.out.println(y);
        }catch(Exception e){}
    }
}

class B extends Thread{
    public void run(){
        try{
            int z;
            /* S5 : */ z = 4;
            /* S6 : */ System.out.println(z);
        }catch(Exception e){}
    }
}

/* S0 mhp? S3 */
/* S1 mhp? S3 */
/* S2 mhp? S3 */
/* S0 mhp? S5 */
/* S1 mhp? S5 */
/* S3 mhp? S5 */
