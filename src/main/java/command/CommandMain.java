package command;

import command.cpu.CpuCommand;
import command.deamon.DaemonCommand;
import command.hwd.DiscCommand;
import command.network.NetworkCommand;
import command.os.OSServiceCommand;
import command.power.PowerCommand;
import command.process.OSProcessCommand;
import command.ram.MemoryCommand;
import command.usb.UsbCommand;
import picocli.CommandLine.Command;

@Command(
    name = "revo",
    description = "Revo system profiler CLI",
    subcommands = {
      CpuCommand.class,
      DiscCommand.class,
      MemoryCommand.class,
      NetworkCommand.class,
      PowerCommand.class,
      OSProcessCommand.class,
      UsbCommand.class,
      OSServiceCommand.class,
      DaemonCommand.class
    })
public class CommandMain implements Runnable {
  @Override
  public void run() {
    System.out.println("Use a subcommand. Example: revo cpu-command --interval 2");
  }
}
