package controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * this is the Welcome screen view, it's purpose to show the logo
 * and greet a new player.
 */
public class GreetingScreenController implements Initializable, ControlledScreen{
	
	@FXML
	private Button btnMagic; // the button to shift to game.
	private ScreenController myController;// the Contrller class for the controler.
	
	/**
	 * sets the view screen shift control.
	 */
	@Override
	public void setScreenParent(ScreenController screenPage) {
		myController = screenPage;
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * this function is the listener to the button press on the view
	 * on press it will pass to the Game view.
	 * @param event
	 */
	@FXML
	public void goToMainApp(ActionEvent event){
		this.btnMagic.setDisable(true);//Disable option to multiple press.
		myController.setScreen(Main.GameID);//switch to game may the odds be in your favor....NOT!
	}
}	
