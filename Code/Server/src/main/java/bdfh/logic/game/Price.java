package bdfh.logic.game;

/**
 * Class used to fix prices for a square.
 *
 * @author Héléna Line Reymond
 * @version 1.0
 */
public class Price {

	private int rent;           // Basic rent of the square
	private int priceHouse;     // Price for one house
	private int priceHotel;     // Price for one hotel
	private int hypothec;       // Amount of the hypothec of the square
	
	/**
	 * Constructor.
	 *
	 * @param rent          Basic rent of the square.
	 * @param priceHouse    Price for one house.
	 * @param priceHotel    Price for one hotel.
	 * @param hypothec      Amount of the hypothec of the square.
	 */
	public Price(int rent, int priceHouse, int priceHotel, int hypothec) {
		this.rent = rent;
		this.priceHouse = priceHouse;
		this.priceHotel = priceHotel;
		this.hypothec = hypothec;
	}
	
	public int getRent() {
		
		return rent;
	}
	
	public int getPriceHouse() {
		
		return priceHouse;
	}
	
	public int getPriceHotel() {
		
		return priceHotel;
	}
	
	public int getHypothec() {
		
		return hypothec;
	}
}