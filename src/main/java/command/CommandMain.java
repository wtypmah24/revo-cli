package command;

import picocli.CommandLine.Command;

@Command(
    name = "revo",
    description = "Revo system profiler CLI",
    subcommands = {CpuCommand.class})
public class CommandMain implements Runnable {
  @Override
  public void run() {
    System.out.println("Use a subcommand. Example: revo cpu-command --interval 2");
  }
}
