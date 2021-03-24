package app.core.state;

import app.core.controllers.AbstractController;
import app.core.controllers.BrowseController;
import app.core.controllers.LoginController;
import app.core.controllers.PostController;

/**
 * The Enum State used for managing the Applications state logic.
 */
public enum State {
  LOGIN(new LoginController()), BROWSE(new BrowseController()), POST(new PostController());

  private AbstractController stateHandler;

  /**
   * Instantiates a new state.
   *
   * @param stateHandler the state handler
   */
  private State(AbstractController stateHandler) {
    this.stateHandler = stateHandler;
  }

  /**
   * Gets the state handler.
   *
   * @return the handler
   */
  public AbstractController getHandler() {
    return this.stateHandler;
  }
}
