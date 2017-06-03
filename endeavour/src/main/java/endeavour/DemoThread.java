package endeavour;

public class DemoThread extends Thread {
    private int x = 2;
    public static void main(String[] args) throws Exception {
        new DemoThread().method();
    }
    public DemoThread(){
        System.out.println("test");
        x = 5;
        start();
    }
    public void method() throws Exception {
        join();
        x = x - 1;
        System.out.println(x);
    }
    public void run(){
        System.out.println("run");
        x *= 2;
    }
}