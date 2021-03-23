package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import app.core.models.Course;
import app.core.models.Folder;
import app.core.models.Post;
import app.core.models.Thread;

public class ForumDao {
	
	public ArrayList<Course> getCourses() throws SQLException {
		ArrayList<Course> result = new ArrayList<Course>();
		Connection conn = ConnectionHandler.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM Course");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			result.add(new Course(rs));
		}
		conn.close();
		return result;
	  }
	
	public ArrayList<Folder> getCourseFolders(Integer courseID) throws SQLException {
		ArrayList<Folder> result = new ArrayList<Folder>();
		Connection conn = ConnectionHandler.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				"SELECT * FROM Folder NATURAL JOIN Course "
				+ "WHERE Course.courseID=(?)"
			);
		ps.setInt(1, courseID);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			result.add(new Folder(rs));
		}
		conn.close();
		return result;
	}
	
	public ArrayList<Thread> getFolderThreads(Integer folderID) throws SQLException {
		ArrayList<Thread> result = new ArrayList<Thread>();
		Connection conn = ConnectionHandler.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				"SELECT * FROM Thread AS T "
				+ "NATURAL JOIN Folder "
				+ "LEFT JOIN (SELECT * FROM Post "
				+ "WHERE parentID IS NULL) AS P ON P.threadID = T.threadID "
				+ "WHERE T.folderId=(?)"
			);
		ps.setInt(1, folderID);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Thread thread = new Thread(rs);
			thread.setThreadPost(new Post(rs));
			result.add(thread);
		}
		conn.close();
		return result;
	}
}
