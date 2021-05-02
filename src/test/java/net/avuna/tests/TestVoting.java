package net.avuna.tests;

import net.avuna.Avuna;
import net.avuna.net.vote.PlayerVoteEvent;
import net.avuna.net.vote.VoteData;

import java.util.concurrent.ExecutionException;

public class TestVoting {

    public static void main(String[] args) {
        Avuna.bootstrap();
        Avuna.getEventManager().registerEventListener(PlayerVoteEvent.class, TestVoting::onVote);
        try {
            System.out.println(Avuna.getVoteHandler().viewVotes().get().body());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(args.length != 1) {
            System.out.println("If you want to manually check a vote please pass an argument with the username");
            return;
        }
        String username = args[0];
        Avuna.getVoteHandler().checkVotes(username);
    }

    private static void onVote(PlayerVoteEvent event) {
        if(event.noVotes()) {
            System.out.println(event.getUsername() + " hasn't got any votes to claim");
        } else {
            System.out.println("Thank you for voting!");
            for(VoteData d : event.getData()) {
                System.out.println(d.getVoteKey());
            }
        }
    }
}
