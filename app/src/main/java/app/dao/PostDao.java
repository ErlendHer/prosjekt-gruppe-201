package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import app.core.models.Post;
import app.core.models.User;

public class PostDao {
	
	public boolean likePost(Integer postID, Integer userID) throws SQLException {
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
			return true;
		}
		return false;
	}
	
	public boolean updatePostView(Integer userID, Integer threadID) throws SQLException {
		System.out.println("Updating view");
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
			return true;
		}
		return false;
	}

	public void insertPost(Post post) throws SQLException {
		Connection conn = ConnectionHandler.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO Post(threadID, userID, parentID, content, isAnswer, datePosted) VALUES(?, ?, ?, ?, ?, ?)"
				, Statement.RETURN_GENERATED_KEYS
			);
		ps.setInt(1, post.getThreadId());
		ps.setInt(2, post.getUserId());
		ps.setInt(3, post.getParentId());
		ps.setString(4, post.getContent());
		ps.setBoolean(5, post.isAnswer());
		ps.setTimestamp(6, post.getDatePosted());
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		if(rs.next()) {
			post.setId(rs.getInt(1));
		}
	}
}
