package net.avuna.net.vote;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.avuna.event.AbstractEvent;

@Getter
@RequiredArgsConstructor
public class PlayerVoteEvent extends AbstractEvent {

    private final String username;
    private final VoteData[] data;

    public boolean noVotes() {
        return data == null || data.length == 0;
    }
}
