package app.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User extends AbstractModel {

	private String email, firstName, lastName;
	private boolean isStudent, isInstructor;
	
	public User(String email, String firstName, String lastName, boolean isStudent, boolean isInstructor) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.isStudent = isStudent;
		this.isInstructor = isInstructor;
	}
	
	public User(ResultSet rs) throws SQLException {
		super(rs);
		this.email = rs.getString("email");
		this.firstName = rs.getString("firstName");
		this.lastName = rs.getString("lastName");
		this.isStudent = rs.getBoolean("isStudent");
		this.isInstructor = rs.getBoolean("isInstructor");
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isStudent() {
		return isStudent;
	}

	public void setStudent(boolean isStudent) {
		this.isStudent = isStudent;
	}

	public boolean isInstructor() {
		return isInstructor;
	}

	public void setInstructor(boolean isInstructor) {
		this.isInstructor = isInstructor;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", isStudent="
				+ isStudent + ", isInstructor=" + isInstructor + "]";
	}
	
}
