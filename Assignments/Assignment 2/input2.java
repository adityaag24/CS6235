class input2 {
    public static void main(String[] args){
        A a;
        A b;
        A r1;
        bool checker;
        a = new A();
        b = new A();
        checker = false;
        if(checker){
            r1 = a;
        }else{
            r1 = b;
        }
        /* a alias? b */
        /* r1 alias? a */
        /* r1 alias? b */
        {}
    }
}
class A{
    int f0;
    public int f0(){
        f0 = 0;
        System.out.println(f0);
        return f0;
    }
}
