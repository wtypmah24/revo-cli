package command.deamon;

import handler.daemon.DaemonHandler;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.List;

@Command(name = "daemon", description = "Manage system info daemon")
public class DaemonCommand implements Runnable {

  @Option(names = "--start", description = "Start the logging daemon")
  private boolean start;

  @Option(names = "--stop", description = "Stop the logging daemon")
  private boolean stop;

  @Option(names = "--status", description = "Show if daemon is running")
  private boolean status;

  @Option(names = "--interval", description = "Logging interval in seconds", defaultValue = "10")
  private int interval;

  @Option(
      names = "--process-name",
      description = "Match process by name or command (case-insensitive)")
  private List<String> processNames;

  @Option(names = "--pid", description = "Track specific PIDs")
  private List<Integer> pids;

  @Option(names = "--run", hidden = true)
  private boolean run;

  @Override
  public void run() {
    var handler = new DaemonHandler();
    if (run) {
      handler.runDaemonLoop(interval);
      return;
    }

    int count = (start ? 1 : 0) + (stop ? 1 : 0) + (status ? 1 : 0);
    if (count != 1) {
      System.out.println("Specify exactly one of --start, --stop, or --status");
      return;
    }

    if (start) handler.startDaemon(interval);
    else if (stop) handler.stopDaemon();
    else handler.checkStatus();
  }
}
