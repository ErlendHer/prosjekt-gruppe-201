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
import app.core.models.User;
import app.core.state.State;
import app.core.utils.TreeBuilder;
import app.core.views.ForumView;
import app.dao.PostDao;
import app.dao.UserDao;

public class BrowseController extends AbstractController {

	private final UserDao userDao;
	private final PostDao postDao;
	private final ArrayList<String> path;
	
	private BrowseView view;
	private ArrayList<Course> courses;
	
	public BrowseController() {
		super();
		this.userDao = new UserDao();
		this.postDao = new PostDao();
		view = BrowseView.COURSE_VIEW;
		path = new ArrayList<String>();
	    try {
			this.courses = TreeBuilder.genTree();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	  }

	private void renderView() {
		if(!path.isEmpty()) {
			System.out.println("[path]: " + String.join(" ", path));
		}
		switch(view) {
			case FOLDER_VIEW:
				ForumView.printFolders();
				ForumView.printThreads();
				break;
			default: 
				ForumView.renderCourses(courses);
				break;
		}
	}
	
	@Override
	public boolean readInput() {
		renderView();
		this.awaitConsoleInput();
		if(List.of("enter", "back").contains(inputs.get(0))) {
			change_dir();
		} else if(Store.getCurrentUser().isInstructor() && inputs.get(0).equalsIgnoreCase("stat")) {
			try {
				userDao.getUserStatistics();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if(inputs.get(0).equalsIgnoreCase("logout")) {
			Store.setCurrentUser(null);
			this.setNextState(State.LOGIN);
			return true;
		} else if(view == BrowseView.FOLDER_VIEW && inputs.get(0).equalsIgnoreCase("create")) {
			createNewThread();
		}
		this.clearInput();
		if(Store.getCurrentThread() != null) {
			return true;
		}
		return false;
	}
	
	private AbstractModel lookupIndex(ArrayList<? extends AbstractModel> list) {
		try {
			int index = Integer.parseInt(inputs.get(1))-1;
			
			return list.get(index);
		} catch(NumberFormatException e) {
			System.err.println("Ugyldig input, du må taste inn et heltall");
		} catch(IndexOutOfBoundsException e2) {
			System.err.println("Ugyldig valg");
		}
		return null;
	}
	
	private void createNewThread() {
		this.clearInput();
		// Insert title
		System.out.println("Skriv inn tittel for tråden:");
		this.readLine(false);
		// Insert content
		System.out.println("Tast inn spørsmålet ditt:");
		this.readLine(false);
		// Insert tags
		System.out.println("Skriv inn tagger for tråden (separert med ',')");
		this.readLine(false);
		
		ThreadPost thread = new ThreadPost(Store.getCurrentFolder().getId(), this.inputs.get(0));
		Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		thread.setOriginalPost(new Post(null, Store.getCurrentUser().getId(), null, this.inputs.get(1), false, currentTimestamp, null, Store.getCurrentUser()));
		String[] tags = inputs.get(2).trim().split(",");
		try {
			postDao.insertThread(thread, tags);
			thread.getThreadPosts().put(thread.getOriginalPost().getId(), thread.getOriginalPost());
			Store.getCurrentFolder().getThreads().add(thread);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void change_dir() {
		if (!inputs.get(0).equalsIgnoreCase("back") && inputs.size() < 2) {
			System.out.println("Missing argument, use cd to change dir, e.g (cd post1)");
			return;
		}
		
		if (inputs.get(0).equalsIgnoreCase("back")) {
			boolean wentBack = true;
			if (view == BrowseView.COURSE_VIEW) {
				System.out.println("You are at root, you can't go back");
				wentBack = false;
			} else if (view == BrowseView.FOLDER_VIEW) {
				if (Store.getCurrentFolder().getParent() == null) {
					view = BrowseView.COURSE_VIEW;
					Store.setCurrentFolder(Store.getCurrentCourse().getRoot());
				} else {
					Store.setCurrentFolder(Store.getCurrentFolder().getParent());
				}
			}
			if(wentBack) {
				this.path.remove(this.path.size() - 1);
			}
		} else {
			boolean exists = false;
			if (view == BrowseView.COURSE_VIEW) {
				Course course = (Course) lookupIndex(courses);
				if (course != null) {
					Store.setCurrentCourse(course);
					Store.setCurrentFolder(course.getRoot());
					exists = true;
					this.path.add(course.getCourseCode());
					this.view = BrowseView.FOLDER_VIEW;
				}
			} else if (view == BrowseView.FOLDER_VIEW) {
				int index = Integer.parseInt(inputs.get(1));
				AbstractModel obj = null;
				if(index <= Store.getCurrentFolder().getSubfolders().size()) {
					obj = lookupIndex(Store.getCurrentFolder().getSubfolders());
				} else {
					int offset = index - Store.getCurrentFolder().getSubfolders().size();
					this.inputs.set(1, String.valueOf(offset));
					obj = lookupIndex(Store.getCurrentFolder().getThreads());
					
				}
				if(obj != null) {
					if(obj instanceof Folder) {
						Folder folder = (Folder) obj;	
						Store.setCurrentFolder(folder);
						this.path.add("=> " + folder.getFolderName());
						exists = true;
						this.view = BrowseView.FOLDER_VIEW;
					} else {
						ThreadPost thread = (ThreadPost) obj;
						Store.setCurrentThread(thread);
						exists = true;
						this.setNextState(State.POST);
					}
				}
			}

			if (!exists) {
				System.out.println("The path does not exist");
			}
		}
	}

	enum BrowseView {
		COURSE_VIEW, FOLDER_VIEW,
	}

}
