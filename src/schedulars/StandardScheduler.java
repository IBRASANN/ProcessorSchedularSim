package schedulars;

import processors.Processor;
import tasks.Task;
import tasks.TaskComparator;

import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

public class StandardScheduler implements Scheduler {
    Queue<Task> readyTasks;

    public StandardScheduler(){
        readyTasks = new PriorityBlockingQueue<>(1,new TaskComparator());
    }

    @Override
    public void addTaskToQueue(Task task){
        readyTasks.add(task);
    }

    @Override
    public boolean isTasksReady(){
        return !readyTasks.isEmpty();
    }

    @Override
    public void printReady(){
        for(Task task : readyTasks){
            System.out.println("ready task: "+task);
        }
    }

    @Override
    public synchronized void giveTaskToProcessor(Processor processor) {
        if (!processor.isIdle()) {
            System.out.println("processor is not idle, can't give task to it :(");
            return;
        }
        if (!isTasksReady()) {
            System.out.println("no ready tasks available :(");
            return;
        }
        Task task = readyTasks.poll();
        if (task != null) {
            System.out.println("Assigned task " + task + " to processor " + processor);
            processor.setTask(task);
        }

    }
}
