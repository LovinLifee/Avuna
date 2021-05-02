package net.avuna.event.impl.entity.player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import net.avuna.event.AbstractEvent;
import net.avuna.game.entity.player.IPlayer;

@Getter
@RequiredArgsConstructor
public class PlayerLogoutEvent<T extends IPlayer> extends AbstractEvent {
    private final T player;
}
