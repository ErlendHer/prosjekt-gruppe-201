package app;

import java.util.LinkedHashMap;
import java.util.Map;

public class Store {
	
	public static Map<String, Object> storeValues;
	
	@SuppressWarnings("serial")
	Store() {
		storeValues = new LinkedHashMap<String, Object>() {{
		    put("currentUser", null);
		    put("currentCourse", null);
		    put("currentFolder", null);
		    put("currentPost", null);
		}};
	}
	
	public static Object getStoreValue(StoreObject obj) {
		return storeValues.get(obj.getKey());
	}
	
	public static void setStoreValue(StoreObject obj, Object value) {
		storeValues.put(obj.getKey(), value);
	}

	public enum StoreObject{
		CurrentUser("currentUser"),
		CurrentCourse("currentCourse"),
		CurrentFolder("currentFolder"),
		postID("currentPost");
		
		private String key;
		
		private StoreObject(String key) {
			this.key = key;
		}
		
		public String getKey() {
			return this.key;
		}
	}
}
