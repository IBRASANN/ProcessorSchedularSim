import java.util.concurrent.atomic.AtomicInteger;

public class Clock extends Thread {
    static AtomicInteger cycle;
    int cycleDuration;
    int numberOfCycles;

    static {
        cycle = new AtomicInteger(0);
    }

    Clock(int cycleDuration,int numberOfCycles){
        if(cycleDuration <= 0 || numberOfCycles <=0)
            throw new IllegalArgumentException("error: the clock cycle duration or number of cycles must be greater than zero");
        this.cycleDuration = cycleDuration;
        this.numberOfCycles = numberOfCycles;
    }

    public AtomicInteger getCurrentCycle(){
        return cycle;
    }

    public int getNumberOfCycles(){
        return numberOfCycles;
    }

    @Override
    public void run() {
        try {
            while(cycle.get() < numberOfCycles){
                System.out.println("**********Starting cycle "+ cycle.get()+"**********");
                Thread.sleep(cycleDuration* 100L);
                cycle.incrementAndGet();
                System.out.println("**********Ending the cycle**********");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
