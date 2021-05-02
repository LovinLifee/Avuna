package net.avuna.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResourceUtils {

    public static InputStream getResource(String resourcePath) {
        return getClassLoader().getResourceAsStream(resourcePath);
    }

    public static String readAsString(String resourcePath) {
        Stream<String> stream = readLines(resourcePath);
        String data = stream.collect(Collectors.joining(System.lineSeparator()));
        stream.close();
        return data;
    }

   /*
        Make sure to close the stream afterwords
    */
    public static Stream<String> readLines(String resourcePath) {
        return new BufferedReader(new InputStreamReader(getClassLoader().getResourceAsStream(resourcePath))).lines();
    }

    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static void copyResourceIfNotExist(String resource, Path path) {
        try {
            if(!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            if(!Files.exists(path)) {
                Files.copy(getResource(resource), path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
