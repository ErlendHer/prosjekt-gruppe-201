package app;

import java.sql.SQLException;

import app.core.state.StateManager;


public class App {
	
	public static void main(String[] args) throws SQLException {
		new Store();
		StateManager manager = new StateManager();
		
		while(true) {
			manager.handleState();
		}
	}
}

