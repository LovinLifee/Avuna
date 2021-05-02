package net.avuna.game.entity.player;

import lombok.extern.java.Log;

import java.util.*;
import java.util.stream.Stream;

@Log
public class PlayerHandler {

    private Collection<IPlayer> players = Collections.emptyList();

    public void bind(Collection<IPlayer> players) {
        this.players = players;
    }

    public void bind(IPlayer[] players) {
        this.players = Arrays.asList(players);
    }

    public Optional<IPlayer> lookup(String username) {
        return getPlayers().filter(p -> p.getUsername().equalsIgnoreCase(username)).findAny();
    }

    public Stream<IPlayer> getPlayers() {
        if(players == Collections.EMPTY_LIST) {
            log.warning("You don't have a collection or array of players bound to PlayerHandler");
        }
        return players.stream().filter(Objects::nonNull);
    }

    public void sendMessageToAll(String message) {
        getPlayers().forEach(p -> p.sendMessage(message));
    }
}
