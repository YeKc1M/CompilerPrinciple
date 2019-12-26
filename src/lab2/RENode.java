package lab2;

import java.util.HashSet;
import java.util.Set;

public class RENode {
    private int waitingIndex;//the waiting element of the re
    private int no;//corresponding RE-index in Table
    Set endSymbol;
    public RENode(int no){
        endSymbol=new HashSet();
        this.no=no;
        waitingIndex=0;
    }
    public boolean equals(RENode reNode){
        return no==reNode.no&&waitingIndex== reNode.waitingIndex;
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
}
