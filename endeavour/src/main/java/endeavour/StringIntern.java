package endeavour;


public class StringIntern {
    public static void main( String[] args ) {
        String s8 = "AB";
        String s9 = s8.intern();
        if(s9.equals(s8) && s9 == s8)
            System.out.println("All Equal");
        if(s9.equals(s8) && s9!=s8)
            System.out.println("equals Equal");
        if(!s9.equals(s8) && s9==s8)
            System.out.println("== Equal");
        if(!s9.equals(s8) && s9!=s8)
            System.out.println("Nothing Equal");
    }
}
