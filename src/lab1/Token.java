package lab1;

public class Token {
    private String tokenName;
    private String RE;
    public Token(String tokenName, String re){
        //check the validation of re
        try{
            int level = 0;//indicate in which ()
            for (int i = 0; i < re.length(); i++) {
                if(!NFA.isValid(re.charAt(i)))
                    throw new Exception("invalid character in regular expression");
                if (re.charAt(i) == '(')
                    level++;
                else if (re.charAt(i) == ')')
                    level--;
            }
            if (level != 0)
                throw new Exception("incorrect regular expression");
        }catch (Exception e){
            e.printStackTrace();
        }
        //initialization
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
        Token t= null;
        try {
            t = new Token("identifier","abc|de|f|ghi");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(t.getTokenName()+" "+t.getRE());
    }
    public static void main(String[] args){
        //System.out.println("hello world!");
        testToken();
    }
}

