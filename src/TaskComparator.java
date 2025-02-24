import java.util.Comparator;

public class TaskComparator implements Comparator<Task> {

    @Override
    public int compare(Task t1, Task t2) {
        if (t1.priority != t2.priority) {
            return Integer.compare(t2.priority, t1.priority);
        }
        // If priorities are the same, compare by creation time (earlier first)
        if (t1.creationTime != t2.creationTime) {
            return Integer.compare(t1.creationTime, t2.creationTime);
        }
        // If creation times are the same, compare by duration (longer first)
        return Integer.compare(t2.duration, t1.duration);
    }
}
