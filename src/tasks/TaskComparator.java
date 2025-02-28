package tasks;

import java.util.Comparator;

public class TaskComparator implements Comparator<Task> {

    @Override
    public int compare(Task t1, Task t2) {
        if (t1.priority != t2.priority) {
            return Integer.compare(t2.priority, t1.priority);
        }

        // If creation times are the same, compare by duration (longer first)
        if (t2.remainingDuration != t1.remainingDuration) {
            return Integer.compare(t2.remainingDuration, t1.remainingDuration);
        }

        // If priorities are the same, compare by creation time (earlier first)
        return Integer.compare(t1.creationTime, t2.creationTime);

    }
}
