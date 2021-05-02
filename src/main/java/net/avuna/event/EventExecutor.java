package net.avuna.event;

import net.avuna.Avuna;
import net.avuna.util.Priority;

public abstract class EventExecutor implements Comparable<EventExecutor> {
	
	public abstract void execute(AbstractEvent event);
	public abstract Priority getPriority();
	
	@Override
	public int compareTo(EventExecutor o) {
		return Integer.compare(o.getPriority().ordinal(), getPriority().ordinal());
	}
}
