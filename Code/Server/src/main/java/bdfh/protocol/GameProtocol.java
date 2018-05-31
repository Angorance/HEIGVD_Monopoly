package bdfh.protocol;

/**
 * Définit le protocole utilisé par le serveur pour le jeu (cartes et cases).
 *
 * @author Héléna Line Reymond
 * @version 1.0
 */
public abstract class GameProtocol {
	
	// Cards
	public static final String CARD_MOVE = "MOVE";
	public static final String CARD_BACK = "BACK";
	public static final String CARD_WIN = "WIN";
	public static final String CARD_LOSE = "LOSE";
	public static final String CARD_GOTO = "GOTO";
	public static final String CARD_CARD = "CARD";
	public static final String CARD_FREE = "FREE";
	public static final String CARD_EACH = "EACH";
	public static final String CARD_CHOICE = "CHOICE";
	public static final String CARD_REP = "REP";
	
	// Squares
	public static final String SQUA_TAX = "TAX";
	public static final String SQUA_INSTITUTE = "INSTITUTE";
	public static final String SQUA_COMPANY = "COMPANY";
	public static final String SQUA_CARD = "CARD";
	public static final String SQUA_START = "START";
	public static final String SQUA_EXAM = "EXAM";
	public static final String SQUA_GO_EXAM = "GO_EXAM";
	public static final String SQUA_BREAK = "BREAK";
	public static final String SQUA_BROWN = "BROWN";
	public static final String SQUA_CYAN = "CYAN";
	public static final String SQUA_PINK = "PINK";
	public static final String SQUA_ORANGE = "ORANGE";
	public static final String SQUA_RED = "RED";
	public static final String SQUA_YELLOW = "YELLOW";
	public static final String SQUA_GREEN = "GREEN";
	public static final String SQUA_BLUE = "BLUE";
	
}