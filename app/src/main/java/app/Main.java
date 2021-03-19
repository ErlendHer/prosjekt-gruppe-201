package app;

import app.core.ConnectionHandler;

public class Main {

	public static void main(String[] args) {
		ConnectionHandler conn = new ConnectionHandler();
		conn.connect();
	}
}
