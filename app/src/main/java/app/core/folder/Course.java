package app.core.folder;

public class Course {

  private Folder root;
  private String courseCode;

  public Course(String courseCode, Folder root) {
    this.root = root;
    this.courseCode = courseCode;
  }

  public Folder getRoot() {
    return this.root;
  }

  public void setRoot(Folder root) {
    this.root = root;
  }

  public String getCourseCode() {
    return this.courseCode;
  }

  public void setCourseCode(String courseCode) {
    this.courseCode = courseCode;
  }

  public Course root(Folder root) {
    setRoot(root);
    return this;
  }

  public Course courseCode(String courseCode) {
    setCourseCode(courseCode);
    return this;
  }

  @Override
  public String toString() {
    return "{" + " root='" + getRoot() + "'" + ", courseCode='" + getCourseCode() + "'" + "}";
  }

}
