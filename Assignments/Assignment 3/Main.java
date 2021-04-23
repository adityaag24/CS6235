import syntaxtree.*;
import visitor.*;

public class Main {
    public static void main(String[] args) {
        try {
            Node root = new QParJavaParser(System.in).Goal();

            System.out.println("Parsed successfully.");

            //GJDepthFirst v = new GJDepthFirst();
            //root.accept(v, null);
            
            //blah blah

        } catch (ParseException e) {
            System.out.println(e.toString());
        }
    }
}