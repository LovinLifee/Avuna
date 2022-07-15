package net.avuna.game.items.drops;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import net.avuna.game.items.Chanceable;

import java.util.*;

public class DropTable<T extends Chanceable> {

	private static final Random random = new Random();

	@Getter
	@JsonProperty("amountOfRolls")
	private int amountOfRolls = 1;

	@JsonProperty("drops")
	private Collection<T> drops = new ArrayList<>();

	public void addDrop(T drop) {
		drops.add(drop);
	}

	public void addAll(Collection<T> drops) {
		this.drops.addAll(drops);
	}

	public Collection<T> rollDrops() {
		return rollDrops(this.amountOfRolls, 1.0D);
	}

	public Collection<T> rollDrops(double dropChanceModifier) {
		return rollDrops(this.amountOfRolls, dropChanceModifier);
	}

	public Collection<T> rollDrops(int rolls, double dropChanceModifier) {
		ArrayList<T> dropTable = new ArrayList<>(this.drops);
		ArrayList<T> selectedItems = new ArrayList<>();
		double totalWeight = 0.0D;
		for(Iterator<T> it = dropTable.iterator(); it.hasNext();) {
			T drop = it.next();
			if(drop.isDroppedAlways()) {
				selectedItems.add(drop);
				//So if you select multiple rolls it doesn't drop multiples of the items that should always be dropped
				it.remove();
			} else {
				totalWeight += drop.getChance();
			}
		}
		for(int i = 0; i < rolls; i++) {
			//this is because the items at the end of the list will never be reached if the total weight exceeds 1
			if(totalWeight * dropChanceModifier > 1.0D) {
				Collections.shuffle(dropTable);
			}
			double value = random.nextDouble();
			for(T d : dropTable) {
				value -= d.getChance() * dropChanceModifier;
				if(value <= 0) {
					selectedItems.add(d);
					break;
				}
			}
		}
		return selectedItems;
	}

	public Collection<T> getDrops() {
		return drops;
	}

	public double getTotalWeight() {
		return drops.stream().mapToDouble(d -> d.getChance()).sum();
	}
}