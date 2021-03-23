package app.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import app.core.folder.Course;
import app.core.folder.Folder;
import app.core.folder.TreeBuilder;

public class Console {

  private State state;
  private Folder activeFolder;
  private Course activeCourse;
  private ArrayList<Course> courses;

  public Console() {
    state = State.COURSE_VIEW;
    courses = TreeBuilder.genTree();
  }

  /**
   * Print the expanded folder structure based on the currently active folder.
   * 
   * @return the whitespace of the deepest folder.
   */
  private int printFolders() {
    ArrayList<String> rootFolders = new ArrayList<>();

    var curFolder = activeFolder;

    while (true) {
      if (curFolder.getParent() == null) {
        break;
      }
      rootFolders.add(curFolder.getName());
      curFolder = curFolder.getParent();
    }
    Collections.reverse(rootFolders);

    System.out.println(activeCourse.getCourseCode());

    for (int i = 0; i < rootFolders.size(); i++) {
      System.out.println(String.format("%1$" + (i * 2 + 2 + rootFolders.get(i).length()) + "s", rootFolders.get(i)));
    }

    int whiteSpace = (rootFolders.size() - 1) * 2 + 4;
    for (var child : activeFolder.getChildren()) {
      System.out.println(String.format("%1$" + (whiteSpace + child.getName().length()) + "s", child.getName()));
    }

    return whiteSpace;

  }

  /**
   * Print a view of all threads in a folder. Calls printFolders() first and
   * builds on it based on the whitespace returned.
   */
  private void printThreads() {
    int whiteSpace = printFolders();
    String threadStr = "[Threads]";
    System.out.println(String.format("%1$" + (whiteSpace + 2 + threadStr.length()) + "s", threadStr));
    for (var thread : activeFolder.getThreads()) {
      System.out.println(String.format("%1$" + (whiteSpace + 2 + thread.getTitle().length()) + "s", thread.getTitle()));
    }
  }

  /**
   * Change directory based on user input. If the user types the name of a folder,
   * course or thread, navigate to it. If the user types ".." navigate back one
   * step.
   * 
   * @param cmd array of user entered input split by spaces.
   */
  private void change_dir(String[] cmd) {
    if (cmd.length < 2) {
      System.out.println("Missing argument, use cd to change dir, e.g (cd post1)");
      return;
    }

    // Make sure we handle names with spaces correctly
    if (cmd.length > 2) {
      for (int i = 2; i < cmd.length; i++) {
        cmd[1] += " " + cmd[i];
      }
    }

    if (cmd[1].equalsIgnoreCase("..")) {
      if (state == State.COURSE_VIEW) {
        System.out.println("You are at root, you can't go back");
      } else if (state == State.FOLDER_VIEW) {
        if (activeFolder.getParent() == null) {
          state = State.COURSE_VIEW;
          activeFolder = activeCourse.getRoot();
        } else {
          activeFolder = activeFolder.getParent();
        }
      } else if (state == State.THREAD_VIEW) {
        if (activeFolder.getParent() != null) {
          state = State.FOLDER_VIEW;
          activeFolder = activeFolder.getParent();
        } else {
          state = State.COURSE_VIEW;
        }
      }
    } else {
      boolean exists = false;

      if (state == State.COURSE_VIEW) {
        for (var course : courses) {
          if (cmd[1].equalsIgnoreCase(course.getCourseCode())) {
            activeFolder = course.getRoot();
            activeCourse = course;
            exists = true;
            this.state = State.FOLDER_VIEW;
            break;
          }
        }
      } else if (state == State.FOLDER_VIEW) {

        for (var folder : activeFolder.getChildren()) {
          if (folder.getName().equalsIgnoreCase(cmd[1])) {
            activeFolder = folder;
            exists = true;
            System.out.println("hello");
            this.state = folder.getChildren().size() == 0 ? State.THREAD_VIEW : State.FOLDER_VIEW;
          }
        }

      }

      if (!exists) {
        System.out.println("The path does not exist");
      }
    }

  }

  /**
   * Console main loop, listen for user commands and execute actions accordingly.
   * Mainly used to navigate between folders and threads.
   */
  public void run() {
    Scanner userInput = new Scanner(System.in);
    boolean running = true;

    while (running) {
      if (state == State.COURSE_VIEW) {
        System.out.println("Courses: ");
        for (var course : courses) {
          System.out.println("  " + course.getCourseCode());
        }
      } else if (state == State.FOLDER_VIEW) {
        printFolders();
      } else if (state == State.THREAD_VIEW) {
        printThreads();
      }

      var cmd = userInput.nextLine().toLowerCase().split(" ");

      if (cmd.length != 0) {
        switch (cmd[0]) {
        case "exit":
          running = false;
          break;
        case "cd":
          change_dir(cmd);
          break;
        default:
          System.out.println("Not a valid command, type 'cd name' to navigate to name, and 'cd ..' to go back one dir");
        }
      }

      System.out.println();

    }

    userInput.close();

  }

  enum State {
    COURSE_VIEW, FOLDER_VIEW, THREAD_VIEW,
  }

}
