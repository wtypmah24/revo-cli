package service;


import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;

public class CpuLoadService {
  private final OperatingSystemMXBean osBean;

  public CpuLoadService() {
    this.osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
  }

  public double getAverageCpuLoad(int durationSeconds) throws InterruptedException {
    double totalLoad = 0;

    for (int i = 0; i < durationSeconds; i++) {
      double load = osBean.getCpuLoad(); // от 0.0 до 1.0
      if (load >= 0) {
        totalLoad += load;
      }
      Thread.sleep(1000);
    }

    return totalLoad / durationSeconds;
  }
}
