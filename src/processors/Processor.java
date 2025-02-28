package processors;

import clocks.Clock;
import tasks.Task;

public abstract class Processor extends Thread{
    String id;
    Task task = null;
    Clock clock;
    boolean isRunning;

    public abstract void setTask(Task task);
    public abstract boolean isIdle();
    public abstract void stopProcessor();
}
