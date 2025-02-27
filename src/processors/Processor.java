package processors;

import clocks.Clock;
import tasks.Task;

public class Processor extends Thread{
    static int idCounter = 0;
    int id;
    Task task = null;

    Processor(){
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
        int lastCycle = Clock.getCurrentCycle().get();
        Clock.waitForNextCycle(lastCycle);

        while(true){
            if(!isIdle()){
                task.perform();
                if(task.getRemainingDuration() <= 0)task = null;
            }
        }
    }

    @Override
    public String toString(){
        return "{id: p"+id+", isIdle: "+isIdle()+"}";
    }

}
