package app.core.state;

import app.core.controllers.AbstractController;

/**
 * The Class StateManager wrapper class for State management.
 * 
 * @see State
 */
public class StateManager {

	private State state;
	private AbstractController currentHandler;

	/**
	 * Instantiates a new state manager.
	 */
	public StateManager() {
		this.setState(State.LOGIN);
	}

	/**
	 * Awaits response from the current handler. If the handler returns True, switch
	 * to the handlers next state.
	 */
	public void handleState() {
		if (currentHandler.readInput()) {
			this.setState(currentHandler.getNextState());
		}
		;
	}

	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public void setState(State state) {
		if (currentHandler != null) {
			currentHandler.clearInput();
		}
		this.state = state;
		currentHandler = this.state.getHandler();
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public State getState() {
		return this.state;
	}
}
