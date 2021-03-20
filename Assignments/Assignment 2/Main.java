import syntaxtree.*;
import visitor.*;

public class Main {
   public static void main(String [] args) {
      try {
         Node root = new QTACoJavaParser(System.in).Goal();

	 System.out.println("Parsed successfully.");
         
	 GJDepthFirst v = new GJDepthFirst();
         root.accept(v,null); // Your assignment part is invoked here.

	 // Feel free to copy/extend GJDepthFirst to newer classes and
	 // instantiate those visitor classes, and "visit" the syntax-tree
	 // by calling root.accept(..)
         
      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
} 



