package net.avuna.event.net.donation;

import net.avuna.config.Config;

public interface DonationStrategy {
    public void checkDonations(String username);
    public Config getConfig();
}
