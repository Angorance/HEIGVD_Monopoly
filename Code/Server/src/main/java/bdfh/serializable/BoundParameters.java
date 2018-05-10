package bdfh.serializable;

/**
 * @author Daniel Gonzalez Lopez
 * @author Héléna Line Reymond
 * @version 1.0
 */
public class BoundParameters {
	
	private int min
	
	private BoundParameters() {}
	
	private static class Instance {
		
		static final BoundParameters instance = new BoundParameters();
	}
	
	public static BoundParameters getInstance() {
		
		return Instance.instance;
	}
}
