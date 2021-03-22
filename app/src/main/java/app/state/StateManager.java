package app.state;

public class StateManager {
	
	private State state;

	public StateManager() {
		this.state = State.LOGIN;
	}
	
	public void handleState() {
		if(this.state.getHandler().readInput()) {
			this.setState(this.state.getHandler().getNextState());
		};
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public State getState() {
		return this.state;
	}
}
