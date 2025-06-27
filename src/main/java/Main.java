import command.CommandMain;

public class Main {
  public static void main(String[] args) {
    new picocli.CommandLine(new CommandMain()).execute(args);
  }
}
