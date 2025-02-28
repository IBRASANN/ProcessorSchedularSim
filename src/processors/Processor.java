package processors;

import clocks.Clock;
import tasks.Task;

public class Processor extends Thread{
    static int idCounter = 1;
    String id;
    volatile Task task = null;

    public Processor(){
        id = "P"+idCounter++;
    }


    public void setTask(Task task){
        if(task == null) throw new IllegalArgumentException("error: undefined task was passed");
        this.task = task;
    }

    public boolean isIdle(){
        return task == null;
    }

    public void passTime(int n){
        for(int i=0;i<n;i++){
            //do nothing
        }
    }

    @Override
    public void run(){
        int lastCycle;
        boolean wasIdle;
        while(true){
            lastCycle = Clock.getCurrentCycle().get();
            wasIdle = isIdle();
            if(!isIdle()){
                task.perform(this);
                if(task.getRemainingDuration() <= 0)task = null;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(wasIdle && !isIdle()){
                task.perform(this);
                if(task.getRemainingDuration() <= 0)task = null;
            }
            Clock.waitForNextCycle(lastCycle);
        }
    }

    @Override
    public String toString(){
        return "{id: "+id+", isIdle: "+isIdle()+"}";
    }

}
