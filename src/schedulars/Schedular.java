package schedulars;

import processors.Processor;
import tasks.Task;
import tasks.TaskComparator;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

public class Schedular {
    Queue<Task> readyTasks;

    public Schedular(){
        readyTasks = new PriorityBlockingQueue<>(1,new TaskComparator());
    }

    public synchronized void addTaskToQueue(Task task){
        readyTasks.add(task);
    }

    public boolean isTasksReady(){
        return !readyTasks.isEmpty();
    }

    public synchronized void printReady(){
        for(Task task : readyTasks){
            System.out.println("ready task: "+task);
        }
    }

    public void giveTaskToProcessor(Processor processor) {
        synchronized (this) {
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
                processor.setTask(task);
                System.out.println("Assigned task " + task + " to processor " + processor);
            }
        }
    }
}
