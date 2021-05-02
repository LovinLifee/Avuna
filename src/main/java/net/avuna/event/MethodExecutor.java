package net.avuna.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.avuna.util.Priority;

@RequiredArgsConstructor
public class MethodExecutor extends EventExecutor {

	@Getter
	private final Object listener;
	private final Method method;

	public void execute(AbstractEvent event) {
		try {
			method.invoke(listener, event);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Priority getPriority() {
		return method.getAnnotation(Event.class).priority();
	}

	@Override
	public int hashCode() {
		return Objects.hash(listener, method);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj instanceof MethodExecutor) {
			MethodExecutor other = (MethodExecutor) obj;
			return Objects.equals(listener, other.listener) && Objects.equals(method, other.method);
		}
		return false;
	}
}