package application;
	
import java.io.IOException;




import controller.ScreenController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;




public class Main extends Application {
	public static String greetingID = "WelcomeView";
	public static String greetingFile = "/view/fxml/WelcomeScreen.fxml";
	public static String GameID = "game";
	public static String GameFile = "/view/fxml/board.fxml";
	
	
	@Override
	public void start(Stage primaryStage) throws IOException {

		ScreenController mainController = new ScreenController(); //make the screen controller
		
		mainController.loadScreen(greetingID, greetingFile); //load the screens
		mainController.loadScreen(GameID, GameFile);
		mainController.setScreen(greetingID);
		
		Group root = new Group(); //make the magic of GUI happen!!
		root.getChildren().addAll(mainController);
		primaryStage.setTitle("Blackjack Empire");
		primaryStage.setResizable(false);
		Scene scene = new Scene(root,800,600);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	

	public static void main(String[] args) {
		launch(args);
	}
}
