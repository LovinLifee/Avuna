package net.avuna.util;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

public class FrequencyMap<T> {

    private final Map<T, AtomicInteger> backingMap = new HashMap<>();

    public int increment(T key) {
        AtomicInteger count = backingMap.computeIfAbsent(key, k -> new AtomicInteger());
        return count.incrementAndGet();
    }

    public int getCount(T key) {
        return backingMap.get(key).get();
    }

    public int getTotalCount() {
        return backingMap.entrySet().stream().mapToInt(e -> e.getValue().get()).sum();
    }

    public Entry<T, Integer> getMin() {
        Entry<T, AtomicInteger> entry = backingMap.entrySet().stream().min(Comparator.comparingInt(o -> o.getValue().get())).orElseThrow(NoSuchElementException::new);
        return Tuple.of(entry.getKey(), entry.getValue().get());
    }

    public Entry<T, Integer> getMax() {
        Entry<T, AtomicInteger> entry = backingMap.entrySet().stream().max(Comparator.comparingInt(o -> o.getValue().get())).orElseThrow(NoSuchElementException::new);
        return Tuple.of(entry.getKey(), entry.getValue().get());
    }

    public double getAverageCount() {
        return backingMap.entrySet().stream().mapToInt(e -> e.getValue().get()).average().orElseThrow(NoSuchElementException::new);
    }

    public void forEach(BiConsumer<T, Integer> action) {
        backingMap.entrySet().iterator().forEachRemaining(e -> action.accept(e.getKey(), e.getValue().get()));
    }
}
