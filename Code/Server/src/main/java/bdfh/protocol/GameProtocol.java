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
	public static final String CARD_EXAM = "EXAM";
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
	public static final String GAM_DENY = "DENY"; // impossible d'effectuer la commande
	public static final String GAM_BUYS = "BUYS"; // le joueur devient propriétaire d'une case
	public static final String GAM_SELL = "SELL"; // le joueur vend une propriété
	public static final String GAM_FREE = "FREE"; // le joueur a la possibilité d'acheter une case
	public static final String GAM_MOV = "MOVE"; // le joueur doit bouger
	public static final String GAM_EXAM = "EXAM"; // le joueur est envoyé en salle d'examen
	public static final String GAM_FRDM = "FRDM"; // le joueur est sorti de salle d'examen
	public static final String GAM_FRDM_C = "FRDM_C";   // le joueur a reçu une carte de sortie d'examen
	public static final String GAM_FRDM_U = "FRDM_U";   // le joueur a utilisé une carte de sortie d'examen
	public static final String GAM_FRDM_T = "FRDM_T";   // le joueur a payé la taxe de sortie d'examen
	public static final String GAM_HYPOT = "HPTQ"; // le joueur met en hypothèque une propriété
	public static final String GAM_NHYPOT = "NHPTQ"; // le joueur lève l'hypothèque d'une propriété
	public static final String GAM_BCOUCH = "BCOUCH"; // le joueur achète un canapé.
	public static final String GAM_SCOUCH = "SCOUCH"; // le joueur vend un canapé.
	public static final String GAM_BHCINE = "BHCINE"; // le joueur achète un home cinema.
	public static final String GAM_SHCINE = "SHCINE"; // le joueur vend un home cinema.

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
	
	public static final double RATE_HYPOTHEQUE = 1.1;
	
	public static final int SUCCESS = 0;
	public static final int NOT_ENOUGH_MONEY = 1;
	public static final int NOT_ENOUGH_COUCHES = 2;
	public static final int BAD_DISTRIBUTION = 3;
	public static final int FULL = 4;
	public static final int NOT_FULL_FAMILY = 5;
	public static final int NOT_YOUR_TURN = 6;
	
	public static final String ERR1 = "Vous n'avez pas assez d'argent.";
	public static final String ERR2 = "Il n'y a pas assez de canapés sur la case.";
	public static final String ERR3 = "Vous n'avez pas assez de canapés dans les autres cases de la même famille.";
	public static final String ERR4 = "Il y a trop de canapés / Home Cinema sur cette case";
	public static final String ERR5 = "Vous ne possédez pas encore toutes les cases de la famille.";
	public static final String ERR6 = "C'est n'est pas à votre tour de jouer.";
}