package net.avuna.io;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

//TODO: write this whole class better
@Getter
@RequiredArgsConstructor
public class BackupManager implements Runnable {

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    private final Path folderToBackup;
    private final Path backupDirectory;
    private final int time;
    private final TimeUnit unit;
    private boolean alreadyScheduled = false;

    private void deleteFilesIfOlderThan(Path baseDir, int time, TimeUnit unit) {
        try {
            Files.walk(baseDir).filter(p -> p.toString().toLowerCase().endsWith(".zip")).forEach(p -> {
                deleteFilesIfOlderThan(p, time, unit);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO: for whatever reason this stops execution so for now we will delete manually
    //TODO: make this configurable
    private void deleteFileIfOlderThan(Path file, int time, TimeUnit unit) {
        try {
            boolean expired = Files.getLastModifiedTime(file).to(unit) <= System.currentTimeMillis() - unit.toMillis(time);
            if(expired) {
                Files.delete(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void zipFile() {
       String fileName = LocalDateTime.now().toString().replaceAll(":", "_") + ".zip";
       Path zipFile = backupDirectory.resolve(fileName);
       try(ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipFile))) {
            zos.setLevel(9);
            Files.walk(folderToBackup)
                    .filter(p -> !Files.isDirectory(p) && !p.equals(zipFile) && !p.toString().toLowerCase().endsWith(".zip"))
                    .forEach(p -> {
                try {
                    zos.putNextEntry(new ZipEntry(folderToBackup.relativize(p).toString()));
                    zos.write(Files.readAllBytes(p));
                    zos.closeEntry();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            });
       } catch(IOException e) {
           e.printStackTrace();
       }
    }

    public void schedule() {
        if(!isAlreadyScheduled()) {
            executorService.scheduleAtFixedRate(this, time, time, unit);
            alreadyScheduled = true;
        }
    }

    @Override
    public void run() {
        zipFile();
        //deleteFilesIfOlderThan(backupDirectory, 3, TimeUnit.DAYS);
    }
}
