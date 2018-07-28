import java.util.concurrent.TimeUnit;
import java.lang.*;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class Computation1 extends Thread {
    public static int product = 0;

    public Computation1(String name) {
        super(name);
    }

    public void run() {
        product = 2*3;
        try {
            System.out.println("thread name: "+Thread.currentThread().getName());
            System.out.println("comp1 sleep 4 sec");
            Thread.sleep(10000);
            System.out.println("comp1 sleep completes.");
            TestCyclicBarrier.newBarrier.await();
            System.out.println("comp1 after barrier in computation1.");
        }
        catch(InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}



class Computation2 extends Thread {
    public static int sum = 0;

    public Computation2(String name) {
        super(name);
    }

    public void run()
    {
        System.out.println("thread, name: " + Thread.currentThread().getName());
        System.out.println("Is the barrier broken? - " + TestCyclicBarrier.newBarrier.isBroken());
        sum = 10 + 20;
        try {
            System.out.println("comp2 sleep 2 sec");
            Thread.sleep(7000);
            System.out.println("comp2 sleep completes");
            TestCyclicBarrier.newBarrier.await(3000,TimeUnit.MILLISECONDS);
            System.out.println(" comp2, Number of parties waiting: "+TestCyclicBarrier.newBarrier.getNumberWaiting());
        }
        catch(InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        catch(TimeoutException e) {
            e.printStackTrace();
        }
    }
}




public class TestCyclicBarrier implements Runnable {

    public static CyclicBarrier newBarrier = new CyclicBarrier(3);

    public static void main(String[] args) {
        TestCyclicBarrier tCBRunnable = new TestCyclicBarrier();

        Thread t1 = new Thread(tCBRunnable);
        t1.start();
    }

    public void run() {
        System.out.println("number of parties required to trip the barrier, newBarrier.getParties(): "+ newBarrier.getParties());
        System.out.println("Sum of product and sum " + (Computation1.product + Computation2.sum));

        Computation1 comp1 = new Computation1("computation1");
        Computation2 comp2 = new Computation2("computation2");

       // Thread t1 = new Thread(comp1);
        //Thread t2 = new Thread(comp2);
       
        comp1.start();
        comp2.start();

       // t1.start();
        //t2.start();

        try{
            System.out.println("waiting in main");
            TestCyclicBarrier.newBarrier.await();
            System.out.println("waiting in main completed.");
        }
        catch(InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

        System.out.println("Sum of product and sum: "+(Computation1.product + Computation2.sum));

        newBarrier.reset();
        System.out.println("Barrier reset successful.");

    }
}


