
package part2;
public class Pid_Manager_Thread implements Runnable {
    private int pidThreadID;            //  int that represents a unique ID for each thread
    private int pidThreadSleepTime;     //  int that established how long thread will sleep
    private Pid_Manager pidManager;     //  Pid_Manager that will facilitate allocation and release of pids

    //  Thread constructor method
    //  Takes int for pidThreadID, int for pidThreadSleepTime, and Pid_Manager for pidManager as parameters
    Pid_Manager_Thread(int id, int time, Pid_Manager manager) {
        this.pidThreadID = id;
        this.pidThreadSleepTime = time;
        this.pidManager = manager;

        System.out.println("Creating Thread " + this.pidThreadID);
    }

    //  Required method for implementation of thread 
    //  Allows thread to run and perform task
    @Override
    public void run(){

        System.out.println("Running Thread " + this.pidThreadID);

        //  Pid is allocated from pidManager
        Integer pidID = pidManager.allocate_pid();

        //  If Pid was not successfully allocated, loop until it is.
        while (pidID == -1) {
            System.out.println("All pids currently in use. Attempting to reallocate...");
            pidID = pidManager.allocate_pid();
        }

        System.out.println("My PID is : " + pidID);
        SleepUtilities.nap(pidThreadSleepTime);

        //  Release Pid from thread use
        pidManager.release_pid(pidID);
        System.out.println("Stopping Thread " + this.pidThreadID);
    }
}