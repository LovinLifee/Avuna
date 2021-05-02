package net.avuna.event;

import java.util.Objects;
import java.util.function.Consumer;

import lombok.RequiredArgsConstructor;
import net.avuna.util.Priority;

@RequiredArgsConstructor
public class ConsumerExecutor<T extends AbstractEvent> extends EventExecutor {

 	private final Consumer<T> consumer;
 	private final Priority priority;
 	
	@Override
	public void execute(AbstractEvent event) {
		this.consumer.accept((T) event);
	}

	@Override
	public Priority getPriority() {
		return priority;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj instanceof ConsumerExecutor) {
			ConsumerExecutor other = (ConsumerExecutor) obj;
			return Objects.equals(consumer, other.consumer) && priority == other.priority;
		}
		return false;
	}
}