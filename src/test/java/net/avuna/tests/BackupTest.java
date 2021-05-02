package net.avuna.tests;

import net.avuna.io.BackupManager;

import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class BackupTest {

    public static void main(String[] args) {
        BackupManager manager = new BackupManager(Paths.get("C:\\Users\\midni\\Desktop\\StoreV2"),
                Paths.get("C:\\Users\\midni\\Desktop\\StoreV2"), 10, TimeUnit.SECONDS);
        manager.schedule();
    }
}
