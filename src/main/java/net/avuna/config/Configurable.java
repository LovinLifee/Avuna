package net.avuna.config;

public interface Configurable<T extends Config> {
    public T getConfig();
}
