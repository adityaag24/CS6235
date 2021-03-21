class input3 {
    public static void main(String[] args){
        A a;
        A b;
        A ret1;
        A ret2;
        A r1;
        A r2;
        a = new A();//R1
        b = new A();//R2
        ret1 = a.foo();//R3
        ret2 = b.foo();//R4
        a.f = ret1;//R1.f = {R3}
        b.f = ret2;//R2.f = {R4} 
        r1 = a;//{R1}
        r2 = b;//{R2}
        /* a alias? b */
        /* r1 alias? r2 */
        /* a alias? r1 */
        /* a alias? r2 */
        /* b alias? r1 */
        /* b alias? r2 */
        {}
    }    
}
class A{
    A f;
    public A foo(){
        A n;
        n = new A();
        return n;
    }
}