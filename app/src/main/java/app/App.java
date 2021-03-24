package app;

import java.sql.SQLException;

import app.core.state.StateManager;

/**
 * Application entry point. All logic will be accessed from the terminal. Type
 * "help" to see list of available commands.
 */
public class App {

	public static void main(String[] args) throws SQLException {
		new Store();
		StateManager manager = new StateManager();
		while (true) {
			manager.handleState();
		}
	}
}
