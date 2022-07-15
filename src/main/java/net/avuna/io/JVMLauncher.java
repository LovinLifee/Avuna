package net.avuna.io;

import lombok.Builder;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Builder
public class JVMLauncher {

    @Builder.Default
    private Path javaBin = Path.of(System.getProperty("java.home"), "bin", "java");
    private Path jarFile;
    private String className;
    private List<String> args;
    private boolean inheritIO;

    public void launch() throws IOException {
        ProcessBuilder builder = new ProcessBuilder();
        List<String> command = new ArrayList<>();
        command.add(javaBin.toAbsolutePath().toString());
        command.add("-cp");
        command.add(jarFile.toAbsolutePath().toString());
        command.add(className);
        command.addAll(args);
        builder.command(command);
        builder.start();
    }

    public static void main(String[] args) {
        /*try {
            JVMLauncher.builder()
                    //.javaBin(Path.of(System.getProperty("java.home"), "bin", "java"))
                    .jarFile(Path.of("C:\\Users\\Hayden\\Desktop\\recaf-2.19.5-J8-jar-with-dependencies.jar"))
                    .className("me.coley.recaf.Recaf")
                    .build()
                    .launch();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        System.out.println(getCurrentClassPath());
    }

    public static String getCurrentClassPath() {
        return JVMLauncher.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    }
}
