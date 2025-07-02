package handler.daemon;

public record DockerStatsDto(String name, String cpu, String mem) {
  @Override
  public String toString() {
    return String.format("Container: %-20s | CPU: %-8s | MEM: %s", name, cpu, mem);
  }
}
