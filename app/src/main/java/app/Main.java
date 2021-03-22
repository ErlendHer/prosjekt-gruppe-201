package app;

import java.sql.SQLException;

import app.core.ConnectionHandler;
import app.core.Console;
import app.core.queries.PasswordSearchController;
import app.core.queries.UserSearchController;

public class Main {

	public static void main(String[] args) {
		// ConnectionHandler conn = new ConnectionHandler();
		// conn.connect();

		Console console = new Console();
		console.run();

		// UserSearchController controller = new UserSearchController();
		// controller.connect();
		// controller.setupQuery();
		// try {
		// controller.executeSearchQueryStringFormatted("wal");
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// try {
		// PasswordSearchController pController = new PasswordSearchController();
		// pController.connect();
		// pController.userLogIn();
		// } catch (Exception e) {
		// // TODO: handle exception
		// }

	}

}
