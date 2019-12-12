public class Token {
    private String tokenName;
    private String RE;
    public Token(String tokenName, String re){
        this.tokenName=tokenName;
        RE=re;
    }
    public String getTokenName(){
        return tokenName;
    }
    public String getRE(){
        return RE;
    }
    public static void testToken(){
        Token t=new Token("identifier","abc|de|f|ghi");
        System.out.println(t.getTokenName()+" "+t.getRE());
    }
    public static void main(String[] args){
        //System.out.println("hello world!");
        testToken();
    }
}
