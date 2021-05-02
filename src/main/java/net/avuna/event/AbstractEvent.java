package net.avuna.event;

import lombok.Getter;
import lombok.Setter;
import net.avuna.util.Priority;

@Getter
@Setter
public abstract class AbstractEvent {

    private boolean cancelled;
    private boolean consumed;

    public Priority getPriority() {
        return Priority.NORMAL;
    }
}
