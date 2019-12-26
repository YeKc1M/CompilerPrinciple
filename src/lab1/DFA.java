package lab1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class DFA {
    private Set states;
    private Set terminalSymbol;//¦Å
    private DFANode startState;
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
        startState=dfaNode;
        while(!addedStates.isEmpty()){
            Object[] elements= addedStates.toArray();//used to find next DFANode, if not in states, add
            for(int i=0;i<elements.length;i++){
                DFANode thisNode= (DFANode) elements[i];
                addedStates.remove(thisNode);
                if(getDFANode(thisNode)==null){
                    states.add(thisNode);
                }else{
                    thisNode=getDFANode(thisNode);
                }
                //find next DFANodes
                Iterator terminalSymbolItr=terminalSymbol.iterator();
                while(terminalSymbolItr.hasNext()){
                    char path= (char) terminalSymbolItr.next();
                    DFANode newDFANode=new DFANode();
                    Iterator itr=((DFANode)elements[i]).getNfaNodes().iterator();
                    boolean flag=false;//
                    while(itr.hasNext()){
                        NFANode itrNext= (NFANode) itr.next();
                        if(itrNext.getNextState(path)!=null){
                            flag=true;
                            itrNext=itrNext.getNextState(path);
                            do{
                                newDFANode.addNFANode(itrNext);
                                itrNext=itrNext.getNextState('¦Å');
                            }while(itrNext!=null);
                        }
                    }
                    if(flag) {
                        if (getDFANode(newDFANode) == null) {
                            addedStates.add(newDFANode);
                        } else {
                            newDFANode = getDFANode(newDFANode);
                        }
                        thisNode.setNextState(path, newDFANode);
                    }
                }
            }
        }
    }

    public String match(String re){
        String s=null;
        DFANode dfaNode=startState;
        try{
            for(int i=0;i<re.length();i++){
                if(dfaNode.getNextState(re.charAt(i))==null){
                    throw new Exception("cannot recognize symbol");
                }else{
                    dfaNode=dfaNode.getNextState(re.charAt(i));
                }
            }
            s=dfaNode.getTokenName();
            if(s==null){
                throw new Exception("cannot recognize symbol");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return s;
    }
    public String getTokens(String seq){
        String s=new String();
        String[] arr=seq.split(" ");
        for(int i=0;i<arr.length;i++){
            s+=match(arr[i])+" ";
        }
        return s;
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
    private DFANode getDFANode(DFANode dfaNode){
        DFANode node=null;
        Iterator iterator=states.iterator();
        while(iterator.hasNext()){
            DFANode nextNode= (DFANode) iterator.next();
            if(nextNode.equal(dfaNode)){
                node=nextNode;
            }
        }
        return node;
    }
    public static void main(String[] args){
        //test1();
        //testSet();
        //testSetContain();
        //testMatch();
        test();
    }
    private static void test(){
        NFA nfa=new NFA();
        nfa.addToken(new Token("token1","int|char|float"));
        nfa.addToken(new Token("id","abc|cba"));
        DFA dfa=new DFA(nfa);
        System.out.println(dfa.getTokens("int abc cba char"));
    }
    private static void test1(){
        NFA nfa=new NFA();
        nfa.addToken(new Token("1","int|inta|intb"));
        DFA dfa=new DFA(nfa);
        System.out.println(dfa.startState);
        System.out.println(dfa.startState.getNextState('i'));
    }
    private static void testMatch(){
        NFA nfa=new NFA();
        nfa.addToken(new Token("1","int|inta|intb"));
        DFA dfa=new DFA(nfa);
        System.out.println(dfa.match("inta"));
        System.out.println(dfa.match("int"));
        System.out.println(dfa.match("intc"));
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
    private HashMap nextStates;
    public DFANode(){
        nfaNodes=new HashSet<NFANode>();
        nextStates=new HashMap<Character, DFANode>();
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
    public void setNextState(char path, DFANode dfaNode){
        nextStates.put(path,dfaNode);
    }
    public DFANode getNextState(char path){
        DFANode dfaNode=null;
        dfaNode= (DFANode) nextStates.get(path);
        return dfaNode;
    }
    public Set getNfaNodes(){
        return nfaNodes;
    }
    public String getTokenName(){
        return tokenName;
    }
    public String toString(){
        if(tokenName==null){
            return nextStates.toString();
        }else{
            return tokenName+":"+nextStates;
        }
    }
}

