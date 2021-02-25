import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Question5b extends Thread {
    static Random rand = new Random();

    static void completeTask1() {
        String[] greeter = { "Hello", "Hola", "Hallo", "Hey" };
        int idx = rand.nextInt(4);
        System.out.println(greeter[idx] + " there!" + Thread.currentThread().getName());
    }

    static void completeTask2() {
        int x = rand.nextInt(5);
        int cubeCoeff = rand.nextInt(3);
        int sqrCoeff = rand.nextInt(2);
        int linCoeff = rand.nextInt(3);
        int constant = rand.nextInt(10);
        double y = cubeCoeff * Math.pow(x, 3) + sqrCoeff * Math.pow(x, 2) + linCoeff * x + constant;
        System.out.printf("The value of the function %dx^3 + %dx^2 + %dx + %d at x=%d is y = %f (computed by thread %s)\n", cubeCoeff, sqrCoeff,
                linCoeff, constant, x, y,Thread.currentThread().getName());
    }

    static void completeTask3() {
        System.out.println("Bye from " + Thread.currentThread().getName());
    }

    static void runJob(CyclicBarrier c1, CyclicBarrier c2) throws InterruptedException, BrokenBarrierException {
        completeTask1();
        c1.await();
        completeTask2();
        c2.await();
        completeTask3();
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(7);
        try {
            CyclicBarrier c1 = new CyclicBarrier(6, () -> System.out.println("Completed Task 1"));
            CyclicBarrier c2 = new CyclicBarrier(6, () -> System.out.println("Completed Task 2"));
            for (int i = 0; i < 7; i++) {
                executor.submit(() -> {
                    try {
                        runJob(c1, c2);
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                });
            }
        }catch(Exception e){
            executor.shutdown();
        }
    }
}