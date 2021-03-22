package app.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Controller {
	private Connection conn;
	
	public Controller() throws SQLException {
		this.conn = ConnectionHandler.getConnection();
		
		getUserStatistics();
	}
	
	private void usecase() throws SQLException {
		/**
		 * UC-2
		 */
		// Get folderID
		int folderID = getFolderID("TDT4145", "Exam");
		// Insert thread
		int threadID = createThread("HEi", folderID);
		// Add threadTags
		addTags(threadID, "Balle", "question", "Kuk", "Hei", "jaidmk");
		// Create the post
		int postID = createPost("Hei jeg er en pung", false, null, threadID, 1);
		/**
		 * UC-3
		 */
		// Post an answer as an Instructor
		createPost("Ja du er en pung", true, postID, threadID, 10);
	}
	
	private Integer createPost(String content, boolean isAnswer, Integer parentID, Integer threadID, Integer userID) throws SQLException {
		String postQuery = "INSERT INTO Post(content, isAnswer, datePosted, lastUpdated, parentID, threadID, userID) VALUES(?,?,?,?,?,?,?)";
		PreparedStatement ps = this.conn.prepareStatement(postQuery, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, content);
		ps.setBoolean(2, isAnswer);
		ps.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
		ps.setTimestamp(4, null);
		ps.setObject(5, parentID);
		ps.setInt(6, threadID);
		ps.setInt(7, userID);
		ps.executeUpdate();
		// Return newly inserted Post
		ResultSet rs = ps.getGeneratedKeys();
		if(rs.next()) {
			return rs.getInt(1);
		}
		return null;
	}
	
	private Integer createThread(String title, int folderID) throws SQLException {
		String threadQuery = "INSERT INTO Thread(title, folderID) VALUES (?, ?)";
		PreparedStatement ps = this.conn.prepareStatement(threadQuery, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, title);
		ps.setInt(2, folderID);
		ps.executeUpdate();
		
		ResultSet rs = ps.getGeneratedKeys();
		if(rs.next()) {
			return rs.getInt(1);
		}
		return null;
	}
	
	private Integer getFolderID(String courseCode, String folderName) throws SQLException {
		String folderQuery = "SELECT F.FolderID FROM Course AS C NATURAL JOIN Folder AS F WHERE C.courseCode=? AND F.folderName=? LIMIT 1";
		PreparedStatement ps = this.conn.prepareStatement(folderQuery);
		ps.setString(1, courseCode);
		ps.setString(2, folderName);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			return rs.getInt("folderID");
		}
		return null;
	}
	
	private void addTags(int threadID, String... tags) throws SQLException {
		String threadTagQuery = "INSERT INTO ThreadTags() VALUES (?, ?)";
		PreparedStatement ps = this.conn.prepareStatement(threadTagQuery);
		
		for(String tag: tags) {
			ps.setInt(1, threadID);
			ps.setInt(2, getTag(tag));
			ps.addBatch();
			ps.clearParameters();
		}
		ps.executeBatch();
	}
	
	private Integer getTag(String tag) throws SQLException {
		String tagQuery = "SELECT tagID FROM Tag WHERE tagName=?";
		PreparedStatement ps = this.conn.prepareStatement(tagQuery);
		ps.setString(1, tag);
		
		ResultSet rs = ps.executeQuery();
		// If no tag matches, create a new tag
		if(!rs.next()) {
			String query = "INSERT INTO Tag(tagName) VALUES (?)";
			PreparedStatement nps = this.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			nps.setString(1, tag);
			nps.executeUpdate();
			// Return newly inserted Tag
			ResultSet nrs = nps.getGeneratedKeys();
			nrs.next();
			return nrs.getInt(1);
		}
		return rs.getInt("tagID");
	}
	
	private void printStatistics(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		String format = "%-30s| %-30s| %-30s";
		System.out.println(String.format(format, "Username", "Number of posts read", "Number of posts created"));
		while (rs.next()) {
			System.out.println(String.format(format, rs.getString(1), rs.getString(2), rs.getString(3)));
		}
	}
	
	private void getUserStatistics() throws SQLException {
		// Select posts read
		String prQuery = "SELECT userID, count(*) AS PR FROM UserViews"
				+ " GROUP BY userID";
		// Select posts created
		String pcQuery = 
				"SELECT userID, count(*) AS PC FROM"
				+ " Post WHERE parentID IS NULL"
				+ " GROUP BY userID";
		String statQuery = String.format("SELECT email AS 'Username', UV.PR AS 'Number of posts read', P.PC AS 'Number of posts created'"
				+ " FROM User as U"
				+ " LEFT JOIN (%s) AS UV ON UV.userID = U.userID"
				+ " LEFT JOIN (%s) AS P ON P.userID = U.userID", prQuery, pcQuery);
		PreparedStatement ps = this.conn.prepareStatement(statQuery);
		ResultSet rs = ps.executeQuery();
		printStatistics(rs);
	}
}
