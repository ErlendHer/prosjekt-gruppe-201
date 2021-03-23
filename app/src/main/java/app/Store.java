package app;

import java.util.LinkedHashMap;
import java.util.Map;

import app.core.models.Course;
import app.core.models.Folder;
import app.core.models.ThreadPost;
import app.core.models.User;

public class Store {
	
	public static Map<String, Object> storeValues;
	
	@SuppressWarnings("serial")
	Store() {
		storeValues = new LinkedHashMap<String, Object>() {{
		    put("currentUser", null);
		    put("currentCourse", null);
		    put("currentFolder", null);
		    put("currentThread", null);
		}};
	}
	
	public static User getCurrentUser() {
		return (User) storeValues.get("currentUser");
	}
	
	public static void setCurrentUser(User user) {
		storeValues.put("currentUser", user);
	}
	
	public static Course getCurrentCourse() {
		return (Course) storeValues.get("currentCourse");
	}
	
	public static void setCurrentCourse(Course course) {
		storeValues.put("currentCourse", course);
	}
	
	public static Folder getCurrentFolder() {
		return (Folder) storeValues.get("currentFolder");
	}
	
	public static void setCurrentFolder(Folder folder) {
		storeValues.put("currentFolder", folder);
	}
	
	public static ThreadPost getCurrentThread() {
		return (ThreadPost) storeValues.get("currentThread");
	}
	
	public static void setCurrentThread(ThreadPost thread) {
		storeValues.put("currentThread", thread);
	}
	
}
