
import java.util.concurrent.Semaphore;

// This is a volunteer that has infinite number
// of 1 material but needs 2 other material in order to make
// a mail.
public class Volunteer extends Thread {

    private Semaphore waitSemaphore;
    private String name;

    // Create the volunteer 
    public Volunteer(String name, Semaphore waitSemaphore) {
        this.waitSemaphore = waitSemaphore;
        this.name = name;

        Globals.volunteersLeft++;
    }

    // Run the the volunteer
    @Override
    public void run() {
        try {
            for (int i = 0; i < 3; i++) {
                waitSemaphore.acquire();

                // Put together a mailer
                System.out.println(System.currentTimeMillis() + ": Volunteer " + name + " put together a mailer.");
                Thread.sleep((int) (Math.random() * 50));
                Globals.agentSem.release();

                // Take the mailer to the mailbox
                System.out.println(System.currentTimeMillis() + ": Volunteer " + name + " took the mailer to the mailbox.");
                Thread.sleep((int) (Math.random() * 50));
            }

            System.out.println(System.currentTimeMillis() + ": Volunteer " + name + " has signed off.");
            Globals.volunteersLeft--;
            Globals.agentSem.release(3);
            
            if (Globals.volunteersLeft == 0) {
                Globals.flyer.release(12);
                Globals.stamp.release(12);
                Globals.envelope.release(12);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
