package visitor;
import java.util.*;

public class PEGNode {
    private int nodeNumber;
    private String receiverObject;
    private String typeOfNode;
    private String threadName;
    private boolean isInSyncBlock;
    private String annotatedLabel;
    ArrayList<PEGNode> localPred = new ArrayList<PEGNode>();
    ArrayList<PEGNode> localSucc = new ArrayList<PEGNode>();
    PEGNode startPred,startSucc;
    PEGNode waitingEdge;
    private boolean inTrueBlock,inFalseBlock,inWhileBlock;
    PEGNode(int nodeNumber,String receiverObject,String typeOfNode,String threadName,boolean isSync,String annotatedLabel,boolean inTrueBlock,boolean inFalseBlock,boolean inWhileBlock){
        this.nodeNumber = nodeNumber;
        this.receiverObject = receiverObject;
        this.typeOfNode = typeOfNode;
        this.threadName = threadName;
        this.isInSyncBlock = isSync;
        this.annotatedLabel = annotatedLabel;
        this.inTrueBlock = inTrueBlock;
        this.inFalseBlock = inFalseBlock;
        this.inWhileBlock = inWhileBlock;
    }
    public boolean getWhileBlock(){
        return inWhileBlock;
    }
    public boolean getTrueBlock(){
        return inTrueBlock;
    }
    public boolean getFalseBlock(){
        return inFalseBlock;
    }
    public int getNodeNumber() {
        return this.nodeNumber;
    }
    public void setNodeNumber(int nodeNumber) {
        this.nodeNumber = nodeNumber;
    }
    public String getReceiverObject() {
        return this.receiverObject;
    }
    public void setReceiverObject(String receiverObject) {
        this.receiverObject = receiverObject;
    }
    public String getTypeOfNode() {
        return this.typeOfNode;
    }
    public void setTypeOfNode(String typeOfNode) {
        this.typeOfNode = typeOfNode;
    }
    public String getThreadName() {
        return this.threadName;
    }
    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }
    public boolean getIsInSyncBlock() {
        return this.isInSyncBlock;
    }
    public void setIsInSyncBlock(boolean isInSyncBlock) {
        this.isInSyncBlock = isInSyncBlock;
    }
    public String getAnnotatedLabel() {
        return this.annotatedLabel;
    }
    public void setAnnotatedLabel(String annotatedLabel) {
        this.annotatedLabel = annotatedLabel;
    }

    public void print(){
        System.out.println(this.nodeNumber + ": ("+this.receiverObject+","+this.typeOfNode+","+this.threadName+","+this.isInSyncBlock+")");
        if(this.annotatedLabel!=null){
            System.out.println(this.annotatedLabel);
        }
        System.out.println("Control Stats");
        if(!inTrueBlock && !inFalseBlock){
            System.out.println("Unconditional");
        }else if(inTrueBlock && !inFalseBlock){
            System.out.println("True");
        }else if(!inTrueBlock && inFalseBlock){
            System.out.println("False");
        }else{
            System.out.println("WTH");
        }
    }
}
