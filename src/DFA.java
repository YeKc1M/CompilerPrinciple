import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class DFA {
    private Set states;
    private Set terminalSymbol;//¦Å
    private DFA(){}
    public DFA(NFA nfa){
        states=new HashSet<DFANode>();
        terminalSymbol=nfa.getTerminalSymbol();
        Set addedStates=new HashSet<DFANode>();//save the nodes that may be added
        //System.out.println(nfa);
        //add start node
        NFANode nfaNode=nfa.getStartState();
        DFANode dfaNode=new DFANode();
        do{
            dfaNode.addNFANode(nfaNode);
            nfaNode=nfaNode.getNextState('¦Å');
        }while(nfaNode!=null);
        addedStates.add(dfaNode);
        //
        while(!addedStates.isEmpty()){
            Object[] elements= addedStates.toArray();//used to find next DFANode, if not in states, add
            for(int i=0;i<elements.length;i++){
                addedStates.remove(elements[i]);
                if(!hasDFANode((DFANode) elements[i])){
                    states.add(elements[i]);
                }
                //find next DFANodes
                Iterator terminalSymbolItr=terminalSymbol.iterator();
                while(terminalSymbolItr.hasNext()){
                    char path= (char) terminalSymbolItr.next();
                    DFANode newDFANode=new DFANode();
                    Iterator itr=((DFANode)elements[i]).getNfaNodes().iterator();
                    while(itr.hasNext()){
                        NFANode itrNext= (NFANode) itr.next();
                        if(itrNext.getNextState(path)!=null){
                            itrNext=itrNext.getNextState(path);
                            do{
                                newDFANode.addNFANode(itrNext);
                                itrNext=itrNext.getNextState('¦Å');
                            }while(itrNext!=null);
                        }
                    }
                    addedStates.add(newDFANode);
                }
            }
        }
    }
    public String toString(){
        String s=new String();
        Iterator iterator=states.iterator();
        while (iterator.hasNext()){
            DFANode node= (DFANode) iterator.next();
            s+=node.toString()+" ";
        }
        return s;
    }
    private boolean hasDFANode(DFANode dfaNode){
        boolean flag=false;
        Iterator iterator=states.iterator();
        while (iterator.hasNext()){
            DFANode inStates= (DFANode) iterator.next();
            if(inStates.equal(dfaNode)){
                flag=true;
                break;
            }
        }
        return flag;
    }
    public static void main(String[] args){
        test1();
        //testSet();
        //testSetContain();
    }
    private static void test1(){
        NFA nfa=new NFA();
        nfa.addToken(new Token("1","int|float|char"));
        DFA dfa=new DFA(nfa);
        System.out.println(dfa);
    }
    private static void testSet(){
        Set set=new HashSet();
        for(int i=0;i<3;i++){
            set.add(new DFANode());
        }
        Object[] elements=set.toArray();
        for(int i=0;i<elements.length;i++){
            set.remove(elements[i]);
            set.add(new DFANode());
        }
        System.out.println(set.isEmpty());
    }
    private static void testSetContain(){
        Set set1=new HashSet<DFANode>();
        DFANode dfaNode1=new DFANode(),dfaNode2=new DFANode();
        set1.add(dfaNode1);
        System.out.println(set1.contains(dfaNode2));
    }
}

class DFANode{
    private String tokenName=null;
    private Set nfaNodes;
    public DFANode(){
        nfaNodes=new HashSet<NFANode>();
    }
    public boolean equal(DFANode dfaNode){
        return nfaNodes.equals(dfaNode.nfaNodes);
    }
    public void addNFANode(NFANode nfaNode){
        try{
            if(nfaNode.getTokenName()!=null){
                if(tokenName==null){
                    tokenName=nfaNode.getTokenName();
                }else{
                    throw new Exception("token conflicts");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        nfaNodes.add(nfaNode);
    }
    public Set getNfaNodes(){
        return nfaNodes;
    }
    public String toString(){
        return tokenName+":"+nfaNodes.toString();
    }
}
