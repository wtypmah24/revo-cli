package handler.daemon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DockerStatsHandler {
  public List<DockerStatsDto> getContainerStats(List<String> nameFilters) {
    List<DockerStatsDto> result = new ArrayList<>();

    try {
      Process process =
          new ProcessBuilder(
                  "docker",
                  "stats",
                  "--no-stream",
                  "--format",
                  "{{.Name}},{{.CPUPerc}},{{.MemUsage}}")
              .start();

      try (BufferedReader reader =
          new BufferedReader(new InputStreamReader(process.getInputStream()))) {
        String line;
        while ((line = reader.readLine()) != null) {
          String[] parts = line.split(",", 3);
          if (parts.length < 3) continue;

          String name = parts[0].trim();
          if (nameFilters != null
              && !nameFilters.isEmpty()
              && nameFilters.stream().noneMatch(name::contains)) {
            continue;
          }

          result.add(new DockerStatsDto(name, parts[1].trim(), parts[2].trim()));
        }
      }
    } catch (IOException e) {
      System.err.println("❌ Failed to read docker stats: " + e.getMessage());
    }

    return result;
  }
}
