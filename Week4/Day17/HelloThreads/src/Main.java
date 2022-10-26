import java.util.ArrayList;

public class Main {
    public static int answer = 0;
    public static void sayHello() throws InterruptedException {
        Thread threadArray[] = new Thread[10];
        for (int i = 0; i < threadArray.length; i++) {
            threadArray[i] = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    System.out.print("Hello number " + j + " from thread number " + Thread.currentThread().getId());
                    if (j % 10 == 0) {
                        System.out.println('.');
                    }
                }
            });
            threadArray[i].start();
            threadArray[i].join();
        } // using println the threads run in order
    }

    public static synchronized void badSum() throws InterruptedException {
        answer = 0;
        int maxValue = 40000;
        Thread threadArray[] = new Thread[5];
        System.out.println(threadArray);
        for(int i = 0; i < threadArray.length; i++) {
            final int finalI = i;
            threadArray[i] = new Thread(() -> {
                for(int val = finalI * maxValue / threadArray.length; val < Math.min((finalI+1)*maxValue/threadArray.length, maxValue); val++) {
                    answer += val;
                }
            });
            threadArray[i].start();
        }

        for (int i = 0; i < threadArray.length; i++) {
            threadArray[i].join();
        }

        System.out.println(answer);
        System.out.println((maxValue * (maxValue - 1) / 2));
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello world!");
//        sayHello();
        badSum(); // if more than one thread is used, or maxValue > 100, the answer is different. i think this is because
        // the threads don't wait for other threads to finish their calculations before doing their own.
    }
}