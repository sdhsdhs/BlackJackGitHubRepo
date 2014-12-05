package controller;



import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import model.BlackjackGame;
import model.Card;
import model.Deck.EmptyDeckException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import javafx.scene.layout.HBox;






/**
 * this function is the controller in the MVC structure
 *it has a reference to the game, and is the defined controller in the view
 *in accordance to javaFX rules.(the controller is defined in the view not the other way around)
 */
public class ControllerFX  implements Initializable, ControlledScreen {

	private BlackjackGame bj; //reference to model
	@FXML 
	private TextArea playerHand; //the player hand text that will show the hand in text in the view
	@FXML 
	private javafx.scene.control.Label playerVal;//will show the player hand value in the view
	@FXML
	private HBox cardHand;//will contain the player hand in the view
	@FXML
	private TextArea DealerHand;
	@FXML 
	private javafx.scene.control.Label DealerVal;
	@FXML
	private HBox dealerCard;
	@FXML
	private javafx.scene.control.Label dealerVal;
	
	@SuppressWarnings("unused")
	private ScreenController myController;
	private int round;
	/*
	 * the constructor with a reference to a game(the model).
	 */
	public ControllerFX() throws EmptyDeckException {
		round =0;
		this.bj = new BlackjackGame();
		bj.beginGame();
	}
	/*
	 * (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @return the current round.
	 */
	public int getRound(){
		return this.round;
	}
	
	/**
	 * this function is the listener to the view DEAL button.
	 * it set the text of the hands to their appropriate field in the view.
	 * this function works only on round 0;
	 * @param evnt
	 */
	@FXML
	public void btnDealListener(ActionEvent evnt){
		
		if(round==0){
			playerHand.setText(bj.getHand(true));//get from the model the player hand in text and set it to its proper field in the view
			playerVal.setText("Player hand: "+Integer.toString(bj.getPlayerValue()));//same but with value			
			DealerHand.setText(bj.getHand(false));						
		}
		this.round++;
	}

	/**
	 * this function receives a player or dealer(!) hand, and a reference to the HBOX that will display it.
	 * and append the picture of the card to the HBox on the board.
	 * @param playerCards the cards to be shown
	 * @param hb the Hbox to attach it to.
	 */
	@SuppressWarnings("unused")
	private void appendPicToHand(ArrayList<Card> playerCards,HBox hb) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * can be used in the futur to shift screens.
	 */
	@Override
	public void setScreenParent(ScreenController screenPage) {
		myController = screenPage;
	}
	
	
}
