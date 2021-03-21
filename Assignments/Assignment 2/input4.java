class input4 {
    public static void main(String[] args){
        A o1;
        A o2;
        A r1;
        A r2;
        A y;
        A x;
        bool checker;
        o1 = new A();
        o2 = new A();
        y = new A();
        checker = true;
        if(checker){
            x = o1;
            r1 = x;
        }else{
            x = o2;
            r2 = x;
        }
        x.f = y;
        /*o1 alias? o2*/
        /*r1 alias? r2*/
        /*x alias? o1*/
        /*x alias? o2*/
        {}
    }    
}
class A{
    A f;
}
