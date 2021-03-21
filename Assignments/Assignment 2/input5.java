class input5 {
    public static void main(String[] args){
        B a1;
        B a2;
        a1 = new B();
        a2 = new B();
        
    }
}
class A{
    A f0;
    A foo(A x){
        f0 = new A();
        return f0;
    }
    A getf0(){
        return f0;
    }
}
class B extends A{
    B f1;
    A foo(A x){
        f1 = new B();
        return f1;
    }
    A getf1(){
        return f1;
    }
}