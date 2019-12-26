package lab2;

public class RE {
    private String left;
    private String right;
    public RE(String re){
        String[] strings=re.split("->");
        left=strings[0];
        right=strings[1];
    }
    public boolean equals(RE re){
        return left.equals(re.left)&&right.equals(re.right);
    }
    public static void main(String[] args){
        //testSplit();
    }
    private static void testSplit(){
        String s="S->T * F";
        String[] strings=s.split("->");
        System.out.println(strings[0]+"\t"+strings[1]);
    }
}
