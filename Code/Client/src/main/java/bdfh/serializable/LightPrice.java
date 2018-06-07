package bdfh.serializable;

import bdfh.net.protocol.GameProtocol;
import com.google.gson.*;

/**
 * @author Héléna Line Reymond
 * @version 1.0
 */
public class LightPrice {
	
	private Integer rent;               // Basic rent of the square
	private Integer price;              // Price of the room
	private Integer priceCouch;         // Price for one couch
	private Integer priceHomeCinema;    // Price for one home cinema
	private Integer hypothec;           // Amount of the hypothec of the square
	
	public LightPrice() {}
	
	public Integer getRent() {
		
		return rent;
	}
	
	private void setRent(Integer rent) {
		
		this.rent = (rent == -1 ? null : rent);
	}
	
	public Integer getPrice() {
		
		return price;
	}
	
	private void setPrice(Integer price) {
		
		this.price = (price == -1 ? null : price);
	}
	
	public Integer getPriceCouch() {
		
		return priceCouch;
	}
	
	private void setPriceCouch(Integer priceCouch) {
		
		this.priceCouch = (priceCouch == -1 ? null : priceCouch);
	}
	
	public Integer getPriceHomeCinema() {
		
		return priceHomeCinema;
	}
	
	private void setPriceHomeCinema(Integer priceHomeCinema) {
		
		this.priceHomeCinema = (priceHomeCinema == -1 ? null : priceHomeCinema);
	}
	
	public Integer getHypothec() {
		
		return hypothec;
	}
	
	private void setHypothec(Integer hypothec) {
		
		this.hypothec = (hypothec == -1 ? null : hypothec);
	}
	
	/**
	 * Get all the possible rents for colored square and institutes.
	 *
	 * - Simple rent
	 * - Rent for 1-2-3-4 couches
	 * - Rent for 1 home cinema
	 *
	 * - Rent for 1 institute
	 * - Rent for 2 institutes
	 * - Rent for 3 institutes
	 * - Rent for 4 institutes
	 *
	 * @return  array with all rents.
	 */
	public int[] getRents() {
		
		int[] rents = new int[6];
		
		// Simple rent
		rents[0] = getRent();
		
		// Rents for colored squares
		if(getPriceCouch() != null) {
			
			// Rents with couch(es)
			rents[1] = getRent() * GameProtocol.RENT_TO_1COUC;
			rents[2] = getRent() * GameProtocol.RENT_TO_2COUC;
			rents[3] = getRent() * GameProtocol.RENT_TO_3COUC;
			rents[4] = getRent() * GameProtocol.RENT_TO_4COUC;
			
			// Rent with home cinema
			rents[5] = getRent() * GameProtocol.RENT_TO_1HOME;
			
		} else {
			
			// Rents for institutes
			rents[1] = getRent() * GameProtocol.RENT_TO_1INST;
			rents[2] = getRent() * GameProtocol.RENT_TO_2INST;
			rents[3] = getRent() * GameProtocol.RENT_TO_3INST;
			rents[4] = getRent() * GameProtocol.RENT_TO_4INST;
		}
		
		return rents;
	}
	
	/**
	 * Get all the possible rents for companies square.
	 *
	 * - Rent for 1 company
	 * - Rent for 2 companies
	 *
	 * @return  array with all rents.
	 */
	public String[] getCompaniesRents() {
		
		String[] rents = new String[2];
		
		// Rents
		rents[0] = GameProtocol.RENT_TO_1COMP + "x le résultat des dés";
		rents[1] = GameProtocol.RENT_TO_2COMP + "x le résultat des dés";
		
		return rents;
	}
	
	/**
	 * Change a json String into a LightPrice.
	 *
	 * @param json  JSON received.
	 *
	 * @return  a LightPrice.
	 */
	public static LightPrice instancify(String json) {
		
		JsonObject jo = GsonSerializer.getInstance().fromJson(json, JsonObject.class);
		
		return instancify(jo);
	}
	
	/**
	 * Change a json Object into a LightPrice.
	 *
	 * @param jo    Json Object received.
	 *
	 * @return  a LightPrice.
	 */
	public static LightPrice instancify(JsonObject jo) {
		
		LightPrice tmp = new LightPrice();
		
		// Set the attributes
		tmp.setRent(jo.get("rent").getAsInt());
		tmp.setPrice(jo.get("price").getAsInt()); // TODO - A CHECKER
		tmp.setPriceCouch(jo.get("couch").getAsInt());
		tmp.setPriceHomeCinema(jo.get("homeCinema").getAsInt());
		tmp.setHypothec(jo.get("hypothec").getAsInt());
		
		return tmp;
	}
}
