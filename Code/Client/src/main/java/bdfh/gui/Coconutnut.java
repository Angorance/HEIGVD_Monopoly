package bdfh.gui;

/**
 * @version 1.0
 * @authors Daniel Gonzalez Lopez
 */
public class Coconutnut {
	
	
	private Coconutnut() {}
	
	private static class Instance {
		
		static final Coconutnut instance = new Coconutnut();
	}
	
	public static Coconutnut getInstance() {
		
		return Instance.instance;
	}
}
