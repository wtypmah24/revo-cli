package info.os;

import oshi.software.os.OSProcess;

public record ProcessInfoDto(int pid, double cpuLoad, long ramBytes, String commandLine) {
  public static ProcessInfoDto mapToDto(OSProcess proc) {
    return new ProcessInfoDto(
        proc.getProcessID(),
        proc.getProcessCpuLoadCumulative(),
        proc.getResidentSetSize(),
        proc.getCommandLine());
  }
}
