package app.core.views;

/**
 * The Class UtilsView handles general print functions.
 */
public class UtilsView {

  /**
   * Prints a separator.
   *
   * @param large whether the separator should be large or not
   * @param len the length of the separator
   */
  public static void printSeparator(boolean large, int len) {
    char sep = large ? '=' : '-';
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < len; i++) {
      sb.append(sep);
    }
    System.out.println(sb.toString());
  }

  /**
   * Gets the help string for Thread-state.
   *
   * @return the help string
   */
  public static String getThreadHelpString() {
    String threadHelp = "\nThe available functions are:\n"
        + "\t answer - answer the original post\n" + "\t comment [postID] - comment a post\n"
        + "\t like [postID] - likes a post\n" + "\t back - return to forum\n"
        + "\t logout - logout user\n" + "\t quit - quit the application\n";
    return threadHelp;
  }

  /**
   * Gets the help string for Browse-state.
   *
   * @return the help string
   */
  public static String getBrowseHelpString() {
    String browseHelp = "\nThe available functions are:\n" + "\t create - create a new thread\n"
        + "\t search [keyword] - searches for posts by specified keyword\n"
        + "\t enter [index] - enter folder/thread at specified index\n"
        + "\t back - return to previous level\n"
        + "\t stat - prints user statistics if the current user is an instructor\n"
        + "\t logout - logout user\n" + "\t quit - quit the application\n";
    return browseHelp;
  }
}
