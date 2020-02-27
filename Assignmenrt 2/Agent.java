
public class Agent extends Thread {

    private String name;

    // Create the agent that releases 2 types of material
    public Agent(String name) {
        this.name = name;
    }

    // Start the agent
    @Override
    public void run() {
        try {
            for (; Globals.volunteersLeft > 0;) {
                Thread.sleep((int) (Math.random() * 200));

                Globals.agentSem.acquire();

                switch ((int) (Math.random() * 3)) {
                    case 0:
                        System.out.println(System.currentTimeMillis() + ": Agent " + name + " placed envelope and flyer on the table.");
                        Globals.envelope.release();
                        Globals.flyer.release();
                        break;
                    case 1:
                        System.out.println(System.currentTimeMillis() + ": Agent " + name + " placed flyer and stamp on the table.");
                        Globals.flyer.release();
                        Globals.stamp.release();
                        break;
                    case 2:
                        System.out.println(System.currentTimeMillis() + ": Agent " + name + " placed stamp and envelope on the table.");
                        Globals.stamp.release();
                        Globals.envelope.release();
                        break;
                }
            }

            System.out.println(System.currentTimeMillis() + ": Agent " + name + " has signed off.");           
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
