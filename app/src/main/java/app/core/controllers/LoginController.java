package app.core.controllers;

import java.sql.SQLException;

import app.Store;
import app.core.models.User;
import app.core.state.State;
import app.dao.UserDao;

public class LoginController extends AbstractController {

	private final UserDao userDao;

	public LoginController() {
		super();
		this.userDao = new UserDao();
	}

	@Override
	public boolean readInput() {
		this.clearInput();
		System.out.println("Fyll inn brukernavn:");
		readLine(false);

		System.out.println("Fyll inn passord:");
		readLine(false);

		try {
			User user = userDao.loginUser(inputs.get(0), inputs.get(1));
			if (user != null) {
				Store.setCurrentUser(user);
				this.setNextState(State.BROWSE);
				System.out.println(String.format("Du er n� logget inn som: %s", user.getEmail()));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

}
