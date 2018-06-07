package bdfh.gui.controller;

import bdfh.gui.model.windowManager;
import bdfh.logic.usr.Player;
import bdfh.net.client.GameHandler;
import bdfh.net.protocol.GameProtocol;
import bdfh.serializable.LightPlayer;
import bdfh.serializable.LightSquare;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.apache.commons.lang3.tuple.MutablePair;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Controller_board implements Initializable, IWindow {
	
	@FXML private GridPane board;
	@FXML private AnchorPane popup;
	
	/*Info properties*/
	@FXML private GridPane infoProperties;
	@FXML private GridPane buttons_properties;
	@FXML private Label nameSquare;
	@FXML private Label price_prop;
	@FXML private Label base;
	@FXML private Label canape1;
	@FXML private Label canape2;
	@FXML private Label canape3;
	@FXML private Label canape4;
	@FXML private Label homecinema;
	@FXML private Label hypotheque;
	@FXML private Label prixCanape;
	@FXML private Label prixHC;
	@FXML private Label venteCanape;
	@FXML private Label venteHC;
	@FXML private JFXButton buy_buttonProp;
	@FXML private JFXButton buy_buttonCanape;
	@FXML private JFXButton sell_buttonProp;
	@FXML private JFXButton sell_buttonCanape;
	@FXML private JFXButton hyp_button;
	
	/*Info Institute*/
	@FXML private GridPane infoInstitute;
	@FXML private GridPane buttons_Institute;
	@FXML private Label price_institute;
	@FXML private Label loyer1_institute;
	@FXML private Label loyer2_institute;
	@FXML private Label loyer3_institute;
	@FXML private Label loyer4_institute;
	@FXML private JFXButton buy_buttonInstitute;
	@FXML private JFXButton sell_buttonInstitute;
	@FXML private JFXButton hyp_buttonInstitute;
	
	/*Info Company*/
	@FXML private GridPane buttons_Company;
	@FXML private GridPane infoCompany;
	@FXML private Label price_company;
	@FXML private Label loyer1_company;
	@FXML private Label loyer2_company;
	@FXML private JFXButton buy_buttonCompany;
	@FXML private JFXButton sell_buttonCompany;
	@FXML private JFXButton hyp_buttonCompany;
	
	/*Info tax*/
	@FXML private GridPane infoTax;
	@FXML private Label price_tax;
	
	/*Info player*/
	@FXML private Label label_username;
	@FXML private Label label_player1;
	@FXML private Label label_player2;
	@FXML private Label label_player3;
	@FXML private Label label_player4;
	@FXML private Label label_capital1;
	@FXML private Label label_capital2;
	@FXML private Label label_capital3;
	@FXML private Label label_capital4;
	@FXML private Label prison_player1;
	@FXML private Label prison_player2;
	@FXML private Label prison_player3;
	@FXML private Label prison_player4;
	@FXML private Label carte_player1;
	@FXML private Label carte_player2;
	@FXML private Label carte_player3;
	@FXML private Label carte_player4;
	
	@FXML private JFXButton rollDice_button;
	@FXML private JFXButton endTurn_button;
	
	private static ArrayList<FlowPane> cases = new ArrayList<>();
	private Label[] labelPlayers = new Label[4];
	private Label[] labelCapitals = new Label[4];
	private Label[] labelPrisons = new Label[4];
	private Label[] labelCartes  = new Label[4];
	private Label[] label_rentProp = new Label[6];
	private Label[] label_rentComp = new Label[2];
	private Label[] label_rentInst = new Label[4];
	private static HashMap<Integer, pawnDisplay> displayerList = new HashMap<>();
	
	private static HashMap<Integer, Integer> posPlayer = new HashMap<>();
	
	private Stage thisStage = null;
	
	private LightSquare square;
	
	public void loadPopup() {
		/* we load the form fxml*/
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/popup.fxml"));
		
		/*Create a instance of the controller of bank account form*/
		Controller_popup cp = new Controller_popup(this);
		
		/*Sets the controller associated with the root object*/
		loader.setController(cp);
		
		popup.setVisible(true);
		popup.setMouseTransparent(false);
		
		try {
			AnchorPane pane = loader.load();
			popup.getChildren().add(pane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void unloadPopup() {
		
		popup.getChildren().clear();
		popup.setMouseTransparent(true);
		popup.setVisible(false);
	}
	
	public void useCard() {
		
		Player.getInstance().useFreedomCard();
		unloadPopup();
	}
	
	public void payTax() {
		
		Player.getInstance().payExamTax();
		unloadPopup();
	}
	
	public class pawnDisplay extends AnchorPane {
		
		public pawnDisplay(String color) {
			
			this.setStyle(
					"-fx-pref-width: 25px; -fx-background-radius: 25px; -fx-pref-height: 25px;-fx-border-radius: 25px; -fx-border-width: 4px; -fx-background-color: "
							+ color + ";");
		}
	}
	
	public class squareDisplayer extends BorderPane {
		
		private FlowPane fp;
		private AnchorPane ap;
		private Label label_House;
		
		private void add(String color, int pos) {
			
			ap.setStyle("-fx-background-color: " + color + "; -fx-border-color: BLACK ; -fx-border-width: 1px");
			label_House = new Label("2");
			
			
			if (pos == 0) {
				ap.setPrefWidth(20);
				label_House.setRotate(90);
				this.setRight(ap);
				
			} else if (pos == 1) {
				ap.setPrefHeight(20);
				this.setBottom(ap);
			} else if (pos == 2) {
				ap.setPrefWidth(20);
				label_House.setRotate(270);
				this.setLeft(ap);
				
			} else {
				ap.setPrefHeight(20);
				this.setTop(ap);
			}
			
			ap.getChildren().add(label_House);
			this.setCenter(fp);
		}
		
		public squareDisplayer(LightSquare square, int pos) {
			
			String famility = square.getFamily();
			this.setStyle("-fx-border-color: BLACK; -fx-border-width: 1px");
			
			fp = new FlowPane();
			ap = new AnchorPane();
			switch (famility) {
				case GameProtocol.SQUA_BROWN:
				case GameProtocol.SQUA_CYAN:
				case GameProtocol.SQUA_PINK:
				case GameProtocol.SQUA_ORANGE:
				case GameProtocol.SQUA_RED:
				case GameProtocol.SQUA_YELLOW:
				case GameProtocol.SQUA_GREEN:
				case GameProtocol.SQUA_BLUE:
					add(famility, pos);
					break;
				case GameProtocol.SQUA_TAX:
					this.setCenter(fp);
					this.setTop(new Label("Taxe"));
					break;
				case GameProtocol.SQUA_INSTITUTE:
					this.setCenter(fp);
					this.setTop(new Label("Institut"));
					break;
				case GameProtocol.SQUA_COMPANY:
					this.setTop(new Label("Compagnie"));
					this.setCenter(fp);
					break;
				case GameProtocol.SQUA_CARD:
					this.setCenter(fp);
					this.setTop(new Label("Carte chance"));
					break;
				case GameProtocol.SQUA_START:
					this.setTop(new Label("Start"));
					this.setCenter(fp);
					break;
				case GameProtocol.SQUA_EXAM:
					this.setTop(new Label("Examen"));
					this.setCenter(fp);
					break;
				case GameProtocol.SQUA_GO_EXAM:
					this.setTop(new Label("Aller en examen"));
					this.setCenter(fp);
					break;
				case GameProtocol.SQUA_BREAK:
					this.setTop(new Label("Pause"));
					this.setCenter(fp);
					break;
			}
			
			this.setOnMouseClicked(new EventHandler<MouseEvent>() {
				
				@Override public void handle(MouseEvent event) {
					
					detailSquare(square);
				}
			});
		}
		
		public FlowPane getFP() {
			
			return fp;
		}
	}
	
	private void detailSquare(LightSquare square) {
		
		this.square = square;
		
		String family = square.getFamily();
		switch (family) {
			case GameProtocol.SQUA_BROWN:
			case GameProtocol.SQUA_CYAN:
			case GameProtocol.SQUA_PINK:
			case GameProtocol.SQUA_ORANGE:
			case GameProtocol.SQUA_RED:
			case GameProtocol.SQUA_YELLOW:
			case GameProtocol.SQUA_GREEN:
			case GameProtocol.SQUA_BLUE:
				infoProperties(square);
				break;
			case GameProtocol.SQUA_INSTITUTE:
				infoInstitute(square);
				break;
			case GameProtocol.SQUA_COMPANY:
				infoCompany(square);
				break;
			case GameProtocol.SQUA_TAX:
				infoTax(square);
				break;
			default:
				reset();
				break;
			
		}
		
	}
	
	private void reset() {
		
		nameSquare.setText("");
		infoProperties.setVisible(false);
		infoCompany.setVisible(false);
		infoInstitute.setVisible(false);
		buttons_properties.setVisible(false);
		buttons_Company.setVisible(false);
		buttons_Institute.setVisible(false);
	}
	
	private void infoProperties(LightSquare square) {
		
		infoProperties.setVisible(true);
		infoCompany.setVisible(false);
		infoInstitute.setVisible(false);
		infoTax.setVisible(false);
		buttons_properties.setVisible(true);
		buttons_Company.setVisible(false);
		buttons_Institute.setVisible(false);
		
		String name = square.getName();
		int price = square.getPrices().getPrice();
		int hyp = square.getPrices().getHypothec();
		int can = square.getPrices().getPriceCouch();
		int hc = square.getPrices().getPriceHomeCinema();
		int vcan = can / 2;
		int vhc = hc / 2;
		
		int cnt = 0;
		for (int i : square.getPrices().getRents()) {
			label_rentProp[cnt].setText(String.valueOf(i));
			cnt++;
		}
		nameSquare.setText(name);
		price_prop.setText(String.valueOf(price));
		hypotheque.setText(String.valueOf(hyp));
		prixCanape.setText(String.valueOf(can));
		prixHC.setText(String.valueOf(hc));
		venteCanape.setText(String.valueOf(vcan));
		venteHC.setText(String.valueOf(vhc));
		
		//TODO à modifier
		buy_buttonProp.setDisable(false);
		buy_buttonCanape.setDisable(false);
		sell_buttonProp.setDisable(false);
		sell_buttonCanape.setDisable(false);
		hyp_button.setDisable(false);
	}
	
	private void infoCompany(LightSquare square) {
		
		infoProperties.setVisible(false);
		infoCompany.setVisible(true);
		infoInstitute.setVisible(false);
		infoTax.setVisible(false);
		buttons_properties.setVisible(false);
		buttons_Company.setVisible(true);
		buttons_Institute.setVisible(false);
		
		String name = "Compagnie";
		nameSquare.setText(name);
		int price = square.getPrices().getPrice();
		
		price_company.setText(String.valueOf(price));
		
		int cnt = 0;
		for (String s : square.getPrices().getCompaniesRents()) {
			label_rentComp[cnt].setText(s);
			cnt++;
		}
		
	}
	
	private void infoInstitute(LightSquare square) {
		
		infoProperties.setVisible(false);
		infoCompany.setVisible(false);
		infoInstitute.setVisible(true);
		infoTax.setVisible(false);
		buttons_properties.setVisible(false);
		buttons_Company.setVisible(false);
		buttons_Institute.setVisible(true);
		
		String name = "Institut";
		int price = square.getPrices().getPrice();
		
		nameSquare.setText(name);
		price_institute.setText(String.valueOf(price));
		
		int cnt = 0;
		for (int i : square.getPrices().getRents()) {
			label_rentInst[cnt].setText(String.valueOf(i));
			cnt++;
		}
		
	}
	
	private void infoTax(LightSquare square) {
		
		infoProperties.setVisible(false);
		infoCompany.setVisible(false);
		infoInstitute.setVisible(false);
		infoTax.setVisible(true);
		buttons_properties.setVisible(false);
		buttons_Company.setVisible(false);
		buttons_Institute.setVisible(false);
		
		String name = "Taxe";
		nameSquare.setText(name);
		int price = square.getPrices().getRent();
		price_tax.setText(String.valueOf(price));
	}
	
	public void updateBoard() {
		
		Platform.runLater(() -> {
			int cnt = 0;
			for (int idPlayer : GameHandler.getInstance().getPlayers().keySet()) {
				int capital = GameHandler.getInstance().getPlayers().get(idPlayer).getCapital();
				boolean isPrison = GameHandler.getInstance().getPlayers().get(idPlayer).isInExam();
				boolean hasCard = GameHandler.getInstance().getPlayers().get(idPlayer).getFreeCards() > 0;
				labelCapitals[cnt].setText(String.valueOf(capital));
				labelPrisons[cnt].setText(isPrison ? "X" : "");
				labelCartes[cnt].setText(hasCard ? "X" : "");
				cnt++;
			}
		});
	}
	
	public static void endGame() {
		
		windowManager.getInstance().displayLobbyList();
	}
	
	@Override public void hide() {
		
		if (thisStage == null) {
			thisStage = (Stage) buy_buttonProp.getScene().getWindow();
		}
		thisStage.hide();
	}
	
	@Override public void show() {
		
		if (thisStage == null) {
			thisStage = (Stage) buy_buttonProp.getScene().getWindow();
		}
		thisStage.show();
	}
	
	private void init() {
		
		int col = 0;
		int row = 10;
		int line = 0;
		int nbCase = 0;
		
		for (LightSquare square : GameHandler.getInstance().getBoard().getSquares()) {
			
			if (nbCase == 10) {
				nbCase = 0;
				line++;
			}
			
			squareDisplayer sd = new squareDisplayer(square, line);
			cases.add(sd.getFP());
			
			if (line == 0) {
				board.add(sd, col, row--);
			} else if (line == 1) {
				board.add(sd, col++, row);
			} else if (line == 2) {
				board.add(sd, col, row++);
			} else {
				board.add(sd, col--, row);
			}
			nbCase++;
		}
		
		
		labelPlayers[0] = label_player1;
		labelPlayers[1] = label_player2;
		labelPlayers[2] = label_player3;
		labelPlayers[3] = label_player4;
		
		labelCapitals[0] = label_capital1;
		labelCapitals[1] = label_capital2;
		labelCapitals[2] = label_capital3;
		labelCapitals[3] = label_capital4;
		
		label_rentProp[0] = base;
		label_rentProp[1] = canape1;
		label_rentProp[2] = canape2;
		label_rentProp[3] = canape3;
		label_rentProp[4] = canape4;
		label_rentProp[5] = homecinema;
		
		label_rentInst[0] = loyer1_institute;
		label_rentInst[1] = loyer2_institute;
		label_rentInst[2] = loyer3_institute;
		label_rentInst[3] = loyer4_institute;
		
		label_rentComp[0] = loyer1_company;
		label_rentComp[1] = loyer2_company;
		
		labelPrisons[0] = prison_player1;
		labelPrisons[1] = prison_player2;
		labelPrisons[2] = prison_player3;
		labelPrisons[3] = prison_player4;
		
		labelCartes[0] = carte_player1;
		labelCartes[1] = carte_player2;
		labelCartes[2] = carte_player3;
		labelCartes[3] = carte_player4;
		
	}
	
	public void move(int idPlayer, int pos) {
		
		Platform.runLater(() -> {
			int tmp = GameHandler.getInstance().getPlayers().get(idPlayer).getPosition();
			pawnDisplay tmpPD = displayerList.get(idPlayer);
			cases.get(tmp).getChildren().removeAll(tmpPD);
			cases.get(pos).getChildren().add(tmpPD);
			GameHandler.getInstance().getPlayers().get(idPlayer).setPosition(pos);
			
			detailSquare(GameHandler.getInstance().getBoard().getSquares().get(pos));
		});
	}
	
	public void movePawn(int idPlayer, List<Integer> dices) {
		
		Platform.runLater(() -> {
			int pos = GameHandler.getInstance().getPlayers().get(idPlayer).getPosition();
			int sumDice = 0;
			
			for (int i : dices) {
				sumDice += i;
			}
			
			int tmp = (pos + sumDice) % 40;
			pawnDisplay tmpPD = displayerList.get(idPlayer);
			cases.get(pos).getChildren().removeAll(tmpPD);
			cases.get(tmp).getChildren().add(tmpPD);
			GameHandler.getInstance().getPlayers().get(idPlayer).setPosition(tmp);
			if (Player.getInstance().isMyTurn()) {
				rollDice_button.setDisable(true);
				endTurn_button.setDisable(false);
			}
			
			detailSquare(GameHandler.getInstance().getBoard().getSquares().get(tmp));
		});
	}
	
	public void rollDice() {
		
		Player.getInstance().rollDice();
		unloadPopup();
	}
	
	private void endTurn() {
		
		Player.getInstance().endTurn();
		
	}
	
	public void notifyTurn() {
		
		if (Player.getInstance().isMyTurn()) {
			rollDice_button.setDisable(false);
			endTurn_button.setDisable(true);
		} else {
			rollDice_button.setDisable(true);
			endTurn_button.setDisable(true);
		}
	}
	
	private void sellHouse() {
		
		if (true/*canSellCouch*/) {
			square.sellCouch();
		} else {
			square.sellHomeCinema();
		}
		
	}
	
	private void buyHouse() {
		
		if (true/*canBuyCouch*/) {
			square.buyCouch();
		} else {
			square.buyHomeCinema();
		}
	}
	
	private void sellProp() {
		square.sellSquare();
	}
	
	private void buyProp() {
		square.buySquare();
	}
	
	@Override public void initialize(URL location, ResourceBundle resources) {
		
		popup.setMouseTransparent(true);
		popup.setVisible(false);
		
		rollDice_button.setDisable(true);
		endTurn_button.setDisable(true);
		label_username.setText(Player.getInstance().getUsername());
		
		synchronized (GameHandler.getInstance()) {
			if (GameHandler.getInstance().getBoard() == null) {
				try {
					GameHandler.getInstance().wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		init();
		
		
		String[] color = { "RED", "BLUE", "GREEN", "YELLOW" };
		
		int cnt = 0;
		for (int idPlayer : GameHandler.getInstance().getPlayers().keySet()) {
			pawnDisplay pd = new pawnDisplay(color[cnt]);
			cases.get(0).getChildren().add(pd);
			displayerList.put(idPlayer, pd);
			LightPlayer lp = GameHandler.getInstance().getPlayers().get(idPlayer);
			String username = lp.getUsername();
			int capital = lp.getCapital();
			boolean isPrison =  lp.isInExam();
			boolean hasCard = lp.getFreeCards() > 0;
			
			labelPlayers[cnt].setText(username);
			labelPlayers[cnt].setStyle("-fx-text-fill: " + color[cnt]);
			labelCapitals[cnt].setText(String.valueOf(capital));
			
			labelPrisons[cnt].setText(isPrison ? "X" : "");
			labelCartes[cnt].setText(hasCard ? "X" : "");
			
			cnt++;
		}
		
		rollDice_button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				
				rollDice();
			}
		});
		
		endTurn_button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				
				endTurn();
			}
		});
		
		buy_buttonProp.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				
				buyProp();
			}
		});
		
		sell_buttonProp.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				
				sellProp();
			}
		});
		
		buy_buttonCanape.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				
				buyHouse();
			}
		});
		
		sell_buttonCanape.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				
				sellHouse();
			}
		});
		
		
		windowManager.getInstance().setBoard(this);
		
		synchronized (GameHandler.getInstance()) {
			
			GameHandler.getInstance().setSub(this);
			GameHandler.getInstance().notify();
		}
	}
}
