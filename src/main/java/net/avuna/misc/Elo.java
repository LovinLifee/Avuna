package net.avuna.misc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Elo implements Comparable<Elo> {

    private int elo;

    public static Elo of(int elo) {
        return new Elo(elo);
    }

    public static Elo ofDefault() {
        return new Elo(1000);
    }

    @Override
    public int compareTo(Elo o) {
        return Integer.compare(elo, o.getElo());
    }

    public static void change(Elo winner, Elo loser, boolean tie) {
        int winnerElo = winner.getElo();
        int loserElo = loser.getElo();

        int newWinnerElo = calculateNewElo(winnerElo, loserElo, tie ? "=" : "+");
        int newLoserElo = calculateNewElo(loserElo, winnerElo, tie ? "=" : "-");

        winner.setElo(newWinnerElo);
        loser.setElo(newLoserElo);

    }

    private static int calculateNewElo(int player1Elo, int player2Elo, String outcome) {
        double actualScore;
        if (outcome.equals("+")) {
            actualScore = 1.0;
        } else if (outcome.equals("=")) {
            actualScore = 0.5;
        } else if (outcome.equals("-")) {
            actualScore = 0;
        } else {
            return player1Elo;
        }
        double expectedOutcome = probabilityOfWinning(player1Elo, player2Elo);
        int kFactor = 32;
        int newRating = (int) Math.round(player1Elo + kFactor * (actualScore - expectedOutcome));
        return newRating;
    }

    public static double probabilityOfWinning(int player1Elo, int player2Elo) {
        double exponent = (double) (player2Elo - player1Elo) / 400.0;
        double expectedOutcome = (1.0 / (1.0 + (Math.pow(10, exponent))));
        return expectedOutcome;
    }
}
