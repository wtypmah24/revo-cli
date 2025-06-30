package info.hardware.hwd;

import java.util.List;
import oshi.SystemInfo;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;

public class HWDisc {
  private final List<HWDiskStore> diskStores;
  private final List<OSFileStore> fileStores;

  public HWDisc() {
    SystemInfo si = new SystemInfo();
    HardwareAbstractionLayer hal = si.getHardware();
    OperatingSystem os = si.getOperatingSystem();

    this.diskStores = hal.getDiskStores();
    FileSystem fs = os.getFileSystem();
    this.fileStores = fs.getFileStores();
  }

  public List<String> getDiscsNames() {
    return diskStores.stream().map(HWDiskStore::getName).toList();
  }

  public List<String> getDiscsModels() {
    return diskStores.stream().map(HWDiskStore::getModel).toList();
  }

  public List<String> getDiscsSerials() {
    return diskStores.stream().map(HWDiskStore::getSerial).toList();
  }

  public List<Long> getDiscsSize() {
    return diskStores.stream().map(HWDiskStore::getSize).toList();
  }

  public List<Long> getDiscsReads() {
    return diskStores.stream().map(HWDiskStore::getReads).toList();
  }

  public List<Long> getDiscsWrites() {
    return diskStores.stream().map(HWDiskStore::getWrites).toList();
  }

  public List<Long> getReadBytes() {
    return diskStores.stream().map(HWDiskStore::getReadBytes).toList();
  }

  public List<Long> getWriteBytes() {
    return diskStores.stream().map(HWDiskStore::getWriteBytes).toList();
  }

  public List<Long> getTransferTime() {
    return diskStores.stream().map(HWDiskStore::getTransferTime).toList();
  }

  public List<Integer> getPartitions() {
    return diskStores.stream().map(d -> d.getPartitions().size()).toList();
  }

  public List<String> getMountPoints() {
    return fileStores.stream().map(OSFileStore::getMount).toList();
  }

  public List<String> getVolumes() {
    return fileStores.stream().map(OSFileStore::getVolume).toList();
  }

  public List<String> getLabels() {
    return fileStores.stream().map(OSFileStore::getLabel).toList();
  }

  public List<String> getTypes() {
    return fileStores.stream().map(OSFileStore::getType).toList();
  }

  public List<Long> getTotalSpaces() {
    return fileStores.stream().map(OSFileStore::getTotalSpace).toList();
  }

  public List<Long> getUsableSpaces() {
    return fileStores.stream().map(OSFileStore::getUsableSpace).toList();
  }

  public List<Long> getFreeSpaces() {
    return fileStores.stream().map(OSFileStore::getFreeSpace).toList();
  }

  public List<String> getDescriptions() {
    return fileStores.stream().map(OSFileStore::getDescription).toList();
  }

  public List<String> getFSType() {
    return fileStores.stream().map(OSFileStore::getLogicalVolume).toList();
  }
}
