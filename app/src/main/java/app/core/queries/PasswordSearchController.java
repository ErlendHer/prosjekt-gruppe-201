package app.core.queries;

import java.util.Scanner;

import app.dao.ConnectionHandler;

import java.sql.*;

public class PasswordSearchController {

  public String executePasswordQuery(String userEmail) throws SQLException {
    Connection conn = ConnectionHandler.getConnection();

    var searchStatement = conn.prepareStatement("select password from User where email = (?)");
    searchStatement.setString(1, userEmail);
    ResultSet result = searchStatement.executeQuery();
    if (result.next()) {
      String userPassword = result.getString("password");
      return userPassword;
    }

    return null;
  }

  public void userLogIn() throws SQLException {
    System.out.println("Enter user email\n");
    Scanner in = new Scanner(System.in);
    String userEmail = in.nextLine();
    System.out.println("Enter password\n");
    String inputPassword = in.nextLine();

    boolean access = grantUserAccess(userEmail, inputPassword);

    if (access) {
      System.out.println("Welcome " + userEmail + '\n');
    }
    System.out.println("Wrong userEmail or password \n");
  }

  private boolean grantUserAccess(String userEmail, String inputPassword) throws SQLException {
    String userPassword = executePasswordQuery(userEmail);
    System.out.println(userPassword);
    if (userPassword == inputPassword) {
      return true;
    }
    return false;
  }
}
