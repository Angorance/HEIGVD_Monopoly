package bdfh.serializable;

/**
 * Class used to save the limits of the game.
 *
 * @author Daniel Gonzalez Lopez
 * @author Héléna Line Reymond
 * @version 2.0
 */
public class BoundParameters {
	
	private int minDice;                      // Minimum number of dice allowed
	private int maxDice;                      // Maximum number of dice allowed
	private int minMoneyAtTheStart;
			// Minimum money allowed at the start per player
	private int maxMoneyAtTheStart;
			// Maximum money allowed at the start per player
	private int minTime;
	private int maxTime;
	private boolean randomGameGeneration;
			// True if random game generation allowed, false otherwise
	
	public BoundParameters() {}
	
	public BoundParameters(int minDice, int maxDice, int minMoneyAtTheStart,
			int maxMoneyAtTheStart, int minTime, int maxTime,
			boolean randomGameGeneration) {
		
		this.minDice = minDice;
		this.maxDice = maxDice;
		this.minMoneyAtTheStart = minMoneyAtTheStart;
		this.maxMoneyAtTheStart = maxMoneyAtTheStart;
		this.minTime = minTime;
		this.maxTime = maxTime;
		this.randomGameGeneration = randomGameGeneration;
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
	
	public int getMinTime() {
		
		return minTime;
	}
	
	public int getMaxTime() {
		
		return maxTime;
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
	
	public void setMinTime(int minTime) {
		
		this.minTime = minTime;
	}
	
	public void setMaxTime(int maxTime) {
		
		this.maxTime = maxTime;
	}
	
	public void setRandomGameGeneration(boolean randomGameGeneration) {
		
		this.randomGameGeneration = randomGameGeneration;
	}
}
