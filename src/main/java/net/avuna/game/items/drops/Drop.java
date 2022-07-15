package net.avuna.game.items.drops;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import net.avuna.game.items.Chanceable;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"itemId", "minAmount", "maxAmount", "rarity"})
public class Drop implements Chanceable {

	@JsonProperty("itemId")
	public int itemId;

	@JsonProperty("minAmount")
	public int minAmount;

	@JsonProperty("maxAmount")
	public int maxAmount;

	@JsonProperty("rarity")
	public double rarity;

	@Override
	public double getChance() {
		return rarity;
	}
}