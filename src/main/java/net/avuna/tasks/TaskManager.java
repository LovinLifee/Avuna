package net.avuna.tasks;

import lombok.Getter;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskManager implements Runnable {

    @Getter
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private final List<Task> tasks = new ArrayList<>();
    private final Queue<Task> newTasks = new ArrayDeque<>();

    public TaskManager() {
        executorService.scheduleAtFixedRate(this, 0, 600, TimeUnit.MILLISECONDS);
    }

    public Task schedule(final Task task) {
        if(task.isExecutedImmediately()) {
            executorService.execute(task::execute);
        }
        synchronized(newTasks) {
            newTasks.add(task);
        }
        return task;
    }

    @Override
    public void run() {
        synchronized (newTasks) {
            Task task;
            while ((task = newTasks.poll()) != null) {
                tasks.add(task);
            }
        }
        for (Iterator<Task> it = tasks.iterator(); it.hasNext();) {
            Task task = it.next();
            if(task.isCancelled()) {
                it.remove();
            } else {
                task.tick();
            }
        }
    }
}
