
import java.util.concurrent.atomic.AtomicInteger;

public class UniqueIdGenerator {
	static AtomicInteger atomicInteger = new AtomicInteger();
	static AtomicInteger atomicInteger2 = new AtomicInteger();
	static AtomicInteger atomicInteger3 = new AtomicInteger();

    public static int getUniqueID() {
        return atomicInteger.incrementAndGet();
    }
    
    public static int getUniqueID2() {
        return atomicInteger2.incrementAndGet();
    }
    
    public static int getUniqueID3() {
        return atomicInteger3.incrementAndGet();
    }
    public static void resetID() {
    	atomicInteger.set(0);
    }
    
    public static void resetID2() {
    	atomicInteger2.set(0);
    }
    
    public static void resetID3() {
    	atomicInteger3.set(0);
    }
    
  
}
