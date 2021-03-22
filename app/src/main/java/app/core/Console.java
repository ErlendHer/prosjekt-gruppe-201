package app.core;

import java.util.Scanner;

public class Console {

  private static State state = State.FOLDER;

  public void run() {
    Scanner userInput = new Scanner(System.in);
    String cmd = "";

    while (true) {
      cmd = userInput.nextLine();
      System.out.println(cmd);
    }
  }

  enum State {
    FOLDER,
  }

}
