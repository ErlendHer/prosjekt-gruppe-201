package app.core.controllers;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import app.Store;
import app.core.models.Post;
import app.core.models.ThreadPost;
import app.core.models.User;
import app.core.state.State;
import app.core.views.ThreadView;
import app.core.views.UtilsView;
import app.dao.PostDao;

/**
 * The Class PostController handles Thread logic. The user can comment, answer, and like threads by
 * use of the command line. Upon entering the Thread, the current user will be registered in the
 * database's UserView-table.
 */
public class PostController extends AbstractController {

  private final PostDao postDao;

  private ThreadPost thread;
  private boolean hasUpdatedUserView;

  /**
   * Instantiates a new post controller.
   */
  public PostController() {
    super();
    this.postDao = new PostDao();
    this.hasUpdatedUserView = false;
  }

  /**
   * Handle interactions in the current Thread based on user input.
   *
   * @return true, if successful
   */
  @Override
  public boolean readInput() {
    this.thread = Store.getCurrentThread();
    if (!hasUpdatedUserView) {
      try {
        // Register current user as a viewer of this Thread
        if (postDao.updatePostView(Store.getCurrentUser().getId(), thread.getId())) {
          thread.addView();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
      this.hasUpdatedUserView = true;
    }

    try {
      ThreadView.printThread(thread);
      this.printMessages();
      // Wait for user commands
      this.awaitConsoleInput();
      if (List.of("comment", "answer").contains(inputs.get(0))) {
        // Post comment or answer
        postComment();
      } else if (inputs.get(0).equalsIgnoreCase("like")) {
        // Like a post
        likePost();
      } else if (inputs.get(0).equalsIgnoreCase("logout")) {
        // Logout current user, switch to Login-state
        Store.resetStore();
        this.setNextState(State.LOGIN);
        return true;
      } else if (inputs.get(0).equalsIgnoreCase("back")) {
        // Return to forum, switch to Browse-state
        Store.setCurrentThread(null);
        this.hasUpdatedUserView = false;
        this.setNextState(State.BROWSE);
        return true;
      } else if (inputs.get(0).equalsIgnoreCase("help")) {
        // Print help commands
        this.addMessage(UtilsView.getThreadHelpString());
      }
      this.clearInput();
    } catch (Exception e) {
      this.addMessage(e.getMessage());
    }

    return false;
  }

  /**
   * Register a like to specified post
   */
  private void likePost() {
    Integer postID = Integer.parseInt(inputs.get(1));
    if (thread.getThreadPosts().get(postID) != null) {
      try {
        if (postDao.likePost(postID, Store.getCurrentUser().getId())) {
          thread.getThreadPosts().get(postID).addLike();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      this.addMessage(String.format("Post with id [%s] does not exist in this thread", postID));
    }
  }

  /**
   * Post a comment/answer in the current Thread. Whether a comment or answer is posted is based on
   * the entered command. An answer will either be added as an 'instructors answer' or 'students
   * answer' based on the role-properties of the current user.
   * 
   * @see User
   * @see Post
   */
  private void postComment() {
    boolean isAnswer = inputs.get(0).equalsIgnoreCase("answer") ? true : false;
    Integer parentID = inputs.get(0).equalsIgnoreCase("comment") ? Integer.parseInt(inputs.get(1))
        : thread.getOriginalPost().getId();
    this.clearInput();
    // Check if parent Post is part of current Thread
    if (isAnswer || (!isAnswer && thread.getThreadPosts().get(parentID) != null)) {
      System.out.println("Enter your comment:");
      this.readLine();
      Timestamp currentTimestamp = new java.sql.Timestamp(
          Calendar.getInstance().getTime().getTime());
      // Create new Post
      Post comment = new Post(thread.getId(), Store.getCurrentUser().getId(), parentID,
          this.inputs.get(0), isAnswer, currentTimestamp, null, Store.getCurrentUser());
      try {
        // Insert to Database, and add created Post to current Thread
        postDao.insertPost(comment);
        thread.getThreadPosts().put(comment.getId(), comment);
      } catch (SQLException e) {
        e.printStackTrace();
      }
      if (comment.isAnswer()) {
        // Insert answer
        if (Store.getCurrentUser().isInstructor()) {
          // If user is an instructor
          thread.getOriginalPost().setInstructorAnswer(comment);
        } else {
          // If user is a student
          thread.getOriginalPost().setStudentAnswer(comment);
        }
      } else {
        thread.getThreadPosts().get(comment.getParentId()).addFollowUp(comment);
      }
    } else {
      this.addMessage(String.format("Post with id [%s] does not exist in this thread", parentID));
    }

  }

}
