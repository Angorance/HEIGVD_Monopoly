package bdfh.serializable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;

/**
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class LightBoard {
	
	ArrayList<LightSquare> board = new ArrayList<>();
	
	/*private LightBoard() {}
	
	private static class Instance {
		
		static final LightBoard instance = new LightBoard();
	}
	
	public static LightBoard getInstance() {
		
		return Instance.instance;
	}*/
	
	public static LightBoard instancify(String json) {
		
		LightBoard tmp = new LightBoard();
		
		JsonArray jsonBoard = GsonSerializer.getInstance().fromJson(json,
				JsonArray.class);
		
		for (JsonElement je : jsonBoard) {
			
			tmp.board.add(LightSquare.instancify(je.getAsJsonObject()));
		}
		
		return tmp;
	}
}
