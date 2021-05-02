package net.avuna.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public class Tuple<K, V> implements Map.Entry<K, V> {

    private final K key;
    private final V value;

    @Override
    public V setValue(V value) {
        throw new UnsupportedOperationException("Tuple is immutable");
    }

    public static <K, V> Tuple<K, V> of(K key, V value) {
        return new Tuple<>(key, value);
    }
}
