package app.core.folder;

import java.util.ArrayList;

import app.core.queries.FolderController;

public class TreeBuilder {

  /**
   * Add all children of the parent folder to the parent folder. Furthermore,
   * remove all added folders from the "folders" ArrayList to avoid uneccesarry
   * iterations.
   *
   * @param folders list of unvisited folders
   * @param parent  parent folder
   */
  private static void addChildren(ArrayList<Folder> folders, Folder parent) {

    for (int i = folders.size() - 1; i >= 0; i--) {
      if (parent.getFolderID() == folders.get(i).getParentID()) {
        parent.addChild(folders.get(i));
        folders.remove(i);
      }
    }
  }

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

    Folder root = new Folder("root", -1, -1);

    queue.add(root);

    // Add all root folders to the "master" root Folder object.
    for (int i = 0; i < folders.size(); i++) {
      if (folders.get(i).getParentID() == 0) {
        root.addChild(folders.get(i));
      }
    }

    while (!queue.isEmpty()) {
      var parent = queue.remove(queue.size() - 1);
      addChildren(folders, parent);
      for (var folder : parent.getChildren()) {
        queue.add(folder);
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
    controller.connect();
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
