package app.core.utils;

import java.sql.SQLException;
import java.util.ArrayList;

import app.core.models.Course;
import app.core.models.Folder;
import app.core.models.ThreadPost;
import app.dao.ForumDao;

/**
 * This Class uses the priniples of Depth-first-search to generate a
 * folder-tree, where every folder has a list of children and a parent
 * attribute. Using DFS, we build the tree and return the "root" folder which
 * again has its subfolders and so on.
 */
public class TreeBuilder {

	/**
	 * Use the principles of DFS to generate a folder tree structure based on an
	 * arraylist of folders. NOTE -> This will generate an arbitrary "root" folder
	 * which does not actually exist, but contains all the children that has its own
	 * children and so on.
	 * 
	 * @param folders list of folders
	 * @return root folder containing all subfolders of the tree
	 */
	public static Folder genFolderTree(ArrayList<Folder> folders) {
		ArrayList<Folder> queue = new ArrayList<>();

		Folder root = new Folder(null, null, "root", true);

		queue.add(root);
		// Add all root folders to the "master" root Folder object.
		for (int i = 0; i < folders.size(); i++) {
			if (folders.get(i).getParentId() == null) {
				root.addSubfolder(folders.get(i));
				folders.get(i).setParent(root);
			}
		}

		while (!queue.isEmpty()) {
			var parent = queue.remove(queue.size() - 1);
			for (int i = folders.size() - 1; i >= 0; i--) {
				if (parent.getId() == folders.get(i).getParentId()) {
					folders.get(i).setParent(parent);
					parent.addSubfolder(folders.get(i));
					queue.add(folders.get(i));
					folders.remove(i);
				}
			}
		}

		return root;
	}

	/**
	 * Generate a tree where each course has a folder tree and each folder tree has
	 * a list of posts.
	 * 
	 * @return list of Courses
	 * @throws SQLException
	 */
	public static ArrayList<Course> genTree() throws SQLException {
		ForumDao forumDao = new ForumDao();

		ArrayList<Course> courses = forumDao.getCourses();

		for (var course : courses) {
			ArrayList<Folder> folders = forumDao.getCourseFolders(course.getId());
			for (var folder : folders) {
				ArrayList<ThreadPost> threads = forumDao.getFolderThreads(folder.getId());
				folder.setThreads(threads);
			}

			course.setRoot(genFolderTree(folders));
		}

		return courses;
	}
}
