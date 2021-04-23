package visitor;

public class CallGraphInfo {
    String name;
    boolean root;
    SymbolTable sTable;
    FunctionTable fTable;
    CallGraphInfo(String name,SymbolTable sTable,FunctionTable fTable){
        this.name = name;
        this.root = false;
    }
    public void setRoot(){
        root = true;
    }
}
