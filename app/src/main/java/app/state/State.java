package app.state;

import app.Store;
import app.state.handlers.AbstractStateHandler;
import app.state.handlers.LoginStateHandler;
import app.state.handlers.PostStateHandler;

public enum State {
	LOGIN(new LoginStateHandler()),
	POST(new PostStateHandler());
	
	private AbstractStateHandler stateHandler;

	private State(AbstractStateHandler stateHandler) {
		this.stateHandler = stateHandler;
	}
	
	public AbstractStateHandler getHandler() {
		return this.stateHandler;
	}
}
