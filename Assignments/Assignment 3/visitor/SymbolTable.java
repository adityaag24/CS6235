package visitor;
import syntaxtree.*;
import java.util.*;

public class SymbolTable {
    String name;
    NodeToken token;
    String key;
    Map<String,SymbolTableEntry> classMembers = new HashMap<String,SymbolTableEntry>();
    Map<String,FunctionTable> functionTables = new HashMap<String,FunctionTable>();
    SymbolTable(NodeToken token){
        this.name = token.toString();
        this.token = token;
        this.key = name;
    }
    public void addClassMember(SymbolTableEntry entry){
        classMembers.put(entry.getKey(),entry);
    }
    public void addFunctionTable(FunctionTable table){
        functionTables.put(table.getKey(),table);
    }
    public void setClassMember(Object value,String idt){
        classMembers.get(idt).setValue(value);
    }
    public String getName(){
        return name;
    }
    public String getKey(){
        return key;
    }
    public NodeToken getToken(){
        return token;
    }
    public Map<String,SymbolTableEntry> getClassMembers(){
        return classMembers;
    }
    public Map<String,FunctionTable> getFunctionTables(){
        return functionTables;
    }
}
