import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PancakeShopConcurrent {

    private static final int MAX_PANCAKES_PER_BATCH = 12;
    private static final int MAX_PANCAKES_PER_USER = 5;
    private static final int NUM_USERS = 3;

    private int totalPancakesProduced = 0;
    private int totalPancakesConsumed = 0;

    public static void main(String[] args) {
        PancakeShopConcurrent shop = new PancakeShopConcurrent();
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                shop.startNew30SecondSlot();
            }
        }, 0, 30000);
    }

    public void startNew30SecondSlot() {
        Thread[] userThreads = new Thread[NUM_USERS];
        for (int i = 0; i < NUM_USERS; i++) {
            userThreads[i] = new Thread(new User());
            userThreads[i].start();
        }

        try {
            for (int i = 0; i < NUM_USERS; i++) {
                userThreads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Starting Time: " + System.currentTimeMillis());
        System.out.println("Ending Time: " + (System.currentTimeMillis() + 30000));
        System.out.println("Shopkeeper met the needs of all users: " + (totalPancakesConsumed >= NUM_USERS * MAX_PANCAKES_PER_USER));
        System.out.println("Pancakes wasted: " + (totalPancakesProduced - totalPancakesConsumed));
        System.out.println("Unmet user orders: " + (NUM_USERS * MAX_PANCAKES_PER_USER - totalPancakesConsumed));
        System.out.println("Total pancakes produced: " + totalPancakesProduced);
        System.out.println("Total pancakes consumed: " + totalPancakesConsumed);
        System.out.println();
    }

    private class User implements Runnable {
        @Override
        public void run() {
            Random random = new Random();
            int demand = random.nextInt(MAX_PANCAKES_PER_USER + 1);
            totalPancakesProduced += Math.min(demand, MAX_PANCAKES_PER_BATCH);
            totalPancakesConsumed += Math.min(demand, MAX_PANCAKES_PER_USER);
        }
    }
}
