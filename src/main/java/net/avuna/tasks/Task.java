package net.avuna.tasks;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@Getter
@RequiredArgsConstructor
public abstract class Task {

    private final boolean executedImmediately;
    private boolean cancelled = false;
    private int ticksEllapsed = 0;

    public abstract void execute();

    public void tick() {
        if(!isCancelled()) {
            execute();
            ticksEllapsed++;
        }
    }

    public void stop() {
        this.cancelled = true;
    }
}
