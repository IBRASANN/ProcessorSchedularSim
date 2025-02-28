package schedulars;

import processors.Processor;
import tasks.Task;

public interface Scheduler {
    public  void addTaskToQueue(Task task);
    public boolean isTasksReady();
    public  void printReady();
    public void giveTaskToProcessor(Processor processor);
}
