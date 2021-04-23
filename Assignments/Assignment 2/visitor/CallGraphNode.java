package visitor;
import java.util.*;

public class CallGraphNode {
    SymbolTable sTable;
    String functionName;
    String className;
    ArrayList<BitSet> formalParams = new ArrayList<BitSet>();
    Map<String, LatticeEntry> stack = new HashMap<String,LatticeEntry>();
    LatticeEntry thisPtr;
    LatticeEntry returnVal;
    int noRefs;
    LatticeEntry oNull;
    public void setThisPtr(LatticeEntry entry){
        this.thisPtr = entry;
    }
    public CallGraphNode(String className,String functionName,int noRefs,SymbolTable sTable){
        this.className = className;
        this.functionName = functionName;
        this.sTable = sTable;
        this.noRefs = noRefs;
        this.returnVal = new LatticeEntry(noRefs);
        this.thisPtr = new LatticeEntry(noRefs);
        this.oNull = new LatticeEntry(noRefs);
        Map<String,SymbolTableEntry> classMembers = sTable.getClassMembers();
        if(classMembers!=null){
            for(String classMember:classMembers.keySet()){
                LatticeEntry entry = new LatticeEntry(noRefs);
                formalParams.add(entry.getBitSet());
            }
        }
    }
    public LatticeEntry getONull(){
        return this.oNull;
    }
    public ArrayList<BitSet> getParams(){
        return formalParams;
    }
    public ArrayList<BitSet> meetParams(ArrayList<BitSet> actualParams){
        ArrayList<BitSet> newParams = new ArrayList<BitSet>();
        for(int i=0;i<actualParams.size();i++){
            BitSet newBits = new BitSet(noRefs);
            newBits.or(formalParams.get(i));
            newBits.or(actualParams.get(i));
            newParams.add(newBits);
        }
        return newParams;
    }
    public void addEntryToStack(String key,LatticeEntry name){
        stack.put(key,name);
    }
    public String getClassName(){
        return className;
    }
    public String getFunctionName(){
        return functionName;
    }
    public LatticeEntry getThisPtr(){
        return thisPtr;
    }
    public Map<String,LatticeEntry> getStack(){
        return stack;
    }
    public SymbolTable getSymbolTable(){
        return sTable;
    }
}
