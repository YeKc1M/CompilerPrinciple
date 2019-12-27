package lab2;

import java.util.*;

//prediction table
public class Table {
    public String[] terminalSymbols;
    public String[] nonTerminalSymbols;
    public RE[] res;
    private Set statuses;//Set<Node>
    private Node startNode;
    //HashMap prior;
    public Table(String[] terminalSymbols, String[] nonTerminalSymbols, RE[] res){
        this.terminalSymbols=new String[terminalSymbols.length+1];
        for(int i=0;i<terminalSymbols.length;i++){
            this.terminalSymbols[i]=terminalSymbols[i];
        }
        this.terminalSymbols[terminalSymbols.length]="$";
        this.nonTerminalSymbols=nonTerminalSymbols;
        this.res=new RE[res.length+1];
        this.res[0]=new RE("S'->S");//suppose when S meets $, success
        for(int i=0;i<res.length;i++){
            this.res[i+1]=res[i];
        }
        statuses=new HashSet();
        //initialize startNode
        startNode=new Node(terminalSymbols.length + nonTerminalSymbols.length);
        startNode.setNo(0);
        RENode startRENode=new RENode(0);
        startRENode.addEndSymbol("$");
        startNode.addWaitingElement(startRENode,this);
        statuses.add(startRENode);
        //construct from startNode
        construct(startNode);
        //prior=new HashMap();
    }
    private void construct(Node node){
        Iterator iterator=node.waitingElements.iterator();
        while (iterator.hasNext()){
            //add node according to waitingSymbol
            RENode itrNode= (RENode) iterator.next();
            int waitingIndex=itrNode.getWaitingIndex();
            int reNo=itrNode.getNo();
            String waitingSymbol=res[reNo].getRight()[waitingIndex];
            //generate new status, if not in statuses, push into statuses
            //
            //set node's row
            int rowIndex=getSymbolIndex(waitingSymbol);
            if(rowIndex<terminalSymbols.length){
                //
            }
        }
    }
    protected Collection computeEndSymbol(RENode addedNode, RENode parentNode) {
        Collection collection=new HashSet();
        if(parentNode.getWaitingIndex() == res[parentNode.getNo()].getRight().length - 1){
            collection.addAll(parentNode.endSymbol);
        }else{
            String firstSymbol = res[parentNode.getNo()].getRight()[parentNode.getWaitingIndex() + 1];
            if(isTerminal(firstSymbol)){
                collection.add(firstSymbol);
            }else{
                compute first of the following non-terminal symbol
            }
        }
        return collection;
    }
    public boolean isTerminal(String s){
        for(int i=0;i<terminalSymbols.length;i++){
            if(terminalSymbols[i].equals(s)){
                return true;
            }
        }
        return false;
    }
    public boolean isNonTerminal(String s){
        for(int i=0;i<nonTerminalSymbols.length;i++){
            if(nonTerminalSymbols[i].equals(s)){
                return true;
            }
        }
        return false;
    }
    protected int getSymbolIndex(String symbol){
        for(int i=0;i<terminalSymbols.length;i++){
            if(terminalSymbols[i].equals(symbol)){
                return i;
            }
        }
        for(int i=0;i<nonTerminalSymbols.length;i++){
            if(nonTerminalSymbols[i].equals(symbol)){
                return terminalSymbols.length+i;
            }
        }
        try {
            throw new Exception("no such symbol");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    public static void main(String[] args){
        //Node.testStrArr();
        //Node.testEquals();
        test();
    }
    private static void test(){
        Table table;
        String[] terminal={"S","T","F"};
        String[] nonTerminal={"id","+"};
        RE[] res={new RE("S->T + F"), new RE("T->F + id"), new RE("F->id")};
        table=new Table(terminal,nonTerminal,res);
    }
}

class Node{
    protected LinkedList waitingElements;//LinkedList<RENode>
    private int no=0;
    private String[] row;//corresponding terminalSymbols and nonTerminalSymbols,show shift or reduction
    public Node(int column){
        waitingElements=new LinkedList();
        row=new String[column];
    }
    private boolean hasRENode(RENode reNode){
        boolean b=false;
        Iterator iterator=waitingElements.iterator();
        while (iterator.hasNext()){
            RENode itrNode= (RENode) iterator.next();
            if(itrNode.equals(reNode)){
                b=true;
                break;
            }
        }
        return b;
    }
    public boolean equals(Node node){
        if(row.length!=node.row.length){
            return false;
        }
        Iterator iterator=node.waitingElements.iterator();
        while (iterator.hasNext()){
            RENode reNode= (RENode) iterator.next();
            if(hasRENode(reNode)){
                continue;
            }
            return false;
        }
        return true;
    }
    protected void addWaitingElement(RENode reNode, Table table){
        if(!hasRENode(reNode)) {
            waitingElements.add(reNode);
            if (reNode.getWaitingIndex() < table.res[reNode.getNo()].getRight().length - 1){
                String waiting = table.res[reNode.getNo()].getRight()[reNode.getWaitingIndex()];
                if (table.isTerminal(waiting)) {
                    for (int i = 0; i < table.res.length; i++) {
                        if (table.res[i].getLeft().equals(waiting)) {
                            RENode addedRENode = new RENode(i);
                            Collection endSymCollection = table.computeEndSymbol(addedRENode, reNode);
                            addedRENode.endSymbol.addAll(endSymCollection);
                            addWaitingElement(addedRENode, table);
                        }
                    }
                }
            }
        }
    }
    public void setNo(int no){
        this.no=no;
    }
    protected void setRow(int i, String s){
        row[i]+=(s+" ");
    }

    //test functions
    public static void testStrArr(){
        String[] strings=new String[5];
        for(int i=0;i<strings.length;i++){
            System.out.println(strings[i]);//null
        }
    }
    public static void testEquals(){
        Node node1=new Node(5),node2=new Node(5),node3=new Node(5);
        RENode reNode1=new RENode(0),reNode2=new RENode(0), reNode3=new RENode(1), reNode4=new RENode(1);
        reNode2.setWaitingIndex(3);
        node1.waitingElements.add(reNode1);node1.waitingElements.add(reNode3);
        node2.waitingElements.add(reNode2);node1.waitingElements.add(reNode4);
        node3.waitingElements.add(reNode1);node3.waitingElements.add(reNode4);
        System.out.println(node1.equals(node2)+" "+node1.equals(node3));
    }
}