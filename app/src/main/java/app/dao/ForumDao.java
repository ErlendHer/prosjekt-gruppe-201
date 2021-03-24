package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import app.core.models.Course;
import app.core.models.Folder;
import app.core.models.Post;
import app.core.models.ThreadPost;

public class ForumDao {

	public ArrayList<Course> getCourses() throws SQLException {
		ArrayList<Course> result = new ArrayList<Course>();
		Connection conn = ConnectionHandler.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM Course");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			result.add(new Course(rs));
		}
		conn.close();
		return result;
	}

	public ArrayList<Folder> getCourseFolders(Integer courseID) throws SQLException {
		ArrayList<Folder> result = new ArrayList<Folder>();
		Connection conn = ConnectionHandler.getConnection();
		PreparedStatement ps = conn
				.prepareStatement("SELECT * FROM Folder NATURAL JOIN Course " + "WHERE Course.courseID=(?)");
		ps.setInt(1, courseID);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			result.add(new Folder(rs));
		}
		conn.close();
		return result;
	}

	public ArrayList<ThreadPost> getFolderThreads(Integer folderID) throws SQLException {
		ArrayList<ThreadPost> result = new ArrayList<ThreadPost>();
		Connection conn = ConnectionHandler.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM Thread AS T " + "NATURAL JOIN Folder "
				+ "LEFT JOIN (SELECT * FROM Post " + "WHERE parentID IS NULL) AS P ON P.threadID = T.threadID "
				+ "LEFT JOIN (SELECT userID AS U, count(*) AS views " + "FROM UserViews "
				+ "GROUP BY userID) as UV ON UV.U = P.userID " + "WHERE T.folderId=(?)");
		ps.setInt(1, folderID);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			ThreadPost thread = new ThreadPost(rs);
			thread.setThreadPosts(buildThread(thread));
			result.add(thread);
		}
		conn.close();
		return result;
	}

	public Map<Integer, Post> buildThread(ThreadPost thread) throws SQLException {
		Post threadPost = null;
		Connection conn = ConnectionHandler.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM Post AS P " + "NATURAL JOIN User AS U "
				+ "LEFT JOIN (SELECT postID AS LP, count(*) AS likes " + "FROM LikedBy "
				+ "GROUP BY postID) as PL ON PL.LP = P.postID " + "WHERE threadID = (?) " + "ORDER BY datePosted ASC");
		ps.setInt(1, thread.getId());
		ResultSet rs = ps.executeQuery();
		Map<Integer, Post> postMap = new LinkedHashMap<Integer, Post>();
		while (rs.next()) {
			Post post = new Post(rs);
			postMap.put(post.getId(), post);
			if (post.getParentId() == 0) {
				threadPost = post;
			} else if (post.isAnswer()) {
				if (post.getUser().isStudent()) {
					threadPost.setStudentAnswer(post);
				} else {
					threadPost.setInstructorAnswer(post);
				}
			} else {
				if (post.getParentId() == threadPost.getId()) {
					threadPost.addFollowUp(post);
				} else {
					ArrayList<Post> queue = new ArrayList<Post>(List.of(threadPost));
					while (queue.size() > 0) {
						Post currPost = queue.remove(queue.size() - 1);
						for (Post followUp : currPost.getFollowUps()) {
							if (post.getParentId() == followUp.getId()) {
								followUp.addFollowUp(post);
								break;
							} else {
								queue.add(followUp);
							}
						}
					}
				}
			}
		}
		conn.close();
		return postMap;
	}

	public ArrayList<Post> getPostsByKeyword(String keyword) throws SQLException {
		Connection conn = ConnectionHandler.getConnection();
		ArrayList<Post> posts = new ArrayList<Post>();

		var searchStatement = conn.prepareStatement("SELECT P.postID " + "FROM POST as P NATURAL JOIN Thread AS T "
				+ "INNER JOIN FOLDER as F ON T.FolderID = F.FolderID" + " WHERE P.content LIKE (?) OR T.Title LIKE (?)");

		var getPostStatement = conn.prepareStatement(
				"SELECT content, isAnswer, datePosted, firstName, lastName, isStudent FROM Post NATURAL JOIN THREAD NATURAL JOIN USER WHERE Post.postID = (?)");

		var ids = getPostIdsByWordMatch(searchStatement, keyword);
		return posts;
	}

	/**
	 * Get the ids of the posts that match the given word
	 * 
	 * @param keyword word to match
	 * @return list of matched post ids
	 */
	private ArrayList<Integer> getPostIdsByWordMatch(PreparedStatement searchStatement, String keyword) {
		try {
			String pattern = "%" + keyword + "%";
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
	 * Get a post ResultSet on the given ID
	 * 
	 * @param id id to get the post on.
	 * @return ResultSet of the given post, null if it doesn't exist
	 */
	private ResultSet getPostById(PreparedStatement getPostStatement, int id) {
		try {
			getPostStatement.setInt(1, id);
			return getPostStatement.executeQuery();
		} catch (Exception e) {
			System.out.println("Error fetching post by id " + id);
		}
		return null;
	}
}