package app.core.state;

import app.core.controllers.AbstractController;

public class StateManager {

	private State state;
	private AbstractController currentHandler;

	public StateManager() {
		this.setState(State.LOGIN);
	}

	public void handleState() {
		if (currentHandler.readInput()) {
			this.setState(currentHandler.getNextState());
		}
		;
	}

	public void setState(State state) {
		this.state = state;
		currentHandler = this.state.getHandler();
	}

	public State getState() {
		return this.state;
	}
}
