package net.avuna.game.items.drops;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import net.avuna.config.Config;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"npcs"})
public class DropConfig extends Config {

    public DropConfig(Path jsonFile, boolean hasDefaultResource) {
        super(jsonFile, hasDefaultResource);
    }

    @JsonProperty("npcs")
    public List<Npc> npcs = null;

    public <V> Map<Integer, List<Drop>> getNpcDrops() {

        return npcs.stream().collect(Collectors.toMap(n -> n.getNpcId(), n -> n.getDrops()));
    }
}
