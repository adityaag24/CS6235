package visitor;
import java.util.*;

public class ClassHierarchyGraph<T> {
    private Map<T, List<T> > subClassNames = new HashMap<>();
    private Map<T, T> parentClass = new HashMap();
    public List<T> getSubClasses(String className){
        return subClassNames.get(className);
    }
    public T getParent(String className){
        return parentClass.get(className);
    }
    public void addClass(T s){
        subClassNames.put(s,new LinkedList<T>());
        parentClass.put(s,s);
    }
    public void addEdge(T src,T dest){
        if(!subClassNames.containsKey(src)){
            addClass(src);
        }
        if(!subClassNames.containsKey(dest)){
            addClass(dest);
        }
        subClassNames.get(src).add(dest);
        parentClass.put(dest,src);
    }
    public void printSubclassNames(){
        for(T v : subClassNames.keySet()){
            if(subClassNames.get(v).size() == 0){
                System.out.println(v.toString() + " has no subclass(es).");
            }else{
                System.out.println(v.toString() + " has the following subclass(es).");
                for(T subClass:subClassNames.get(v)){
                    System.out.println(subClass.toString());
                }
            }
        }
    }
    public void printParentClassNames(){
        for(T v : parentClass.keySet()){
            if(v.toString().equals(parentClass.get(v))){
                System.out.println(v.toString() + " has no parentclass(es).");
            }else{
                System.out.println(v.toString() + " parent is "+parentClass.get(v));
            }
        }
    }
}
