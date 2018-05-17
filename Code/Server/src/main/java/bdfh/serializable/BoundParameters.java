package bdfh.serializable;

/**
 * Class used to update the limits of the game.
 *
 * @author Daniel Gonzalez Lopez
 * @author Héléna Line Reymond
 * @version 1.0
 */
public class BoundParameters {
	
	private int minDice;                      // Minimum number of dice allowed
	private int maxDice;                      // Maximum number of dice allowed
	private int minMoneyAtTheStart;           // Minimum money allowed at the start per player
	private int maxMoneyAtTheStart;           // Maximum money allowed at the start per player
	private boolean randomGameGeneration;     // True if random game generation allowed, false otherwise
	
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
	
	/**
	 * Set the limits of parameters for all game created.
	 *
	 * @param minDice                   Minimum number of dice allowed
	 * @param maxDice                   Maximum number of dice allowed
	 * @param minMoney                  Minimum money allowed at the start per player
	 * @param maxMoney                  Maximum money allowed at the start per player
	 * @param randomGameGeneration      True if randomGameGeneration allowed, false otherwise
	 */
	public void updateLimits(int minDice, int maxDice, int minMoney, int maxMoney, boolean randomGameGeneration) {
		this.minDice = minDice;
		this.maxDice = maxDice;
		this.minMoneyAtTheStart = minMoney;
		this.maxMoneyAtTheStart = maxMoney;
		this.randomGameGeneration = randomGameGeneration;
	}
}
