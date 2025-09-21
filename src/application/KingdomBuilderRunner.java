package application;
	

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.awt.Toolkit;
import java.awt.Dimension;



public class KingdomBuilderRunner extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int height = (int) screenSize.getHeight();
			int width = (int) screenSize.getWidth();
			width = (int) (width * 0.8333333333333333333333333);
			height = (int) (height * 0.8333333333333333333333333333);
			Parent root = FXMLLoader.load(getClass().getResource("MM.fxml"));
			Scene scene = new Scene(root,width,height);
			scene.getStylesheets().add(getClass().getResource("mainmenu.css").toExternalForm());
			primaryStage.setScene(scene);   
			primaryStage.setResizable(false);
			primaryStage.setTitle("Kingdom Builder");
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

