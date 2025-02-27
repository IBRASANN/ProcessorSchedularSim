package schedulars;

import processors.Processor;
import tasks.Task;
import tasks.TaskComparator;

import java.util.PriorityQueue;
import java.util.Queue;

public class Schedular {
    Queue<Task> readyTasks;

    Schedular(){
        readyTasks = new PriorityQueue<>(new TaskComparator());
    }

    public synchronized void addTaskToQueue(Task task){
        readyTasks.add(task);
    }

    public synchronized void giveTaskToProcessor(Processor processor){
        if(!processor.isIdle()){
            System.out.println("processor is not idle, can't give task to it :(");
            return;
        }
        if(readyTasks.isEmpty()){
            System.out.println("no ready tasks available :(");
            return;
        }
        processor.setTask(readyTasks.remove());
    }
}
