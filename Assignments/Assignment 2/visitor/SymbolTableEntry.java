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
    static int count = 0;
    SymbolTableEntry(NodeToken token,String type,String className,Object value,boolean alloc,String typeVar){
        this.token = token;
        this.name = token.toString();
        this.type = type;
        this.className = className;
        this.key = name;
        if(alloc){
            value = (String)("R" + count);
            count++;
        }
        this.value = value;
        this.typeVar = typeVar;
    }
    public String getTypeVar(){
        return typeVar;
    }
    public void setAbsRef(){
        value = (String)("R" + count);
        count++;
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
        System.out.println();
        System.out.println(type+" "+name);
        System.out.println(className);
        System.out.println();
    }
}
