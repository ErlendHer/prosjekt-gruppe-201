package app.core.folder;

import java.util.ArrayList;

import app.core.queries.FolderController;

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

    Folder root = new Folder("root", 0, -1, null);

    queue.add(root);

    // Add all root folders to the "master" root Folder object.
    for (int i = 0; i < folders.size(); i++) {
      if (folders.get(i).getParentID() == 0) {
        root.addChild(folders.get(i));
        folders.get(i).setParent(root);
        folders.remove(i);
      }
    }

    while (!queue.isEmpty()) {
      var parent = queue.remove(queue.size() - 1);

      for (int i = folders.size() - 1; i >= 0; i--) {

        if (parent.getFolderID() == folders.get(i).getParentID()) {
          // Folder folderCopy = new Folder(folders.get(i));
          folders.get(i).setParent(parent);
          parent.addChild(folders.get(i));
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
   */
  public static ArrayList<Course> genTree() {
    var controller = new FolderController();
    controller.setupQuery();

    var courseCodes = controller.executeGetCourses();

    ArrayList<Course> courses = new ArrayList<>();

    for (var courseCode : courseCodes) {
      var folders = controller.executeGetFolders(courseCode);
      courses.add(new Course(courseCode, genFolderTree(folders)));
    }

    return courses;
  }
}
