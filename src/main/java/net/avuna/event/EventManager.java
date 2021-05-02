package net.avuna.event;

import lombok.Getter;
import net.avuna.util.Priority;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;

@Getter
public class EventManager {

	private final Map<Class<? extends AbstractEvent>, List<EventExecutor>> listeners = new HashMap<>();

	public void registerEventListener(Object listener) {
		for(Method method : listener.getClass().getMethods()) {
			if(!method.isAnnotationPresent(Event.class)) {
				continue;
			}
			if(!method.isAccessible()) {
				throw new RuntimeException("The event method must be public");
			}
			if(method.getParameterTypes().length != 1 || !AbstractEvent.class.isAssignableFrom(method.getParameterTypes()[0])) {
				throw new RuntimeException("The event method must have a parameter that is assignable from AbstractEvent");
			}
			Class<? extends AbstractEvent> eventClass = (Class<? extends AbstractEvent>) method.getParameterTypes()[0];
			MethodExecutor executor = new MethodExecutor(listener, method);
			registerEventListener(eventClass, executor);
		}
	}

	public <T extends AbstractEvent> void registerEventListener(Class<T> eventClass, Consumer<T> consumer) {
		registerEventListener(eventClass, consumer, Priority.NORMAL);
	}

	public <T extends AbstractEvent> void registerEventListener(Class<T> eventClass, Consumer<T> consumer, Priority priority) {
		ConsumerExecutor<T> executor = new ConsumerExecutor<>(consumer, priority);
		registerEventListener(eventClass, executor);
	}
	
	public void registerEventListener(Class<? extends AbstractEvent> eventClass, EventExecutor executor) {
		List<EventExecutor> eventListeners = listeners.computeIfAbsent(eventClass, k -> new ArrayList<>());
		eventListeners.add(executor);
		Collections.sort(eventListeners);
	}
	
	public void unregisterEventListener(EventExecutor executor) {
		for(List<EventExecutor> listeners : listeners.values()) {
			listeners.remove(executor);
		}
	}

	public void submit(AbstractEvent event) {
		final List<EventExecutor> executors = listeners.get(event.getClass());
		if(executors == null || executors.isEmpty()) {
			return;
		}
		for(EventExecutor executor : executors) {
			if(event.isCancelled()) {
				continue;
			}
			executor.execute(event);
			if(event.isConsumed()) {
				break;
			}
		}
	}
}