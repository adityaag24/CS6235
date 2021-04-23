package visitor;
import syntaxtree.*;
import java.util.*;

public class FunctionCallInfo {
    String name;
    String className;
    NodeToken returnToken;
    NodeToken callerToken;
    Map<String,SymbolTableEntry> params = new HashMap<String,SymbolTableEntry>();
}
