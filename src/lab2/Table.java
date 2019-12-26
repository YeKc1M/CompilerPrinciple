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
        startNode.addWaitingElement(startRENode);
        //prior=new HashMap();
    }
    public static void main(String[] args){
        //Node.testStrArr();
        //Node.testEquals();
    }
    private static void test(){
        //
    }
}

class Node{
    private LinkedList waitingElements;//LinkedList<RENode>
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
    protected void addWaitingElement(RENode reNode){
        if(!hasRENode(reNode)){
            waitingElements.add(reNode);
            //recursive add continue
        }
    }
    public void setNo(int no){
        this.no=no;
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