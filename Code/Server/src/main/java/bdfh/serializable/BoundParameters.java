package bdfh.serializable;

/**
 * @author Daniel Gonzalez Lopez
 * @author Héléna Line Reymond
 * @version 1.0
 */
public class BoundParameters {
	
	public int minDice;                      // Minimum number of dice allowed
	public int maxDice;                      // Maximum number of dice allowed
	public int minMoneyAtTheStart;           // Minimum money allowed at the start per player
	public int maxMoneyAtTheStart;           // Maximum money allowed at the start per player
	public boolean randomGameGeneration;     // True if random game generation allowed, false otherwise
	
	
	private BoundParameters() {}
	
	private static class Instance {
		
		static final BoundParameters instance = new BoundParameters();
	}
	
	public static BoundParameters getInstance() {
		
		return Instance.instance;
	}
	
	
	public int getMinDice() {
		
		return minDice;
	}
	
	public int getMaxDice() {
		
		return maxDice;
	}
	
	public int getMinMoneyAtTheStart() {
		
		return minMoneyAtTheStart;
	}
	
	public int getMaxMoneyAtTheStart() {
		
		return maxMoneyAtTheStart;
	}
	
	public boolean isRandomGameGeneration() {
		
		return randomGameGeneration;
	}
	
	public void setMinDice(int minDice) {
		
		this.minDice = minDice;
	}
	
	public void setMaxDice(int maxDice) {
		
		this.maxDice = maxDice;
	}
	
	public void setMinMoneyAtTheStart(int minMoneyAtTheStart) {
		
		this.minMoneyAtTheStart = minMoneyAtTheStart;
	}
	
	public void setMaxMoneyAtTheStart(int maxMoneyAtTheStart) {
		
		this.maxMoneyAtTheStart = maxMoneyAtTheStart;
	}
	
	public void setRandomGameGeneration(boolean randomGameGeneration) {
		
		this.randomGameGeneration = randomGameGeneration;
	}
}
