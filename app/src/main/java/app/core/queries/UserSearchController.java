package app.core.queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import app.dao.ConnectionHandler;

public class UserSearchController {

  private PreparedStatement searchStatement;
  private PreparedStatement getPostStatement;

  /**
   * Setup the prepared query statements for the controller.
   */
  public void setupQuery() {
    try {
      Connection conn = ConnectionHandler.getConnection();

      searchStatement = conn.prepareStatement("SELECT P.postID " + "FROM POST as P NATURAL JOIN Thread AS T "
          + "INNER JOIN FOLDER as F ON T.FolderID = F.FolderID" + " WHERE P.content LIKE (?) OR T.Title LIKE (?)");

      getPostStatement = conn.prepareStatement(
          "SELECT content, isAnswer, datePosted, firstName, lastName, isStudent FROM Post NATURAL JOIN THREAD NATURAL JOIN USER WHERE Post.postID = (?)");
    } catch (Exception e) {
      System.out.println("Error occured during prepared SELECT statement");
    }
  }

  /**
   * Get the ids of the posts that match the given word
   * 
   * @param wordMatch word to match
   * @return list of matched post ids
   */
  private ArrayList<Integer> getPostIdsByWordMatch(String wordMatch) {
    try {

      String pattern = "%" + wordMatch + "%";
      searchStatement.setString(1, pattern);
      searchStatement.setString(2, pattern);
      var result = searchStatement.executeQuery();

      ArrayList<Integer> postIds = new ArrayList<>();

      while (result.next()) {
        postIds.add(result.getInt("PostID"));
      }

      return postIds;

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Get the ids of the posts that match the given word
   * 
   * @param wordMatch word to match
   * @return list of matched post ids
   */
  public ArrayList<Integer> executeSearchQuery(String wordMatch) {
    return getPostIdsByWordMatch(wordMatch);
  }

  /**
   * Get a post ResultSet on the given ID
   * 
   * @param id id to get the post on.
   * @return ResultSet of the given post, null if it doesn't exist
   */
  private ResultSet getPostById(int id) {
    try {
      getPostStatement.setInt(1, id);
      return getPostStatement.executeQuery();
    } catch (Exception e) {
      System.out.println("Error fetching post by id " + id);
    }
    return null;
  }

  /**
   * Split a long string into an array of smaller strings based on the
   * maxCharInLine restriction
   * 
   * @param input         string to split
   * @param maxCharInLine number of chars per line
   * @return String[] of evenly split lines
   */
  private String[] splitStringToLines(String input, int maxCharInLine) {

    StringTokenizer tok = new StringTokenizer(input, " ");
    StringBuilder output = new StringBuilder(input.length());
    int lineLen = 0;
    while (tok.hasMoreTokens()) {
      String word = tok.nextToken();

      while (word.length() > maxCharInLine) {
        output.append(word.substring(0, maxCharInLine - lineLen) + "\n");
        word = word.substring(maxCharInLine - lineLen);
        lineLen = 0;
      }

      if (lineLen + word.length() > maxCharInLine) {
        output.append("\n");
        lineLen = 0;
      }
      output.append(word + " ");

      lineLen += word.length() + 1;
    }

    return output.toString().split("\n");
  }

  /**
   * Represent the post resultSet on String format.
   * 
   * @param set ResultSet of the post
   * @return formatted string
   * @throws SQLException if post retrival failed or fields changed.
   */
  private String formatUserResultSet(ResultSet set) throws SQLException {
    set.next();
    StringBuilder postBuilder = new StringBuilder();

    postBuilder.append("|---------------POST---------------|\n");
    postBuilder.append((String.format("  Post by %s %s %s\n", set.getInt("isStudent") == 1 ? "student" : "instructor",
        set.getString("firstName"), set.getString("lastName"))));
    postBuilder.append(String.format("  Posted %s\n", set.getString("datePosted")));
    postBuilder.append("  Content:\n");
    String[] contentLines = splitStringToLines(set.getString("content"), 40);

    for (String line : contentLines) {
      postBuilder.append(String.format("    %s\n", line));
    }

    postBuilder.append("|-------------POST END-------------|\n");

    return postBuilder.toString();
  }

  public ArrayList<String> executeSearchQueryStringFormatted(String wordMatch) throws SQLException {
    ArrayList<Integer> ids = getPostIdsByWordMatch(wordMatch);

    for (int id : ids) {
      var post = getPostById(id);
      if (post == null) {
        throw new SQLException("Post with id '" + id + "' does not exist");
      }
      System.out.println(formatUserResultSet(post));
    }

    return null;
  }

}
