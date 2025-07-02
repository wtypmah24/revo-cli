package command.deamon;

import handler.daemon.DockerStatsDto;
import handler.daemon.DockerStatsHandler;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Command(name = "docker-stats", description = "Monitor Docker container resource usage")
public class DockerStatsCommand implements Runnable {
  @Option(names = "--name", description = "Filter containers by name substring")
  private List<String> nameFilters;

  @Option(names = "--interval", description = "Refresh interval in seconds", defaultValue = "5")
  private int interval;

  @Option(names = "--once", description = "Print stats once and exit")
  private boolean once;

  @Override
  public void run() {
    DockerStatsHandler monitor = new DockerStatsHandler();

    Runnable task =
        () -> {
          List<DockerStatsDto> stats = monitor.getContainerStats(nameFilters);
          stats.forEach(System.out::println);
          System.out.println("------");
        };
    if (once) {
      task.run();
    } else {
      ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
      scheduler.scheduleAtFixedRate(task, 0, interval, TimeUnit.SECONDS);

      try {
        Thread.currentThread().join();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }
}
