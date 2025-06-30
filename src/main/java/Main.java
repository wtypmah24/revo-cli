import command.CommandMain;

public class Main {
  public static void main(String[] args) {
    int exitCode = new picocli.CommandLine(new CommandMain()).execute(args);
    System.exit(exitCode);
  }
}
