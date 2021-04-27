package visitor;
import syntaxtree.*;

public class SymbolTableEntry {
    String name;
    NodeToken token;
    String type;
    String className;
    Object value;
    String key;
    String typeVar;
    SymbolTableEntry(NodeToken token,String type,String className,Object value,String typeVar){
        this.token = token;
        this.name = token.toString();
        this.type = type;
        this.className = className;
        this.key = name;
        this.value = value;
        this.typeVar = typeVar;
    }
    public String getTypeVar(){
        return typeVar;
    }
    public NodeToken getToken(){
        return token;
    }
    public String getName(){
        return name;
    }
    public String getType(){
        return type;
    }
    public String getClassName(){
        return className;
    }
    public Object getValue(){
        return value;
    }
    public String getKey(){
        return key;
    }
    public void setValue(Object value){
        this.value = value;
    }
    public void printEntry(){
        System.out.println(className+" "+name);
        System.out.println("Type: "+type);
        System.out.println("Value: "+value);
    }
}
