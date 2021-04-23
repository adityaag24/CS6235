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
    Map<String,SymbolTableEntry> arguements = new HashMap<String,SymbolTableEntry>();
    Map<String,FunctionTable> childTables = new HashMap<String,FunctionTable>();
    NodeListOptional qStatements;
    ArrayList<ArrayList<SymbolTableEntry> > queries = new ArrayList<ArrayList<SymbolTableEntry> >();
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
    public void addChild(FunctionTable table){
        childTables.put(table.getKey(),table);
    }
    public void addLocalVar(SymbolTableEntry entry){
        localVars.put(entry.getKey(),entry);
    }
    public void addArguement(SymbolTableEntry entry){
        arguements.put(entry.getKey(),entry);
    }
    public void setLocalVar(Object value,String entry){
        localVars.get(entry).setValue(value);
    }
    public void setArguement(Object value,String entry){
        arguements.get(entry).setValue(value);
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
    public Map<String,SymbolTableEntry> getArguements(){
        return arguements;
    }
    public Object getReturnValue(){
        return returnValue;
    }
    public String getType(){
        return type;
    }
    public void printTable(){
        System.out.println("The Formal Arguements are");
        for(String fArg:arguements.keySet()){
            System.out.println("Arguement : "+fArg);
            arguements.get(fArg).printEntry();
        }
        System.out.println("Variable Declarations are");
        for(String localVar:localVars.keySet()){
            System.out.println("Local Variable : "+localVar);
            localVars.get(localVar).printEntry();
        }
    }
    public void addQuery(SymbolTableEntry f1,SymbolTableEntry f2){
        ArrayList<SymbolTableEntry> query = new ArrayList<SymbolTableEntry>(2);
        query.add(f1);
        query.add(f2);
        queries.add(query);
    }
    public ArrayList<ArrayList<SymbolTableEntry> > getQueries(){
        return queries;
    }
}
