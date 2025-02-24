public class Task {
    static int idCounter;
    int id;
    int creationTime;
    int duration;
    int priority;

    static {
        idCounter = 0;
    }

    Task(int creationTime, int duration, int priority){
        if(priority != 0 && priority != 1)
            throw new IllegalArgumentException("error: priority must be either 0 or 1");

        id = idCounter++;
        this.creationTime = creationTime;
        this.duration = duration;
        this.priority = priority;
    }

    public void perform(){
        System.out.println("performing task number: " + id);
    }

    @Override
    public String toString(){
        return "";
    }
}
