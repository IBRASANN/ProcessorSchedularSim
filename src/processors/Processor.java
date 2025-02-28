package processors;

import clocks.Clock;
import tasks.Task;

public abstract class Processor extends Thread{
    protected String id;
    protected Task task = null;
    protected Clock clock;
    protected boolean isRunning;

    public abstract void setTask(Task task);
    public abstract boolean isIdle();
    public abstract void stopProcessor();
}
