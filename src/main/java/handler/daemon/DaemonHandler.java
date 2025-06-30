package handler.daemon;

import command.cpu.CpuOutputOption;
import command.deamon.DaemonCommand;
import handler.cpu.CpuCommandHandler;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DaemonHandler {
  private static final Path PID_FILE = Path.of("revo-daemon.pid");
  private static final Path LOG_FILE = Path.of("revo-daemon.log");

  public void runDaemonLoop(int interval) {
    CpuCommandHandler cpu = new CpuCommandHandler();
    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    Runnable task =
        () -> {
          try (PrintWriter log =
              new PrintWriter(
                  Files.newBufferedWriter(
                      LOG_FILE, StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            log.printf("Timestamp: %s%n", Instant.now());
            cpu.handle(Set.of(CpuOutputOption.TOTAL_LOAD));
            log.println("------");
          } catch (IOException e) {
            System.err.println("❌ Logging failed: " + e.getMessage());
          }
        };

    executor.scheduleAtFixedRate(task, 0, interval, TimeUnit.SECONDS);

    try {
      Thread.currentThread().join();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  public void startDaemon(int interval) {
    if (Files.exists(PID_FILE)) {
      System.out.println("❗ Daemon is already running.");
      return;
    }

    try {
      String javaBin =
          System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
      String jarPath =
          new File(DaemonCommand.class.getProtectionDomain().getCodeSource().getLocation().toURI())
              .getPath();

      ProcessBuilder pb =
          new ProcessBuilder(
              javaBin, "-jar", jarPath, "daemon", "--run", "--interval", String.valueOf(interval));

      pb.redirectOutput(ProcessBuilder.Redirect.appendTo(LOG_FILE.toFile()));
      pb.redirectError(ProcessBuilder.Redirect.appendTo(LOG_FILE.toFile()));

      Process process = pb.start();

      try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(PID_FILE))) {
        out.println(process.pid());
      }

      System.out.printf(
          "✅ Daemon started in background (PID: %d), logging every %d seconds.%n",
          process.pid(), interval);
    } catch (Exception e) {
      System.err.println("❌ Failed to start daemon: " + e.getMessage());
    }
  }

  public void stopDaemon() {
    if (!Files.exists(PID_FILE)) {
      System.out.println("❌ Daemon is not running.");
      return;
    }

    try {
      long pid = Long.parseLong(Files.readString(PID_FILE).trim());
      Optional<ProcessHandle> handle = ProcessHandle.of(pid);
      handle.ifPresentOrElse(
          ph -> {
            boolean killed = ph.destroy();
            System.out.println(killed ? "🛑 Daemon stopped." : "⚠️ Could not stop daemon.");
            try {
              Files.delete(PID_FILE);
            } catch (IOException ignored) {
            }
          },
          () -> System.out.println("⚠️ PID exists but process is dead. Cleaning up."));
    } catch (IOException e) {
      System.err.println("❌ Error stopping daemon: " + e.getMessage());
    }
  }

  public void checkStatus() {
    if (!Files.exists(PID_FILE)) {
      System.out.println("❌ Daemon is not running.");
      return;
    }
    try {
      long pid = Long.parseLong(Files.readString(PID_FILE).trim());
      boolean alive = ProcessHandle.of(pid).map(ProcessHandle::isAlive).orElse(false);
      System.out.println(
          alive
              ? "✅ Daemon is running (PID " + pid + ")"
              : "⚠️ PID file exists but process is dead.");
    } catch (IOException e) {
      System.err.println("❌ Error reading PID: " + e.getMessage());
    }
  }
}
