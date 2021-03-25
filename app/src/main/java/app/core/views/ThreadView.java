package app.core.views;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import app.core.models.Post;
import app.core.models.ThreadPost;

/**
 * The Class ThreadView prints views for the Thread-state.
 */
public class ThreadView {

  /**
   * Prints the thread.
   *
   * @param thread the thread
   */
  public static void printThread(ThreadPost thread) {
    Post threadPost = thread.getOriginalPost();
    UtilsView.printSeparator(true, 100);
    System.out.println(String.format("[Views]: %d", thread.getViews()));
    System.out
        .println(String.format("[COLOR CODES]: %s", thread.getOriginalPost().getColorCodes()));
    printPost(0, thread.getTitle(), "[Good question]", threadPost);
    if (threadPost.hasInstructorsAnswer()) {
      printAnswer(threadPost.getInstructorAnswer(), true);
    }
    if (threadPost.hasStudentAnswer()) {
      printAnswer(threadPost.getStudentAnswer(), false);
    }
    for (Post followUp : threadPost.getFollowUps()) {
      printFollowUp(followUp);
    }
  }

  /**
   * Prints the follow ups.
   *
   * @param post the post
   */
  private static void printFollowUp(Post post) {
    printPost(1, "[Follow up discussion]", "", post);
    List<Post> queue = new ArrayList<Post>(post.getFollowUps());
    queue.sort((e1, e2) -> e2.getDatePosted().compareTo(e1.getDatePosted()));
    Map<Integer, Integer> indexMap = new LinkedHashMap<Integer, Integer>();
    int index = 2;
    while (queue.size() > 0) {
      Post currPost = queue.remove(queue.size() - 1);
      indexMap.put(currPost.getId(), index);
      if (currPost.hasFollowUps()) {
        index++;
        List<Post> followUps = currPost.getFollowUps();
        followUps.sort((e1, e2) -> e2.getDatePosted().compareTo(e1.getDatePosted()));
        for (Post comment : followUps) {
          queue.add(comment);
        }
      }

      if (indexMap.get(currPost.getParentId()) != null) {
        printPost(indexMap.get(currPost.getParentId()) + 1, "", "[Good comment]", currPost);
      } else {
        printPost(2, "", "[Good comment]", currPost);
      }

    }
  }

  /**
   * Prints the post.
   *
   * @param indent the level of indent
   * @param caption the post caption
   * @param likeCaption the like caption
   * @param post the post
   */
  public static void printPost(int indent, String caption, String likeCaption, Post post) {
    String footer = String.format("Posted by %s", post.getUser().getEmail()).toString();
    String likes = String.format("%s: %d", likeCaption, post.getLikes()).toString();
    if (caption.length() > 0) {
      System.out.println(String.format("%1$" + (caption.length() + (4 * indent)) + "s\n", caption));
    }
    System.out.println(String.format("%1$" + (post.getContent().length() + (4 * indent)) + "s\n",
        post.getContent()));
    System.out.println(String.format("%1$" + (footer.length() + (4 * indent)) + "s", footer));
    System.out.println(
        String.format("%1$" + (String.valueOf(post.getId()).length() + 10 + (4 * indent)) + "s",
            "[PostID]: " + post.getId()));
    if (likeCaption.length() > 0) {
      System.out.println(String.format("%1$" + (likes.length() + (4 * indent)) + "s", likes));
    }
    System.out.println(
        String.format("%1$" + (post.getDatePosted().toString().length() + 2 + (4 * indent)) + "s",
            "[" + post.getDatePosted() + "]"));
    UtilsView.printSeparator(false, 100);
  }

  /**
   * Prints the answer.
   *
   * @param post the post
   * @param isInstructor whether post owner is instructor or not
   */
  private static void printAnswer(Post post, boolean isInstructor) {
    String caption = isInstructor ? "[Instructors answer]" : "[Students answer]";
    printPost(1, caption, "[Good answer]", post);
  }

}
