package net.avuna.net.donation;

import lombok.Getter;
import net.avuna.config.Config;

import java.nio.file.Path;

@Getter
public class MasterDonationConfig extends Config {

    private String donationGateway;

    public MasterDonationConfig(Path jsonFile, boolean hasDefaultResource) {
        super(jsonFile, hasDefaultResource);
    }
}
