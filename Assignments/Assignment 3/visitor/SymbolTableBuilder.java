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
public class SymbolTableBuilder<R,A> implements GJVisitor<R,A> {
   //
   // Auto class visitors--probably don't need to be overridden.
   //
   Map<String, SymbolTable> symbolTable = new HashMap<String,SymbolTable>();
   public Map<String, SymbolTable> getSymbolTable(){
       return symbolTable;
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
      Identifier className = n.f1;
      SymbolTable sTable = new SymbolTable(className.f0);
      FunctionTable fTable = new FunctionTable("main","static",className.f0.toString(),null,null);
      NodeListOptional varDeclarations = n.f16;
      for(Enumeration e = varDeclarations.elements();e.hasMoreElements();){
        VarDeclaration v = (VarDeclaration)e.nextElement();
        Type t = v.f0;
        Identifier idt = v.f1;
        NodeToken token = idt.f0;
        String type = t.f0.choice.getClass().getSimpleName();
        String varClassName;
        Object value;
        if(type.equals("BooleanType")){
            varClassName = "Boolean";
            value = new Boolean(false);
        }else if(type.equals("IntegerType")){
            varClassName = "Integer";
            value = new Integer(0);
        }else{
            Identifier varIdt = (Identifier)t.f0.choice;
            varClassName = varIdt.f0.toString();
            value = null;
        }
        SymbolTableEntry entry = new SymbolTableEntry(token,type,varClassName,value,"localVar");
        fTable.addLocalVar(entry);
      }
      sTable.addFunctionTable(fTable);
      symbolTable.put(className.f0.toString(),sTable);
      return _ret;
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
      Identifier className = n.f1;
      NodeListOptional nListOpt = n.f3;
      SymbolTable sTable = new SymbolTable(className.f0);
      for(Enumeration e = nListOpt.elements();e.hasMoreElements();){
         VarDeclaration v = (VarDeclaration)e.nextElement();
         Type t = v.f0;
         Identifier idt = v.f1;
         NodeToken token = idt.f0;
         String type = t.f0.choice.getClass().getSimpleName();
         String varClassName;
         Object value;
         if(type.equals("BooleanType")){
               varClassName = "Boolean";
               value = new Boolean(false);
         }else if(type.equals("IntegerType")){
               varClassName = "Integer";
               value = new Integer(0);
         }else{
               Identifier varIdt = (Identifier)t.f0.choice;
               varClassName = varIdt.f0.toString();
               value = null;
         }
         SymbolTableEntry entry = new SymbolTableEntry(token,type,varClassName,value,"localVar");
         sTable.addClassMember(entry);
      }
      symbolTable.put(className.f0.toString(),sTable);
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
      Identifier className = n.f1;
      NodeListOptional nListOpt = n.f5;
      SymbolTable sTable = new SymbolTable(className.f0);
      for(Enumeration e = nListOpt.elements();e.hasMoreElements();){
         VarDeclaration v = (VarDeclaration)e.nextElement();
         Type t = v.f0;
         Identifier idt = v.f1;
         NodeToken token = idt.f0;
         String type = t.f0.choice.getClass().getSimpleName();
         String varClassName;
         Object value;
         if(type.equals("BooleanType")){
               varClassName = "Boolean";
               value = new Boolean(false);
         }else if(type.equals("IntegerType")){
               varClassName = "Integer";
               value = new Integer(0);
         }else{
               Identifier varIdt = (Identifier)t.f0.choice;
               varClassName = varIdt.f0.toString();
               value = null;
         }
         SymbolTableEntry entry = new SymbolTableEntry(token,type,varClassName,value,"localVar");
         sTable.addClassMember(entry);
      }
      NodeListOptional methodDeclarations = n.f6;
      for(Enumeration e = methodDeclarations.elements();e.hasMoreElements();){
         MethodDeclaration method = (MethodDeclaration)e.nextElement();
         FunctionTable fTable = new FunctionTable("run","instance",className.f0.toString(),null,null);
         NodeListOptional varDeclarations = method.f8;
         for(Enumeration e1 = varDeclarations.elements();e1.hasMoreElements();){
            VarDeclaration v = (VarDeclaration)e1.nextElement();
            Type t = v.f0;
            Identifier idt = v.f1;
            NodeToken token = idt.f0;
            String type = t.f0.choice.getClass().getSimpleName();
            String varClassName;
            Object value;
            if(type.equals("BooleanType")){
                  varClassName = "Boolean";
                  value = new Boolean(false);
            }else if(type.equals("IntegerType")){
                  varClassName = "Integer";
                  value = new Integer(0);
            }else{
                  Identifier varIdt = (Identifier)t.f0.choice;
                  type = varIdt.f0.toString();
                  varClassName = varIdt.f0.toString();
                  value = null;
            }
            SymbolTableEntry entry = new SymbolTableEntry(token,type,varClassName,value,"localVar");
            fTable.addLocalVar(entry);
         }
         sTable.addFunctionTable(fTable);
      }
      symbolTable.put(className.f0.toString(),sTable);
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