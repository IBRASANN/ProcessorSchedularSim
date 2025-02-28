package tasks;

public class StandardTask extends Task {
    static int idCounter = 1;

    public StandardTask(int creationTime, int duration, int priority){
        if(priority != 0 && priority != 1)
            throw new IllegalArgumentException("error: priority must be either 0 or 1");

        id = "T"+idCounter++;
        this.creationTime = creationTime;
        this.remainingDuration = duration;
        this.priority = priority;
    }

    @Override
    public void perform(String processor){
        decrementDuration();
        System.out.println("processor:" + processor + " performing task:" + this);
    }

    @Override
    public void decrementDuration(){
        if(remainingDuration > 0) --remainingDuration;
    }

    @Override
    public int getRemainingDuration(){
        return remainingDuration;
    }

    @Override
    public int getCreationTime(){
        return creationTime;
    }

}
