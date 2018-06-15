package bdfh.net.protocol;

/**
 * Définit le protocole utilisé par le serveur pour le jeu (cartes et cases).
 *
 * @author Héléna Line Reymond
 * @version 1.0
 */
public abstract class GameProtocol {
	
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
	
	// Commands
	public static final String GAM_PLAY = "PLAY";
	public static final String GAM_ROLL = "ROLL";
	public static final String GAM_DRAW = "DRAW";
	public static final String GAM_ENDT = "ENDT";
	public static final String GAM_BOARD = "BOARD";
	public static final String GAM_PAY = "PAYS";
	public static final String GAM_GAIN = "GAIN";
	public static final String GAM_BUYS = "BUYS";
	public static final String GAM_SELL = "SELL";
	public static final String GAM_PLYR = "PLYR";
	public static final String GAM_MOV = "MOVE";
	public static final String GAM_EXAM = "EXAM";
	public static final String GAM_FRDM = "FRDM";
	public static final String GAM_HYPOT = "HPTQ";
	public static final String GAM_NHYPOT = "NHPTQ";
	public static final String GAM_FRDM_C = "FRDM_C";
	public static final String GAM_FRDM_U = "FRDM_U";
	public static final String GAM_FRDM_T = "FRDM_T";
	public static final String GAM_BCOUCH = "BCOUCH"; // le joueur achète un canapé.
	public static final String GAM_SCOUCH = "SCOUCH"; // le joueur vend un canapé.
	public static final String GAM_BHCINE = "BHCINE"; // le joueur achète un home cinema.
	public static final String GAM_SHCINE = "SHCINE"; // le joueur vend un home cinema.
	public static final String GAM_BKRPT = "BKRPT"; // le joueur est en banqueroute
	public static final String GAM_GOVR = "GOVR"; // game over ~~ le joueur a perdu
	public static final String GAM_WIN = "WIN"; // game over ~~ le joueur a gagné
	public static final String GAM_RST = "RST"; // reset une case
	public static final String GAM_QUIT = "Q";
	
	// Factors used to calculate rents
	public static final int RENT_TO_1COUC = 5;
	public static final int RENT_TO_2COUC = 15;
	public static final int RENT_TO_3COUC = 30;
	public static final int RENT_TO_4COUC = 45;
	public static final int RENT_TO_1HOME = 54;
	public static final int RENT_TO_1INST = 1;
	public static final int RENT_TO_2INST = 2;
	public static final int RENT_TO_3INST = 4;
	public static final int RENT_TO_4INST = 8;
	public static final int RENT_TO_1COMP = 4;
	public static final int RENT_TO_2COMP = 10;
}