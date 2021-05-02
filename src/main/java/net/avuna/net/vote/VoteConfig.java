package net.avuna.net.vote;

import lombok.Getter;
import net.avuna.config.Config;

import java.nio.file.Path;

@Getter
public class VoteConfig extends Config {

    private String apiUrl;
    private String bearerToken;

    public VoteConfig(Path jsonFile, boolean hasDefaultResource) {
        super(jsonFile, hasDefaultResource);
    }
}
