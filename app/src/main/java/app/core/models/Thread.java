package app.core.models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The Class Thread.
 */
public class Thread extends AbstractModel {

	private Integer folderID;
	private String title;
	private Post threadPost;

	/**
	 * Instantiates a new thread.
	 *
	 * @param folderID the folder ID
	 * @param title the title
	 * @param threadPost the original post
	 */
	public Thread(Integer folderID, String title, Post threadPost) {
		super();
		this.folderID = folderID;
		this.title = title;
		this.threadPost = threadPost;
	}

	/**
	 * Instantiates a new thread.
	 *
	 * @param rs the database result set
	 * @throws SQLException the SQL exception
	 */
	public Thread(ResultSet rs) throws SQLException {
		super(rs.getInt("threadID"));
		this.folderID = rs.getInt("folderID");
		this.title = rs.getString("title");
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the folder id.
	 *
	 * @return the folder id
	 */
	public Integer getfolderId() {
		return this.folderID;
	}

	/**
	 * Sets the id.
	 *
	 * @param folderID the new id
	 */
	public void setId(Integer folderID) {
		this.folderID = folderID;
	}
	
	/**
	 * Gets the thread post.
	 * 
	 * @return the thread post
	 */
	public Post getThreadPost() {
		return this.threadPost;
	}
	
	/**
	 * Sets the thread post.
	 * 
	 * @param threadPost the original post
	 */
	public void setThreadPost(Post threadPost) {
		this.threadPost = threadPost;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Thread [threadID=" + this.getId() + ", folderID=" + folderID + ", title=" + title + "]";
	}

	

}
