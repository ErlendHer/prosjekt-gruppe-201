package app.core.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The Class Folder.
 */
public class Folder extends AbstractModel {

	private String folderName;
	private Integer courseID, parentID;
	private boolean allowAnonymous;

	private ArrayList<Folder> subfolders;
	private ArrayList<ThreadPost> threads = new ArrayList<ThreadPost>();
	private Folder parent;

	/**
	 * Instantiates a new folder.
	 *
	 * @param courseID       the course ID
	 * @param parentID       the parent ID
	 * @param folderName     the folder name
	 * @param allowAnonymous whether the folder allows for anonymous posts or not
	 */
	public Folder(Integer courseID, Integer parentID, String folderName, boolean allowAnonymous) {
		super();
		this.courseID = courseID;
		this.parentID = parentID;
		this.folderName = folderName;
		this.allowAnonymous = allowAnonymous;
		subfolders = new ArrayList<Folder>();
	}

	/**
	 * Instantiates a new folder.
	 *
	 * @param rs the database result set
	 * @throws SQLException the SQL exception
	 */
	public Folder(ResultSet rs) throws SQLException {
		super(rs.getInt("folderID"));
		this.courseID = rs.getInt("courseID");
		this.parentID = rs.getInt("parentID");
		this.folderName = rs.getString("folderName");
		this.allowAnonymous = rs.getBoolean("allowAnonymous");
		subfolders = new ArrayList<Folder>();
	}

	/**
	 * Gets the course id.
	 *
	 * @return the course id
	 */
	public Integer getCourseId() {
		return this.courseID;
	}

	/**
	 * Sets the course id.
	 *
	 * @param courseID the new course id
	 */
	public void setCourseId(Integer courseID) {
		this.courseID = courseID;
	}

	/**
	 * Gets the parent id.
	 *
	 * @return the parent id
	 */
	public Integer getParentId() {
		return this.parentID;
	}

	/**
	 * Sets the parent id.
	 *
	 * @param parentID the new parent id
	 */
	public void setParentId(Integer parentID) {
		this.parentID = parentID;
	}

	/**
	 * Gets the folder name.
	 *
	 * @return the folder name
	 */
	public String getFolderName() {
		return this.folderName;
	}

	/**
	 * Sets the folder name.
	 *
	 * @param folderName the new folder name
	 */
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	/**
	 * Checks if folder allows anonymous posts.
	 *
	 * @return true, if is allow anonymous
	 */
	public boolean isAllowAnonymous() {
		return this.allowAnonymous;
	}

	/**
	 * Sets allow anonymous property.
	 *
	 * @param allowAnonymous the new allow anonymous property
	 */
	public void setAllowAnonymous(boolean allowAnonymous) {
		this.allowAnonymous = allowAnonymous;
	}

	/**
	 * Gets the parent.
	 *
	 * @return the parent
	 */
	public Folder getParent() {
		return this.parent;
	}

	/**
	 * Sets the parent.
	 *
	 * @param folder the new parent
	 */
	public void setParent(Folder folder) {
		parent = folder;
	}

	/**
	 * Adds the subfolder.
	 *
	 * @param folder the folder
	 */
	public void addSubfolder(Folder folder) {
		this.subfolders.add(folder);
	}

	/**
	 * Gets the subfolders.
	 *
	 * @return the subfolders
	 */
	public ArrayList<Folder> getSubfolders() {
		return this.subfolders;
	}

	/**
	 * Sets the subfolders.
	 *
	 * @param subfolders the new subfolders
	 */
	public void setSubfolders(ArrayList<Folder> subfolders) {
		this.subfolders = subfolders;
	}

	/**
	 * Gets the threads.
	 *
	 * @return the threads
	 */
	public ArrayList<ThreadPost> getThreads() {
		return this.threads;
	}

	/**
	 * Sets the threads.
	 *
	 * @param threads the new threads
	 */
	public void setThreads(ArrayList<ThreadPost> threads) {
		this.threads = threads;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Folder [folderID=" + this.getId() + ", courseID=" + courseID + ", parentID=" + parentID + ", folderName="
				+ folderName + ", allowAnonymous=" + allowAnonymous + "]";
	}

}
