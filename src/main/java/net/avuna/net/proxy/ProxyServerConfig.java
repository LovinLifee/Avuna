package net.avuna.net.proxy;

import lombok.Getter;
import net.avuna.config.Config;

import java.nio.file.Path;

@Getter
public class ProxyServerConfig extends Config {

    private int port;
    private String authUsername;
    private String authPassword;

    public ProxyServerConfig(Path jsonFile, boolean hasDefaultResource) {
        super(jsonFile, hasDefaultResource);
    }
}
