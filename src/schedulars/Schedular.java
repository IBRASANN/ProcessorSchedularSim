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
        readyTasks = new PriorityBlockingQueue<>(11,new TaskComparator());
    }

    public synchronized void addTaskToQueue(Task task){
        readyTasks.add(task);
    }

    public boolean isTasksReady(){
        return !readyTasks.isEmpty();
    }

    public synchronized void giveTaskToProcessor(Processor processor){
        if(!processor.isIdle()){
            System.out.println("processor is not idle, can't give task to it :(");
            return;
        }
        if(!isTasksReady()){
            System.out.println("no ready tasks available :(");
            return;
        }
        processor.setTask(readyTasks.remove());
    }
}
