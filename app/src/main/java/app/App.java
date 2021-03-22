package app;

import java.sql.SQLException;

import app.Store.StoreObject;
import app.core.ConnectionHandler;
import app.core.Controller;
import app.state.StateManager;


public class App {
	

	public static void main(String[] args) throws SQLException {
		//Controller conn = new Controller();
		new Store();
		StateManager manager = new StateManager();
		
		while(true) {
			manager.handleState();
		}
		
		
	}
}

