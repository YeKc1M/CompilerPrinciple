import java.util.*;

public class NFA {
    private Set terminalSymbol;
    private NFANode startState;
    public NFA(){
        startState=new NFANode();
        terminalSymbol=new HashSet<Character>();
    }
    //add new token and construct NFA                      ��
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
        NFANode start=startState;
        LinkedList list=new LinkedList<Character>();
        boolean or=false;
        for(int i=0;i<re.length();i++){
            Character c=re.charAt(i);
            if(terminalSymbol.contains(c)){
                list.add(c);
            }else{
                if(c=='('){
                    while(!list.isEmpty()){
                        char added=(char)list.removeFirst();
                        if(start.getNextState(added)==null){
                            start.addNextState(added,new NFANode());
                        }
                        start=start.getNextState(added);
                    }
                    for(int j=i+1;j<re.length();j++) {
                        char c1 = re.charAt(j);
                        if (c1 == '|'){
                            or = true;
                        }else if(c1==')'){
                            if(j<re.length()-1&&re.charAt(j+1)=='*'){
                                if(start.getNextState('��')==null){
                                    start.addNextState('��',new NFANode());
                                }
                                start=start.getNextState('��');
                                if(start.getNextState('��')==null){
                                    start.addNextState('��',new NFANode());
                                }
                            }
                        }
                    }
                }else if(c==')'){
                    while(!list.isEmpty()){
                        char c1=(char)list.removeFirst();
                        if(start.getNextState(c1)==null){
                            start.addNextState(c1, new NFANode());
                        }
                        start=startState.getNextState(c1);
                    }
                    or=false;
                }else if(c=='*'){
                    if(re.charAt(i-1)==')'){
                        start=start.getNextState('��');
                    }else{
                        while(!list.isEmpty()){
                            char c1=(char)list.removeFirst();
                            if(!list.isEmpty()){
                                if(start.getNextState(c1)==null){
                                    start.addNextState(c1,new NFANode());
                                }
                                start=start.getNextState(c1);
                            }else{
                                if(start.getNextState('��')!=null){
                                    start.addNextState('��',new NFANode());
                                }
                                start.addNextState(c1,start);
                                if(start.getNextState('��')==null){
                                    start.addNextState('��',new NFANode());
                                }
                                start=start.getNextState('��');
                            }
                        }
                    }
                }else if(c=='|'){
                    //
                }
            }
        }
    }
    //if the character is terminal symbol
    public static boolean isValid(Character c){
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
    public NFANode getNextState(char pathCh){
        return nextStates.get(pathCh);
    }
    public String toString(){
        return nextStates.toString();
    }
    //test functions
    public static void test(){
        NFANode n=new NFANode();
        n.addNextState('a',new NFANode());
        System.out.println(n.getNextStates());
        System.out.println(n.getNextState('a')+" "+n.getNextState('b'));
    }
}
