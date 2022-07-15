package net.avuna.io;

import net.avuna.config.Config;

import java.nio.file.Path;

public class RestartConfig extends Config {

    public RestartConfig(Path jsonFile, boolean hasDefaultResource) {
        super(jsonFile, hasDefaultResource);
    }
}
