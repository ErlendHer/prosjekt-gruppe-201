package app.core.models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The Class User.
 */
public class User extends AbstractModel {

	private String email, firstName, lastName;

	private boolean isStudent, isInstructor;
	
	/**
	 * Instantiates a new user.
	 *
	 * @param email the email
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param isStudent whether user is student or not
	 * @param isInstructor whether user is instructor or not
	 */
	public User(String email, String firstName, String lastName, boolean isStudent, boolean isInstructor) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.isStudent = isStudent;
		this.isInstructor = isInstructor;
	}
	
	/**
	 * Instantiates a new user.
	 *
	 * @param rs the database result set
	 * @throws SQLException the SQL exception
	 */
	public User(ResultSet rs) throws SQLException {
		super(rs.getInt("userID"));
		this.email = rs.getString("email");
		this.firstName = rs.getString("firstName");
		this.lastName = rs.getString("lastName");
		this.isStudent = rs.getBoolean("isStudent");
		this.isInstructor = rs.getBoolean("isInstructor");
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Checks if the user is a student.
	 *
	 * @return true, if is student
	 */
	public boolean isStudent() {
		return isStudent;
	}

	/**
	 * Sets the student property.
	 *
	 * @param isStudent whether the user is a student or not
	 */
	public void setStudent(boolean isStudent) {
		this.isStudent = isStudent;
	}

	/**
	 * Checks if the user is an instructor.
	 *
	 * @return true, if is instructor
	 */
	public boolean isInstructor() {
		return isInstructor;
	}

	/**
	 * Sets the instructor property.
	 *
	 * @param isInstructor whether the user is an instructor or not
	 */
	public void setInstructor(boolean isInstructor) {
		this.isInstructor = isInstructor;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "User [userID=" + this.getId() + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", isStudent="
				+ isStudent + ", isInstructor=" + isInstructor + "]";
	}
	
}
