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
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.apache.commons.lang3.tuple.MutablePair;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Controller_board implements Initializable, IWindow {
	
	@FXML private GridPane board;
	@FXML private AnchorPane popup;
	@FXML private Label message;
	@FXML private VBox vbox_Log;
	
	
	@FXML private Label loyer0;
	@FXML private Label loyer1;
	@FXML private Label loyer2;
	@FXML private Label loyer3;
	@FXML private Label loyer4;
	@FXML private Label loyer5;
	
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
	@FXML private JFXButton dehyp_button;
	
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
	@FXML private JFXButton dehyp_buttonInstitute;
	
	/*Info Company*/
	@FXML private GridPane buttons_Company;
	@FXML private GridPane infoCompany;
	@FXML private Label price_company;
	@FXML private Label loyer1_company;
	@FXML private Label loyer2_company;
	@FXML private JFXButton buy_buttonCompany;
	@FXML private JFXButton sell_buttonCompany;
	@FXML private JFXButton hyp_buttonCompany;
	@FXML private JFXButton dehyp_buttonCompany;
	
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
	@FXML private JFXButton quit_button;
	
	private static ArrayList<squareDisplayer> cases = new ArrayList<>();
	private Label[] labelNameLoyer = new Label[6];
	private Label[] labelPlayers = new Label[4];
	private Label[] labelCapitals = new Label[4];
	private Label[] labelPrisons = new Label[4];
	private Label[] labelCartes = new Label[4];
	private Label[] label_rentProp = new Label[6];
	private Label[] label_rentComp = new Label[2];
	private Label[] label_rentInst = new Label[4];
	private static HashMap<Integer, pawnDisplay> displayerList = new HashMap<>();
	private static HashMap<Integer, String> colorPlayer = new HashMap<>();
	
	private Stage thisStage = null;
	
	private LightSquare square;
	
	public void errorMessage(String error) {
		
		Platform.runLater(() -> {
			message.setText(error);
			message.setStyle("-fx-text-fill: RED; -fx-font-weight: bold;");
		});
	}
	
	public void logMessage(int idPlayer, boolean isBold, String log) {
		
		Platform.runLater(() -> {
			Label label_log = new Label(log);
			label_log.setStyle("-fx-text-fill: " + colorPlayer.get(idPlayer) + ";");
			
			if (isBold) {
				label_log.setStyle("-fx-font-weight: bold;");
			}
			vbox_Log.getChildren().add(0, label_log);
			
		});
	}
	
	public void loadPopup() {
		
		Platform.runLater(() -> {
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
		});
	}
	
	public void loadPopup_BR() {
		
		Platform.runLater(() -> {
			/* we load the form fxml*/
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/popup_BR.fxml"));
			
			/*Create a instance of the controller of bank account form*/
			Controller_popupBR cp = new Controller_popupBR(this);
			
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
		});
	}
	
	public void unloadPopup() {
		
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
					"-fx-pref-width: 25px; -fx-background-radius: 25px; -fx-pref-height: 25px;-fx-border-radius: 25px; -fx-border-width: 2px; -fx-background-color: "
							+ color + "; -fx-border-color: BLACK;");
		}
	}
	
	public void removePawn(int idPlayer, int pos) {
		
		Platform.runLater(()->{
			pawnDisplay tmpPD = displayerList.get(idPlayer);
			cases.get(pos).getFP().getChildren().removeAll(tmpPD);
		});

	}
	
	public class squareDisplayer extends BorderPane {
		
		private FlowPane fp;
		private FlowPane ap;
		private Label label_House;
		private LightSquare mySquare;
		
		private void add(String color, int pos) {
			
			ap.setStyle("-fx-background-color: " + color + "; -fx-border-color: BLACK ; -fx-border-width: 1px");
			label_House = new Label(String.valueOf(mySquare.getNbCouches()));
			label_House.setFont(new Font("Cambria", 12));
			label_House.setAlignment(Pos.CENTER);
			
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
			mySquare = square;
			int rotation = 90 * (pos+1);
			String famility = square.getFamily();
			this.setStyle("-fx-border-color: BLACK; -fx-border-width: 1px");
			Label name = new Label();
			name.setStyle("-fx-background-color: transparent");
			fp = new FlowPane();
			ap = new FlowPane();
			ap.setAlignment(Pos.CENTER);
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
					name.setText("Taxe");
					this.setTop(name);
					break;
				case GameProtocol.SQUA_INSTITUTE:
					this.setCenter(fp);
					name.setText("Institut");
					this.setTop(name);
					break;
				case GameProtocol.SQUA_COMPANY:
					name.setText("Compagnie");
					this.setTop(name);
					this.setCenter(fp);
					break;
				case GameProtocol.SQUA_CARD:
					this.setCenter(fp);
					String image = getClass().getResource("/gui/image/chance" + pos +".png").toExternalForm();
					fp.setStyle("-fx-background-image: url('" + image + "'); -fx-background-size: stretch;");
					break;
				case GameProtocol.SQUA_START:
					name.setText("Start");
					this.setTop(name);
					this.setCenter(fp);
					break;
				case GameProtocol.SQUA_EXAM:
					name.setText("Examen");
					this.setTop(name);
					this.setCenter(fp);
					break;
				case GameProtocol.SQUA_GO_EXAM:
					name.setText("Aller en examen");
					this.setTop(name);
					this.setCenter(fp);
					break;
				case GameProtocol.SQUA_BREAK:
					name.setText("Pause");
					this.setTop(name);
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
		
		public void redraw() {
			
			if (mySquare.hasCine()) {
				label_House.setText("HC : 1");
			} else {
				label_House.setText("C : " + String.valueOf(mySquare.getNbCouches()));
			}
		}
		
	}
	
	public void redrawUsername(int playerOrder) {
		labelPlayers[playerOrder].setStyle("-fx-text-fill: black");
	}
	
	public void redrawSquare(int pos) {
		
		Platform.runLater(() -> {
			cases.get(pos).redraw();
		});
	}
	
	public void setOwner(int pos, int idPlayer) {
		
		String colorUse;
		if (idPlayer != -1) {
			colorUse = colorPlayer.get(idPlayer) + "88";
		} else {
			colorUse = "transparent";
		}
		cases.get(pos).getFP().setStyle("-fx-background-color: " + colorUse + ";");
	}
	
	public void setHypothec(int pos, boolean hypothec, int idPlayer) {
		
		if(hypothec) {
			cases.get(pos).getFP().setStyle("-fx-background-color: " + "lightgrey" + ";");
		} else {
			
			cases.get(pos).getFP().setStyle("-fx-background-color: " + colorPlayer.get(idPlayer) + "88;");
		}
	}
	
	public void detailSquare(LightSquare square) {
		
		Platform.runLater(()->{
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
			propertiesButton();
		});

		
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
		price_prop.setStyle("-fx-font-weight: bold;");
		
		hypotheque.setText(String.valueOf(hyp));
		prixCanape.setText(String.valueOf(can));
		prixHC.setText(String.valueOf(hc));
		venteCanape.setText(String.valueOf(vcan));
		venteHC.setText(String.valueOf(vhc));
		
		buy_buttonProp.setDisable(false);
		buy_buttonCanape.setDisable(false);
		sell_buttonProp.setDisable(false);
		sell_buttonCanape.setDisable(false);
		hyp_button.setDisable(false);
		
		infoLoyer();
	}
	
	private void infoLoyer() {
		
		int nbCouche = square.getNbCouches();
		for (int i = 0; i < 6; ++i) {
			label_rentProp[i].setStyle("-fx-text-fill: black");
			labelNameLoyer[i].setStyle("-fx-text-fill: black");
			if ((i == nbCouche && !square.hasCine()) || (i == 5 && square.hasCine())) {
				label_rentProp[i].setStyle("-fx-text-fill: green;-fx-font-weight: bold;");
				labelNameLoyer[i].setStyle("-fx-text-fill: green;-fx-font-weight: bold;");
			}
		}
	}
	
	public void propertiesButton() {
		
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
				propertiesProd();
				break;
			case GameProtocol.SQUA_INSTITUTE:
				propertiesInstitut();
				break;
			case GameProtocol.SQUA_COMPANY:
				propertiesCompany();
				break;
			
		}
	}
	
	private void propertiesProd() {
		
		if (square.getOwner() == null) {
			if (Player.getInstance().isMyTurn() && square.getPosition() == GameHandler.getInstance().getPlayers()
					.get(Player.getInstance().getID()).getPosition()) {
				buy_buttonProp.setDisable(false);
				buy_buttonCanape.setDisable(true);
				sell_buttonProp.setDisable(true);
				sell_buttonCanape.setDisable(true);
				hyp_button.setDisable(true);
				dehyp_button.setDisable(true);
			} else {
				buy_buttonProp.setDisable(true);
				buy_buttonCanape.setDisable(true);
				sell_buttonProp.setDisable(true);
				sell_buttonCanape.setDisable(true);
				hyp_button.setDisable(true);
				dehyp_button.setDisable(true);
			}
		} else if (square.getOwner().getId() == Player.getInstance().getID()) {
			boolean hasCine = square.hasCine();
			boolean canSellHouse = square.getNbCouches() > 0 || square.hasCine();
			boolean isMortgage = square.isMortgaged();
			
			if (square.getNbCouches() == 4) {
				buy_buttonCanape.setText("Achat HC");
			} else {
				buy_buttonCanape.setText("Achat canapé");
			}
			
			if (square.hasCine()) {
				sell_buttonCanape.setText("Vente HC");
			} else {
				sell_buttonCanape.setText("Vente canapé");
				
			}
			buy_buttonProp.setDisable(true);
			buy_buttonCanape.setDisable(square.hasCine());
			sell_buttonProp.setDisable(canSellHouse);
			sell_buttonCanape.setDisable(!canSellHouse);
			hyp_button.setDisable(isMortgage);
			dehyp_button.setDisable(!isMortgage);
		} else {
			buy_buttonProp.setDisable(true);
			buy_buttonCanape.setDisable(true);
			sell_buttonProp.setDisable(true);
			sell_buttonCanape.setDisable(true);
			hyp_button.setDisable(true);
			dehyp_button.setDisable(true);
		}
	}
	
	private void propertiesInstitut() {
		
		if (square.getOwner() == null) {
			if (Player.getInstance().isMyTurn() && square.getPosition() == GameHandler.getInstance().getPlayers()
					.get(Player.getInstance().getID()).getPosition()) {
				buy_buttonInstitute.setDisable(false);
				sell_buttonInstitute.setDisable(true);
				hyp_buttonInstitute.setDisable(true);
				dehyp_buttonInstitute.setDisable(true);
			} else {
				buy_buttonInstitute.setDisable(true);
				sell_buttonInstitute.setDisable(true);
				hyp_buttonInstitute.setDisable(true);
				dehyp_buttonInstitute.setDisable(true);
			}
		} else if (square.getOwner().getId() == Player.getInstance().getID()) {
			boolean isMortgage = square.isMortgaged();
			buy_buttonInstitute.setDisable(true);
			sell_buttonInstitute.setDisable(false);
			hyp_buttonInstitute.setDisable(isMortgage);
			dehyp_buttonInstitute.setDisable(!isMortgage);
		} else {
			buy_buttonInstitute.setDisable(true);
			sell_buttonInstitute.setDisable(true);
			hyp_buttonInstitute.setDisable(true);
			dehyp_buttonInstitute.setDisable(true);
		}
	}
	
	private void propertiesCompany() {
		
		if (square.getOwner() == null) {
			if (Player.getInstance().isMyTurn() && square.getPosition() == GameHandler.getInstance().getPlayers()
					.get(Player.getInstance().getID()).getPosition()) {
				buy_buttonCompany.setDisable(false);
				sell_buttonCompany.setDisable(true);
				hyp_buttonCompany.setDisable(true);
				dehyp_buttonCompany.setDisable(true);
			} else {
				buy_buttonCompany.setDisable(true);
				sell_buttonCompany.setDisable(true);
				hyp_buttonCompany.setDisable(true);
				dehyp_buttonCompany.setDisable(true);
			}
		} else if (square.getOwner().getId() == Player.getInstance().getID()) {
			boolean isMortgage = square.isMortgaged();
			buy_buttonCompany.setDisable(true);
			sell_buttonCompany.setDisable(false);
			hyp_buttonCompany.setDisable(isMortgage);
			dehyp_buttonCompany.setDisable(!isMortgage);
		} else {
			buy_buttonCompany.setDisable(true);
			sell_buttonCompany.setDisable(true);
			hyp_buttonCompany.setDisable(true);
			dehyp_buttonCompany.setDisable(true);
		}
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
		price_company.setStyle("-fx-font-weight: bold;");
		
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
		price_institute.setStyle("-fx-font-weight: bold;");
		
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
				LightPlayer lp = GameHandler.getInstance().getPlayers().get(idPlayer);
				labelCapitals[lp.getOrder()].setText(String.valueOf(capital));
				labelPrisons[lp.getOrder()].setText(isPrison ? "X" : "");
				labelCartes[lp.getOrder()].setText(hasCard ? "X" : "");
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
			cases.add(sd);
			
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
		
		labelNameLoyer[0] = loyer0;
		labelNameLoyer[1] = loyer1;
		labelNameLoyer[2] = loyer2;
		labelNameLoyer[3] = loyer3;
		labelNameLoyer[4] = loyer4;
		labelNameLoyer[5] = loyer5;
		
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
			cases.get(tmp).getFP().getChildren().removeAll(tmpPD);
			cases.get(pos).getFP().getChildren().add(tmpPD);
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
			cases.get(pos).getFP().getChildren().removeAll(tmpPD);
			cases.get(tmp).getFP().getChildren().add(tmpPD);
			GameHandler.getInstance().getPlayers().get(idPlayer).setPosition(tmp);
			
			detailSquare(GameHandler.getInstance().getBoard().getSquares().get(tmp));
		});
	}
	
	public void rollDice() {
		
		unloadPopup();
		Player.getInstance().rollDice();
		rollDice_button.setDisable(true);
		endTurn_button.setDisable(false);
	}
	
	private void endTurn() {
		
		Player.getInstance().endTurn();
		
	}
	
	private void quit() {
		
		Player.getInstance().exitGame();
		endGame();
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
		
		if (!square.hasCine()) {
			square.sellCouch();
		} else {
			square.sellHomeCinema();
		}
		
	}
	
	private void buyHouse() {
		
		
		if (square.getNbCouches() < 4) {
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
	
	private void hypoth() {
		
		square.setMortgage();
	}
	
	private void dehypoth() {
		
		square.cancelMortgage();
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
		
		
		String[] color;
		color = new String[] { "#d60e0e" /*rouge*/, "#0766ff" /*bleu */, "#00ad1f" /*vert*/, "#cc28be"/*violet*/ };
		
		int cnt = 0;
		for (int idPlayer : GameHandler.getInstance().getPlayers().keySet()) {
			colorPlayer.put(idPlayer, color[cnt]);
			pawnDisplay pd = new pawnDisplay(color[cnt]);
			cases.get(0).getFP().getChildren().add(pd);
			displayerList.put(idPlayer, pd);
			LightPlayer lp = GameHandler.getInstance().getPlayers().get(idPlayer);
			String username = lp.getUsername();
			int capital = lp.getCapital();
			boolean isPrison = lp.isInExam();
			boolean hasCard = lp.getFreeCards() > 0;
			
			labelPlayers[lp.getOrder()].setText(username);
			labelPlayers[lp.getOrder()].setStyle("-fx-text-fill: " + color[cnt]);
			labelCapitals[lp.getOrder()].setText(String.valueOf(capital));
			
			labelPrisons[lp.getOrder()].setText(isPrison ? "X" : "");
			labelCartes[lp.getOrder()].setText(hasCard ? "X" : "");
			
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
		
		quit_button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				
				quit();
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
		
		hyp_button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				
				hypoth();
			}
		});
		
		dehyp_button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				
				dehypoth();
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
		
		buy_buttonInstitute.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				
				buyProp();
			}
		});
		
		sell_buttonInstitute.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				
				sellProp();
			}
		});
		
		hyp_buttonInstitute.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				
				hypoth();
			}
		});
		
		dehyp_buttonInstitute.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				
				dehypoth();
			}
		});
		
		buy_buttonCompany.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				
				buyProp();
			}
		});
		
		sell_buttonCompany.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				
				sellProp();
			}
		});
		
		hyp_buttonCompany.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				
				hypoth();
			}
		});
		
		dehyp_buttonCompany.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override public void handle(ActionEvent event) {
				
				dehypoth();
			}
		});
		
		windowManager.getInstance().setBoard(this);
		
		synchronized (GameHandler.getInstance()) {
			
			GameHandler.getInstance().setSub(this);
			GameHandler.getInstance().notify();
		}
	}
}
