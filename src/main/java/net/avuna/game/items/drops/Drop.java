package net.avuna.game.items.drops;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.avuna.game.items.Chanceable;

import java.util.Objects;

@Getter
@ToString
@RequiredArgsConstructor
public class Drop implements Chanceable {

	private final int itemId;
	private final int minAmount;
	private final int maxAmount;

	/**
	 * @return a value between 0.0 and 1.0 inclusively, can be represented as a fraction between 2 floating point numbers
	 * where the numerator is less than or equal to the denominator
	 */
	private final double chance;

	public Drop(int itemId, int amount, double rarity) {
		this(itemId, amount, amount, rarity);
	}

	public Drop(int itemId, int minAmount, int maxAmount) {
		this(itemId, minAmount, maxAmount, 1D);
	}

	public Drop(int itemId, int amount) {
		this(itemId, amount, amount, 1D);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Drop drop = (Drop) o;
		return getItemId() == drop.getItemId() &&
				getMinAmount() == drop.getMinAmount() &&
				getMaxAmount() == drop.getMaxAmount() &&
				Double.compare(drop.getChance(), getChance()) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getItemId(), getMinAmount(), getMaxAmount(), getChance());
	}
}
