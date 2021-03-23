package app.core.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * The Class Post.
 */
public class Post extends AbstractModel {

	
	private Integer threadID, userID, parentID;
	private String content;
	private boolean isAnswer;
	private Timestamp datePosted, lastUpdated;
	
	
	/**
	 * Instantiates a new post.
	 *
	 * @param threadID the thread ID
	 * @param userID the user ID
	 * @param parentID the parent ID
	 * @param content the content
	 * @param isAnswer whether the post is an answer or not
	 * @param datePosted the date posted
	 * @param lastUpdated the last updated
	 */
	public Post(Integer threadID, Integer userID, Integer parentID, String content, boolean isAnswer, Timestamp datePosted, Timestamp lastUpdated) {
		super();
		this.threadID = threadID;
		this.userID = userID;
		this.parentID = parentID;
		this.content = content;
		this.isAnswer = isAnswer;
		this.datePosted = datePosted;
		this.lastUpdated = lastUpdated;
	}
	
	/**
	 * Instantiates a new post.
	 *
	 * @param rs the database result set
	 * @throws SQLException the SQL exception
	 */
	public Post(ResultSet rs) throws SQLException {
		super(rs.getInt("postID"));
		this.threadID = rs.getInt("threadID");
		this.userID = rs.getInt("userID");
		this.parentID = rs.getInt("parentID");
		this.content = rs.getString("content");
		this.isAnswer = rs.getBoolean("isAnswer");
		this.datePosted = rs.getTimestamp("datePosted");
		this.lastUpdated = rs.getTimestamp("lastUpdated");
	}

	/**
	 * Gets the thread Id.
	 *
	 * @return the thread Id
	 */
	public Integer getThreadId() {
		return threadID;
	}

	/**
	 * Sets the thread Id.
	 *
	 * @param threadID the new thread Id
	 */
	public void setThreadId(Integer threadID) {
		this.threadID = threadID;
	}

	/**
	 * Gets the user Id.
	 *
	 * @return the user Id
	 */
	public Integer getUserId() {
		return userID;
	}

	/**
	 * Sets the user Id.
	 *
	 * @param userID the new user Id
	 */
	public void setUserId(Integer userID) {
		this.userID = userID;
	}

	/**
	 * Gets the parent Id.
	 *
	 * @return the parent Id
	 */
	public Integer getParentId() {
		return parentID;
	}

	/**
	 * Sets the parent Id.
	 *
	 * @param parentID the new parent Id
	 */
	public void setParentId(Integer parentID) {
		this.parentID = parentID;
	}
	
	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the content.
	 *
	 * @param content the new content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Gets the date posted timestamp.
	 *
	 * @return the date posted timestamp
	 */
	public Timestamp getDatePosted() {
		return datePosted;
	}

	/**
	 * Sets the date posted timestamp.
	 *
	 * @param datePosted the new date posted timestamp
	 */
	public void setDatePosted(Timestamp datePosted) {
		this.datePosted = datePosted;
	}

	/**
	 * Gets the last updated timestamp.
	 *
	 * @return the last updated timestamp
	 */
	public Timestamp getLastUpdated() {
		return lastUpdated;
	}

	/**
	 * Sets the last updated timestamp.
	 *
	 * @param lastUpdated the new last updated timestamp
	 */
	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	/**
	 * Checks if the post is an answer.
	 *
	 * @return true, if is answer
	 */
	public boolean isAnswer() {
		return isAnswer;
	}

	/**
	 * Sets the answer property.
	 *
	 * @param isAnswer whether the post is an answer or not
	 */
	public void setAnswer(boolean isAnswer) {
		this.isAnswer = isAnswer;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Post [postID=" + this.getId() + ", threadID=" + threadID + ", userID=" + userID + ", parentID=" + parentID + ", content=" + content
				+ ", isAnswer=" + isAnswer + ", datePosted=" + datePosted + ", lastUpdated=" + lastUpdated + "]";
	}
	
}
