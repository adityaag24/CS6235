class input1 {
    public static void main(String [] args){
        try {
            A t1;
            int x;
            t1 = new A();
            x = 1;
            t1.start();
            /* S0 : */ x = 2;
            /* S1 : */ System.out.println(x);
        } catch (Exception e) { }
    }
}

class A extends Thread{
    public void run(){
        try{
            int x;
            /* S2 : */ x = 1;
            /* S3 : */ System.out.println(x);
        }catch(Exception e){}
    }
}

/* S0 mhp? S1 */
/* S1 mhp? S2 */
/* S0 mhp? S2 */
/* S0 mhp? S3 */
/* S2 mhp? S3 */
