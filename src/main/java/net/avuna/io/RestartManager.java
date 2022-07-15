package net.avuna.io;

import lombok.SneakyThrows;
import net.avuna.tasks.security.PlayerPermissions;

public class RestartManager {

    public static void restart(String[] args) {

        ProcessHandle.current().pid();
    }

    @SneakyThrows
    public static void main(String[] args) {
        //System.out.println(RestartManager.class.getProtectionDomain().getCodeSource().getLocation().toURI().toString());

/*        Optional<ProcessHandle> oldProcess = ProcessHandle.of(Long.parseLong(args[0]));
        oldProcess.ifPresent(p -> {
            try {
                if(p.destroy()) {
                    p.onExit().get();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });*/
    }
}
