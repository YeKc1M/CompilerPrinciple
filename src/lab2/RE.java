package lab2;

public class RE {
    private String left;
    private String[] right;
    public RE(String re){
        String[] strings=re.split("->");
        left=strings[0];
        right=strings[1].split(" ");
    }
    public boolean equals(RE re){
        return left.equals(re.left)&&right.equals(re.right);
    }
    public String[] getRight(){
        return right;
    }
    public String getLeft(){
        return left;
    }
    public static void main(String[] args){
        //testSplit();
        testRE();
    }
    private static void testSplit(){
        String s="S->T * F";
        String[] strings=s.split("->");
        System.out.println(strings[0]+"\t"+strings[1]);
    }
    private static void testRE(){
        RE re=new RE("S->T * F");
        System.out.println(re.left);
        for(int i=0;i<re.right.length;i++){
            System.out.println(re.right[i]);
        }
    }
}
