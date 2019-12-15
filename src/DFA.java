import java.util.HashSet;
import java.util.Set;

public class DFA {
    private Set states;
    private Set terminalSymbol;
    public DFA(NFA nfa){
        states=new HashSet<DFANode>();
        terminalSymbol=nfa.getTerminalSymbol();
    }
    public static void main(String[] args){
        //
    }
}

class DFANode{
    private Set nfaNodes;
    public boolean equal(DFANode dfaNode){
        return nfaNodes.equals(dfaNode.nfaNodes);
    }
}
