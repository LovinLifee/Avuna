package net.avuna.net.donation.kingfoxv2;

import lombok.Getter;
import net.avuna.config.Config;

import java.nio.file.Path;

@Getter
public class KingFoxConfig extends Config {

    private String dbHost;
    private int dbPort;
    private String dbUsername;
    private String dbPassword;
    private String database;

    public KingFoxConfig(Path jsonFile, boolean hasDefaultResource) {
        super(jsonFile, hasDefaultResource);
    }
}
