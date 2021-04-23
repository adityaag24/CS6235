package visitor;
import java.util.*;

public class LatticeEntry {
    BitSet bits;
    Map<String,LatticeEntry> fields = new HashMap<String,LatticeEntry>();
    public LatticeEntry(int size){
        this.bits = new BitSet(size);
    }
    public LatticeEntry(int size,SymbolTable sTable){
        this.bits = new BitSet(size);
        Map<String,SymbolTableEntry> classMembers = sTable.getClassMembers();
        for(String classMember:classMembers.keySet()){
            fields.put(classMember,new LatticeEntry(size));
        }
    }
    public int getSetBits(){
        return bits.cardinality();
    }
    public void meet(LatticeEntry other){
        this.bits.or(other.bits);
    }
    public void setField(String field,LatticeEntry entry){
        this.fields.get(field).meet(entry);
    }
    public LatticeEntry getField(String entry){
        return fields.get(entry);
    }
    public boolean join(LatticeEntry other){
        return this.bits.intersects(other.bits);
    }
    public BitSet getBitSet(){
        return this.bits;
    }
    public void setBit(int idx){
        this.bits.set(idx);
    }
    public void getBit(int idx){
        this.bits.get(idx);
    }
}
