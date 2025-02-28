package processors;

import clocks.Clock;
import tasks.Task;

public class Processor extends Thread{
    static int idCounter = 0;
    int id;
    volatile Task task = null;

    public Processor(){
        id = idCounter++;
    }


    public void setTask(Task task){
        if(task == null) throw new IllegalArgumentException("error: undefined task was passed");
        this.task = task;
    }

    public boolean isIdle(){
        return task == null;
    }

    @Override
    public void run(){


        while(true){
            int lastCycle = Clock.getCurrentCycle().get();
            if(!isIdle()){
                task.perform(this);
                if(task.getRemainingDuration() <= 0)task = null;
            }
            Clock.waitForNextCycle(lastCycle);
        }
    }

    @Override
    public String toString(){
        return "{id: p"+id+", isIdle: "+isIdle()+"}";
    }

}
