import java.util.HashMap;

public class NFA {
    private NFANode startState;
    public NFA(){
        startState=new NFANode();
    }
    public static void main(String[] args) {
        //System.out.println("hello world!");

        NFANode n=new NFANode();
        n.addNextState('a',new NFANode());
        System.out.println(n.getNextStates());
        System.out.println(n.getNextStates().get('a')+" "+n.getNextStates().get('b'));
    }
}

class NFANode{
    private String tokenName=null;
    private HashMap<Character,NFANode> nextStates;
    public NFANode(){
        nextStates=new HashMap<>();
    }
    public String getTokenType() throws Exception {
        if(tokenName==null){
            throw new Exception("cannot recognize this symbol");
        }
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }
    public String getTokenName(){return tokenName;}
    public void addNextState(Character c,NFANode nfaNode){
        nextStates.put(c,nfaNode);
    }
    public HashMap getNextStates(){return nextStates;}
    public String toString(){
        return nextStates.toString();
    }
}
