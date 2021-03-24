package app.core.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The Class Thread.
 */
public class ThreadPost extends AbstractModel {

	private Integer folderID;
	private String title;
	private int views;
	private Post originalPost;
	private Map<Integer, Post> threadPosts;

	/**
	 * Instantiates a new thread.
	 *
	 * @param folderID the folder ID
	 * @param title    the title
	 */
	public ThreadPost(Integer folderID, String title) {
		super();
		this.folderID = folderID;
		this.title = title;
		this.threadPosts = new LinkedHashMap<Integer, Post>();
	}

	/**
	 * Instantiates a new thread.
	 *
	 * @param rs the database result set
	 * @throws SQLException the SQL exception
	 */
	public ThreadPost(ResultSet rs) throws SQLException {
		super(rs.getInt("threadID"));
		this.folderID = rs.getInt("folderID");
		this.title = rs.getString("title");
		this.views = rs.getInt("views");
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
	 * Returns the post's views.
	 * 
	 * @return the views count
	 */
	public int getViews() {
		return this.views;
	}

	/**
	 * Sets the post's views.
	 * 
	 * @param views the views to set
	 */
	public void setViews(int views) {
		this.views = views;
	}

	/**
	 * Add a view to the post.
	 * 
	 */
	public void addView() {
		this.views++;
	}

	/**
	 * Gets the folder id.
	 *
	 * @return the folder id
	 */
	public Integer getFolderId() {
		return this.folderID;
	}

	/**
	 * Sets the id.
	 *
	 * @param folderID the new id
	 */
	public void setFolderId(Integer folderID) {
		this.folderID = folderID;
	}

	/**
	 * Gets the original post.
	 * 
	 * @return the original post
	 */
	public Post getOriginalPost() {
		return this.originalPost;
	}

	/**
	 * Sets the original post.
	 * 
	 * @param originalPost the original post
	 */
	public void setOriginalPost(Post originalPost) {
		this.originalPost = originalPost;
	}

	/**
	 * Gets the thread posts.
	 * 
	 * @return the thread posts
	 */
	public Map<Integer, Post> getThreadPosts() {
		return this.threadPosts;
	}

	/**
	 * Sets the thread posts.
	 * 
	 * @param threadPosts the thread posts
	 */
	public void setThreadPosts(Map<Integer, Post> threadPosts) {
		this.threadPosts = threadPosts;
		this.setOriginalPost(threadPosts.entrySet().iterator().next().getValue());
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
