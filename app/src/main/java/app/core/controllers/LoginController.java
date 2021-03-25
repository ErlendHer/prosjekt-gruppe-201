package app.core.controllers;

import java.sql.SQLException;

import app.Store;
import app.core.models.User;
import app.core.state.State;
import app.dao.UserDao;

/**
 * The Class LoginController handles User login. Reads username and password
 * from user input and verifies the user by looking up the column where username
 * and password matches in the database.
 */
public class LoginController extends AbstractController {

	private final UserDao userDao;

	/**
	 * Instantiates a new login controller.
	 */
	public LoginController() {
		super();
		this.userDao = new UserDao();
	}

	/**
	 * Read user input and attempt to login user.
	 *
	 * @return true, if login attempt is successful
	 */
	@Override
	public boolean readInput() {
		this.clearInput();
		System.out.println("Enter username:");
		readLine();

		System.out.println("Enter password:");
		readLine();

		try {
			User user = userDao.loginUser(inputs.get(0), inputs.get(1));
			if (user != null) {
				// Store the logged in user in store, and proceed to Browse-state
				Store.setCurrentUser(user);
				this.setNextState(State.BROWSE);
				System.out.println(String.format("\n**You are logged in as: %s**\n", user.getEmail()));
				return true;
			} else {
				System.out.println("\n**Invalid credentials, try again**\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

}
