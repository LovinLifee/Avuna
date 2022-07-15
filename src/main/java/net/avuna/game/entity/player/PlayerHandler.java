package net.avuna.game.entity.player;

import lombok.extern.java.Log;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Stream;

@Log
public class PlayerHandler {

    private Collection<IPlayer> players = new CopyOnWriteArraySet<>();

    public void bind(Collection<IPlayer> players) {
        this.players = new CopyOnWriteArraySet(players);
    }

    public void bind(IPlayer[] players) {
        this.players = new CopyOnWriteArraySet(Arrays.asList(players));
    }

    public Collection<IPlayer> getBackingCollection() {
        return players;
    }

    public Optional<IPlayer> lookup(String username) {
        return getPlayers().filter(p -> p.getUsername().equalsIgnoreCase(username)).findAny();
    }

    public Stream<IPlayer> getPlayers() {
        return players.stream().filter(Objects::nonNull);
    }

    public void sendMessageToAll(String message) {
        getPlayers().forEach(p -> p.sendMessage(message));
    }
}
