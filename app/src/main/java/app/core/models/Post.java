package app.core.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * The Class Post.
 */
public class Post extends AbstractModel {

	private User user;
	private Integer threadID, userID, parentID;
	private int likes;
	private String content;
	private boolean isAnswer;
	private Timestamp datePosted, lastUpdated;
	private Post studentAnswer, instructorAnswer = null;
	private ArrayList<Post> followUps = new ArrayList<Post>();

	/**
	 * Instantiates a new post.
	 *
	 * @param threadID    the thread ID
	 * @param userID      the user ID
	 * @param parentID    the parent ID
	 * @param content     the content
	 * @param isAnswer    whether the post is an answer or not
	 * @param datePosted  the date posted
	 * @param lastUpdated the last updated
	 * @param user        the post owner
	 */
	public Post(Integer threadID, Integer userID, Integer parentID, String content, boolean isAnswer,
			Timestamp datePosted, Timestamp lastUpdated, User user) {
		super();
		this.threadID = threadID;
		this.userID = userID;
		this.parentID = parentID;
		this.content = content;
		this.isAnswer = isAnswer;
		this.datePosted = datePosted;
		this.lastUpdated = lastUpdated;
		this.likes = 0;
		this.user = user;
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
		this.likes = rs.getInt("likes");
		this.user = new User(rs);
	}

	/**
	 * Returns the post likes.
	 * 
	 * @return the amount of likes
	 */
	public int getLikes() {
		return this.likes;
	}

	/**
	 * Sets the post's likes.
	 * 
	 * @param likes the likes to set
	 */
	public void setLikes(int likes) {
		this.likes = likes;
	}

	/**
	 * Add a like to the post.
	 * 
	 */
	public void addLike() {
		this.likes++;
	}

	/**
	 * Check if post has a students answer.
	 * 
	 * @return true, if has students answer
	 */
	public boolean hasStudentAnswer() {
		if (this.studentAnswer != null) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the students answer.
	 * 
	 * @return the students answer
	 */
	public Post getStudentAnswer() {
		return this.studentAnswer;
	}

	/**
	 * Sets the students answer.
	 * 
	 * @param post the students answer
	 */
	public void setStudentAnswer(Post post) {
		this.studentAnswer = post;
	}

	/**
	 * Check if post has a instructors answer.
	 * 
	 * @return true, if has instructors answer
	 */
	public boolean hasInstructorsAnswer() {
		if (this.instructorAnswer != null) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the instructors answer.
	 * 
	 * @return the instructors answer
	 */
	public Post getInstructorAnswer() {
		return this.instructorAnswer;
	}

	/**
	 * Sets the instructors answer.
	 * 
	 * @param post the instructors answer
	 */
	public void setInstructorAnswer(Post post) {
		this.instructorAnswer = post;
	}

	/**
	 * Check if post has comments/follow ups
	 * 
	 * @return true, if has post has comments/follow ups
	 */
	public boolean hasFollowUps() {
		return !this.followUps.isEmpty();
	}

	/**
	 * Adds a follow up post to the thread.
	 * 
	 * @param post the follow up post to add
	 */
	public void addFollowUp(Post post) {
		this.followUps.add(post);
	}

	/**
	 * Removes a follow up post from the thread.
	 * 
	 * @param post the follow up post to remove
	 */
	public void removeFollowUp(Post post) {
		this.followUps.remove(post);
	}

	/**
	 * Returns all follow up discussions.
	 * 
	 * @return follow up discussions
	 */
	public ArrayList<Post> getFollowUps() {
		return this.followUps;
	}

	/**
	 * Gets the post owner.
	 * 
	 * @return the post owner
	 */
	public User getUser() {
		return this.user;
	}

	/**
	 * Sets the post owner.
	 * 
	 * @param user the post owner
	 */
	public void setUser(User user) {
		this.user = user;
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
	 * Returns an array of color codes based on the post's properties.
	 * 
	 * <ul>
	 * <li>[RED]: unanswered</li>
	 * <li>[YELLOW]: instructor has answered</li>
	 * <li>[GREEN]: student has answered</li>
	 * </ul>
	 * 
	 * @return colorCodes
	 */
	public ArrayList<String> getColorCodes() {
		ArrayList<String> colorCodes = new ArrayList<String>();
		if (this.hasInstructorsAnswer()) {
			colorCodes.add("YELLOW");
		}
		if (this.hasStudentAnswer()) {
			colorCodes.add("GREEN");
		}
		if (colorCodes.size() < 1 && !this.hasFollowUps()) {
			colorCodes.add("RED");
		}

		return colorCodes;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Post [postID=" + this.getId() + ", threadID=" + threadID + ", userID=" + userID + ", parentID=" + parentID
				+ ", content=" + content + ", isAnswer=" + isAnswer + ", datePosted=" + datePosted + ", lastUpdated="
				+ lastUpdated + "]";
	}

}