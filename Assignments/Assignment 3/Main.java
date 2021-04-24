import syntaxtree.*;
import visitor.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            Node root = new QParJavaParser(System.in).Goal();
            System.out.println("Parsed successfully.");
            SymbolTableBuilder sTableBuilder = new SymbolTableBuilder();
            root.accept(sTableBuilder, null);
            System.out.println("Symbol Table Built.");
            ParallelExecutionGraph builder = new ParallelExecutionGraph(sTableBuilder.getSymbolTable());
            /*Map<String,SymbolTable> symbolTable = sTableBuilder.getSymbolTable();
            for(String className : symbolTable.keySet()){
                System.out.println("Class Name : "+className);
                System.out.println("The class has "+symbolTable.get(className).getClassMembers().size()+ " number of class Members.");
                for(String classMember:symbolTable.get(className).getClassMembers().keySet()){
                    symbolTable.get(className).getClassMembers().get(classMember).printEntry();
                }
                System.out.println("The class has "+symbolTable.get(className).getFunctionTables().size() + " number of functions.");
                for(String function:symbolTable.get(className).getFunctionTables().keySet()){
                    FunctionTable fTable = symbolTable.get(className).getFunctionTables().get(function);
                    fTable.printTable();
                }
            }*/
            //GJDepthFirst v = new GJDepthFirst();
            //root.accept(v, null);
            
            //blah blah

        } catch (ParseException e) {
            System.out.println(e.toString());
        }
    }
}