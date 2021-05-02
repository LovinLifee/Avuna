package net.avuna.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import net.avuna.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

@Getter
public abstract class Config {

    private static final Gson gson = new Gson().newBuilder().setPrettyPrinting().create();

    private final transient Path jsonFile;

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
        try {
            BufferedReader reader = Files.newBufferedReader(jsonFile);
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            reader.close();
            update(jsonObject);
        } catch (IOException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            BufferedWriter writer = Files.newBufferedWriter(jsonFile);
            gson.toJson(this, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
        https://github.com/google/gson/issues/431
     */
    private void update(JsonObject json) throws NoSuchFieldException, IllegalAccessException {
        Set<Map.Entry<String, JsonElement>> entrySet = json.entrySet();
        for (Map.Entry<String, JsonElement> entry : entrySet) {
            String key = entry.getKey();
            Field field = getClass().getDeclaredField(key);
            if(field == null) {
                field = getClass().getSuperclass().getDeclaredField(key);
            }
            field.setAccessible(true);
            Type genType = field.getGenericType();
            field.set(this, gson.fromJson(entry.getValue(), genType));
        }
    }
}
