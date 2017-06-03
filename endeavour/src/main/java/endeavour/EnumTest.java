package endeavour;

public class EnumTest {
    public EnumTest(){
        System.out.println("Inside Constructor");
    }
    public void printType(){
        // following line would not compile as Enums are not allowed to be declared in a method
        // enum PrinterType {DOT,INK,LASER}
    }
}
