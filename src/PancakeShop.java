
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PancakeShop {

    private static final int MAX_PANCAKES_PER_BATCH = 12;
    private static final int MAX_PANCAKES_PER_USER = 5;
    private static final int NUM_USERS = 3;

    private int totalPancakesProduced = 0;
    private int totalPancakesConsumed = 0;

    public static void main(String[] args) {
        PancakeShop shop = new PancakeShop();
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                shop.startNew30SecondSlot();
            }
        }, 0, 30000);
    }

    public void startNew30SecondSlot() {
        Random random = new Random();
        int[] userDemands = new int[NUM_USERS];
        int totalDemands = 0;

        for (int i = 0; i < NUM_USERS; i++) {
            userDemands[i] = random.nextInt(MAX_PANCAKES_PER_USER + 1);
            totalDemands += userDemands[i];
        }

        int produced = Math.min(totalDemands, MAX_PANCAKES_PER_BATCH);
        totalPancakesProduced += produced;

        int wasted = totalDemands - produced;
        totalPancakesConsumed += Math.min(totalDemands, NUM_USERS * MAX_PANCAKES_PER_USER);

        System.out.println("Starting Time: " + System.currentTimeMillis());
        System.out.println("Ending Time: " + (System.currentTimeMillis() + 30000));
        System.out.println("Shopkeeper met the needs of all users: " + (produced >= totalDemands));
        System.out.println("Pancakes wasted: " + wasted);
        System.out.println("Unmet user orders: " + (totalDemands - produced));
        System.out.println("Total pancakes produced: " + totalPancakesProduced);
        System.out.println("Total pancakes consumed: " + totalPancakesConsumed);
        System.out.println();
    }
}