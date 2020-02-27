
import java.util.concurrent.Semaphore;

// Just a class to store variables that can be globally accessed
// by any classes
public class Globals {
    
    // Variables to indicate which material is produced by an agent
    public static boolean isEnvelope;
    public static boolean isFlyer;
    public static boolean isStamp;
    
    // Semaphore to control access of materials
    public static Semaphore envelope;
    public static Semaphore flyer;
    public static Semaphore stamp;    
    
    // Some binary semaphore to control the wait of threads
    // and mutual exclusion
    public static Semaphore agentSem;
    public static Semaphore envelopeSem;
    public static Semaphore flyerSem;
    public static Semaphore stampSem;
    public static Semaphore mutex;
    
    public static int volunteersLeft = 0;
}
