package net.avuna.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.*;
import java.util.stream.*;

public class CharacterStream implements Stream<Character> {

    private final Stream<Character> stream;

    private CharacterStream(CharSequence sequence) {
        this.stream = sequence.chars().mapToObj(c -> (char) c);
    }

    public static CharacterStream of(CharSequence sequence) {
        return new CharacterStream(sequence);
    }

    @Override
    public Stream<Character> filter(Predicate<? super Character> predicate) {
        return stream.filter(predicate);
    }

    @Override
    public <R> Stream<R> map(Function<? super Character, ? extends R> mapper) {
        return stream.map(mapper);
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super Character> mapper) {
        return stream.mapToInt(mapper);
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super Character> mapper) {
        return stream.mapToLong(mapper);
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super Character> mapper) {
        return stream.mapToDouble(mapper);
    }

    @Override
    public <R> Stream<R> flatMap(Function<? super Character, ? extends Stream<? extends R>> mapper) {
        return stream.flatMap(mapper);
    }

    @Override
    public IntStream flatMapToInt(Function<? super Character, ? extends IntStream> mapper) {
        return stream.flatMapToInt(mapper);
    }

    @Override
    public LongStream flatMapToLong(Function<? super Character, ? extends LongStream> mapper) {
        return stream.flatMapToLong(mapper);
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super Character, ? extends DoubleStream> mapper) {
        return stream.flatMapToDouble(mapper);
    }

    @Override
    public Stream<Character> distinct() {
        return stream.distinct();
    }

    @Override
    public Stream<Character> sorted() {
        return stream.sorted();
    }

    @Override
    public Stream<Character> sorted(Comparator<? super Character> comparator) {
        return stream.sorted(comparator);
    }

    @Override
    public Stream<Character> peek(Consumer<? super Character> action) {
        return stream.peek(action);
    }

    @Override
    public Stream<Character> limit(long maxSize) {
        return stream.limit(maxSize);
    }

    @Override
    public Stream<Character> skip(long n) {
        return stream.skip(n);
    }

    @Override
    public void forEach(Consumer<? super Character> action) {
        stream.forEach(action);
    }

    @Override
    public void forEachOrdered(Consumer<? super Character> action) {
        stream.forEachOrdered(action);
    }

    @Override
    public Character[] toArray() {
        return (Character[]) stream.toArray();
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return stream.toArray(generator);
    }

    @Override
    public Character reduce(Character identity, BinaryOperator<Character> accumulator) {
        return stream.reduce(identity, accumulator);
    }

    @Override
    public Optional<Character> reduce(BinaryOperator<Character> accumulator) {
        return stream.reduce(accumulator);
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super Character, U> accumulator, BinaryOperator<U> combiner) {
        return stream.reduce(identity, accumulator, combiner);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super Character> accumulator, BiConsumer<R, R> combiner) {
        return stream.collect(supplier, accumulator, combiner);
    }

    @Override
    public <R, A> R collect(Collector<? super Character, A, R> collector) {
        return stream.collect(collector);
    }

    @Override
    public Optional<Character> min(Comparator<? super Character> comparator) {
        return stream.min(comparator);
    }

    @Override
    public Optional<Character> max(Comparator<? super Character> comparator) {
        return stream.max(comparator);
    }

    @Override
    public long count() {
        return stream.count();
    }

    @Override
    public boolean anyMatch(Predicate<? super Character> predicate) {
        return stream.anyMatch(predicate);
    }

    @Override
    public boolean allMatch(Predicate<? super Character> predicate) {
        return stream.allMatch(predicate);
    }

    @Override
    public boolean noneMatch(Predicate<? super Character> predicate) {
        return stream.noneMatch(predicate);
    }

    @Override
    public Optional<Character> findFirst() {
        return stream.findFirst();
    }

    @Override
    public Optional<Character> findAny() {
        return stream.findAny();
    }

    @Override
    public Iterator<Character> iterator() {
        return stream.iterator();
    }

    @Override
    public Spliterator<Character> spliterator() {
        return stream.spliterator();
    }

    @Override
    public boolean isParallel() {
        return stream.isParallel();
    }

    @Override
    public Stream<Character> sequential() {
        return stream.sequential();
    }

    @Override
    public Stream<Character> parallel() {
        return stream.parallel();
    }

    @Override
    public Stream<Character> unordered() {
        return stream.unordered();
    }

    @Override
    public Stream<Character> onClose(Runnable closeHandler) {
        return stream.onClose(closeHandler);
    }

    @Override
    public void close() {
        stream.close();
    }

    @Override
    public String toString() {
        return "";
    }
}
