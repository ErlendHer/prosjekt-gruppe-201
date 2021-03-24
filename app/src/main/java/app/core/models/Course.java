package app.core.models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The Class Course represents a Course-object fetched from the Database.
 * 
 * @see Folder
 */
public class Course extends AbstractModel {

  private String courseCode, courseName;
  private int term;
  private Folder root = null;

  /**
   * Instantiates a new course.
   *
   * @param courseCode the course code
   * @param courseName the course name
   * @param term the term
   */
  public Course(String courseCode, String courseName, int term) {
    super();
    this.courseCode = courseCode;
    this.courseName = courseName;
    this.term = term;
  }

  /**
   * Instantiates a new course.
   *
   * @param rs the database result set
   * @throws SQLException the SQL exception
   */
  public Course(ResultSet rs) throws SQLException {
    super(rs.getInt("courseID"));
    this.courseCode = rs.getString("courseCode");
    this.courseName = rs.getString("courseName");
    this.term = rs.getInt("term");
  }

  /**
   * Sets the root folder.
   *
   * @param folder the new root folder
   */
  public void setRoot(Folder folder) {
    this.root = folder;
  }

  /**
   * Gets the root folder.
   *
   * @return the root folder
   */
  public Folder getRoot() {
    return this.root;
  }

  /**
   * Gets the course code.
   *
   * @return the course code
   */
  public String getCourseCode() {
    return courseCode;
  }

  /**
   * Sets the course code.
   *
   * @param courseCode the new course code
   */
  public void setCourseCode(String courseCode) {
    this.courseCode = courseCode;
  }

  /**
   * Gets the course name.
   *
   * @return the course name
   */
  public String getCourseName() {
    return courseName;
  }

  /**
   * Sets the course name.
   *
   * @param courseName the new course name
   */
  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  /**
   * Gets the term.
   *
   * @return the term
   */
  public int getTerm() {
    return term;
  }

  /**
   * Sets the term.
   *
   * @param term the new term
   */
  public void setTerm(int term) {
    this.term = term;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "Course [courseID=" + this.getId() + ", courseCode=" + courseCode + ", courseName="
        + courseName + ", term=" + term + "]";
  }

}
