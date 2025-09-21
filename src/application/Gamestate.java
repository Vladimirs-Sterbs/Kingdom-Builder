package application;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Pattern;
import java.awt.Toolkit;
import java.awt.Dimension;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.scene.text.*;
import javafx.scene.image.*;

public class Gamestate {

	@FXML
	TabPane pane;
	@FXML
	AnchorPane gamePane, cardsPane, scoringPane, rulesPane, PinfoextraPane, discardPane;
	@FXML
	ImageView TR;
	@FXML
	ImageView TL;
	@FXML
	ImageView BR;
	@FXML
	ImageView BL;
	@FXML
	ImageView Explanation1;
	@FXML
	ImageView Explanation2;
	@FXML
	ImageView Explanation3;
	@FXML
	ImageView Explanation4;
	@FXML
	ImageView KBC1;
	@FXML
	ImageView KBC2;
	@FXML
	ImageView KBC3;
	@FXML
	ImageView KBCB1;
	@FXML
	ImageView KBCB2;
	@FXML
	ImageView KBCB3;
	@FXML
	ImageView TerrainCard, setmain, firstplayertok1, firstplayertok;
	@FXML
	ImageView set1, set2, set3;
	@FXML
	ImageView EA1, EA11, EA111, EA1111;
	@FXML
	ImageView EA2, EA21, EA211, EA2111;
	@FXML
	ImageView EA3, EA31, EA311, EA3111;
	@FXML
	ImageView EA4, EA41, EA411, EA4111;
	@FXML
	ImageView EA5, EA51, EA511, EA5111;
	@FXML
	ImageView EA6, EA61, EA611, EA6111;
	@FXML
	ImageView EA7, EA71, EA711, EA7111;
	@FXML
	ImageView EA8, EA81, EA811, EA8111, cardBack;
	@FXML
	Text playerstate, otherplayer1, otherplayer2, otherplayer3, settext1, settext2, settext3, settextmain, Info,
			setleft1, setleft2, discardtext1, discardtext2, discardtext3, discardtext4, discardtext5;
	@FXML
	Text p11, p12, p13, p14, p15, p21, p22, p23, p24, p25, p31, p32, p33, p34, p35, p41, p42, p43, p44, p45;
	@FXML
	Text KBCard1, KBCard2, KBCard3;
	@FXML
	Text p1, p2, p3, p4;
	@FXML
	Text MM, toScores, toBoard, winners;
	@FXML
	ImageView rules;

	private Scene scene;
	private Stage stage;
	private Parent root;
	private Board board;
	private Tile[][] tileMat;
	private int page, lossX, lossY;
	private int[] cards;
	private Player[] players;
	private int turn, currentPlayer, numberPlaced, state;
	private String currentExtra, lossEA;
	private Group highlights, settlements;
	private Deck deck;
	private Scoring scoring;
	private ImageView[] EAs, EAs1, EAs11, EAs111;
	private ArrayList<Integer> availableTiles;
	private boolean waitingToLose;
	private Text[][] scores;
	private String[][] texts;

	public void initialize() throws IOException {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int) screenSize.getHeight();
		int width = (int) screenSize.getWidth();
		width = (int) (width * 0.8333333333333333333333333);
		height = (int) (height * 0.8333333333333333333333333333);
		double w = width;
		double h = height;
		System.out.println(w + " " + h);
		gamePane.setScaleY((h / 900));
		gamePane.setScaleX((w / 1600));
		cardsPane.setScaleY((h / 900));
		cardsPane.setScaleX((w / 1600));
		scoringPane.setScaleY((h / 900));
		scoringPane.setScaleX((w / 1600));
		rulesPane.setScaleY((h / 900));
		rulesPane.setScaleX((w / 1600));
		PinfoextraPane.setScaleX((w / 1600));
		PinfoextraPane.setScaleY((h / 900));
		discardPane.setScaleX((w / 1600));
		discardPane.setScaleY((h / 900));
		double x = w / 10;
		double y = (-(0.030620285939435 * (x * x))) + (4.8486997635934 * x) + 8.0873578745919;
		double x2 = h;
		double y2 = (-(0.00070512820512821 * (x2 * x2))) + (0.72435897435897 * x2) - 80.769230769231;
		gamePane.setTranslateY(-y2);
		gamePane.setTranslateX(-y);
		cardsPane.setTranslateY(-y2);
		cardsPane.setTranslateX(-y);
		scoringPane.setTranslateY(-y2);
		scoringPane.setTranslateX(-y);
		rulesPane.setTranslateY(-y2);
		rulesPane.setTranslateX(-y);
		PinfoextraPane.setTranslateY(-y2);
		PinfoextraPane.setTranslateX(-y);
		discardPane.setTranslateY(-y2);
		discardPane.setTranslateX(-y);
		cards = pullKingdomBuilderCards();
		board = new Board();
		board.createBoard();
		turn = 0;
		numberPlaced = 0;
		state = 1;
		waitingToLose = false;
		scoring = new Scoring(board);
		// System.out.println(board);
		int[] nums = board.getBoardNumbers();
		boolean[] orientations = board.getOrientations();
		Image image = new Image(getClass().getResourceAsStream(
				"/application/assets/boards/board" + nums[0] + (orientations[0] ? "_flip" : "") + "@2x.png"));
		TL.setImage(image);
		image = new Image(getClass().getResourceAsStream(
				"/application/assets/boards/board" + nums[1] + (orientations[1] ? "_flip" : "") + "@2x.png"));
		TR.setImage(image);
		image = new Image(getClass().getResourceAsStream(
				"/application/assets/boards/board" + nums[2] + (orientations[2] ? "_flip" : "") + "@2x.png"));
		BL.setImage(image);
		image = new Image(getClass().getResourceAsStream(
				"/application/assets/boards/board" + nums[3] + (orientations[3] ? "_flip" : "") + "@2x.png"));
		BR.setImage(image);
		tileMat = board.getBoard();
		image = new Image(
				getClass().getResourceAsStream("/application/assets/explantion tokens/exp" + nums[0] + ".png"));
		Explanation1.setImage(image);
		image = new Image(
				getClass().getResourceAsStream("/application/assets/explantion tokens/exp" + nums[1] + ".png"));
		Explanation2.setImage(image);
		image = new Image(
				getClass().getResourceAsStream("/application/assets/explantion tokens/exp" + nums[2] + ".png"));
		Explanation3.setImage(image);
		image = new Image(
				getClass().getResourceAsStream("/application/assets/explantion tokens/exp" + nums[3] + ".png"));
		Explanation4.setImage(image);
		image = new Image(
				getClass().getResourceAsStream("/application/assets/special card names/" + cards[0] + ".png"));
		KBC1.setImage(image);
		image = new Image(
				getClass().getResourceAsStream("/application/assets/special card names/" + cards[1] + ".png"));
		KBC2.setImage(image);
		image = new Image(
				getClass().getResourceAsStream("/application/assets/special card names/" + cards[2] + ".png"));
		KBC3.setImage(image);
		image = new Image(
				getClass().getResourceAsStream("/application/assets/Special Cards/" + cards[0] + ".png"));
		KBCB1.setImage(image);
		image = new Image(
				getClass().getResourceAsStream("/application/assets/Special Cards/" + cards[1] + ".png"));
		KBCB2.setImage(image);
		image = new Image(
				getClass().getResourceAsStream("/application/assets/Special Cards/" + cards[2] + ".png"));
		KBCB3.setImage(image);
		EAs = new ImageView[8];
		EAs[0] = EA1;
		EAs[1] = EA2;
		EAs[2] = EA3;
		EAs[3] = EA4;
		EAs[4] = EA5;
		EAs[5] = EA6;
		EAs[6] = EA7;
		EAs[7] = EA8;
		EAs1 = new ImageView[8];
		EAs1[0] = EA11;
		EAs1[1] = EA21;
		EAs1[2] = EA31;
		EAs1[3] = EA41;
		EAs1[4] = EA51;
		EAs1[5] = EA61;
		EAs1[6] = EA71;
		EAs1[7] = EA81;
		EAs11 = new ImageView[8];
		EAs11[0] = EA111;
		EAs11[1] = EA211;
		EAs11[2] = EA311;
		EAs11[3] = EA411;
		EAs11[4] = EA511;
		EAs11[5] = EA611;
		EAs11[6] = EA711;
		EAs11[7] = EA811;
		EAs111 = new ImageView[8];
		EAs111[0] = EA1111;
		EAs111[1] = EA2111;
		EAs111[2] = EA3111;
		EAs111[3] = EA4111;
		EAs111[4] = EA5111;
		EAs111[5] = EA6111;
		EAs111[6] = EA7111;
		EAs111[7] = EA8111;
		discardtext1.setText("0");
		discardtext2.setText("0");
		discardtext3.setText("0");
		discardtext4.setText("0");
		discardtext5.setText("0");
		firstplayertok.setVisible(false);
		firstplayertok1.setVisible(true);
		currentExtra = "";
		int xcenter = 363;
		int ycenter = 39;
		for (int r = 0; r < 20; r++) {
			for (int c = 0; c < 20; c++) {
				// create hexagon and add it to the tile so that collision can function.
				int[] xPoints = new int[6];
				int[] yPoints = new int[6];
				xPoints[0] = xcenter - 24;
				xPoints[1] = xcenter;
				xPoints[2] = xcenter + 24;
				xPoints[3] = xcenter + 24;
				xPoints[4] = xcenter;
				xPoints[5] = xcenter - 24;
				yPoints[0] = ycenter + 14;
				yPoints[1] = ycenter + 28;
				yPoints[2] = ycenter + 14;
				yPoints[3] = ycenter - 14;
				yPoints[4] = ycenter - 28;
				yPoints[5] = ycenter - 14;
				tileMat[r][c * 2 + r % 2].setPoly(xPoints, yPoints);
				if (Pattern.matches("[ABCDEFGH]", tileMat[r][c * 2 + r % 2].getType())) {
					Text numTokensText = new Text(xPoints[1] - 6, yPoints[1] - 10, "");
					numTokensText.setFont(new Font("Gill Sans MT", 24));
					numTokensText.setFill(Color.WHITE);
					numTokensText.setStroke(Color.BLACK);
					numTokensText.setStrokeWidth(.8);
					gamePane.getChildren().add(numTokensText);
					tileMat[r][c * 2 + r % 2].instantiateText(numTokensText);
					tileMat[r][c * 2 + r % 2].setText("2");
				}
				xcenter += 48;
			}
			xcenter = 363 + ((r + 1) % 2 * 24);
			// System.out.println(r);
			ycenter += 42;

		}
		MM.setVisible(false);
		toScores.setVisible(false);
		toBoard.setVisible(false);
		board.printBoard(tileMat);
	}

	public void instantiateTextMat() {
		scores = new Text[5][6];
		texts = new String[5][6];
		KBCard1.setText(getKBName(cards[0]));
		KBCard2.setText(getKBName(cards[1]));
		KBCard3.setText(getKBName(cards[2]));
		scores[0][2] = KBCard1;
		scores[0][3] = KBCard2;
		scores[0][4] = KBCard3;
		scores[1][0] = p1;
		scores[1][1] = p11;
		scores[1][2] = p12;
		scores[1][3] = p13;
		scores[1][4] = p14;
		scores[1][5] = p15;
		scores[2][0] = p2;
		scores[2][1] = p21;
		scores[2][2] = p22;
		scores[2][3] = p23;
		scores[2][4] = p24;
		scores[2][5] = p25;
		scores[3][0] = p3;
		scores[3][1] = p31;
		scores[3][2] = p32;
		scores[3][3] = p33;
		scores[3][4] = p34;
		scores[3][5] = p35;
		scores[4][0] = p4;
		scores[4][1] = p41;
		scores[4][2] = p42;
		scores[4][3] = p43;
		scores[4][4] = p44;
		scores[4][5] = p45;
	}

	public static int[] pullKingdomBuilderCards() {
		int x1 = -1;
		int x2 = -1;
		int x3 = -1;
		int temp = (int) (Math.floor(Math.random() * 10) + 1);
		x1 = temp;
		int intArray[] = new int[3];
		intArray[0] = x1;
		while (temp == x1) {
			temp = (int) (Math.floor(Math.random() * 10) + 1);
		}
		x2 = temp;
		intArray[1] = x2;
		while (temp == x1 || temp == x2) {
			temp = (int) (Math.floor(Math.random() * 10) + 1);
		}
		x3 = temp;
		intArray[2] = x3;
		return intArray;
	}

	public void setPlayers(int p) {
		players = new Player[p];
		deck = new Deck();
		instantiateTextMat();

		for (int r = 1; r < 5; r++) {
			for (int c = 0; c < 6; c++) {
				scores[r][c].setVisible(false);
			}
		}
		for (int i = 0; i < players.length; i++) {
			players[i] = new Player(i);
			players[i].setTerrainCard(deck.drawNextTerrain());
			for (int c = 0; c < 6; c++) {
				scores[i + 1][c].setVisible(true);
				if (c != 0)
					scores[i + 1][c].setText("");
			}
		}
		// System.out.println(p + " players");
		setTerrainCardVisual(players[0].getTerrain());
		highlight(players[0].getAvailableTiles(players[0].getTerrain(), board));
		currentPlayer = 0;
		drawEA();
		for (ImageView i : EAs1) {
			i.setVisible(false);
		}
		for (ImageView i : EAs11) {
			i.setVisible(false);
		}
		for (ImageView i : EAs111) {
			i.setVisible(false);
		}
		Info.setText(getInstructionsString(state));
		if (p == 4) {
			Image image = new Image(getClass().getResourceAsStream(
					"/application/assets/settlements/hauscount" + 1 + ".png"));
			set1.setImage(image);
			image = new Image(getClass().getResourceAsStream(
					"/application/assets/settlements/hauscount" + 2 + ".png"));
			set2.setImage(image);
			image = new Image(getClass().getResourceAsStream(
					"/application/assets/settlements/hauscount" + 3 + ".png"));
			set3.setImage(image);
			otherplayer1.setText("Player: " + 2);
			otherplayer2.setText("Player: " + 3);
			otherplayer3.setText("Player: " + 4);
		}
		if (p == 3) {
			Image image = new Image(getClass().getResourceAsStream(
					"/application/assets/settlements/hauscount" + 1 + ".png"));
			set1.setImage(image);
			image = new Image(getClass().getResourceAsStream(
					"/application/assets/settlements/hauscount" + 2 + ".png"));
			set2.setImage(image);
			image = new Image(getClass().getResourceAsStream(
					"/application/assets/settlements/hauscount" + 3 + ".png"));
			set3.setVisible(false);
			otherplayer1.setText("Player: " + 2);
			otherplayer2.setText("Player: " + 3);
			otherplayer3.setVisible(false);
			setleft1.setVisible(false);
			settext3.setVisible(false);
		}
		if (p == 2) {
			Image image = new Image(getClass().getResourceAsStream(
					"/application/assets/settlements/hauscount" + 1 + ".png"));
			set1.setImage(image);
			image = new Image(getClass().getResourceAsStream(
					"/application/assets/settlements/hauscount" + 2 + ".png"));
			set2.setVisible(false);
			image = new Image(getClass().getResourceAsStream(
					"/application/assets/settlements/hauscount" + 3 + ".png"));
			set3.setVisible(false);
			otherplayer1.setText("Player:" + 2);
			otherplayer2.setVisible(false);
			otherplayer3.setVisible(false);
			setleft1.setVisible(false);
			setleft2.setVisible(false);
			settext2.setVisible(false);
			settext3.setVisible(false);
		}
	}

	public void setTerrainCardVisual(int ter) {
		Image image = new Image(getClass().getResourceAsStream("/application/assets/Area Cards/" + ter + ".png"));
		TerrainCard.setImage(image);
	}

	public String getKBName(int i) {
		if (i == 1) {
			return "Citizens";
		} else if (i == 2)
			return "Discoverers";
		else if (i == 3)
			return "Farmers";
		else if (i == 4)
			return "Fishermen";
		else if (i == 5)
			return "Hermits";
		else if (i == 6)
			return "Knights";
		else if (i == 7)
			return "Lords";
		else if (i == 8)
			return "Merchants";
		else if (i == 9)
			return "Miners";
		else if (i == 10)
			return "Workers";
		return "";
	}

	public void clearHighlights() {
		gamePane.getChildren().remove(highlights);
	}

	public void highlight(ArrayList<Integer> list) {
		if (list != null)
			Collections.sort(list);
		// testing
		// System.out.println(list);
		// list = new ArrayList<>();
		// list.add(1201);
		clearHighlights();
		highlights = new Group();
		// list is a list of all settlement locations with r*100+c
		int idx = 0;
		for (int r = 0; r < 20; r++) {
			for (int c = 0; c < 20; c++) {
				// change if to when it should be highlighted
				if (idx < list.size() && list.get(idx) % 100 == c && list.get(idx) / 100 == r) {
					Polygon poly = new Polygon(tileMat[r][c * 2 + r % 2].getPolygonBounds());
					// set color to transparent yellow
					poly.setFill(new Color(1, 0, 1, .6));
					// random color (for testing)
					// poly.setFill(new Color(Math.random(), Math.random(), Math.random(), .6));
					highlights.getChildren().add(poly);
					idx++;
				}

			}
		}
		gamePane.getChildren().add(highlights);
	}

	public void drawSettlements() {

		gamePane.getChildren().remove(settlements);
		settlements = new Group();
		for (int r = 0; r < 20; r++) {
			for (int c = 0; c < 20; c++) {
				if (board.getTileAt(r, c).hasOwner()) {
					Image image = new Image(getClass().getResourceAsStream(
							"/application/assets/settlements/hauscount" + board.getTileAt(r, c).getOwner() + ".png"));
					ImageView settlement = new ImageView(image);

					settlement.setLayoutX(board.getTileAt(r, c).getPolygonBounds()[0] + 6);
					settlement.setLayoutY(board.getTileAt(r, c).getPolygonBounds()[1] - 36);
					settlement.setScaleX(.8);
					settlement.setScaleY(.8);
					settlements.getChildren().add(settlement);
				}

			}
		}
		gamePane.getChildren().add(settlements);
	}

	public void drawEA() {
		ArrayList<String> EAStrings = players[currentPlayer].getExtra();
		ArrayList<Boolean> EABools = players[currentPlayer].getExtraBooleans();

		for (ImageView i : EAs) {
			i.setVisible(false);
		}
		for (int i = 0; i < EAStrings.size(); i++) {
			Image image = new Image(
					getClass().getResourceAsStream("/application/assets/tokens/" + EAStrings.get(i) + ".png"));
			ColorAdjust c = new ColorAdjust();

			if (EABools.get(i))
				c.setBrightness(-.7);
			EAs[i].setEffect(c);
			EAs[i].setImage(image);
			EAs[i].setVisible(true);
		}
	}

	public void backToMM(MouseEvent e) throws IOException {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int) screenSize.getHeight();
		int width = (int) screenSize.getWidth();
		width = (int) (width * 0.8333333333333333333333333);
		height = (int) (height * 0.8333333333333333333333333333);
		root = FXMLLoader.load(getClass().getResource("MM.fxml"));
		stage = (Stage) (((Node) e.getSource()).getScene().getWindow());
		scene = new Scene(root, width, height);
		scene.getStylesheets().add(getClass().getResource("mainmenu.css").toExternalForm());

		stage.setScene(scene);
		stage.show();
	}

	public void extraActions(String t) {
		if (numberPlaced != 0 && numberPlaced != 3) {
			endTurn();
			return;
		}
		if(state==15)
			return;
		if (state == 1)
			state = 2;
		clearHighlights();
		currentExtra = t;
		if (t.equals("A")) {
			availableTiles = players[currentPlayer].getAvailableTiles(players[currentPlayer].getTerrain(), board);
			highlight(availableTiles);
		} else if (t.equals("B")) {
			availableTiles = players[currentPlayer].getAvailableTiles(0, board);
			highlight(availableTiles);
		} else if (t.equals("C")) {
			availableTiles = players[currentPlayer].getTavern(board);
			highlight(availableTiles);
		} else if (t.equals("D")) {
			availableTiles = players[currentPlayer].getTower(board);
			highlight(availableTiles);
		} else if (t.equals("E")) {
			// state of 5 is waiting to move a settlement
			selectSettlement();
		} else if (t.equals("F")) {
			selectSettlement();
		} else if (t.equals("G")) {
			selectSettlement();
		} else if (t.equals("H")) {
			availableTiles = players[currentPlayer].getAvailableTiles(4, board);
			highlight(availableTiles);
		}
		if (availableTiles.size() == 0) {
			if(state==11)
				players[currentPlayer].setToUsed(players[currentPlayer].getExtra().indexOf(t));
			if (state == 2) {
				state = 1;
				availableTiles = players[currentPlayer].getAvailableTiles(players[currentPlayer].getTerrain(), board);
				highlight(availableTiles);
				drawEA();
			} else if (!players[currentPlayer].hasUsableActions()) {
				drawNewCard();
			}
		}
		Info.setText(getInstructionsString(state));

	}

	public void selectSettlement() {
		state = 5;
		availableTiles = players[currentPlayer].getSettlements();
		highlight(availableTiles);
	}

	public void tileClicked(MouseEvent e) {
		Info.setText(getInstructionsString(state));
		
		if (state == 15) {
			clearHighlights();
			return;
		}
		tileMat = board.getBoard();
		int x = -1;
		int y = -1;
		// System.out.println(e.getX() + ", " + e.getY());
		for (int r = 0; r < 20; r++) {
			for (int c = 0; c < 20; c++) {
				// System.out.println(tileMat[r][c*2+r%2]);
				if (tileMat[r][c * 2 + r % 2].contains(e.getX(), e.getY())) {
					x = r;
					y = c;
				}
			}
		}
		// System.out.println(x + ", " + y);
		if (x == -1) {
			return;
		}

		currentPlayer = turn % players.length;
		if (players[currentPlayer].getsettlementnum() <= 0) {
			numberPlaced = 3;
			clearHighlights();
			state = 15;
			return;
		}
		
		
		// moving settlements code
		int xind = x;
		int yind = y * 2 + x % 2;
		if (state == 5) {
			if (availableTiles.contains(x * 100 + y)) {
				state = 6;
				if (currentExtra.equals("E")) {
					availableTiles = players[currentPlayer].getAvailableTiles(6, board);
					highlight(availableTiles);
				} else if (currentExtra.equals("F")) {
					int yin = y * 2 + x % 2;
					availableTiles = players[currentPlayer].getPaddock(x, yin, board);
					highlight(availableTiles);
				} else if (currentExtra.equals("G")) {
					availableTiles = players[currentPlayer].getAvailableTiles(players[currentPlayer].getTerrain(),
							board);
					highlight(availableTiles);
				}
				players[currentPlayer].removeSettlement(x, y, board);
				Tile[] adjList = board.adjacentTiles(x, y * 2 + x % 2);
				String EAlost = containsEA(adjList);

				if (!EAlost.equals("")) {
					for (int i = 0; i < adjList.length; i++) {
						if (adjList[i].getType().equals(EAlost)) {
							if (i == 0) {
								xind -= 1;
								yind -= 1;
							} else if (i == 1) {
								xind -= 1;
								yind += 1;
							} else if (i == 2) {
								yind += 2;
							} else if (i == 3) {
								xind += 1;
								yind += 1;
							} else if (i == 4) {
								xind += 1;
								yind -= 1;
							} else {
								yind -= 2;
							}

						}
					}
					System.out.println(xind+" "+yind+" "+containsPlayer(currentPlayer, board.adjacentTiles(xind, yind)));
					if (!containsPlayer(currentPlayer, board.adjacentTiles(xind, yind))){
						lossX = xind;
						lossY = yind;
						lossEA = EAlost;
						waitingToLose = true;
					}
				}
				// players[currentPlayer].addSettlment(x, y*2+x%2);

				// players[currentPlayer].removeSettlement(x, y, board);
				drawSettlements();
			}
			return;
		}
		// placing settlements code
		// change the type 1 to the player's terrain card
		if (state == 1)
			availableTiles = players[currentPlayer].getAvailableTiles(players[currentPlayer].getTerrain(), board);
		if (state<=10&&availableTiles.contains(x * 100 + y)) {
			board.getTileAt(x, y).changeOwner(currentPlayer);
			String s = containsEA(board.adjacentTiles(x, y * 2 + x % 2));
			Tile EATile = getEA(board.adjacentTiles(x, y * 2 + x % 2));
			if (!s.equals("") && !(EATile.blackListContains(currentPlayer)) && EATile.getBlackList().size() < 2) {
				players[currentPlayer].addExtra(s);
				EATile.addToBlacklist(currentPlayer);

			}
			if (waitingToLose) {
				if (!containsPlayer(currentPlayer, board.adjacentTiles(lossX, lossY))) {
					players[currentPlayer].minusExtra(lossEA);
				}
				waitingToLose = false;
				drawEA();
			}
			players[currentPlayer].addSettlment(x, y);
		} else {
			return;
		}

		if (state == 1) {
			highlight(players[currentPlayer].getAvailableTiles(players[currentPlayer].getTerrain(), board));
			numberPlaced++;
		} else if (state == 2 || state == 11) {
			clearHighlights();
		}

		drawSettlements();
		drawEA();
		if (state == 2) {
			availableTiles = players[currentPlayer].getAvailableTiles(players[currentPlayer].getTerrain(), board);
			highlight(availableTiles);
			state = 1;
		}
		if (state == 6) {
			state = 1;
			availableTiles = players[currentPlayer].getAvailableTiles(players[currentPlayer].getTerrain(), board);
			highlight(players[currentPlayer].getAvailableTiles(players[currentPlayer].getTerrain(), board));
		}
		if (numberPlaced >= 3) {
			// state of 15 means that the turn will end during the next click
			if (players[currentPlayer].hasUsableActions()) {
				if (state == 1)
					state = 11;
			} else {
				drawNewCard();
			}
			clearHighlights();
			// add extra action tiles
			// endTurn();

		}
		Info.setText(getInstructionsString(state));
		Image image = new Image(
				getClass().getResourceAsStream("/application/assets/settlements/hauscount" + currentPlayer + ".png"));
		setmain.setImage(image);
		settextmain.setText(players[currentPlayer].getsettlementnum() + " x");
	}

	public String getInstructionsString(int i) {
		String s = "";
		if (state == 1) {
			if (players[currentPlayer].hasUsableActions()&&(numberPlaced==0||numberPlaced==3))
				return "Choose a tile to place on or an extra action to use";
			else
				return "Choose a tile to place on";
		} else if (state == 2) {
			return "Use the extra action";
		} else if (state == 5) {
			return "Choose a settlement to move";
		} else if (state == 6) {
			return "Choose a place to move";
		} else if (state == 11) {
			return "Use an extra action or end turn";
		} else if (state == 15) {
			return "New card drawn. Waiting to end turn";
		}

		return s;
	}

	public void drawNewCard() {
		players[currentPlayer].useAll();
		drawEA();
		
		players[currentPlayer].setTerrainCard(deck.drawNextTerrain(players[currentPlayer].getTerrain()));
		setTerrainCardVisual(players[currentPlayer].getTerrain());
		ColorAdjust c = new ColorAdjust();
		if(deck.isEmpty())
			c.setBrightness(-1);
		cardBack.setEffect(c);
		state = 15;
		discardtext1.setText(getTerrainDiscard(0));
		discardtext2.setText(getTerrainDiscard(1));
		discardtext3.setText(getTerrainDiscard(2));
		discardtext4.setText(getTerrainDiscard(3));
		discardtext5.setText(getTerrainDiscard(4));
	}

	public String getTerrainDiscard(int x) {
		ArrayList<Integer> temp = deck.getDiscard();
		System.out.println(temp);
		int count = 0;
		for (int i = 0; i < temp.size(); i++) {
			if (temp.get(i) == x) {
				count++;
			}
		}
	/* 	if (x == players[0].getTerrain()) {
			count = count - 1;
		}
		if (x == players[1].getTerrain()) {
			count = count - 1;
		}
		if (players.length >= 3 && x == players[2].getTerrain()) {
			count = count - 1;
		}
		if (players.length >= 4 && x == players[3].getTerrain()) {
			count = count - 1;
		}*/
		return "" + count;
	}

	public void endTurn() {
		if (state < 10) {
			return;
		}
		if (state == 11) {
			drawNewCard();
			state = 15;
			return;
		}
		if (gameEnded() && turn % players.length == players.length - 1) {
			endGame();
			Info.setText("Game Ended");
			return;
		}

		turn++;
		numberPlaced = 0;
		currentPlayer = turn % players.length;
		// change the 1 to the terrain card that they have
		highlight(players[currentPlayer].getAvailableTiles(players[currentPlayer].getTerrain(), board));
		// System.out.println("player "+currentPlayer+ " turn");
		drawSettlements();
		setTerrainCardVisual(players[currentPlayer].getTerrain());
	//	for(int i: scoring.scoreLords(players.length))
	//		System.out.println(i+" "+currentPlayer);
		state = 1;
		players[currentPlayer].resetExtraBooleans();
		Info.setText(getInstructionsString(state));

		drawEA();
		Image image = new Image(
				getClass().getResourceAsStream("/application/assets/settlements/hauscount" + currentPlayer + ".png"));
		setmain.setImage(image);
		settextmain.setText(players[currentPlayer].getsettlementnum() + " x");
		playerstate.setText("Current Player: " + (currentPlayer + 1));
		for (ImageView i : EAs1) {
			i.setVisible(false);
		}
		for (ImageView i : EAs11) {
			i.setVisible(false);
		}
		for (ImageView i : EAs111) {
			i.setVisible(false);
		}
		int x = 1;
		int r = 1;
		while (x < players.length + 1) {
			if (x != (currentPlayer + 1) && r == 1) {
				otherplayer1.setText("Player:" + x);
				ArrayList<String> EAS1trings = players[x - 1].getExtra();
				for (int i = 0; i < EAS1trings.size(); i++) {
					image = new Image(
							getClass().getResourceAsStream("/application/assets/tokens/" + EAS1trings.get(i) + ".png"));
					EAs1[i].setImage(image);
					EAs1[i].setVisible(true);

				}
				image = new Image(getClass().getResourceAsStream(
						"/application/assets/settlements/hauscount" + (x - 1) + ".png"));
				set1.setImage(image);
				settext1.setText(players[x - 1].getsettlementnum() + " x");
				r++;

			} else if (x != (currentPlayer + 1) && r == 2) {
				otherplayer2.setText("Player: " + x);
				ArrayList<String> EAS11trings = players[x - 1].getExtra();
				for (int i = 0; i < EAS11trings.size(); i++) {
					image = new Image(
							getClass()
									.getResourceAsStream("/application/assets/tokens/" + EAS11trings.get(i) + ".png"));
					EAs11[i].setImage(image);
					EAs11[i].setVisible(true);
				}
				image = new Image(getClass().getResourceAsStream(
						"/application/assets/settlements/hauscount" + (x - 1) + ".png"));
				set2.setImage(image);
				settext2.setText(players[x - 1].getsettlementnum() + " x");
				r++;
			} else if (x != (currentPlayer + 1) && r == 3) {
				otherplayer3.setText("Player: " + x);
				ArrayList<String> EAS111trings = players[x - 1].getExtra();
				for (int i = 0; i < EAS111trings.size(); i++) {
					image = new Image(
							getClass()
									.getResourceAsStream("/application/assets/tokens/" + EAS111trings.get(i) + ".png"));
					EAs111[i].setImage(image);
					EAs111[i].setVisible(true);
				}
				image = new Image(getClass().getResourceAsStream(
						"/application/assets/settlements/hauscount" + (x - 1) + ".png"));
				set3.setImage(image);
				settext3.setText(players[x - 1].getsettlementnum() + " x");
			}

			x++;
		}
		if (currentPlayer == 0) {
			firstplayertok.setVisible(false);
			firstplayertok1.setVisible(true);
		} else {
			firstplayertok.setVisible(true);
			firstplayertok1.setVisible(false);
		}
	}

	public void endGame() {
		pane.getSelectionModel().select(3);
		clearHighlights();

		MM.setVisible(true);
		toScores.setVisible(true);
		toBoard.setVisible(true);
		int[] finalScores = new int[players.length];
		int lvlScores[] = scoring.scoreCities(board, players.length);
		for (int i = 0; i < players.length; i++) {
			finalScores[i] += lvlScores[i];
		}
		showScores(lvlScores, 1);
		for (int j = 0; j < 3; j++) {
			lvlScores = scoreKBC(cards[j]);
			showScores(lvlScores, j + 2);
			for (int i = 0; i < players.length; i++) {
				finalScores[i] += lvlScores[i];
			}
		}
		showScores(finalScores, 5);
		int[] ranks = new int[finalScores.length], tempScores = new int[finalScores.length];
		for (int i = 0; i < tempScores.length; i++)
			tempScores[i] = finalScores[i];
		Arrays.sort(tempScores);
		tempScores = flip(tempScores);

		for (int i : tempScores)
			System.out.print(i + " ");
		System.out.println();
		for (int i = 0; i < finalScores.length; i++) {
			for (int r = 0; r < finalScores.length; r++) {
				if (finalScores[i] == tempScores[r] && ranks[i] == 0)
					ranks[i] = r + 1;
			}
		}
		ArrayList<Integer> winnersList = new ArrayList<>();
		for (int i = 0; i < ranks.length; i++) {
			if (ranks[i] == 1)
				winnersList.add(i);
		}
		winners.setText(createWinnerString(winnersList));
		ArrayList<Integer> allTies = new ArrayList<>();
		for (int i = 0; i < players.length; i++) {
			while (allTies.contains(ranks[i]))
				ranks[i]++;
			allTies.add(ranks[i]);
		}
		for (int i : ranks)
			System.out.print(i + " ");

		System.out.println();
		for (int i = 0; i < ranks.length; i++) {
			showScores(texts[i + 1], ranks[i], i + 1);
			for (int c = 0; c < texts[i].length; c++) {
				System.out.print(texts[i + 1][c] + " ");
			}
			System.out.println();
		}
		Info.setText("Game Ended");
		// change winners

	}

	public String createWinnerString(ArrayList<Integer> winners) {
		String s = "";

		for (int i = 0; i < winners.size(); i++) {
			s += "Player " + (winners.get(i)+1);
			if (winners.size() - i > 1)
				s += " and ";
		}
		s += " wins!";
		return s;
	}

	public int[] flip(int[] list) {
		int[] temp = new int[list.length];
		for (int i = 0; i < temp.length; i++) {
			temp[i] = list[list.length - i - 1];
		}
		return temp;
	}

	public int[] scoreKBC(int type) {
		if (type == 7)
			return scoring.scoreLords(players.length);
		int[] scoreList = new int[4];
		for (int p = 0; p < players.length; p++) {
			if (type == 1)
				scoreList[p] = scoring.scoreCitizens(p);
			else if (type == 2)
				scoreList[p] = scoring.scoreDiscoverers(p);
			else if (type == 3)
				scoreList[p] = scoring.scoreFarmers(p);
			else if (type == 4)
				scoreList[p] = scoring.scoreFisherman(p);
			else if (type == 5)
				scoreList[p] = scoring.scoreHermits(p);
			else if (type == 6)
				scoreList[p] = scoring.scoreKnights(p);
			else if (type == 8)
				scoreList[p] = scoring.scoreMerchants(p);
			else if (type == 9)
				scoreList[p] = scoring.scoreMiners(p);
			else if (type == 19)
				scoreList[p] = scoring.scoreWorkers(p);

		}
		return scoreList;
	}

	public void showScores(int[] lvlScores, int i) {
		scores[1][i].setText(lvlScores[0] + "");
		texts[1][i] = lvlScores[0] + "";
		scores[2][i].setText(lvlScores[1] + "");
		texts[2][i] = lvlScores[1] + "";
		if (players.length >= 3) {
			texts[3][i] = lvlScores[2] + "";
			scores[3][i].setText(lvlScores[2] + "");

		}
		if (players.length >= 4) {
			texts[4][i] = lvlScores[3] + "";
			scores[4][i].setText(lvlScores[3] + "");
		}
	}

	public void showScores(String[] lvlScores, int i, int p) {
		scores[i][0].setText("Player " + p);
		scores[i][1].setText(lvlScores[1]);
		scores[i][2].setText(lvlScores[2]);
		scores[i][3].setText(lvlScores[3]);
		scores[i][4].setText(lvlScores[4]);
		scores[i][5].setText(lvlScores[5]);

	}

	public String containsEA(Tile[] list) {
		for (Tile t : list) {
			if (Pattern.matches("[ABCDEFGH]", t.getType()))
				return t.getType();
		}
		return "";
	}

	public boolean containsPlayer(int p, Tile[] list) {
		for (Tile t : list) {
			if (t.getOwner() == p)
				return true;
		}
		return false;
	}

	public Tile getEA(Tile[] list) {
		for (Tile t : list) {
			if (Pattern.matches("[ABCDEFGH]", t.getType()))
				return t;
		}
		return null;
	}

	public boolean gameEnded() {
		for (Player p : players) {
			// change to 38 to end game instantly
			if (p.getsettlementnum() <= 0)
				return true;
		}
		return false;
	}

	// ignore, code for instructions
	public void showOne() {
		page = 1;
		Image image = new Image(getClass().getResourceAsStream("/application/assets/Rules/rules-1.png"));
		rules.setImage(image);

	}

	public void showTwo() {

		Image image = new Image(getClass().getResourceAsStream("/application/assets/Rules/rules-2.png"));
		rules.setImage(image);
		page = 2;

	}

	public void showThree() {

		Image image = new Image(getClass().getResourceAsStream("/application/assets/Rules/rules-3.png"));
		rules.setImage(image);
		page = 3;
	}

	public void showFour() {

		Image image = new Image(getClass().getResourceAsStream("/application/assets/Rules/rules-4.png"));
		rules.setImage(image);
		page = 4;
	}

	public void showFive() {

		Image image = new Image(getClass().getResourceAsStream("/application/assets/Rules/rules-5.png"));
		rules.setImage(image);
		page = 5;
	}

	public void showSix() {

		Image image = new Image(getClass().getResourceAsStream("/application/assets/Rules/rules-6.png"));
		rules.setImage(image);
		page = 6;
	}

	public void showSeven() {

		Image image = new Image(getClass().getResourceAsStream("/application/assets/Rules/rules-7.png"));
		rules.setImage(image);
		page = 7;
	}

	public void showEight() {

		Image image = new Image(getClass().getResourceAsStream("/application/assets/Rules/rules-8.png"));
		rules.setImage(image);
		page = 8;
	}

	public void next() {
		page++;
		page = Math.min(page, 8);
		Image image = new Image(getClass().getResourceAsStream("/application/assets/Rules/rules-" + page + ".png"));
		rules.setImage(image);
	}

	public void previous() {
		page--;
		page = Math.max(page, 1);
		Image image = new Image(getClass().getResourceAsStream("/application/assets/Rules/rules-" + page + ".png"));
		rules.setImage(image);
	}

	public void extra1() {
		ArrayList<String> eas = players[turn % players.length].getExtra();
		if (eas.size() > 0 && !players[turn % players.length].getExtraBooleans().get(0)) {
			if (numberPlaced == 0 || numberPlaced == 3)
				players[turn % players.length].setToUsed(0);
			extraActions(eas.get(0));

		}
	}

	public void extra2() {
		ArrayList<String> eas = players[turn % players.length].getExtra();
		if (eas.size() > 1 && !players[turn % players.length].getExtraBooleans().get(1)) {
			if (numberPlaced == 0 || numberPlaced == 3)
				players[turn % players.length].setToUsed(1);
			extraActions(eas.get(1));
		}
	}

	public void extra3() {
		ArrayList<String> eas = players[turn % players.length].getExtra();
		if (eas.size() > 2 && !players[turn % players.length].getExtraBooleans().get(2)) {
			if (numberPlaced == 0 || numberPlaced == 3)
				players[turn % players.length].setToUsed(2);
			extraActions(eas.get(2));
		}

	}

	public void extra4() {
		ArrayList<String> eas = players[turn % players.length].getExtra();
		if (eas.size() > 3 && !players[turn % players.length].getExtraBooleans().get(3)) {
			if (numberPlaced == 0 || numberPlaced == 3)
				players[turn % players.length].setToUsed(3);
			extraActions(eas.get(3));
		}

	}

	public void extra5() {
		ArrayList<String> eas = players[turn % players.length].getExtra();
		if (eas.size() > 4 && !players[turn % players.length].getExtraBooleans().get(4)) {
			if (numberPlaced == 0 || numberPlaced == 3)
				players[turn % players.length].setToUsed(4);
			extraActions(eas.get(4));
		}
	}

	public void extra6() {
		ArrayList<String> eas = players[turn % players.length].getExtra();
		if (eas.size() > 5 && !players[turn % players.length].getExtraBooleans().get(5)) {
			if (numberPlaced == 0 || numberPlaced == 3)
				players[turn % players.length].setToUsed(5);
			extraActions(eas.get(5));
		}
	}

	public void extra7() {
		ArrayList<String> eas = players[turn % players.length].getExtra();
		if (eas.size() > 6 && !players[turn % players.length].getExtraBooleans().get(6)) {
			if (numberPlaced == 0 || numberPlaced == 3)
				players[turn % players.length].setToUsed(6);
			extraActions(eas.get(6));

		}
	}

	public void extra8() {
		ArrayList<String> eas = players[turn % players.length].getExtra();
		if (eas.size() > 7 && !players[turn % players.length].getExtraBooleans().get(7)) {
			if (numberPlaced == 0 || numberPlaced == 3)
				players[turn % players.length].setToUsed(7);
			extraActions(eas.get(7));
		}

	}

	public void toScores() {
		pane.getSelectionModel().select(3);
	}

	public void toBoard() {
		pane.getSelectionModel().select(0);
	}

}
