
package part3;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Random;

public class Pid_Manager_Multithread {
    public static void main(String[] args) {
        //  Create a Pid_Manager to pass into Pid_Manager_Thread constructor
        Pid_Manager pidManagerMain = new Pid_Manager();

        //  Allocate the map for the Pid_Manager
        pidManagerMain.allocate_map();

        //  Create a random number generation to determine the duration of sleep for a thread
        Random randomSleepTime = new Random();

        //  Create a ExecutorService pool to manage 100 threads  
        ExecutorService pidPool = Executors.newFixedThreadPool(100);

        //  Loop through pool, create a thread by passing a unique id, a random duration of sleep between 1000 and 10000 milliseconds, and a Pid_Manager 
        //  Execute each thread in the pool
        for (int i = 0; i < 100; i++) {
            Pid_Manager_Thread pidThread = new Pid_Manager_Thread(i, randomSleepTime.nextInt(60) + 300, pidManagerMain);
            pidPool.execute(pidThread);
        }
        
        //  Shut the ExecutorService down after executing 100 threads
        pidPool.shutdown();
    }
}