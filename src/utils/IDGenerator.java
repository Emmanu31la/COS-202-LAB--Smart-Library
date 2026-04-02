package utils;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Utility class to generate unique IDs across the system.
 */
public class IDGenerator {
    private static final AtomicInteger itemCounter = new AtomicInteger(1000);
    private static final AtomicInteger userCounter = new AtomicInteger(500);

    public static String generateItemID() {
        return "ITEM-" + itemCounter.incrementAndGet();
    }

    public static String generateUserID() {
        return "USER-" + userCounter.incrementAndGet();
    }

    public static void reset() {
        itemCounter.set(1000);
        userCounter.set(500);
    }
}