package visitor;

import java.util.*;
public class ObjectInfo {
    String name;
    String className;
    Map<String,SymbolTableEntry> fields = new HashMap<String,SymbolTableEntry>();
    ObjectInfo(String name,String className,SymbolTable sTable){
        this.name = name;
        this.className = className;
        Map<String,SymbolTableEntry> classMembers = sTable.getClassMembers();
        for(String field:classMembers.keySet()){
            fields.put(field,classMembers.get(field));
        }
    }
    public SymbolTableEntry getField(String field){
        return fields.get(field);
    }
    public void setField(String field,Object value){
        fields.get(field).setValue(value);
    }
    public String getName(){
        return name;
    }
    public String getClassName(){
        return className;
    }
}
