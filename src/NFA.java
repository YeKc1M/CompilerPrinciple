import java.util.*;

public class NFA {
    private Set terminalSymbol;
    private NFANode startState;
    public NFA(){
        startState=new NFANode();
        terminalSymbol=new HashSet<Character>();
    }
    //add new token and construct NFA
    public void addToken(Token token){
        String tokenName=token.getTokenName();
        String re=token.getRE();
        //add non-terminal symbol
        addTerminalSymbol(re);
        //construct NFA
        construct(tokenName,re);
    }
    //add terminal symbol
    private void addTerminalSymbol(String re){
        for(int i=0;i<re.length();i++){
            Character c=re.charAt(i);
            if(isValid(c)){
                if(!terminalSymbol.contains(c)){
                    terminalSymbol.add(c);
                }
            }
        }
    }
    //construct NFA
    private void construct(String tokenName, String re){
        NFANode temp=startState;
        LinkedList list=new LinkedList<Character>();
        for(int i=0;i<re.length();i++){
            Character c=re.charAt(i);
            if(isValid(c)){
                list.add(c);
            }else{
                //if */+, pop all elements and add NFANode except the *ed one
                //if(, pop all elements and add NFANode, then do according to the content of ()     iterate
                //if), do according to following of )                                               iterate
                //if |, pop all elements and add NFANode, if | is not in (), add tokenName to NFANode
            }
        }
    }
    //if the character is terminal symbol
    private static boolean isValid(Character c){
        int i=(int) c;
        //System.out.print(i+" ");
        if(i<=(int)'9'&&i>=(int)'0'){
            return true;
        }else if(i<=(int)'z'&&i>=(int)'a'){
            return true;
        }else if(i<=(int)'Z'&&i>=(int)'A'){
            return true;
        }else{
            return false;
        }
    }
    //test functions
    public static void testSet1(){
        Set s=new HashSet();
        String re="ab|abc|ac*|b(a|c)|c(ab)*c";
        for(int i=0;i<re.length();i++){
            Character c=re.charAt(i);
            //
        }
        System.out.println(s);
    }
    private static void testList(){
        LinkedList queue=new LinkedList<Character>();
        queue.add('a');queue.add('b');queue.add('c');queue.add('d');
        System.out.println(queue);
        Character c=(Character)queue.remove();//remove first by default
        System.out.println(c+" "+queue);
        Character c1=(Character)queue.removeFirst();
        System.out.println(c1+" "+queue);
        Character c2=(Character)queue.removeLast();
        System.out.println(c2+" "+queue);
    }
    private static void testIsValid(){
        System.out.println(isValid('0'));
        System.out.println(isValid((char)((int)'0'-1)));
        System.out.println(isValid('9'));
        System.out.println(isValid((char)((int)'9'+1)));
        System.out.println(isValid('a'));
        System.out.println(isValid((char)((int)'a'-1)));
        System.out.println(isValid('z'));
        System.out.println(isValid((char)((int)'z'+1)));
        System.out.println(isValid('A'));
        System.out.println(isValid((char)((int)'A'-1)));
        System.out.println(isValid('Z'));
        System.out.println(isValid((char)((int)'Z'+1)));
    }
    private static void testAddTerminalSymbol(){
        NFA nfa=new NFA();
        nfa.addToken(new Token("token1","ab|abc|ac*|b(a|c)|c(ab)*c"));
        System.out.println(nfa.terminalSymbol);
    }
    public static void main(String[] args) {
        //System.out.println("hello world!");
        //NFANode.test();
        //testSet1();
        //testIsValid();
        //testAddTerminalSymbol();
        //testList();
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
    //test functions
    public static void test(){
        NFANode n=new NFANode();
        n.addNextState('a',new NFANode());
        System.out.println(n.getNextStates());
        System.out.println(n.getNextStates().get('a')+" "+n.getNextStates().get('b'));
    }
}
