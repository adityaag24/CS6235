//
// Generated by JTB 1.3.2
//

package visitor;
import syntaxtree.*;
import java.util.*;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class ParallelExecutionGraph<R,A> implements GJVisitor<R,A> {
   //
   // Auto class visitors--probably don't need to be overridden.
   //
   int nodeCounter;
   int objCounter;
   boolean secondPass;
   Map<String,ArrayList<PEGNode>> PEG = new HashMap<String,ArrayList<PEGNode>>();
   Map<ObjectInfo,ArrayList<PEGNode>> waitingNodes = new HashMap<ObjectInfo,ArrayList<PEGNode>>();
   Map<ObjectInfo,ArrayList<PEGNode>> monitorNodes = new HashMap<ObjectInfo,ArrayList<PEGNode>>();
   Map<String,SymbolTable> symbolTable;
   public void setSecondPass(){
      secondPass = true;
   }
   public Map<ObjectInfo, ArrayList<PEGNode>> getMonitorNodes(){
      return monitorNodes;
   }
   public Map<ObjectInfo, ArrayList<PEGNode>> getWaitingNodes(){
      return waitingNodes;
   }
   public ParallelExecutionGraph(Map<String,SymbolTable> symbolTable,boolean secondPass){
      this.symbolTable = symbolTable;
      this.nodeCounter = 0;   
      this.objCounter = 0;
      this.secondPass = secondPass;
   }

   public Map<String,SymbolTable> getSymbolTable(){
      return symbolTable;
   }

   public Map<String,ArrayList<PEGNode>> getPEG(){
      return PEG;
   }
   public R visit(NodeList n, A argu) {
      R _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
   }

   public R visit(NodeListOptional n, A argu) {
      if ( n.present() ) {
         R _ret=null;
         int _count=0;
         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            e.nextElement().accept(this,argu);
            _count++;
         }
         return _ret;
      }
      else
         return null;
   }

   public R visit(NodeOptional n, A argu) {
      if ( n.present() )
         return n.node.accept(this,argu);
      else
         return null;
   }

   public R visit(NodeSequence n, A argu) {
      R _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
   }

   public R visit(NodeToken n, A argu) { return null; }

   //
   // User-generated visitor methods below
   //

   /**
    * f0 -> MainClass()
    * f1 -> ( TypeDeclaration() )*
    * f2 -> ( Query() )*
    * f3 -> <EOF>
    */
   public R visit(Goal n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> "public"
    * f4 -> "static"
    * f5 -> "void"
    * f6 -> "main"
    * f7 -> "("
    * f8 -> "String"
    * f9 -> "["
    * f10 -> "]"
    * f11 -> Identifier()
    * f12 -> ")"
    * f13 -> "{"
    * f14 -> "try"
    * f15 -> "{"
    * f16 -> ( VarDeclaration() )*
    * f17 -> ( QParStatement() )*
    * f18 -> "}"
    * f19 -> "catch"
    * f20 -> "("
    * f21 -> Identifier()
    * f22 -> Identifier()
    * f23 -> ")"
    * f24 -> "{"
    * f25 -> "}"
    * f26 -> "}"
    * f27 -> "}"
    */
   public R visit(MainClass n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      n.f7.accept(this, argu);
      n.f8.accept(this, argu);
      n.f9.accept(this, argu);
      n.f10.accept(this, argu);
      n.f11.accept(this, argu);
      n.f12.accept(this, argu);
      n.f13.accept(this, argu);
      n.f14.accept(this, argu);
      n.f15.accept(this, argu);
      n.f16.accept(this, argu);
      n.f17.accept(this, argu);
      n.f18.accept(this, argu);
      n.f19.accept(this, argu);
      n.f20.accept(this, argu);
      n.f21.accept(this, argu);
      n.f22.accept(this, argu);
      n.f23.accept(this, argu);
      n.f24.accept(this, argu);
      n.f25.accept(this, argu);
      n.f26.accept(this, argu);
      n.f27.accept(this, argu);
      NodeListOptional qParStatements = n.f17;
      String className = n.f1.f0.toString();
      SymbolTable sTable = symbolTable.get(className);
      FunctionTable fTable = symbolTable.get(className).getFunctionTables().get("main");
      String key = className+":main";
      ObjectInfo info = new ObjectInfo(key,className,sTable);
      fTable.setThisPtr(info);
      handleFunction(qParStatements,className,key,sTable,fTable,secondPass);
      return _ret;
   }
   void addMonitorNodes(ArrayList<PEGNode> pegNode,FunctionTable fTable,SymbolTable sTable,ObjectInfo thisPtr){
      for(PEGNode pNode : pegNode){
         if(pNode.getIsInSyncBlock() == true){
            if(!monitorNodes.containsKey(thisPtr)){
               monitorNodes.put(thisPtr,new ArrayList<PEGNode>());
            }
            monitorNodes.get(thisPtr).add(pNode);
         }
         if(pNode.getTypeOfNode().equals("start")){
            SymbolTableEntry entry = getEntry(pNode.getReceiverObject(),sTable,fTable);
            String classRun = entry.getClassName();
            String key = classRun + ":run";
            SymbolTable cSTable = symbolTable.get(classRun);
            FunctionTable cFTable = symbolTable.get(classRun).getFunctionTables().get("run");
            cFTable.setThisPtr((ObjectInfo)entry.getValue());
            addMonitorNodes(PEG.get(key),cFTable,cSTable,cFTable.getThisPtr());
         }
      }
   }
   void addWaitingNodes(ArrayList<PEGNode> pegNode,FunctionTable fTable,SymbolTable sTable,ObjectInfo thisPtr){
      for(PEGNode pNode : pegNode){
         if(pNode.getTypeOfNode().equals("waiting")){
            SymbolTableEntry entry = getEntry(pNode.getReceiverObject(),sTable,fTable);
            String classWaiting = entry.getClassName();
            ObjectInfo waitingObject = (ObjectInfo)entry.getValue();
            if(!waitingNodes.containsKey(waitingObject)){
               waitingNodes.put(waitingObject,new ArrayList<PEGNode>());
            }
            waitingNodes.get(waitingObject).add(pNode);
         }
         if(pNode.getTypeOfNode().equals("start")){
            SymbolTableEntry entry = getEntry(pNode.getReceiverObject(),sTable,fTable);
            String classRun = entry.getClassName();
            String key = classRun + ":run";
            SymbolTable cSTable = symbolTable.get(classRun);
            FunctionTable cFTable = symbolTable.get(classRun).getFunctionTables().get("run");
            cFTable.setThisPtr((ObjectInfo)entry.getValue());
            addWaitingNodes(PEG.get(key),cFTable,cSTable,cFTable.getThisPtr());
         }
      }
   }
   void handleFunction(NodeListOptional qParStatements,String className,String key,SymbolTable sTable,FunctionTable fTable,boolean secondPass){
      if(!secondPass){
         nodeCounter++;
         PEG.put(key,new ArrayList<PEGNode>());
         PEGNode beginNode = new PEGNode(nodeCounter,"main","begin","main",false,null,false,false,false);
         nodeCounter++;
         PEG.get(key).add(beginNode);
         boolean isSync = false;
         for(Enumeration e = qParStatements.elements();e.hasMoreElements();){
            QParStatement qParStmt = (QParStatement)e.nextElement();
            NodeListOptional ann = qParStmt.f0;
            String strAnnotatedLabel = null;
            if(ann.present()){
               for(Enumeration annotation = ann.elements();annotation.hasMoreElements();){
                  Ann annotated = (Ann)annotation.nextElement();
                  Label annotatedLabel = annotated.f1;
                  Identifier idt = annotatedLabel.f0;
                  strAnnotatedLabel = idt.f0.toString(); 
               }
            }  
            Statement stmt = qParStmt.f1;
            handleStatement(key,stmt,sTable,fTable,"main",isSync,strAnnotatedLabel,false,false,false);
         }
      }else{
         if(key.equals(className+":main")){
            addMonitorNodes(PEG.get(key),fTable,sTable,fTable.getThisPtr());
            addWaitingNodes(PEG.get(key),fTable,sTable,fTable.getThisPtr());
         }
      }
   }
   SymbolTableEntry getEntry(String name,SymbolTable sTable,FunctionTable fTable){
      if(fTable.getLocalVars().containsKey(name)){
         return fTable.getLocalVars().get(name);
      }else{
         return sTable.getClassMembers().get(name);
      }
   }
   public Object evaluateExpression(String classExpr,Expression expr,SymbolTable sTable,FunctionTable fTable){
      Object returnValue = null;
      if(classExpr.equals("AndExpression")){
         AndExpression andExpr = (AndExpression)expr.f0.choice;
         Identifier firstOperand = andExpr.f0;
         Identifier secondOperand = andExpr.f2;
         SymbolTableEntry entryFirstOperand = getEntry(firstOperand.f0.toString(),sTable,fTable);
         SymbolTableEntry entrySecondOperand = getEntry(secondOperand.f0.toString(),sTable,fTable);
         returnValue = (Boolean)entryFirstOperand.getValue() && (Boolean)entrySecondOperand.getValue();
      }else if(classExpr.equals("CompareExpression")){
         CompareExpression cmpExpr = (CompareExpression)expr.f0.choice;
         Identifier firstOperand = cmpExpr.f0;
         Identifier secondOperand = cmpExpr.f2;
         SymbolTableEntry entryFirstOperand = getEntry(firstOperand.f0.toString(),sTable,fTable);
         SymbolTableEntry entrySecondOperand = getEntry(secondOperand.f0.toString(),sTable,fTable);
         returnValue = (Integer)entryFirstOperand.getValue() < (Integer)entrySecondOperand.getValue();
      }else if(classExpr.equals("PlusExpression")){
         PlusExpression plusExpr = (PlusExpression)expr.f0.choice;
         Identifier firstOperand = plusExpr.f0;
         Identifier secondOperand = plusExpr.f2;
         SymbolTableEntry entryFirstOperand = getEntry(firstOperand.f0.toString(),sTable,fTable);
         SymbolTableEntry entrySecondOperand = getEntry(secondOperand.f0.toString(),sTable,fTable);
         returnValue = (Integer)entryFirstOperand.getValue() + (Integer)entrySecondOperand.getValue();
      }else if(classExpr.equals("MinusExpression")){
         MinusExpression minusExpr = (MinusExpression)expr.f0.choice;
         Identifier firstOperand = minusExpr.f0;
         Identifier secondOperand = minusExpr.f2;
         SymbolTableEntry entryFirstOperand = getEntry(firstOperand.f0.toString(),sTable,fTable);
         SymbolTableEntry entrySecondOperand = getEntry(secondOperand.f0.toString(),sTable,fTable);
         returnValue = (Integer)entryFirstOperand.getValue() - (Integer)entrySecondOperand.getValue();
      }else if(classExpr.equals("TimesExpression")){
         TimesExpression timesExpr = (TimesExpression)expr.f0.choice;
         Identifier firstOperand = timesExpr.f0;
         Identifier secondOperand = timesExpr.f2;
         SymbolTableEntry entryFirstOperand = getEntry(firstOperand.f0.toString(),sTable,fTable);
         SymbolTableEntry entrySecondOperand = getEntry(secondOperand.f0.toString(),sTable,fTable);
         returnValue = (Integer)entryFirstOperand.getValue() * (Integer)entrySecondOperand.getValue();
      }else if(classExpr.equals("FieldRead")){
         FieldRead fieldRead = (FieldRead)expr.f0.choice;
         Identifier obj = fieldRead.f0;
         Identifier fieldObj = fieldRead.f2;
         SymbolTableEntry entry = getEntry(obj.f0.toString(),sTable,fTable);
         SymbolTableEntry field = ((ObjectInfo)(entry.getValue())).getField(fieldObj.f0.toString());
         returnValue = field.getValue();
      }else if(classExpr.equals("PrimaryExpression")){
         PrimaryExpression prExpr = (PrimaryExpression)expr.f0.choice;
         String classPrExpr = prExpr.f0.choice.getClass().getSimpleName();
         if(classPrExpr.equals("IntegerLiteral")){
            IntegerLiteral integerLiteral = (IntegerLiteral)prExpr.f0.choice;
            returnValue = Integer.parseInt(integerLiteral.f0.toString());
         }else if(classPrExpr.equals("TrueLiteral")){
            returnValue = new Boolean(true);
         }else if(classPrExpr.equals("FalseLiteral")){
            returnValue = new Boolean(false);
         }else if(classPrExpr.equals("Identifier")){
            Identifier idt = (Identifier)prExpr.f0.choice;
            SymbolTableEntry rhsEntry = getEntry(idt.f0.toString(),sTable,fTable);
            returnValue = rhsEntry.getValue();
         }else if(classPrExpr.equals("ThisExpression")){
            returnValue = fTable.getThisPtr();
         }else if(classPrExpr.equals("AllocationExpression")){
            AllocationExpression allocExpr = (AllocationExpression)prExpr.f0.choice;
            ObjectInfo objInfo = new ObjectInfo("R"+objCounter,allocExpr.f1.f0.toString(),symbolTable.get(allocExpr.f1.f0.toString()));
            returnValue = objInfo;
         }else if(classPrExpr.equals("NotExpression")){
            NotExpression notExpr = (NotExpression)prExpr.f0.choice;
            Identifier idt = notExpr.f1;
            SymbolTableEntry rhsEntry = getEntry(idt.f0.toString(),sTable,fTable);
            Boolean value = (Boolean)rhsEntry.getValue();
            returnValue = new Boolean(!value);
         }
      }
      return returnValue;
   }
   public void handleStatement(String key,Statement stmt,SymbolTable sTable,FunctionTable fTable,String threadName,boolean isSync,String annotation,boolean inTrueBlock,boolean inFalseBlock,boolean inWhileBlock){      
      String classOfStmt = stmt.f0.choice.getClass().getSimpleName();
      if(classOfStmt.equals("AssignmentStatement")){
         AssignmentStatement assignmentStmt = (AssignmentStatement)stmt.f0.choice;
         Identifier lhsIdentifier = assignmentStmt.f0;
         Expression expr = assignmentStmt.f2;
         String classExpr = expr.f0.choice.getClass().getSimpleName();
         Object value = evaluateExpression(classExpr,expr,sTable,fTable);
         SymbolTableEntry lhsSTE = getEntry(lhsIdentifier.f0.toString(),sTable,fTable);
         lhsSTE.setValue(value);
         PEGNode computeNode = new PEGNode(nodeCounter,lhsSTE.getName(),"compute",threadName,isSync,annotation,inTrueBlock,inFalseBlock,inWhileBlock);
         nodeCounter++;
         PEG.get(key).add(computeNode);
      }else if(classOfStmt.equals("Block")){
         Block blk = (Block)stmt.f0.choice;
         NodeListOptional qParStmts = blk.f1;
         for(Enumeration e = qParStmts.elements();e.hasMoreElements();){
            QParStatement qParStmt = (QParStatement)e.nextElement();
            NodeListOptional ann = qParStmt.f0;
            String strAnnotatedLabel = null;
            if(ann.present()){
               for(Enumeration annotation1 = ann.elements();annotation1.hasMoreElements();){
                  Ann annotated = (Ann)annotation1.nextElement();
                  Label annotatedLabel = annotated.f1;
                  Identifier idt = annotatedLabel.f0;
                  strAnnotatedLabel = idt.f0.toString(); 
               }
            }  
            String temp = annotation;
            annotation = strAnnotatedLabel;
            handleStatement(key,qParStmt.f1,sTable,fTable,threadName,isSync,annotation,inTrueBlock,inFalseBlock,inWhileBlock);
            annotation = temp;
         }
      }else if(classOfStmt.equals("FieldAssignmentStatement")){
         FieldAssignmentStatement fAssignment = (FieldAssignmentStatement)stmt.f0.choice;
         Identifier lhsObject = fAssignment.f0;
         Identifier fieldOfObject = fAssignment.f2;
         Identifier rhsObject = fAssignment.f4;
         SymbolTableEntry lhsObjectEntry = getEntry(lhsObject.f0.toString(),sTable,fTable);
         SymbolTableEntry rhsObjectEntry = getEntry(rhsObject.f0.toString(),sTable,fTable);
         ObjectInfo lhsObjectInfo = (ObjectInfo)lhsObjectEntry.getValue();
         lhsObjectInfo.setField(fieldOfObject.f0.toString(),rhsObjectEntry.getValue());
         PEGNode fieldAssignmentNode = new PEGNode(nodeCounter,lhsObjectEntry.getName(),"compute",threadName,isSync,annotation,inTrueBlock,inFalseBlock,inWhileBlock);
         nodeCounter++;
         PEG.get(key).add(fieldAssignmentNode);
      }else if(classOfStmt.equals("IfStatement")){
         IfStatement ifStmt = (IfStatement)stmt.f0.choice;
         Identifier predicateIdentifier = ifStmt.f2;
         PEGNode predicateNode = new PEGNode(nodeCounter,predicateIdentifier.f0.toString(),"predicate",threadName,isSync,annotation,inTrueBlock,inFalseBlock,inWhileBlock);
         nodeCounter++;
         PEG.get(key).add(predicateNode);
         Statement trueStmt = ifStmt.f4;
         Statement falseStmt = ifStmt.f6;
         inTrueBlock = true; inFalseBlock = false;
         handleStatement(key,trueStmt,sTable,fTable,threadName,isSync,annotation,inTrueBlock,inFalseBlock,inWhileBlock);
         inTrueBlock = false; inFalseBlock = true;
         handleStatement(key,falseStmt,sTable,fTable,threadName,isSync,annotation,inTrueBlock,inFalseBlock,inWhileBlock);
         inTrueBlock = true; inFalseBlock = false;
      }else if(classOfStmt.equals("WhileStatement")){
         WhileStatement whileStmt = (WhileStatement)stmt.f0.choice;
         Identifier whileIdentifier = whileStmt.f2;
         PEGNode whileNode = new PEGNode(nodeCounter,whileIdentifier.f0.toString(),"while",threadName,isSync,annotation,inTrueBlock,inFalseBlock,inWhileBlock);
         nodeCounter++;
         PEG.get(key).add(whileNode);
         Statement whileStmt1 = whileStmt.f4;
         inWhileBlock = true;
         handleStatement(key,whileStmt1,sTable,fTable,threadName,isSync,annotation,inTrueBlock,inFalseBlock,inWhileBlock);
         inWhileBlock = false;
      }else if(classOfStmt.equals("MessageSend")){
         MessageSend messageSend = (MessageSend)stmt.f0.choice;
         String classMessageSend = messageSend.f0.choice.getClass().getSimpleName();
         if(classMessageSend.equals("callStartMethod")){
            callStartMethod startMethod = (callStartMethod)messageSend.f0.choice;
            PEGNode startNode = new PEGNode(nodeCounter,startMethod.f0.f0.toString(),"start",threadName,isSync,annotation,inTrueBlock,inFalseBlock,inWhileBlock);
            nodeCounter++;
            PEG.get(key).add(startNode);
         }else if(classMessageSend.equals("callNotifyMethod")){
            callNotifyMethod notifyMethod = (callNotifyMethod)messageSend.f0.choice;
            PEGNode notifyNode = new PEGNode(nodeCounter,notifyMethod.f0.f0.toString(),"notify",threadName,isSync,annotation,inTrueBlock,inFalseBlock,inWhileBlock);
            nodeCounter++;
            PEG.get(key).add(notifyNode);
         }else if(classMessageSend.equals("callNotifyAllMethod")){
            callNotifyAllMethod notifyAllMethod = (callNotifyAllMethod)messageSend.f0.choice;
            PEGNode notifyAllNode = new PEGNode(nodeCounter,notifyAllMethod.f0.f0.toString(),"notifyAll",threadName,isSync,annotation,inTrueBlock,inFalseBlock,inWhileBlock);
            nodeCounter++;
            PEG.get(key).add(notifyAllNode);
         }else if(classMessageSend.equals("callWaitMethod")){
            callWaitMethod waitMethod = (callWaitMethod)messageSend.f0.choice;
            PEGNode waitNode = new PEGNode(nodeCounter,waitMethod.f0.f0.toString(),"wait",threadName,isSync,annotation,inTrueBlock,inFalseBlock,inWhileBlock);
            nodeCounter++;
            PEGNode waitingNode = new PEGNode(nodeCounter,waitMethod.f0.f0.toString(),"waiting",threadName,isSync,annotation,inTrueBlock,inFalseBlock,inWhileBlock);
            nodeCounter++;
            PEGNode notifiedEntryNode = new PEGNode(nodeCounter,waitMethod.f0.f0.toString(),"notified-entry",threadName,isSync,annotation,inTrueBlock,inFalseBlock,inWhileBlock);
            nodeCounter++;
            PEG.get(key).add(waitNode);
            PEG.get(key).add(waitingNode);
            PEG.get(key).add(notifiedEntryNode);
         }else{  
            callJoinMethod joinMethod = (callJoinMethod)messageSend.f0.choice;
            PEGNode joinNode = new PEGNode(nodeCounter,joinMethod.f0.f0.toString(),"join",threadName,isSync,annotation,inTrueBlock,inFalseBlock,inWhileBlock);
            nodeCounter++;
            PEG.get(key).add(joinNode);
         }
      }else if(classOfStmt.equals("PrintStatement")){
         PrintStatement printStmt = (PrintStatement)stmt.f0.choice;
         PEGNode printNode = new PEGNode(nodeCounter,printStmt.f2.f0.toString(),"print",threadName,isSync,annotation,inTrueBlock,inFalseBlock,inWhileBlock);
         nodeCounter++;
         PEG.get(key).add(printNode);
      }else if(classOfStmt.equals("SynchStatement")){
         SynchStatement syncStmt = (SynchStatement)stmt.f0.choice;
         Identifier lockObject = syncStmt.f2;
         PEGNode entryNode = new PEGNode(nodeCounter,lockObject.f0.toString(),"entry",threadName,isSync,annotation,inTrueBlock,inFalseBlock,inWhileBlock); 
         PEG.get(key).add(entryNode);
         isSync = true;
         Statement stmt1 = syncStmt.f4;
         handleStatement(key,stmt1,sTable,fTable,threadName,isSync,annotation,inTrueBlock,inFalseBlock,inWhileBlock);
         PEGNode exitNode = new PEGNode(nodeCounter,lockObject.f0.toString(),"exit",threadName,isSync,annotation,inTrueBlock,inFalseBlock,inWhileBlock);
         isSync = false;
         PEG.get(key).add(exitNode);
      }
   }
   /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
   public R visit(TypeDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> ( VarDeclaration() )*
    * f4 -> "}"
    */
   public R visit(ClassDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "extends"
    * f3 -> "Thread"
    * f4 -> "{"
    * f5 -> ( VarDeclaration() )*
    * f6 -> ( MethodDeclaration() )*
    * f7 -> "}"
    */
   public R visit(ClassExtendsDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      n.f7.accept(this, argu);
      String className = n.f1.f0.toString();
      SymbolTable sTable = symbolTable.get(className);
      NodeListOptional methods = n.f6;
      if(!secondPass){
         String key = className + ":run";
         PEG.put(key,new ArrayList<PEGNode>());
         for(Enumeration method = methods.elements();method.hasMoreElements();){
            MethodDeclaration methodDecl = (MethodDeclaration)method.nextElement();
            String methodName = "run";
            FunctionTable fTable = symbolTable.get(className).getFunctionTables().get(methodName);
            PEGNode beginNode = new PEGNode(nodeCounter,"this","begin","this",false,null,false,false,false);
            PEG.get(key).add(beginNode);
            nodeCounter++;
            boolean isSync = false;
            NodeListOptional qParStatements = methodDecl.f9;
            for(Enumeration e = qParStatements.elements();e.hasMoreElements();){
               QParStatement qParStmt = (QParStatement)e.nextElement();
               NodeListOptional ann = qParStmt.f0;
               String strAnnotatedLabel = null;
               if(ann.present()){
                  for(Enumeration annotation = ann.elements();annotation.hasMoreElements();){
                     Ann annotated = (Ann)annotation.nextElement();
                     Label annotatedLabel = annotated.f1;
                     Identifier idt = annotatedLabel.f0;
                     strAnnotatedLabel = idt.f0.toString(); 
                  }
               }  
               Statement stmt = qParStmt.f1;
               handleStatement(key,stmt,sTable,fTable,"this",isSync,strAnnotatedLabel,false,false,false);
            }
         }
      }else{

      }
      return _ret;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
   public R visit(VarDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "public"
    * f1 -> "void"
    * f2 -> "run"
    * f3 -> "("
    * f4 -> ")"
    * f5 -> "{"
    * f6 -> "try"
    * f7 -> "{"
    * f8 -> ( VarDeclaration() )*
    * f9 -> ( QParStatement() )*
    * f10 -> "}"
    * f11 -> "catch"
    * f12 -> "("
    * f13 -> Identifier()
    * f14 -> Identifier()
    * f15 -> ")"
    * f16 -> "{"
    * f17 -> "}"
    * f18 -> "}"
    */
   public R visit(MethodDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      n.f7.accept(this, argu);
      n.f8.accept(this, argu);
      n.f9.accept(this, argu);
      n.f10.accept(this, argu);
      n.f11.accept(this, argu);
      n.f12.accept(this, argu);
      n.f13.accept(this, argu);
      n.f14.accept(this, argu);
      n.f15.accept(this, argu);
      n.f16.accept(this, argu);
      n.f17.accept(this, argu);
      n.f18.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> BooleanType()
    *       | IntegerType()
    *       | Identifier()
    */
   public R visit(Type n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "boolean"
    */
   public R visit(BooleanType n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "int"
    */
   public R visit(IntegerType n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ( Ann() )*
    * f1 -> Statement()
    */
   public R visit(QParStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> <SCOMMENT1>
    * f1 -> Label()
    * f2 -> <SCOMMENT2>
    */
   public R visit(Ann n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> ":"
    */
   public R visit(Label n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Block()
    *       | AssignmentStatement()
    *       | FieldAssignmentStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | MessageSend()
    *       | PrintStatement()
    *       | SynchStatement()
    */
   public R visit(Statement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "{"
    * f1 -> ( QParStatement() )*
    * f2 -> "}"
    */
   public R visit(Block n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
   public R visit(AssignmentStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "."
    * f2 -> Identifier()
    * f3 -> "="
    * f4 -> Identifier()
    * f5 -> ";"
    */
   public R visit(FieldAssignmentStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Identifier()
    * f3 -> ")"
    * f4 -> Statement()
    * f5 -> "else"
    * f6 -> Statement()
    */
   public R visit(IfStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Identifier()
    * f3 -> ")"
    * f4 -> Statement()
    */
   public R visit(WhileStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "synchronized"
    * f1 -> "("
    * f2 -> Identifier()
    * f3 -> ")"
    * f4 -> Statement()
    */
   public R visit(SynchStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> Identifier()
    * f3 -> ")"
    * f4 -> ";"
    */
   public R visit(PrintStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> AndExpression()
    *       | CompareExpression()
    *       | PlusExpression()
    *       | MinusExpression()
    *       | TimesExpression()
    *       | FieldRead()
    *       | PrimaryExpression()
    */
   public R visit(Expression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "&&"
    * f2 -> Identifier()
    */
   public R visit(AndExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "<"
    * f2 -> Identifier()
    */
   public R visit(CompareExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "+"
    * f2 -> Identifier()
    */
   public R visit(PlusExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "-"
    * f2 -> Identifier()
    */
   public R visit(MinusExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "*"
    * f2 -> Identifier()
    */
   public R visit(TimesExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "."
    * f2 -> Identifier()
    */
   public R visit(FieldRead n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> callStartMethod()
    *       | callNotifyMethod()
    *       | callNotifyAllMethod()
    *       | callWaitMethod()
    *       | callJoinMethod()
    */
   public R visit(MessageSend n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "."
    * f2 -> "start"
    * f3 -> "("
    * f4 -> ")"
    * f5 -> ";"
    */
   public R visit(callStartMethod n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "."
    * f2 -> "notify"
    * f3 -> "("
    * f4 -> ")"
    * f5 -> ";"
    */
   public R visit(callNotifyMethod n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "."
    * f2 -> "notifyAll"
    * f3 -> "("
    * f4 -> ")"
    * f5 -> ";"
    */
   public R visit(callNotifyAllMethod n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "."
    * f2 -> "wait"
    * f3 -> "("
    * f4 -> ")"
    * f5 -> ";"
    */
   public R visit(callWaitMethod n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "."
    * f2 -> "join"
    * f3 -> "("
    * f4 -> ")"
    * f5 -> ";"
    */
   public R visit(callJoinMethod n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> IntegerLiteral()
    *       | TrueLiteral()
    *       | FalseLiteral()
    *       | Identifier()
    *       | ThisExpression()
    *       | AllocationExpression()
    *       | NotExpression()
    */
   public R visit(PrimaryExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public R visit(IntegerLiteral n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "true"
    */
   public R visit(TrueLiteral n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "false"
    */
   public R visit(FalseLiteral n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public R visit(Identifier n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "this"
    */
   public R visit(ThisExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
   public R visit(AllocationExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "!"
    * f1 -> Identifier()
    */
   public R visit(NotExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> <SCOMMENT1>
    * f1 -> Identifier()
    * f2 -> "mhp?"
    * f3 -> Identifier()
    * f4 -> <SCOMMENT2>
    */
   public R visit(Query n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      return _ret;
   }

}