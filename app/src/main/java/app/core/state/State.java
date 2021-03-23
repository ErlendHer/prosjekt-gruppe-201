package app.core.state;

import app.core.controllers.AbstractController;
import app.core.controllers.BrowseController;
import app.core.controllers.LoginController;
import app.core.controllers.PostController;

public enum State {
	LOGIN(new LoginController()),
	BROWSE(new BrowseController()),
	POST(new PostController());
	
	private AbstractController stateHandler;

	private State(AbstractController stateHandler) {
		this.stateHandler = stateHandler;
	}
	
	public AbstractController getHandler() {
		return this.stateHandler;
	}
}
