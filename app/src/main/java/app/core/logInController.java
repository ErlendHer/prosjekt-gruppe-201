package app.core;

import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class logInController extends ConnectionHandler {
	
/**
* Retrieves the password of the submitted user email.
* 
* @param String users email
* @return String users password if exists otherwise NULL
* @throws SQLException if password retrieval failed or fields changed.
*/
	public String executePasswordQuery(String userEmail) throws SQLException {
		var searchStatement = this.conn.prepareStatement("select password from User where email = (?)");
		searchStatement.setString(1, userEmail);
		ResultSet result = searchStatement.executeQuery();
		if (result.next()) {
		String userPassword = result.getString("password");
		return userPassword;
		}

		return null;
		}

/**
* Inserts the current date into the lastLoggedIn column of the User table
* 
* @param String user email
* @throws SQLException if update failed or fields changed.
*/
	public void updateLastLoginQuery(String userEmail) throws SQLException {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		var searchStatement = this.conn.prepareStatement("update User set lastLoggedIn = (?) where email = (?)");
		searchStatement.setTimestamp(1, timestamp);
		searchStatement.setString(2, userEmail);
		searchStatement.executeUpdate();
	}

/**
* Reads user email and password from console and decides whether the user submitted the correct log in information 
* 
* @param set ResultSet of the post
* @return formatted string
* @throws SQLException if post retrieval failed or fields changed.
*/	
  public void userLogIn() throws SQLException {
	  
	    System.out.println("Enter user email\n");
	    Scanner in = new Scanner(System.in);
	    String userEmail = in.nextLine();
	    System.out.println("Enter password\n");
	    String inputPassword = in.nextLine();

	    boolean access = grantUserAccess(userEmail, inputPassword);
	    updateLastLoginQuery(userEmail);

	    if (access) {
	      System.out.println("Welcome " + userEmail + '\n');
	    } else {
		System.out.println("Wrong email or password \n"); 
	    }
  }

/**
* Check if the user email input matches the corresponding user password 
* 
* @param String users email, String submitted password
* @return boolean variable where true is granted system access, and false is failure.
* @throws SQLException if post retrieval failed or fields changed.
*/

   private boolean grantUserAccess(String userEmail, String inputPassword) throws SQLException {
 
	String userPassword = executePasswordQuery(userEmail);
    
    if (userPassword.equals(inputPassword)) {
    	return true;
    } 
    return false;
  }
}