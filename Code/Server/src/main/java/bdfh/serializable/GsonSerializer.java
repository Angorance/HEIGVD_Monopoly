package bdfh.serializable;

import com.google.gson.*;

/**
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class GsonSerializer {
	
	private Gson gson = new GsonBuilder().serializeNulls().create();
	
	private GsonSerializer() {}
	
	private static class Instance {
		
		static final GsonSerializer instance = new GsonSerializer();
	}
	
	public static Gson getInstance() {
		
		return Instance.instance.gson;
	}
}
