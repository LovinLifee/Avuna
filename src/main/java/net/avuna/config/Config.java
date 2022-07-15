package net.avuna.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.Getter;
import net.avuna.game.items.drops.DropConfig;
import net.avuna.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Getter
public abstract class Config {

    private static final transient ObjectMapper mapper = new ObjectMapper();

    static {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(DropConfig.class, new DropConfigDeserializer());
        mapper.registerModule(module);
    }

    @JsonIgnore
    private final Path jsonFile;

    public Config(Path jsonFile) {
        this(jsonFile, false);
    }

    public Config(Path jsonFile, boolean hasDefaultResource) {
        this.jsonFile = jsonFile;
        if(hasDefaultResource) {
            ResourceUtils.copyResourceIfNotExist("configs/" + jsonFile.getFileName().toString(), jsonFile);
        }
    }

    public void load()  {
        try(BufferedReader reader = Files.newBufferedReader(jsonFile)) {
            mapper.readerForUpdating(this).readValue(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try(BufferedWriter writer = Files.newBufferedWriter(jsonFile)) {
            mapper.writeValue(writer, this);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
