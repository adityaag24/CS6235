import syntaxtree.*;
import visitor.*;
import java.util.*;

public class A1 {
   static int count = 0;
   String absRef;
   static Map<String,SymbolTable> symbolTable;
   static String absRefBuilder(String className){
      String str1 = className+",R" + count;
      count++;
      return str1;
   }
   static Map<String,String> objects = new HashMap<String,String>();
   public static void main(String [] args) {
      try {
         Node root = new QTACoJavaParser(System.in).Goal();
         SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
         root.accept(symbolTableBuilder,null);
         ClassHierarchyGraph chGraph = symbolTableBuilder.getCHG();
         CallGraphCreator cGraph = new CallGraphCreator(chGraph,symbolTableBuilder.getSymbolTable(),symbolTableBuilder.getRefs());
         root.accept(cGraph,null);
         int noRefs = symbolTableBuilder.getRefs();
         symbolTable = symbolTableBuilder.getSymbolTable();
         Map<String,ArrayList<String> > callGraph = cGraph.getCallGraph();
         Map<String,CallGraphNode> callGraphNodes = cGraph.getCallGraphNodes();
         InterPointsTo(noRefs,callGraph,callGraphNodes);
      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
   static LatticeEntry getLatticeEntry(CallGraphNode p,Identifier idt,FunctionTable fTable,SymbolTable sTable){
      if(idt.f0.toString().equals("null")){
         return p.getONull();
      }else{
         return p.getStack().get(idt.f0.toString());
      }
   }
   static SymbolTableEntry getEntry(FunctionTable fTable,SymbolTable sTable,Identifier idt){
      SymbolTableEntry entry;
      String identifierName = idt.f0.toString();
      if(fTable.getLocalVars().containsKey(identifierName)){
         entry = fTable.getLocalVars().get(identifierName);
      }else if(fTable.getArguements().containsKey(identifierName)){
         entry = fTable.getArguements().get(identifierName);
      }else{
         entry = sTable.getClassMembers().get(identifierName);
      }
      return entry;
   }
   static void initStack(CallGraphNode p,String funcName,int noRefs){
      SymbolTable sTable = p.getSymbolTable();
      FunctionTable fTable = sTable.getFunctionTables().get(funcName);
      Map<String,SymbolTableEntry> args = fTable.getArguements();
      Map<String,SymbolTableEntry> localVars = fTable.getLocalVars();
      for(String arg: args.keySet()){
         SymbolTableEntry entry = args.get(arg);
         if(entry.getType().equals("Identifier")){
            SymbolTable sTable1 = symbolTable.get(entry.getClassName());
            LatticeEntry latticeEntry = new LatticeEntry(noRefs,sTable1);
            p.addEntryToStack(arg, latticeEntry);
         }
      }
      for(String localVar:localVars.keySet()){
         SymbolTableEntry entry = localVars.get(localVar);
         if(entry.getType().equals("Identifier")){
            SymbolTable sTable1 = symbolTable.get(entry.getClassName());
            LatticeEntry latticeEntry = new LatticeEntry(noRefs,sTable1);
            p.addEntryToStack(localVar, latticeEntry);
         }
      }
   }
   static void processStatement(Statement stmt,CallGraphNode p,FunctionTable fTable,SymbolTable sTable){
      String classOfStatement = stmt.f0.choice.getClass().getSimpleName();
      if(classOfStatement.equals("Block")){
         Block blockStmt = (Block)stmt.f0.choice;
         NodeListOptional qStatements = blockStmt.f1;
         for(Enumeration<Node> e = qStatements.elements();e.hasMoreElements();){
            QStatement qStmt = (QStatement)e.nextElement();
            NodeListOptional queries = qStmt.f0;
            for(Enumeration<Node> e1 = queries.elements();e1.hasMoreElements();){
               Query q = (Query)e1.nextElement();
               fTable.addQuery(getEntry(fTable,sTable,q.f1),getEntry(fTable,sTable,q.f3));
            }
            Statement nStmt = (Statement)qStmt.f1;
            processStatement(nStmt,p,fTable,sTable);
         }
      }else{
         if(classOfStatement.equals("AssignmentStatement")){
            AssignmentStatement aStmt = (AssignmentStatement)stmt.f0.choice;
            Identifier idt = aStmt.f0;
            SymbolTableEntry sEntry = getEntry(fTable,sTable,idt);
            if(sEntry.getType().equals("Identifier")){
               LatticeEntry latticeEntryLHS = getLatticeEntry(p,idt,fTable,sTable);
               Expression expr = aStmt.f2;
               String classOfExpression = expr.f0.choice.getClass().getSimpleName();
               if(classOfExpression.equals("PrimaryExpression")){
                  PrimaryExpression prExpr = (PrimaryExpression)expr.f0.choice;
                  String classOfPrimaryExpression = prExpr.f0.choice.getClass().getSimpleName();
                  if(classOfPrimaryExpression.equals("Identifier")){
                     Identifier idt1 = (Identifier)prExpr.f0.choice;
                     if(idt1.f0.toString().equals("null")){
                        latticeEntryLHS.getBitSet().clear();
                     }else{
                        LatticeEntry latticeEntryRHS = getLatticeEntry(p,idt1,fTable,sTable); 
                        latticeEntryLHS.meet(latticeEntryRHS);
                     }
                  }else if(classOfPrimaryExpression.equals("ThisExpression")){
                     LatticeEntry latticeEntryRHS = p.getThisPtr();
                     latticeEntryLHS.meet(latticeEntryRHS);
                  }else if(classOfPrimaryExpression.equals("AllocationExpression")){
                     AllocationExpression allocExpr = (AllocationExpression)prExpr.f0.choice;
                     String classOfNewObject = allocExpr.f1.f0.toString();
                     String absRef = absRefBuilder(classOfNewObject);
                     objects.put(absRef,classOfNewObject);
                     latticeEntryLHS.setBit(count-1);
                  }
               }else if(classOfExpression.equals("FieldRead")){
                     FieldRead fRead = (FieldRead)expr.f0.choice;
                     Identifier objName = fRead.f0;
                     Identifier fieldName = fRead.f2;
                     LatticeEntry objNameEntry = getLatticeEntry(p,objName,fTable,sTable);
                     LatticeEntry latticeEntryRHS = objNameEntry.getField(fieldName.f0.toString());
                     latticeEntryLHS.meet(latticeEntryRHS);
               }else if(classOfExpression.equals("MessageSend")){
                     /*MessageSend msgSend = (MessageSend)prExpr.f0.choice;
                     PrimaryExpression prExpr1 = msgSend.f0;
                     Identifier idt1 = msgSend.f2;
                     NodeOptional args = msgSend.f4;
                     ArrayList<BitSet> actualParams = new ArrayList<BitSet>();
                     if(args.present()){
                        ArgList argList = (ArgList)args.node;
                        Identifier param = (Identifier)argList.f0;
                        LatticeEntry paramEntry = getLatticeEntry(p, param, fTable, sTable);
                        actualParams.add(paramEntry.getBitSet());
                        NodeListOptional restParams = (NodeListOptional)argList.f1;
                        for(Enumeration<Node> e=restParams.elements();e.hasMoreElements();){
                           ArgRest argRest = (ArgRest)e.nextElement();
                           Identifier param1 = (Identifier)argRest.f1;
                           LatticeEntry paramEntry1 = getLatticeEntry(p, param1, fTable, sTable);
                           actualParams.add(paramEntry1.getBitSet());
                        }
                     }*/
               }
            }
         }else if(classOfStatement.equals("FieldAssignmentStatement")){
            FieldAssignmentStatement fieldAStmt = (FieldAssignmentStatement)stmt.f0.choice;
            Identifier varName = fieldAStmt.f0;
            Identifier fieldName = fieldAStmt.f2;
            Identifier rhsIdentifier = fieldAStmt.f4;
            System.out.println("Setting field "+fieldName.f0.toString()+" of variable "+varName.f0.toString()+" to the lattice entry of "+rhsIdentifier.f0.toString());
            LatticeEntry rhsLEntry = getLatticeEntry(p, rhsIdentifier, fTable, sTable);
            LatticeEntry varLEntry = getLatticeEntry(p, varName, fTable, sTable);
            varLEntry.setField(fieldName.f0.toString(),rhsLEntry); 

         }else if(classOfStatement.equals("IfStatement")){
            IfStatement ifStmt = (IfStatement)stmt.f0.choice;
            Statement stmt1 = ifStmt.f4;
            Statement stmt2 = ifStmt.f6;
            processStatement(stmt1,p,fTable,sTable);
            processStatement(stmt2,p,fTable,sTable);
         }
      }
   }
   static void InterPointsTo(int noRefs,Map<String,ArrayList<String>> callGraph, Map<String,CallGraphNode> callGraphNodes){
      Queue<CallGraphNode> worklist = new LinkedList<CallGraphNode>();
      for(String key:callGraphNodes.keySet()){
         if((key.split(":")[1]).equals("main")){
            worklist.add(callGraphNodes.get(key));
            break;
         }
      }
      while(!worklist.isEmpty()){
         CallGraphNode p = worklist.remove();
         String functionName = p.getFunctionName();
         SymbolTable sTable = p.getSymbolTable();
         FunctionTable fTable = p.getSymbolTable().getFunctionTables().get(functionName);
         initStack(p,functionName,noRefs);
         NodeListOptional qStatements = fTable.getQStatements();
         for(Enumeration<Node> e=qStatements.elements();e.hasMoreElements();){
            QStatement q = (QStatement)e.nextElement();
            Statement stmt = q.f1;
            processStatement(stmt,p,fTable,sTable);
         }
         answerQueries(p);
      }
   }
   static void answerQueries(CallGraphNode p){
      String functionName = p.getFunctionName();
      FunctionTable fTable = p.getSymbolTable().getFunctionTables().get(functionName);
      ArrayList<ArrayList<SymbolTableEntry> > queries = fTable.getQueries();
      for(ArrayList<SymbolTableEntry> query:queries){
         SymbolTableEntry entry1 = query.get(0);
         SymbolTableEntry entry2 = query.get(1);
         LatticeEntry lEntry1 = p.getStack().get(entry1.getName());
         LatticeEntry lEntry2 = p.getStack().get(entry2.getName());
         if(lEntry1.join(lEntry2)){
            System.out.println("Yes");
         }else{
            System.out.println("No");
         }
      }
   }
   static void printSymbolTable(SymbolTable table){
      Map<String,SymbolTableEntry> classMembers = table.getClassMembers();
      for(String classMember:classMembers.keySet()){
         System.out.println("Class Member : "+classMember);
         SymbolTableEntry entry = classMembers.get(classMember);
         entry.printEntry();
      }
      Map<String,FunctionTable> functionTables = table.getFunctionTables();
      for(String function:functionTables.keySet()){
         System.out.println("Function : "+function);
         functionTables.get(function).printTable();
         ArrayList<ArrayList<SymbolTableEntry> > queries = functionTables.get(function).getQueries();
         for(ArrayList<SymbolTableEntry> query:queries){
            System.out.println("Query => "+query.get(0).getName()+" and "+query.get(1).getName());
         }
      }
   }
} 



