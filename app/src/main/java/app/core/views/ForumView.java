package app.core.views;

import java.util.ArrayList;
import java.util.Collections;

import app.Store;
import app.core.models.Course;
import app.core.models.Folder;
import app.core.models.Thread;

public class ForumView {

	public static void renderCourses(ArrayList<Course> courses) {
		System.out.println("Courses: ");
		int index = 1;
		for (Course course : courses) {
			System.out.println(String.format("(%d): [%s] %s", index, course.getCourseCode(), course.getCourseName()));
			index++;
		}
	}

	public static void printFolders() {
		ArrayList<String> rootFolders = new ArrayList<>();

		Folder currFolder = Store.getCurrentFolder();

		while (true) {
			if (currFolder.getParent() == null) {
				break;
			}
			rootFolders.add(currFolder.getFolderName());
			currFolder = currFolder.getParent();
		}
		Collections.reverse(rootFolders);

		System.out.println(String.format("%1$10s", "[Folders]"));
		int index = 1;
		for (var child : Store.getCurrentFolder().getSubfolders()) {
			System.out.println(String.format("%1$5s: %2$1s", "(" + String.valueOf(index) + ")", child.getFolderName()));
			index++;
		}
	}

	public static void printThreads() {
		Folder currFolder = Store.getCurrentFolder();
		ArrayList<Thread> folderThreads = currFolder.getThreads();
		if (!folderThreads.isEmpty()) {
			System.out.println(String.format("%1$10s", "[Threads]"));
			int index = currFolder.getSubfolders().size() + 1;
			for (var thread : Store.getCurrentFolder().getThreads()) {
				System.out.println(String.format("%1$5s: %2$1s [%3$1s]", "(" + String.valueOf(index) + ")", thread.getTitle(),
						thread.getThreadPost().getDatePosted()));
				index++;
			}
		}
	}

}
