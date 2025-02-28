package simulators;

import clocks.Clock;
import processors.Processor;
import schedulars.Scheduler;
import tasks.Task;

import java.io.FileNotFoundException;
import java.util.List;

public abstract class Simulator {
    List<Task> allTasks;
    List<Processor> processors;
    Clock clock;
    Scheduler scheduler;
    int numberOfProcessors;
    int numberOfSimulationCycles;
    String taskFilePath;

    public abstract void readTasksFromFile() throws FileNotFoundException;
    public abstract void updateCreatedTasks();
    public abstract void initProcessors();
    public abstract void giveTasksToIdleProcessors();
    public abstract void startSimulation();
}
