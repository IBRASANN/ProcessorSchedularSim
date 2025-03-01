package simulators;

import clocks.StandardClock;
import processors.Processor;
import processors.StandardThreadedProcessor;
import schedulars.StandardScheduler;
import tasks.StandardTask;
import tasks.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class StandardSimulator extends Simulator {

    public StandardSimulator(int numberOfProcessors, int numberOfSimulationCycles, String taskFilePath){
        if(numberOfProcessors <= 0)
            throw new IllegalArgumentException("error: number of processors must be greater than 0");
        if(numberOfSimulationCycles <= 0)
            throw new IllegalArgumentException("error: number of simulation cycles must be greater than 0");

        this.numberOfProcessors = numberOfProcessors;
        this.numberOfSimulationCycles = numberOfSimulationCycles;
        this.taskFilePath = taskFilePath;
        allTasks = new CopyOnWriteArrayList<>();
        processors = new CopyOnWriteArrayList<>();
        clock = new StandardClock(1);
        scheduler = new StandardScheduler();
    }

    @Override
    public void readTasksFromFile() throws FileNotFoundException {
        List<List<String>> tasks = new ArrayList<>();
        int numOfTasks = 0;
        Scanner scanner = new Scanner(new File(taskFilePath));
        if(scanner.hasNextLine())numOfTasks = Integer.parseInt(scanner.nextLine());
        while (scanner.hasNextLine()) {
            tasks.add(List.of(scanner.nextLine().split(" ")));
        }
        scanner.close();

        for(int i = 0; i < numOfTasks; i++){
            try{
                allTasks.add(new StandardTask(Integer.parseInt(tasks.get(i).get(0)), Integer.parseInt(tasks.get(i).get(1)), Integer.parseInt(tasks.get(i).get(2))));
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        }
        allTasks.sort(Comparator.comparingInt(Task::getCreationTime));
    }

    @Override
    public void updateCreatedTasks(){
        List<Task> tasksToDelete = new ArrayList<>();
        for(Task task : allTasks){
            if(task.getCreationTime() == clock.getCurrentCycle().get()){
                scheduler.addTaskToQueue(task);
                tasksToDelete.add(task);
            } else if(task.getCreationTime() > clock.getCurrentCycle().get()){
                return;
            }
        }
        for(Task task : tasksToDelete)
            allTasks.remove(task);
    }

    @Override
    public void initProcessors(){
        for(int i=0; i < numberOfProcessors; i++){
            processors.add(new StandardThreadedProcessor(clock));
        }
        for(Processor processor : processors){
            processor.start();
        }
    }

    @Override
    public void giveTasksToIdleProcessors(){
        scheduler.printReady();
        for(Processor processor : processors){
            if(processor.isIdle() && scheduler.isTasksReady())
                scheduler.giveTaskToProcessor(processor);
        }
    }

    private void passTime(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void startSimulation(){
        System.out.println("**********starting simulation**********");
        try {
            readTasksFromFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        initProcessors();
        while(clock.getCurrentCycle().get() < numberOfSimulationCycles){
            clock.nextCycle();
            updateCreatedTasks();
            giveTasksToIdleProcessors();
            passTime(600);
        }
        for(Processor processor : processors){
            processor.stopProcessor();
        }
        passTime(100);
        System.out.println("**********end of simulation**********");
    }
}
