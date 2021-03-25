package app.core.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import app.core.models.AbstractModel;
import app.core.state.State;
import app.dao.ConnectionHandler;

/**
 * The Class AbstractController. Abstract class for handling logic and user interaction.
 */
public abstract class AbstractController {

  // Available command flags
  private final List<String> cmdFlags = List.of("enter", "back", "quit", "search", "help", "answer",
      "create", "comment", "like", "stat", "logout");

  private final List<String> messages;

  protected Scanner scanner;
  protected ArrayList<String> inputs;
  protected State nextState;

  /**
   * Instantiates a new abstract controller.
   */
  public AbstractController() {
    this.messages = new ArrayList<String>();
    this.scanner = new Scanner(System.in);
    this.inputs = new ArrayList<String>();
  }

  /**
   * Read user input as text input.
   */
  protected void readLine() {
    inputs.add(scanner.nextLine());
  }

  /**
   * Read user input as console commands. Validates the entered command based on predefined flags,
   * and throws an exception if the command is formatted incorrectly.
   *
   * @throws Exception the incorrect command exception
   */
  protected void awaitConsoleInput() throws Exception {
    List<String> cmd = Arrays.asList(scanner.nextLine().toLowerCase().split(" "));
    boolean valid = false;
    String errMessage = "";

    if (cmd.isEmpty()) {
      // If entered command is empty
      errMessage = "Please enter a command";
    } else if (!cmdFlags.contains(cmd.get(0))) {
      // If no flags detected
      errMessage = "Invalid command, enter 'help' for information of available commands";
    } else if (List.of("enter", "search", "comment", "like").contains(cmd.get(0))
        && (cmd.size() < 2 || cmd.get(0).length() < 1)) {
      // If flag requires a parameter, but none is given
      errMessage = String.format("Invalid command, '%s' requires an additional parameter",
          cmd.get(0));
    } else if (cmd.get(0).equalsIgnoreCase("quit")) {
      // Quit the application
      try {
        ConnectionHandler.closePool();
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        System.out.println("Exited the application");
        System.exit(-1);
      }
    } else {
      // Command is valid
      valid = true;
      inputs.addAll(cmd);
    }

    // Throw an exception if entered command is incorrect
    if (!valid)
      throw new Exception(errMessage);
  }

  /**
   * Check if an object exists on a given index in a list of AbstractModel-objects.
   * 
   * @see AbstractModel
   *
   * @param list the list
   * @return the abstract model
   */
  protected AbstractModel lookupIndex(ArrayList<? extends AbstractModel> list) {
    try {
      int index = Integer.parseInt(inputs.get(1)) - 1;

      return list.get(index);
    } catch (NumberFormatException e) {
      this.addMessage("Invalid input, expected an integer");
    } catch (IndexOutOfBoundsException e2) {
      this.addMessage("Invalid index");
    }
    return null;
  }

  /**
   * Adds a message.
   *
   * @param message the message
   */
  protected void addMessage(String message) {
    this.messages.add(message);
  }

  /**
   * Print messages, if any.
   */
  protected void printMessages() {
    if (!this.messages.isEmpty()) {
      for (String message : messages) {
        System.out.println(message);
      }
      this.messages.clear();
    }
  }

  /**
   * Sets the next state.
   *
   * @param state the new next state
   */
  protected void setNextState(State state) {
    this.nextState = state;
  }

  /**
   * Clears the user input.
   */
  public void clearInput() {
    this.inputs.clear();
  }

  /**
   * Close the input scanner.
   */
  public void closeScanner() {
    this.scanner.close();
  }

  /**
   * Gets the next state.
   *
   * @return the next state
   */
  public State getNextState() {
    return this.nextState;
  }

  /**
   * Reads input in a given state. Returns true if controller is ready to switch to the next state.
   *
   * @return true, if successful
   */
  public abstract boolean readInput();

}
