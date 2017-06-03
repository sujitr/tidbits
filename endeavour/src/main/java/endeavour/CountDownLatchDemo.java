package endeavour;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
    public static void main(String[] args)throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(3);
        latch.countDown();
        latch.countDown();
        new Thread(){
            public void run(){
                try{
                    Thread.sleep(3000);
                }catch(InterruptedException ex){
                    ex.printStackTrace();
                }
                latch.countDown();
            };
        }.start();
        System.out.println("Before");
        latch.await();
        System.out.println("After");
    }
}
