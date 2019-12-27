package lab2;

import java.util.HashSet;
import java.util.Set;

public class RENode {
    private int waitingIndex;//the waiting element of the re
    private int no;//corresponding RE-index in Table
    Set endSymbol;//Set<String>
    public RENode(int no){
        endSymbol=new HashSet();
        this.no=no;
        waitingIndex=0;
    }
    public boolean equals(RENode reNode){
        return no==reNode.no&&waitingIndex== reNode.waitingIndex&&endSymbol.equals(reNode.endSymbol);
    }
    public void setWaitingIndex(int waitingIndex){
        this.waitingIndex=waitingIndex;
    }
    public void addEndSymbol(String symbol){
        endSymbol.add(symbol);
    }
    public int getNo(){
        return no;
    }
    public int getWaitingIndex(){
        return waitingIndex;
    }

    public static void main(String[] args){
        //testSetEquals();
    }
    private static void testSetEquals(){
        Set set1=new HashSet(),set2=new HashSet();
        String s1=new String("abc"),s2=new String("abc"),s3=new String("ab");
        set1.add(s1);set1.add(s3);set2.add(s2);set2.add(s3);
        System.out.print(set1.equals(set2));
    }
}
