package net.avuna.game.items.drops;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"npcId", "amountOfRolls", "drops"})
public class Npc {

    @JsonProperty("npcId")
    public int npcId;

    @JsonProperty("amountOfRolls")
    public int amountOfRolls;

    @JsonProperty("drops")
    public List<Drop> drops = null;
}