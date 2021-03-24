package app.core.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.sql.Timestamp;

import app.Store;
import app.core.models.Post;
import app.core.models.ThreadPost;
import app.core.models.User;
import app.core.state.State;
import app.core.views.ThreadView;
import app.dao.ForumDao;
import app.dao.PostDao;

public class PostController extends AbstractController {

	private final PostDao postDao;

	private ThreadPost thread;
	private boolean hasUpdatedUserView;

	public PostController() {
		super();
		this.postDao = new PostDao();
		this.hasUpdatedUserView = false;

	}

	@Override
	public boolean readInput() {
		this.thread = Store.getCurrentThread();
		if (!hasUpdatedUserView) {
			try {
				if (postDao.updatePostView(Store.getCurrentUser().getId(), thread.getId())) {
					thread.addView();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.hasUpdatedUserView = true;
		}

		ThreadView.printThread(thread);

		try {
			this.awaitConsoleInput();
			if (List.of("comment", "answer").contains(inputs.get(0))) {
				postComment();
			} else if (inputs.get(0).equalsIgnoreCase("like")) {
				likePost();
			} else if (inputs.get(0).equalsIgnoreCase("logout")) {
				Store.setCurrentUser(null);
				this.setNextState(State.LOGIN);
				return true;
			} else if (inputs.get(0).equalsIgnoreCase("back")) {
				Store.setCurrentThread(null);
				this.hasUpdatedUserView = false;
				this.setNextState(State.BROWSE);
				return true;
			}
			this.clearInput();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	private void likePost() {
		Integer postID = Integer.parseInt(inputs.get(1));
		try {
			if (postDao.likePost(postID, Store.getCurrentUser().getId())) {
				thread.getThreadPosts().get(postID).addLike();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void postComment() {
		boolean isAnswer = inputs.get(0).equalsIgnoreCase("answer") ? true : false;
		Integer parentID = inputs.get(0).equalsIgnoreCase("comment") ? Integer.parseInt(inputs.get(1))
				: thread.getOriginalPost().getId();
		this.clearInput();
		System.out.println("Skriv inn din kommentar:");
		this.readLine(false);
		Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		Post comment = new Post(thread.getId(), Store.getCurrentUser().getId(), parentID, this.inputs.get(0), isAnswer,
				currentTimestamp, null, Store.getCurrentUser());
		try {
			postDao.insertPost(comment);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (comment.isAnswer()) {
			if (Store.getCurrentUser().isInstructor()) {
				thread.getOriginalPost().setInstructorAnswer(comment);
			} else {
				thread.getOriginalPost().setStudentAnswer(comment);
			}
		} else {
			thread.getThreadPosts().get(comment.getParentId()).addFollowUp(comment);
		}

	}

}
