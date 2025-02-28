package processors;

import clocks.Clock;
import tasks.Task;

public class StandardThreadedProcessor extends Processor {
    static int idCounter = 1;

    public StandardThreadedProcessor(Clock clock){
        if(clock == null) throw new IllegalArgumentException("error: undefined clock was passed");
        id = "P"+idCounter++;
        this.clock = clock;
        isRunning = false;
    }

    @Override
    public void setTask(Task task){
        if(task == null) throw new IllegalArgumentException("error: undefined task was passed");
        this.task = task;
    }

    @Override
    public boolean isIdle(){
        return task == null;
    }

    @Override
    public void stopProcessor(){
        isRunning = false;
    }

    @Override
    public void run(){
        isRunning = true;
        int lastCycle;
        boolean wasIdle;
        while(isRunning){
            lastCycle = clock.getCurrentCycle().get();
            wasIdle = isIdle();
            if(!isIdle()){
                task.perform(this.toString());
                if(task.getRemainingDuration() <= 0)task = null;
            }
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(wasIdle && !isIdle()){
                task.perform(this.toString());
                if(task.getRemainingDuration() <= 0)task = null;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(!isRunning) break;
            clock.waitForNextCycle(lastCycle);
        }
    }

    @Override
    public String toString(){
        return "{id: "+id+", isIdle: "+isIdle()+"}";
    }

}
