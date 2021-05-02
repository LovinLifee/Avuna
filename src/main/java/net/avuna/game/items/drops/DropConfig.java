package net.avuna.game.items.drops;

import net.avuna.config.Config;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class DropConfig extends Config {

    private final Map<Integer, DropTable<Drop>> npcs = new HashMap<>();

    public DropConfig(Path jsonFile, boolean hasDefaultResource) {
        super(jsonFile, hasDefaultResource);
    }

    public Map<Integer, DropTable<Drop>> getNpcDrops() {
        return npcs;
    }
}
