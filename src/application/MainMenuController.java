package application;

import java.io.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.awt.Toolkit;
import java.awt.Dimension;

public class MainMenuController {
	@FXML
	Slider playerSlider;
	@FXML
	AnchorPane pane;
	@FXML
	ImageView sPane;

	private Scene scene;
	private Stage stage;
	private Parent root;
	public static int numPlayers;

	public void initialize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int) screenSize.getHeight();
		int width = (int) screenSize.getWidth();
		width = (int) (width * 0.8333333333333333333333333);
		height = (int) (height * 0.8333333333333333333333333333);
		double w=width;  
		double h=height;
		System.out.println(w + " " + h);
		pane.setScaleY((h/900));
		pane.setScaleX((w/1600));
		double x = w/10;
		double y = (-(0.030620285939435* (x*x)))+(4.8486997635934 * x)+8.0873578745919;
		pane.setTranslateX(-y);
		double x2 = h;
		double y2 = (-(0.00070512820512821* (x2*x2)))+(0.72435897435897 * x2)-80.769230769231;
		pane.setTranslateY(-y2);
		
	}

	public void setPlayers(int i){
		playerSlider.setValue(i);
	}

	public void startGame(MouseEvent e) throws IOException {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int) screenSize.getHeight();
		int width = (int) screenSize.getWidth();
		width = (int) (width * 0.8333333333333333333333333);
		height = (int) (height * 0.8333333333333333333333333333);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Game.fxml"));
		numPlayers = (int) playerSlider.getValue();
		root = loader.load();
		stage = (Stage) (((Node) e.getSource()).getScene().getWindow());
		scene = new Scene(root,width,height);
		stage.setUserData(numPlayers);
		Gamestate g = loader.getController();
		g.setPlayers(numPlayers);

		stage.setScene(scene);
		

		stage.show();

	}

	public void goToInstructions(MouseEvent e) throws IOException {
		int x = (int) playerSlider.getValue();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int) screenSize.getHeight();
		int width = (int) screenSize.getWidth();
		width = (int) (width * 0.8333333333333333333333333);
		height = (int) (height * 0.8333333333333333333333333333);
		stage = (Stage) (((Node) e.getSource()).getScene().getWindow());
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Instructions.fxml"));
		root = loader.load();
		scene = new Scene(root,width,height);
		stage.setUserData(x);
		instructions g = loader.getController();
		g.savePlayer(x);
		stage.setScene(scene);
		stage.show();		
	}

}
