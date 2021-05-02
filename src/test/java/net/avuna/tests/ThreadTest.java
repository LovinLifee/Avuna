package net.avuna.tests;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadTest {

    private static final int THREADS = Runtime.getRuntime().availableProcessors();
    private static int offset = 0;

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(THREADS);
        while(true) {
            List<Task> tasks = new ArrayList<>();
            for(int i = 0; i < THREADS; i++) {
                tasks.add(new Task(i + offset));
                offset++;
            }
            try {
                service.invokeAll(tasks, 10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Stopped all threads after awaiting 10 seconds.");
            System.out.printf("Resubmitting %d threads\n", THREADS);
        }
    }

    @RequiredArgsConstructor
    private static class Task implements Callable<Void> {

        private final int id;

        @Override
        public Void call() throws Exception {
            System.out.printf("Running thread %d and waiting 1000 seconds\n", id);
            try {
                Thread.sleep(1000 * 1000);
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
            return null;
        }
    }
}
