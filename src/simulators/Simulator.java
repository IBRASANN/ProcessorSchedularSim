package simulators;

import clocks.Clock;
import processors.Processor;
import schedulars.Schedular;
import tasks.Task;

import java.io.File;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class Simulator {
    List<Task> allTasks;
    List<Processor> processors;
    Clock clock;
    Schedular schedular;
    int numberOfProcessors;
    int numberOfSimulationCycles;

    public Simulator(int numberOfProcessors, int numberOfSimulationCycles){
        if(numberOfProcessors <= 0)
            throw new IllegalArgumentException("error: number of processors must be greater than 0");
        if(numberOfSimulationCycles <= 0)
            throw new IllegalArgumentException("error: number of simulation cycles must be greater than 0");

        this.numberOfProcessors = numberOfProcessors;
        this.numberOfSimulationCycles = numberOfSimulationCycles;
        allTasks = new CopyOnWriteArrayList<>();
        processors = new CopyOnWriteArrayList<>();
        clock = new Clock(1);
        schedular = new Schedular();
    }

    public void readTasksFromFile() throws FileNotFoundException {
        List<List<String>> tasks = new ArrayList<>();
        int numOfTasks = 0;
        Scanner scanner = new Scanner(new File("src/example1.txt"));
        if(scanner.hasNextLine())numOfTasks = Integer.parseInt(scanner.nextLine());
        while (scanner.hasNextLine()) {
            tasks.add(List.of(scanner.nextLine().split(" ")));
        }
        scanner.close();

        for(int i = 0; i < numOfTasks; i++){
            try{
                allTasks.add(new Task(Integer.parseInt(tasks.get(i).get(0)), Integer.parseInt(tasks.get(i).get(1)), Integer.parseInt(tasks.get(i).get(2))));
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        }
        allTasks.sort(Comparator.comparingInt(Task::getCreationTime));
    }

    public void updateCreatedTasks(){
        List<Task> tasksToDelete = new ArrayList<>();
        for(Task task : allTasks){
            if(task.getCreationTime() == Clock.getCurrentCycle().get()){
                schedular.addTaskToQueue(task);
                tasksToDelete.add(task);
            } else if(task.getCreationTime() > Clock.getCurrentCycle().get()){
                return;
            }
        }
        for(Task task : tasksToDelete)
            allTasks.remove(task);
    }

    public void initProcessors(){
        for(int i=0; i < numberOfProcessors; i++){
            processors.add(new Processor());
        }
    }

    public void giveTasksToIdleProcessors(){
        for(Processor processor : processors){
            if(processor.isIdle() && schedular.isTasksReady())
                schedular.giveTaskToProcessor(processor);
        }
    }

    public void startSimulation(){
        try {
            readTasksFromFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        updateCreatedTasks();
        initProcessors();
        giveTasksToIdleProcessors();
        System.out.println("**********cycle number " + Clock.getCurrentCycle()+"**********");
        for(Processor processor : processors){
            processor.start();
        }
        for(int cycle = 1; cycle<numberOfSimulationCycles; cycle++){
            updateCreatedTasks();
            giveTasksToIdleProcessors();
            try {
                clock.nextCycle();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            updateCreatedTasks();
            giveTasksToIdleProcessors();
        }
    }
}
