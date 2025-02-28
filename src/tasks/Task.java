package tasks;

public abstract class Task {
    String id;
    int creationTime;
    int remainingDuration;
    int priority;

    public abstract void perform(String processor);
    public abstract void decrementDuration();
    public abstract int getRemainingDuration();
    public abstract int getCreationTime();

    @Override
    public String toString(){
        return "{id: " + this.id + ", remaining_duration: " + remainingDuration + "}";
    }
}
