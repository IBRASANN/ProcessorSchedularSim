package clocks;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Clock {
    static AtomicInteger cycle = new AtomicInteger(0);
    int cycleSecondsDuration;
    static final Lock lock = new ReentrantLock();
    static final Condition cycleChanged = lock.newCondition();

    Clock(int cycleSecondsDuration){
        if(cycleSecondsDuration <= 0)
            throw new IllegalArgumentException("error: the clock cycle duration or number of cycles must be greater than zero");
        this.cycleSecondsDuration = cycleSecondsDuration;
    }

    public static AtomicInteger getCurrentCycle(){
        return cycle;
    }

    public void nextCycle() throws InterruptedException {
        lock.lock();
        try {
            Thread.sleep(cycleSecondsDuration * 100L);
            cycle.incrementAndGet();
            cycleChanged.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public static void waitForNextCycle(int lastCycle) {
        lock.lock();
        try {
            while (cycle.get() == lastCycle) {
                cycleChanged.await(); // Wait until the cycle changes
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
}
