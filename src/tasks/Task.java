package tasks;

import processors.Processor;

public class Task {
    static int idCounter = 1;
    String id;
    int creationTime;
    int remainingDuration;
    int priority;

    public Task(int creationTime, int duration, int priority){
        if(priority != 0 && priority != 1)
            throw new IllegalArgumentException("error: priority must be either 0 or 1");

        id = "T"+idCounter++;
        this.creationTime = creationTime;
        this.remainingDuration = duration;
        this.priority = priority;
    }

    public void perform(Processor processor){
        decrementDuration();
        System.out.println(processor + " performing task:" + this);
    }

    public void decrementDuration(){
        if(remainingDuration > 0) --remainingDuration;
    }

    public int getRemainingDuration(){
        return remainingDuration;
    }

    public int getCreationTime(){
        return creationTime;
    }


    @Override
    public String toString(){
        return "{id: " + this.id + ", remaining_duration: " + remainingDuration + "}";
    }
}
