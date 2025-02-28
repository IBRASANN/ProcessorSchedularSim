package clocks;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StandardClock extends Clock {
    final Lock lock = new ReentrantLock();
    final Condition cycleChanged = lock.newCondition();

    public StandardClock(int cycleSecondsDuration){
        if(cycleSecondsDuration <= 0)
            throw new IllegalArgumentException("error: the clock cycle duration or number of cycles must be greater than zero");
        this.cycleSecondsDuration = cycleSecondsDuration;
    }

    @Override
    public AtomicInteger getCurrentCycle(){
        return cycle;
    }

    @Override
    public void nextCycle(){
        lock.lock();
        try {
            Thread.sleep(cycleSecondsDuration * 1000L);
            cycle.incrementAndGet();
            System.out.println("**********cycle " + cycle.get() +"**********");
            cycleChanged.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void waitForNextCycle(int lastCycle) {
        lock.lock();
        try {
            while (cycle.get() == lastCycle) {
                cycleChanged.await();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
}
