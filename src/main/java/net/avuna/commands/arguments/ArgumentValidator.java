package net.avuna.commands.arguments;

public interface ArgumentValidator<T> {
    public boolean validateType(String arg);
    public T get(String arg);
}
