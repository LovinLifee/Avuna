package net.avuna.tests;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.avuna.game.items.Chanceable;
import net.avuna.game.entity.player.IPlayer;
import net.avuna.game.items.drops.DropTable;
import net.avuna.util.Console;
import net.avuna.util.FrequencyMap;

import java.util.Collection;
import java.util.Map;

public class TestDrops {

    public static void main(String[] args) {
        DropTable<StringWrapper> dropTable = new DropTable<>();
        dropTable.addDrop(new StringWrapper("Dragon longsword", 1 / 69.0));
        dropTable.addDrop(new StringWrapper("Ranarr weed", 1 / 420.0));
        dropTable.addDrop(new StringWrapper("White partyhat", 1 / 1337.0));
        dropTable.addDrop(new StringWrapper("Bones", 1D));

        MockPlayer player = new MockPlayer("Hayden");

        final int iterations = 100_000;

        System.out.println(Console.GREEN + "default drop rate" + Console.RESET);
        simulateDrops(iterations, dropTable, player);

        player.setDropChanceModifier(1.5D);
        System.out.println(Console.GREEN + "with " + player.getDropChanceModifier() * 100 + "% drop modifier" + Console.RESET);
        simulateDrops(iterations, dropTable, player);
    }

    private static void simulateDrops(int iterations, DropTable dropTable, IPlayer player) {
        FrequencyMap<StringWrapper> frequencyMap = new FrequencyMap<>();
        for(int i = 0; i < iterations; i++) {
            Collection<StringWrapper> drops = dropTable.rollDrops(player.getDropChanceModifier());
            drops.forEach(frequencyMap::increment);
        }
        frequencyMap.forEach((item, count) -> {
            player.sendMessage(String.format("%s dropped %d times (expected approximately %d)", item.getText(), count, (int) (iterations * item.getChance())));
        });
        Map.Entry<StringWrapper, Integer> minElement = frequencyMap.getMin();
        Map.Entry<StringWrapper, Integer> maxElement = frequencyMap.getMax();
        System.out.print(Console.BLUE);
        System.out.printf("MIN = %s - %s\n", minElement.getKey().getText(), minElement.getValue());
        System.out.printf("MAX = %s - %s\n", maxElement.getKey().getText(), maxElement.getValue());
        System.out.printf("AVERAGE COUNT = %.2f\n\n", frequencyMap.getAverageCount());
        System.out.print(Console.RESET);
    }

    @Getter
    @RequiredArgsConstructor
    private static class StringWrapper implements Chanceable {
        private final String text;
        private final double chance;
    }
}
