package app.core;

import java.util.ArrayList;
import java.util.Scanner;

import app.core.folder.TreeBuilder;

public class Console {

  public void run() {
    var courses = TreeBuilder.genTree();

    for (var course : courses) {
      System.out.println(course);
    }

    Scanner userInput = new Scanner(System.in);
    boolean running = true;

    while (running) {
      var cmd = userInput.nextLine().toLowerCase().split(" ");

      System.out.println(cmd[0]);

      if (cmd.length != 0) {
        switch (cmd[0]) {
        case "exit":
          running = false;
          break;
        default:
          System.out.println("Not a valid command");
        }
      }

    }

    userInput.close();

  }

}
