package net.avuna.net.donation.runepay;

import lombok.Getter;
import net.avuna.config.Config;

import java.nio.file.Path;

@Getter
public class RunePayConfig extends Config {

    private String bearerToken;

    public RunePayConfig(Path jsonFile, boolean hasDefaultResource) {
        super(jsonFile, hasDefaultResource);
    }
}