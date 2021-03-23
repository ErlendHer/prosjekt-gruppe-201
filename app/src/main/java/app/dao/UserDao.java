package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.core.models.User;

public class UserDao {
	
	public User loginUser(String inputEmail, String inputPassword) throws SQLException {
		User result = null;
		Connection conn = ConnectionHandler.getConnection();
		PreparedStatement searchStatement = conn.prepareStatement("SELECT * FROM User WHERE email = (?) AND password = (?) LIMIT 1");
		searchStatement.setString(1, inputEmail);
		searchStatement.setString(2, inputPassword);
		ResultSet rs = searchStatement.executeQuery();
		if (rs.next()) {
			result = new User(rs);
		}
		conn.close();
		return result;
	  }
}
