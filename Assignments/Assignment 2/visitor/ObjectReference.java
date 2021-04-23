package visitor;

public class ObjectReference {
    static int _count = 0;
    String ref;
    String className;
    String varName;
    ObjectReference(String varName,String className){
        this.varName = varName;
        this.className = className;
        this.ref = "R" + _count;
        _count++;
    }
}
