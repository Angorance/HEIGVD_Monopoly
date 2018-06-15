package bdfh.serializable;

/**
 * Class used to update the limits of the logic.
 *
 * @author Daniel Gonzalez Lopez
 * @author Héléna Line Reymond
 * @version 2.0
 */
public class BoundParameters {
	
	// Number of dice allowed
	private int minDice;
	private int maxDice;
	
	// Money allowed at the start per player
	private int minMoneyAtTheStart;
	private int maxMoneyAtTheStart;
	
	// Time limits for time mode.
	private int minTime;
	private int maxTime;
	
	// True if random logic generation allowed, false otherwise
	private boolean randomGameGeneration;
	
	private BoundParameters() {}
	
	/**
	 * Internal static class used to create one and only one instance of
	 * BoundParameters to guarantee it follows the singleton model.
	 */
	private static class Instance {
		
		static final BoundParameters instance = new BoundParameters();
	}
	
	/**
	 * Get the only instance of BoundParameters.
	 *
	 * @return the instance of BoundParameters.
	 */
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
	
	/**
	 * Set the limits of parameters for all logic created.
	 *
	 * @param minDice Minimum number of dice allowed
	 * @param maxDice Maximum number of dice allowed
	 * @param minMoney Minimum money allowed at the start per player
	 * @param maxMoney Maximum money allowed at the start per player
	 * @param minTime
	 * @param maxTime
	 * @param randomGameGeneration True if randomGameGeneration allowed, false
	 * 		otherwise
	 */
	public void updateLimits(int minDice, int maxDice, int minMoney,
			int maxMoney, int minTime, int maxTime,
			boolean randomGameGeneration) {
		
		this.minDice = minDice;
		this.maxDice = maxDice;
		this.minMoneyAtTheStart = minMoney;
		this.maxMoneyAtTheStart = maxMoney;
		this.minTime = minTime;
		this.maxTime = maxTime;
		this.randomGameGeneration = randomGameGeneration;
	}
}
