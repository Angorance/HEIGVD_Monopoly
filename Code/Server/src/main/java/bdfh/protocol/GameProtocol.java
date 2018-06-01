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
	public static final String CARD_FREE = "FREE";
	public static final String CARD_EACH = "EACH";
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
	
	// commande envoyée du serveur (logique de jeu)
	public static final String GAM_PLAY = "PLAY"; // indique au joueur que c'est son tour
	public static final String GAM_PLYR = "PLYR"; // envoie la liste des joueurs et leur capitaux
	public static final String GAM_ROLL = "ROLL"; // commande pour lancer les dés
	public static final String GAM_DRAW = "DRAW"; // informe le joueur de la carte tirée
	public static final String GAM_ENDT = "ENDT"; // commande de fin de tour
	public static final String GAM_BOARD = "BOARD"; // transmission du plateau de jeu
	public static final String GAM_PAY = "PAYS"; // le joueur indiqué a perdu X
	public static final String GAM_GAIN = "GAIN"; // le joueur indiqué a gagné X
	public static final String GAM_DENY = "DENY"; // le joueur refuse le paiement
	public static final String GAM_BUYS = "BUYS"; // le joueur devient propriétaire d'une case
	public static final String GAM_FREE = "FREE"; // le joueur a la possibilité d'acheter une case
	public static final String GAM_MOV = "MOVE"; // le joueur doit bouger
	public static final String GAM_EXAM = "EXAM"; // le joueur est envoyé en salle d'examen
	public static final String GAM_FRDM = "FRDM"; // le joueur est sorti de salle d'examen
}