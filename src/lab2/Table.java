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
        System.out.print("hello world!");
    }
    private void construct(Node node){
        /*
        HashMap waitingGroup=new HashMap();//LinkedList<String,Set<RENode>> grouped by waiting symbol
        Iterator waitingElementsItr=node.waitingElements.iterator();
        while (waitingElementsItr.hasNext()){
            RENode itrNode = (RENode) waitingElementsItr.next();
            int waitingIndex=itrNode.getWaitingIndex();
            int reNo=itrNode.getNo();
            if(waitingIndex<res[reNo].getRight().length){
                String waitingSymbol=res[reNo].getRight()[waitingIndex];
                Set set= (Set) waitingGroup.get(waitingSymbol);
                if(set==null){
                    set=new HashSet();
                }
                waitingGroup.put(waitingSymbol,set.add(itrNode));
            }else{
                Set endSymbols=itrNode.endSymbol;
                Iterator endSymbolsItr=endSymbols.iterator();
                while (endSymbolsItr.hasNext()){
                    String endSymbol= (String) endSymbolsItr.next();
                    int rowIndex=getSymbolIndex(endSymbol);
                    node.setRow(rowIndex,"r"+reNo);
                }
            }
        }*/
        for(int i=0;i<terminalSymbols.length;i++){
            Iterator iterator=node.waitingElements.iterator();
            //construct new status
            Node newNode=new Node(terminalSymbols.length+nonTerminalSymbols.length);
            while (iterator.hasNext()){
                RENode reNode= (RENode) iterator.next();
                int reNo=reNode.getNo();int waitingIndex=reNode.getWaitingIndex();
                if(waitingIndex==res[reNo].getRight().length){
                    continue;
                }else{
                    String waitingSymbol=res[reNo].getRight()[waitingIndex];
                    if(waitingSymbol.equals(terminalSymbols[i])){
                        RENode newRENode=new RENode(reNo);
                        newRENode.setWaitingIndex(waitingIndex+1);
                        newNode.addWaitingElement(newRENode,this);
                    }
                }
            }
            //add if not in statuses
            if(!newNode.equals(new Node(terminalSymbols.length+nonTerminalSymbols.length))){
                int nodeNo=getStatus(newNode);
                if(nodeNo==statuses.toArray().length){
                    //add newNode
                    newNode.setNo(nodeNo);
                    statuses.add(newNode);
                }
                node.setRow(i,"s"+nodeNo);
            }
        }
        for(int i=0;i<nonTerminalSymbols.length;i++){
            Iterator iterator=node.waitingElements.iterator();
            //construct new status
            Node newNode=new Node(terminalSymbols.length+nonTerminalSymbols.length);
            while (iterator.hasNext()){
                RENode reNode= (RENode) iterator.next();
                int reNo=reNode.getNo();int waitingIndex=reNode.getWaitingIndex();
                if(waitingIndex==res[reNo].getRight().length){
                    //
                    continue;
                }else{
                    String waitingSymbol=res[reNo].getRight()[waitingIndex];
                    if(waitingSymbol.equals(terminalSymbols[i])){
                        RENode newRENode=new RENode(reNo);
                        newRENode.setWaitingIndex(waitingIndex+1);
                        newRENode.endSymbol=reNode.endSymbol;
                        newNode.addWaitingElement(newRENode,this);
                    }
                }
            }
            //add if not in statuses
            if(!newNode.equals(new Node(terminalSymbols.length+nonTerminalSymbols.length))){
                int nodeNo=getStatus(newNode);
                if(nodeNo==statuses.toArray().length){
                    //add newNode
                    newNode.setNo(nodeNo);
                    statuses.add(newNode);
                }
                node.setRow(i,""+nodeNo);
            }
        }
        /*
        Iterator iterator=waitingGroup.entrySet().iterator();
        while (iterator.hasNext()){
            //add node according to waitingSymbol
            Map.Entry entry= (Map.Entry) iterator.next();
            Set reNodes= (Set) entry.getValue();
            String waitingSymbol= (String) entry.getKey();
            //generate new status, if not in statuses, push into statuses
            Node newStatus=new Node(terminalSymbols.length + nonTerminalSymbols.length);
            Iterator reNodesItr=reNodes.iterator();
            while (reNodesItr.hasNext()){
                RENode reNode= (RENode) reNodesItr.next();
                RENode newRENode=new RENode(reNode.getNo());
                newRENode.setWaitingIndex(reNode.getWaitingIndex()+1);
                newStatus.addWaitingElement(newRENode,this);
            }
            int nodeNo=getStatus(newStatus);
            if(nodeNo==statuses.toArray().length){
                newStatus.setNo(nodeNo);
                statuses.add(newStatus);
            }
            //set node's row
            int rowIndex=getSymbolIndex(waitingSymbol);
            if(rowIndex<terminalSymbols.length){
                node.setRow(rowIndex,"s"+nodeNo);
            }else{
                node.setRow(rowIndex,nodeNo+"");
            }
        }*/
    }
    protected Collection computeEndSymbol(RENode addedNode, RENode parentNode) {
        Collection collection=new HashSet();
        if(parentNode.getWaitingIndex() == res[parentNode.getNo()].getRight().length - 1){
            collection.addAll(parentNode.endSymbol);
        }else{
            String nextSymbol = res[parentNode.getNo()].getRight()[parentNode.getWaitingIndex() + 1];
            if(isTerminal(nextSymbol)){
                collection.add(nextSymbol);
            }else{
                //compute first of the following non-terminal symbol
                collection.addAll(computeFirst(nextSymbol));
            }
        }
        return collection;
    }
    private int getStatus(Node node){
        Iterator iterator=statuses.iterator();
        while (iterator.hasNext()){
            Node n= (Node) iterator.next();
            if(n.equals(node)){
                return n.getNo();
            }
        }
        return statuses.toArray().length;
    }
    private Collection computeFirst(String s){
        Collection collection=new HashSet();
        for(int i=0;i<res.length;i++){
            if(res[i].getLeft().equals(s)){
                String firstSymbol=res[i].getRight()[0];
                if(isTerminal(firstSymbol)){
                    collection.add(firstSymbol);
                }else{
                    collection.addAll(computeFirst(firstSymbol));
                }
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
        String[] terminal={"c","d"};
        String[] nonTerminal={"S","C"};
        RE[] res={new RE("S->C C"), new RE("C->c C"), new RE("C->d")};
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
        if(row.length!=node.row.length||waitingElements.toArray().length!=node.waitingElements.toArray().length){
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
            if(reNode.getWaitingIndex()<table.res[reNode.getNo()].getRight().length){
                String waiting = table.res[reNode.getNo()].getRight()[reNode.getWaitingIndex()];
                if (table.isNonTerminal(waiting)) {
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
    public int getNo(){
        return no;
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