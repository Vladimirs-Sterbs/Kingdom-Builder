package application;

import java.io.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import java.awt.Dimension;
import java.awt.Toolkit;

public class instructions {
    @FXML
    ImageView rules;
    @FXML
    AnchorPane pane;
    private int page = 1;
    private Scene scene;
    private Stage stage;
    private Parent root;
    public static int players;
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
    public void next(){
        page++;
        page = Math.min(page, 8);
        Image image = new Image(getClass().getResourceAsStream("/application/assets/Rules/rules-"+page+".png"));
        rules.setImage(image);   
    }
    public void previous(){
        page--;
        page = Math.max(page, 1);
        Image image = new Image(getClass().getResourceAsStream("/application/assets/Rules/rules-"+page+".png"));
        rules.setImage(image);   
    }
    public void savePlayer(int x){
            players = x;
    }
    public int getPlayer() {
        return players;
    }

    public void goToMenu(MouseEvent e) throws IOException {
         Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int) screenSize.getHeight();
		int width = (int) screenSize.getWidth();
		width = (int) (width * 0.8333333333333333333333333);
		height = (int) (height * 0.8333333333333333333333333333);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MM.fxml"));
        root = loader.load();
        stage = (Stage) (((Node) e.getSource()).getScene().getWindow());
        scene = new Scene(root,width,height);
        stage.setUserData(players);
		MainMenuController g = loader.getController();
		g.setPlayers(players);
		stage.setScene(scene);
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("mainmenu.css").toExternalForm());
        stage.show();
    }
}
