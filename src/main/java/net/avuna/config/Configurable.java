package net.avuna.config;

public interface Configurable<T extends Config> {
    T getConfig();
}
