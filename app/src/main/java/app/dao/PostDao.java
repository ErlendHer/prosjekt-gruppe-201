package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import app.core.models.Post;
import app.core.models.ThreadPost;

public class PostDao {
	
	public boolean likePost(Integer postID, Integer userID) throws SQLException {
		boolean result = false;
		Connection conn = ConnectionHandler.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO LikedBy(postID, userID) "
				+ "SELECT ?, ? WHERE NOT EXISTS "
				+ "(SELECT * FROM LikedBy WHERE postID=? "
				+ "AND userID=?)"
			);
		ps.setInt(1, postID);
		ps.setInt(2, userID);
		ps.setInt(3, postID);
		ps.setInt(4, userID);
		int rowsAffected = ps.executeUpdate();
		if(rowsAffected > 0) {
			result = true;
		}
		conn.close();
		return result;
	}
	
	public boolean updatePostView(Integer userID, Integer threadID) throws SQLException {
		boolean result = false;
		Connection conn = ConnectionHandler.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO UserViews(userID, threadID) "
				+ "SELECT ?, ? WHERE NOT EXISTS "
				+ "(SELECT * FROM UserViews WHERE userID=? "
				+ "AND threadID=?)"
			);
		ps.setInt(1, userID);
		ps.setInt(2, threadID);
		ps.setInt(3, userID);
		ps.setInt(4, threadID);
		int rowsAffected = ps.executeUpdate();
		if(rowsAffected > 0) {
			result = true;
		}
		conn.close();
		return result;
	}
	
	public void insertThread(ThreadPost thread, String[] tags) throws SQLException {
		Connection conn = ConnectionHandler.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO Thread(folderID, title) VALUES(?, ?)"
				, Statement.RETURN_GENERATED_KEYS
			);
		ps.setInt(1, thread.getFolderId());
		ps.setString(2, thread.getTitle());
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		if(rs.next()) {
			int threadID = rs.getInt(1);
			thread.setId(threadID);
			thread.getOriginalPost().setThreadId(threadID);
			insertPost(thread.getOriginalPost());
			insertTags(threadID, tags);
		}
		conn.close();
	}
	
	private void insertTags(int threadID, String[] tags) throws SQLException {
		Connection conn = ConnectionHandler.getConnection();
		PreparedStatement ps = conn.prepareStatement("INSERT INTO ThreadTags() VALUES (?, ?)");
		for(String tag: tags) {
			ps.setInt(1, threadID);
			ps.setInt(2, getTag(tag.trim()));
			ps.addBatch();
			ps.clearParameters();
		}
		ps.executeBatch();
		conn.close();
	}
	
	private Integer getTag(String tag) throws SQLException {
		Integer result = null;
		Connection conn = ConnectionHandler.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO Tag(tagName) VALUES(?) " 
				+ "ON DUPLICATE KEY UPDATE tagID=LAST_INSERT_ID(tagID), tagName=?"
			);
		ps.setString(1, tag);
		ps.setString(2, tag);
		PreparedStatement ps2 = conn.prepareStatement(
				"SELECT LAST_INSERT_ID()"
			);
		ps.executeUpdate();
		ResultSet rs = ps2.executeQuery();
		if(rs.next()) {
			result = rs.getInt(1);
		}
		conn.close();
		return result;
	}

	public void insertPost(Post post) throws SQLException {
		Connection conn = ConnectionHandler.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO Post(threadID, userID, parentID, content, isAnswer, datePosted) VALUES(?, ?, ?, ?, ?, ?)"
				, Statement.RETURN_GENERATED_KEYS
			);
		ps.setInt(1, post.getThreadId());
		ps.setInt(2, post.getUserId());
		ps.setObject(3, post.getParentId());
		ps.setString(4, post.getContent());
		ps.setBoolean(5, post.isAnswer());
		ps.setTimestamp(6, post.getDatePosted());
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		if(rs.next()) {
			post.setId(rs.getInt(1));
		}
		conn.close();
	}
}
