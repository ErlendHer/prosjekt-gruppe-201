package app;

import java.util.LinkedHashMap;
import java.util.Map;

import app.core.models.Course;
import app.core.models.Folder;
import app.core.models.ThreadPost;
import app.core.models.User;

/**
 * The Class Store. Static class for handling access to stored objects cross controllers.
 */
public class Store {

  private static Map<String, Object> storeValues;

  /**
   * Instantiates a new store.
   */
  @SuppressWarnings("serial")
  Store() {
    storeValues = new LinkedHashMap<String, Object>() {
      {
        put("currentUser", null);
        put("currentCourse", null);
        put("currentFolder", null);
        put("currentThread", null);
      }
    };
  }

  /**
   * Resets the stored objects.
   */
  public static void resetStore() {
    storeValues.put("currentUser", null);
    storeValues.put("currentCourse", null);
    storeValues.put("currentFolder", null);
    storeValues.put("currentThread", null);
  }

  /**
   * Gets the current user.
   *
   * @return the current user
   */
  public static User getCurrentUser() {
    return (User) storeValues.get("currentUser");
  }

  /**
   * Sets the current user.
   *
   * @param user the new current user
   */
  public static void setCurrentUser(User user) {
    storeValues.put("currentUser", user);
  }

  /**
   * Gets the current course.
   *
   * @return the current course
   */
  public static Course getCurrentCourse() {
    return (Course) storeValues.get("currentCourse");
  }

  /**
   * Sets the current course.
   *
   * @param course the new current course
   */
  public static void setCurrentCourse(Course course) {
    storeValues.put("currentCourse", course);
  }

  /**
   * Gets the current folder.
   *
   * @return the current folder
   */
  public static Folder getCurrentFolder() {
    return (Folder) storeValues.get("currentFolder");
  }

  /**
   * Sets the current folder.
   *
   * @param folder the new current folder
   */
  public static void setCurrentFolder(Folder folder) {
    storeValues.put("currentFolder", folder);
  }

  /**
   * Gets the current thread.
   *
   * @return the current thread
   */
  public static ThreadPost getCurrentThread() {
    return (ThreadPost) storeValues.get("currentThread");
  }

  /**
   * Sets the current thread.
   *
   * @param thread the new current thread
   */
  public static void setCurrentThread(ThreadPost thread) {
    storeValues.put("currentThread", thread);
  }

}
