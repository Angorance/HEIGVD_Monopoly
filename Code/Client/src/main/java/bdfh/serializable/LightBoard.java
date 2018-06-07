package bdfh.serializable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public class LightBoard {
	
	ArrayList<LightSquare> squares = new ArrayList<>();
	
	/*private LightBoard() {}
	
	private static class Instance {
		
		static final LightBoard instance = new LightBoard();
	}
	
	public static LightBoard getInstance() {
		
		return Instance.instance;
	}*/
	
	public ArrayList<LightSquare> getSquares() {
		
		return squares;
	}
	
	public static LightBoard instancify(String json) {
		
		LightBoard tmp = new LightBoard();
		
		JsonArray jsonBoard = GsonSerializer.getInstance().fromJson(json,
				JsonArray.class);
		
		for (JsonElement je : jsonBoard) {
			
			tmp.squares.add(LightSquare.instancify(je.getAsJsonObject()));
		}
		
		return tmp;
	}
}
