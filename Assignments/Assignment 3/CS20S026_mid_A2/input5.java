class input5 {
    public static void main(String[] args){
        try{
            A a;
            int x;
            boolean res;
            /* S0 : */res = true;
            if(res){
                a = new A();
                a.start();
                /* S1 : */ x = 3;
                /* S2 : */ System.out.println(x);
            }else{
                /* S3 : */ x = 4;
                /* S4 : */ System.out.println(x);
            }
        }catch(Exception e){}
    }
}   

class A extends Thread{
    public void run(){
        try{
            int x;
            /* S4 : */ x = 2;
            /* S5 : */ System.out.println(x);
        }catch(Exception e){}
    }
}

/* S0 mhp? S4 */
/* S1 mhp? S5 */
/* S2 mhp? S5 */
/* S3 mhp? S4 */
/* S4 mhp? S5 */