import java.util.*;

public class NFA {
    private Set terminalSymbol;
    private NFANode startState;
    public NFA(){
        startState=new NFANode();
        terminalSymbol=new HashSet<Character>();
    }
    //add new token and construct NFA                      ¦Å
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
            if(isTerminalSymbol(c)){
                if(!terminalSymbol.contains(c)){
                    terminalSymbol.add(c);
                }
            }
        }
    }
    //construct NFA
    private void construct(String tokenName, String re){
        LinkedList start=new LinkedList<NFANode>();
        start.add(startState);
        LinkedList list=new LinkedList<Character>();
        process(list, start, tokenName, re, true);
    }
    //process ()   ¦Å
    private void process(LinkedList list, LinkedList start, String tokenName, String pre, boolean last){
        for(int i=0;i<pre.length();i++){
            char c=pre.charAt(i);
            if(terminalSymbol.contains(c)){
                list.add(c);
            }else{
                if(c=='*'){
                    if(terminalSymbol.contains(pre.charAt(i-1))) {
                        //add nodes in list
                        while (!list.isEmpty()) {
                            //add until the *ed one
                            char added = (char) list.removeFirst();
                            if(list.isEmpty()){
                                //add *ed one and ¦Å nodes
                                break;
                            }
                            //add node
                        }
                    }
                }else if(c=='('){
                    while(!list.isEmpty()){
                        //add all nodes in list
                    }
                    //find the content in the same level
                    int level=1;
                    for(int j=i+1;j<pre.length();j++){
                        if(pre.charAt(j)=='(') level ++;
                        else if(pre.charAt(j)==')') level--;
                        if(level==0){
                            boolean l=true;
                            if(j<pre.length()-1){
                                if(pre.charAt(j+1)=='*'){
                                    j++;
                                    //add ¦Å node or nodes?
                                }
                            }
                            for(int k=j+1;k<pre.length();k++){
                                if(terminalSymbol.contains(pre.charAt(k))){
                                    l=false;
                                    break;
                                }
                            }
                            process(list,start,tokenName,pre.substring(i,j),l&&last);
                            i=j;
                            continue;
                        }
                    }
                }else if(pre.charAt(i)=='|'){
                    //add all nodes int list
                    while(!list.isEmpty()){
                        //
                    }
                    if(last){
                        //add tokenName
                    }
                    //reset startNode
                }else if(pre.charAt(i)==')'){
                    //the last or second last character of pre
                    //if i==pre.length()-1
                    //if the last is *
                    //if there is |
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
        }else if(c=='|'||c=='*'||c=='('||c==')'){
            return true;
        }else{
            return false;
        }
    }
    public static boolean isTerminalSymbol(Character c){
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
    public String toString(){
        String s=new String();
        Iterator itr=startState.getNextStates().entrySet().iterator();
        while(itr.hasNext()){
            s+=itr.next()+" ";
        }
        return terminalSymbol+" "+s;
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
    private static void testRef(){
        NFANode n1=new NFANode();
        NFANode n2=n1;
        n1.addNextState('a',new NFANode());
        System.out.println(n1+" "+n2);
        n1=n1.getNextState('a');
        System.out.println(n1+" "+n2);
    }
    private static void test(){
        NFA nfa=new NFA();
        nfa.addToken(new Token("1","a(b|c)|a*"));
        System.out.println(nfa);
    }
    public static void main(String[] args) {
        //System.out.println("hello world!");
        //NFANode.test();
        //testSet1();
        //testIsValid();
        //testAddTerminalSymbol();
        //testList();
        //testRef();
        test();
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
    public void putNextState(Character c,NFANode nfaNode){
        nextStates.put(c,nfaNode);
    }
    public void addNextState(Character c, NFANode nfaNode){
        if(nextStates.get(c)==null){
            this.putNextState(c,nfaNode);
        }
    }
    public HashMap getNextStates(){return nextStates;}
    public NFANode getNextState(char pathCh){
        return nextStates.get(pathCh);
    }
    public String toString(){
        return tokenName==null? nextStates.toString(): tokenName+" "+nextStates;
    }
    //test functions
    public static void test(){
        NFANode n=new NFANode();
        System.out.println(n);
        n.addNextState('a',new NFANode());
        System.out.println(n.getNextStates());
        System.out.println(n.getNextState('a')+" "+n.getNextState('b'));
    }
}
