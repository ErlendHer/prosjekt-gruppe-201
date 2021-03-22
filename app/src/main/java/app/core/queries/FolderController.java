package app.core.queries;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import app.core.ConnectionHandler;
import app.core.folder.Folder;

public class FolderController extends ConnectionHandler {

  private PreparedStatement getFoldersStatement;
  private PreparedStatement getCoursesStatement;
  private PreparedStatement getThreadsStatement;

  /**
   * Setup the prepared query statements for the controller.
   */
  public void setupQuery() {
    try {
      getFoldersStatement = conn.prepareStatement(
          "SELECT folderName, folderID, parentID FROM Folder NATURAL JOIN Course WHERE Course.courseCode=(?)");

      getCoursesStatement = conn.prepareStatement("SELECT CourseCode from Course");

      getThreadsStatement = conn.prepareStatement("SELECT title FROM Thread NATURAL JOIN Folder WHERE folderId=(?)");

    } catch (Exception e) {
      System.out.println("Error occured during prepared SELECT statement");
      e.printStackTrace();
    }
  }

  /**
   * Execute an sql query that gets all available folders of the given course and
   * returns a list of therof generated Folder objects.
   * 
   * @param courseCoude course code
   * @return List of folders.
   */
  public ArrayList<Folder> executeGetFolders(String courseCoude) {
    try {
      getFoldersStatement.setString(1, courseCoude);
      var result = getFoldersStatement.executeQuery();

      ArrayList<Folder> folders = new ArrayList<>();

      while (result.next()) {
        folders.add(new Folder(result.getString("folderName"), result.getInt("folderID"), result.getInt("parentID")));
      }

      return folders;

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Execute an SQL query that returns all availible course codes.
   * 
   * @return
   */
  public ArrayList<String> executeGetCourses() {
    try {
      var result = getCoursesStatement.executeQuery();
      ArrayList<String> courseCodes = new ArrayList<>();

      while (result.next()) {
        courseCodes.add(result.getString("CourseCode"));
      }

      return courseCodes;
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null;
  }

  /**
   * Get all thread titles of a given folderID
   * 
   * @param folderId folderID
   * @return List of thread ids
   */
  public ArrayList<String> executeGetThreads(int folderId) {
    try {
      getThreadsStatement.setInt(1, folderId);
      var result = getThreadsStatement.executeQuery();

      ArrayList<String> titles = new ArrayList<>();

      while (result.next()) {
        titles.add(result.getString("title"));
      }

      return titles;

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null;
  }
}
