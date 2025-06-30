package handler.hwd;

import command.hwd.DiscOutputOption;
import info.hardware.hwd.HWDisc;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;

public class DiscCommandHandler {
  private final HWDisc disc = new HWDisc();

  private final Map<DiscOutputOption, Runnable> actions =
      Map.of(
          DiscOutputOption.INFO, this::printDiscInfo,
          DiscOutputOption.READ_WRITE, this::printReadWriteStats,
          DiscOutputOption.MOUNT_POINTS, this::printMountPoints);

  public void handle(Set<DiscOutputOption> selected) {
    if (selected == null || selected.isEmpty()) {
      System.out.println("⚠️ No disk options specified. Use --help to see available flags.");
      return;
    }

    selected.stream().map(actions::get).filter(Objects::nonNull).forEach(Runnable::run);
  }

  private void printDiscInfo() {
    System.out.println("💽 Disk Info:");
    var names = disc.getDiscsNames();
    var models = disc.getDiscsModels();
    var sizes = disc.getDiscsSize();
    var serials = disc.getDiscsSerials();

    IntStream.range(0, names.size())
        .forEach(
            i -> {
              System.out.printf(
                  "• Name: %s | Model: %s | Serial: %s | Size: %.2f GB%n",
                  names.get(i), models.get(i), serials.get(i), sizes.get(i) / 1e9);
            });
  }

  private void printReadWriteStats() {
    System.out.println("📈 Disk I/O Stats:");
    var reads = disc.getDiscsReads();
    var writes = disc.getDiscsWrites();
    var readBytes = disc.getReadBytes();
    var writeBytes = disc.getWriteBytes();

    IntStream.range(0, reads.size())
        .forEach(
            i -> {
              System.out.printf(
                  "• Disk %d → Reads: %d, Writes: %d, ReadBytes: %.2f MB, WriteBytes: %.2f MB%n",
                  i, reads.get(i), writes.get(i), readBytes.get(i) / 1e6, writeBytes.get(i) / 1e6);
            });
  }

  private void printMountPoints() {
    System.out.println("📁 Mount Points:");
    var mounts = disc.getMountPoints();
    var types = disc.getTypes();
    var total = disc.getTotalSpaces();
    var free = disc.getFreeSpaces();

    IntStream.range(0, mounts.size())
        .forEach(
            i -> {
              System.out.printf(
                  "• Mount: %s | Type: %s | Total: %.2f GB | Free: %.2f GB%n",
                  mounts.get(i), types.get(i), total.get(i) / 1e9, free.get(i) / 1e9);
            });
  }
}
