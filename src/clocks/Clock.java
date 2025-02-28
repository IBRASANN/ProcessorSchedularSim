package clocks;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Clock {
    AtomicInteger cycle = new AtomicInteger(0);
    int cycleSecondsDuration;

    public abstract AtomicInteger getCurrentCycle();
    public abstract void nextCycle();
    public abstract void waitForNextCycle(int lastCycle);
}
