package net.avuna.event.impl.game;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.avuna.event.AbstractEvent;
import net.avuna.game.entity.player.IPlayer;

@Getter
@RequiredArgsConstructor
public class TriviaCorrectEvent extends AbstractEvent {
    private final IPlayer player;
}
