package tasks;

public class Task {
    static int idCounter;
    int id;
    int creationTime;
    int remainingDuration;
    int priority;

    static {
        idCounter = 0;
    }

    public Task(int creationTime, int duration, int priority){
        if(priority != 0 && priority != 1)
            throw new IllegalArgumentException("error: priority must be either 0 or 1");

        id = idCounter++;
        this.creationTime = creationTime;
        this.remainingDuration = duration;
        this.priority = priority;
    }

    public void perform(){
        System.out.println("performing task:" + this);
        decrementDuration();
    }

    public void decrementDuration(){
        if(remainingDuration > 0) --remainingDuration;
    }

    public int getRemainingDuration(){
        return remainingDuration;
    }


    @Override
    public String toString(){
        return "{id: " + this.id + ", remaining_duration: " + remainingDuration + "}";
    }
}
