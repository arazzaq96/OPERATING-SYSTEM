
import java.util.concurrent.Semaphore;

// A Thread that waits for a particular
// material to be placed on a table and signal
// the appropriate volunteer to wake up and use the material
public class Pusher extends Thread {

    private Semaphore materialToWait;

    // Create the pusher
    public Pusher(Semaphore materialToWait) {
        this.materialToWait = materialToWait;
    }

    // Start the pusher
    @Override
    public void run() {
        try {
            for (; Globals.volunteersLeft > 0;) {
                materialToWait.acquire();
                Globals.mutex.acquire();

                if (materialToWait == Globals.envelope) {
                    // For a pusher who woke up because an envelope
                    // was placed on the table
                    if (Globals.isFlyer) {
                        Globals.isFlyer = false;
                        Globals.stampSem.release();
                    } else if (Globals.isStamp) {
                        Globals.isStamp = false;
                        Globals.flyerSem.release();
                    } else {
                        Globals.isEnvelope = true;
                    }
                } else if (materialToWait == Globals.flyer) {
                    // For a pusher who woke up because a flyer
                    // was placed on the table
                    if (Globals.isEnvelope) {
                        Globals.isEnvelope = false;
                        Globals.stampSem.release();
                    } else if (Globals.isStamp) {
                        Globals.isStamp = false;
                        Globals.envelopeSem.release();
                    } else {
                        Globals.isFlyer = true;
                    }
                } else if (materialToWait == Globals.stamp) {
                    // For a pusher who woke up because a stamp
                    // was placed on the table
                    if (Globals.isEnvelope) {
                        Globals.isEnvelope = false;
                        Globals.flyerSem.release();
                    } else if (Globals.isFlyer) {
                        Globals.isFlyer = false;
                        Globals.envelopeSem.release();
                    } else {
                        Globals.isStamp = true;
                    }
                }

                Globals.mutex.release();
            }

            System.out.println(System.currentTimeMillis() + ": Pusher signed off");
            
            // Don't make the agent hanging
            Globals.agentSem.release(3);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
