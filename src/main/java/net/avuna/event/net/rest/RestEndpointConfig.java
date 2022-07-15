package net.avuna.event.net.rest;

import lombok.Getter;
import net.avuna.config.Config;

import java.nio.file.Path;

@Getter
public class RestEndpointConfig extends Config {

    private int port;
    private String authUsername;
    private String authPassword;
    private boolean enableConsole;

    public RestEndpointConfig(Path jsonFile, boolean hasDefaultResource) {
        super(jsonFile, hasDefaultResource);
    }
}
