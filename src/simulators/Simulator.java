package simulators;

import clocks.Clock;
import processors.Processor;
import schedulars.Scheduler;
import tasks.Task;

import java.io.FileNotFoundException;
import java.util.List;

public abstract class Simulator {
    protected List<Task> allTasks;
    protected List<Processor> processors;
    protected Clock clock;
    protected Scheduler scheduler;
    protected int numberOfProcessors;
    protected int numberOfSimulationCycles;
    protected String taskFilePath;

    public abstract void readTasksFromFile() throws FileNotFoundException;
    public abstract void updateCreatedTasks();
    public abstract void initProcessors();
    public abstract void giveTasksToIdleProcessors();
    public abstract void startSimulation();
}
