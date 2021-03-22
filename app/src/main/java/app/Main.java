package app;

import app.core.ConnectionHandler;
import app.core.logInController;

public class Main {

	public static void main(String[] args) {
		ConnectionHandler conn = new ConnectionHandler();
		conn.connect();
		
		try {
			logInController pController = new logInController();
			pController.connect();
			pController.userLogIn();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
	}
	}
}
