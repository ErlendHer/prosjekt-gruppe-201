package app.state.handlers;

import java.sql.SQLException;

import app.Store;
import app.Store.StoreObject;
import app.core.dao.UserDao;
import app.models.User;
import app.state.State;

public class LoginStateHandler extends AbstractStateHandler {
	
	private UserDao userDao;

	public LoginStateHandler() {
		super();
		this.userDao = new UserDao();
	}

	@Override
	public boolean readInput() {
		System.out.println("Fyll inn brukernavn:");
		readLine(false);
		
		System.out.println("Fyll inn passord:");
		readLine(false);
		
		try {
			User user = userDao.loginUser(inputs.get(0), inputs.get(1));
			if(user != null) {
				Store.setStoreValue(StoreObject.CurrentUser, user);
				this.setNextState(State.POST);
				System.out.println(String.format("Du er nå logget inn som: %s", user.getEmail()));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
