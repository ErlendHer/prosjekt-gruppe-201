package app.core.controllers;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import app.Store;
import app.core.models.AbstractModel;
import app.core.models.Course;
import app.core.models.Folder;
import app.core.models.Post;
import app.core.models.ThreadPost;
import app.core.state.State;
import app.core.utils.TreeBuilder;
import app.core.views.ForumView;
import app.core.views.ThreadView;
import app.core.views.UtilsView;
import app.dao.ForumDao;
import app.dao.PostDao;
import app.dao.UserDao;

/**
 * The Class BrowseController handles browsing of the forum. The user can browse folders and threads
 * within courses, or search for threads by keywords.
 */
public class BrowseController extends AbstractController {

  private final UserDao userDao;
  private final PostDao postDao;
  private final ForumDao forumDao;

  // Breadcrumbs for representing current path
  private final ArrayList<String> path;

  private BrowseView view;
  private ArrayList<Course> courses;

  /**
   * Instantiates a new browse controller.
   */
  public BrowseController() {
    super();
    this.userDao = new UserDao();
    this.postDao = new PostDao();
    this.forumDao = new ForumDao();
    view = BrowseView.COURSE_VIEW;
    path = new ArrayList<String>();
    try {
      this.courses = TreeBuilder.genTree();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Render view based on current View state.
   */
  private void renderView() {
    if (!path.isEmpty()) {
      System.out.println("[path]: " + String.join(" ", path));
    }
    switch (view) {
    case FOLDER_VIEW:
      ForumView.printFolders();
      ForumView.printThreads();
      break;
    default:
      ForumView.renderCourses(courses);
      break;
    }
  }

  /**
   * Browse the Courses, Folders and Threads fetched from the database based on user input.
   *
   * @return true, if a Thread is entered
   */
  @Override
  public boolean readInput() {
    try {
      renderView();
      this.printMessages();
      // Wait for user commands
      this.awaitConsoleInput();
      if (List.of("enter", "back").contains(inputs.get(0))) {
        // Change directory
        change_dir();
      } else if (Store.getCurrentUser().isInstructor() && inputs.get(0).equalsIgnoreCase("stat")) {
        try {
          // Fetch and print user statistics
          userDao.getUserStatistics();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      } else if (inputs.get(0).equalsIgnoreCase("logout")) {
        // Logout current user, switch to Login-state
        Store.resetStore();
        this.path.clear();
        this.setNextState(State.LOGIN);
        view = BrowseView.COURSE_VIEW;
        return true;
      } else if (view == BrowseView.FOLDER_VIEW && inputs.get(0).equalsIgnoreCase("create")) {
        // Create a new Thread if the user is within a folder
        if (Store.getCurrentFolder().getCourseId() != null) {
          createNewThread();
        } else {
          this.addMessage("You must be within a folder in order to create a thread");
        }
      } else if (view == BrowseView.COURSE_VIEW && inputs.get(0).equalsIgnoreCase("search")) {
        // Search and print Threads
        printMatchedThreads();
      } else if (inputs.get(0).equalsIgnoreCase("help")) {
        // Print help commands
        this.addMessage(UtilsView.getBrowseHelpString());
      }
      this.clearInput();
      if (Store.getCurrentThread() != null) {
        return true;
      }
    } catch (Exception e) {
      this.addMessage(e.getMessage());
    }

    return false;
  }

  /**
   * Creates new ThreadPost. Reads title, question and tags from user input and attempts to insert a
   * new ThreadPost to the database
   * 
   * @see ThreadPost
   */
  private void createNewThread() {
    this.clearInput();
    // Insert title
    System.out.println("Enter the thread's title:");
    this.readLine();
    // Insert content
    System.out.println("Enter your question:");
    this.readLine();
    // Insert tags
    System.out.println("Enter tags for this thread (separated with ',')");
    this.readLine();

    ThreadPost thread = new ThreadPost(Store.getCurrentFolder().getId(), this.inputs.get(0));
    Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
    thread.setOriginalPost(new Post(null, Store.getCurrentUser().getId(), null, this.inputs.get(1),
        false, currentTimestamp, null, Store.getCurrentUser()));
    String[] tags = inputs.get(2).trim().split(",");
    try {
      postDao.insertThread(thread, tags);
      thread.getThreadPosts().put(thread.getOriginalPost().getId(), thread.getOriginalPost());
      Store.getCurrentFolder().getThreads().add(thread);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Handles directory change.
   */
  private void change_dir() {
    if (inputs.get(0).equalsIgnoreCase("back")) {
      boolean wentBack = true;
      if (view == BrowseView.COURSE_VIEW) {
        this.addMessage("You are at root, you can't go back");
        wentBack = false;
      } else if (view == BrowseView.FOLDER_VIEW) {
        if (Store.getCurrentFolder().getParent() == null) {
          // Exit current course
          view = BrowseView.COURSE_VIEW;
          Store.setCurrentFolder(Store.getCurrentCourse().getRoot());
          Store.setCurrentCourse(null);
        } else {
          // Return to parent folder
          Store.setCurrentFolder(Store.getCurrentFolder().getParent());
        }
      }
      if (wentBack) {
        // Pop last path if user went back one level
        this.path.remove(this.path.size() - 1);
      }
    } else {
      boolean exists = false;
      if (view == BrowseView.COURSE_VIEW) {
        Course course = (Course) lookupIndex(courses);
        if (course != null) {
          // Enter specified course
          Store.setCurrentCourse(course);
          Store.setCurrentFolder(course.getRoot());
          exists = true;
          this.path.add(course.getCourseCode());
          this.view = BrowseView.FOLDER_VIEW;
        }
      } else if (view == BrowseView.FOLDER_VIEW) {
        int index = Integer.parseInt(inputs.get(1));
        AbstractModel obj = null;

        if (index <= Store.getCurrentFolder().getSubfolders().size()) {
          // Look for Folder at current index
          obj = lookupIndex(Store.getCurrentFolder().getSubfolders());
        } else {
          // Look for Thread at current index
          int offset = index - Store.getCurrentFolder().getSubfolders().size();
          this.inputs.set(1, String.valueOf(offset));
          obj = lookupIndex(Store.getCurrentFolder().getThreads());

        }
        if (obj != null) {
          if (obj instanceof Folder) {
            // Lookup result was a Folder, enter specified folder
            Folder folder = (Folder) obj;
            Store.setCurrentFolder(folder);
            this.path.add("=> " + folder.getFolderName());
            exists = true;
            this.view = BrowseView.FOLDER_VIEW;
          } else {
            // Lookup result was a Thread, proceed to Post-state
            ThreadPost thread = (ThreadPost) obj;
            Store.setCurrentThread(thread);
            exists = true;
            this.setNextState(State.POST);
          }
        }
      }

      if (!exists) {
        this.addMessage("The path does not exist");
      }
    }
  }

  /**
   * Find all threads where either the title or the posts match the given keyword (inputs.get(1))
   * and print the result to the console.
   */
  private void printMatchedThreads() {
    try {
      String keyword = inputs.get(1);
      var posts = forumDao.getPostsByKeyword(keyword);
      if (posts.size() > 0) {
        System.out.println("|******************************************THREADS MATCHING '" + keyword
            + "'******************************************|");
        System.out.println("\n### Found " + posts.size() + " matching thread"
            + (posts.size() == 1 ? "" : "s") + " ###\n");
        for (var thread : posts) {
          ThreadView.printThread(thread);
          System.out.println(
              "====================================================================================================");
        }
        System.out.println(
            "|************************************************MATCH END************************************************|");
      } else {
        this.addMessage("The search query did not match any posts.");
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * The Enum BrowseView. Used for deciding how to render the current level in the forum.
   */
  enum BrowseView {
    COURSE_VIEW, FOLDER_VIEW,
  }

}
