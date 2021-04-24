package visitor;
import syntaxtree.*;
import java.util.*;

public class FunctionTable {
    String name;
    String className;
    NodeToken token;
    String type;
    String key;
    SymbolTableEntry thisPtr;
    Map<String,SymbolTableEntry> localVars = new HashMap<String,SymbolTableEntry>();
    NodeListOptional qStatements;
    Object returnValue;
    FunctionTable(String name,String type,String className,NodeToken token,Object returnValue){
        this.name = name;
        this.type = type;
        this.className = className;
        this.token = token;
        this.returnValue = returnValue;
        this.key = name;
    }
    public NodeListOptional getQStatements(){
        return qStatements;
    }
    public void setQStatements(NodeListOptional qStatements){
        this.qStatements = qStatements;
    }
    public void setThisPtr(SymbolTableEntry thisPtr){
        this.thisPtr = thisPtr;
    }
    public SymbolTableEntry getThisPtr(){
        return thisPtr;
    }
    public void addLocalVar(SymbolTableEntry entry){
        localVars.put(entry.getKey(),entry);
    }
    public void setLocalVar(Object value,String entry){
        localVars.get(entry).setValue(value);
    }
    public String getName(){
        return name;
    }
    public String getClassName(){
        return className;
    }
    public String getKey(){
        return key;
    }
    public NodeToken getToken(){
        return token;
    }
    public Map<String,SymbolTableEntry> getLocalVars(){
        return localVars;
    }
    public Object getReturnValue(){
        return returnValue;
    }
    public String getType(){
        return type;
    }
    public void printTable(){
        System.out.println(getType() + " " + getName());
        System.out.println("Variable Declarations are");
        for(String localVar:localVars.keySet()){
            System.out.println("Local Variable : "+localVar);
            localVars.get(localVar).printEntry();
        }
    }
}
