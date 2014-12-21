package controller;



import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;






import model.BlackjackGame;
import model.Card;
import model.Deck.EmptyDeckException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;






/**
 * this function is the controller in the MVC structure
 *it has a reference to the game, and is the defined controller in the view
 *in accordance to javaFX rules.(the controller is defined in the view not the other way around)
 */
public class ControllerFX  implements Initializable, ControlledScreen {

	private BlackjackGame bj; //reference to model
	@FXML 
	private TextArea MsgBox; //the player hand text that will show the hand in text in the view
	@FXML 
	private javafx.scene.control.Label playerVal;//will show the player hand value in the view
	@FXML
	private HBox cardHand;//will contain the player hand in the view

	@FXML 
	private javafx.scene.control.Label DealerVal;
	@FXML
	private HBox dealerCard;
	@FXML
	private javafx.scene.control.Label dealerVal;
	
	
	@SuppressWarnings("unused")
	private ScreenController myController;
	private int round;
	
	private boolean[] btnSetting;
	
	/*
	 * the constructor with a reference to a game(the model).
	 */
	public ControllerFX() throws EmptyDeckException {
		round =0;
		this.bj = new BlackjackGame();
		bj.beginGame();
		btnSetting = new boolean[]{true,false,false,true};
	}
	/*
	 * (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		MsgBox.setEditable(false);
		MsgBox.setOpacity(1);
	}
	
	/**
	 * 
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
		
		if(btnSetting[0]){
			btnSetting[0]=false;
			MsgBox.setText("Player Hand:\n"+bj.getHand(true)+"Hand Value: "+Integer.toString(bj.getPlayerValue()));//get from the model the player hand in text and set it to its proper field in the view
			playerVal.setText("Player hand: "+Integer.toString(bj.getPlayerValue()));//same but with value
			appendPicToHand(bj.getPlayerCards(),cardHand); 
			MsgBox.appendText("\nDealer Hand:\n"+bj.getHand(false));	
			appendPicToHand(bj.getDealerCards(),dealerCard);
			this.round++;
			btnSetting[1]=true;//enable hit.
			btnSetting[2]=true;//enable stand.
		}
	}
	
	@FXML
	public void btnHitListener(ActionEvent evnt) throws EmptyDeckException
	{
		if(btnSetting[1])
		{
			Card c = bj.playerDraw();
			appendSinglePic(c, this.cardHand);
			MsgBox.appendText("\nPlayer Draw: "+c.toString()+"\nHand value:"+bj.getPlayerValue());
			playerVal.setText("\nPlayer hand: "+Integer.toString(bj.getPlayerValue()));
			if(bj.getPlayerValue()>21)
			{
				btnSetting[2]=false;
				btnSetting[1]=false;
				bj.setPlayerBurn();
				MsgBox.appendText("\n**Player Burns, Dealer Win**");
			}
		}else if(bj.getPlayerBurn())
				{
					MsgBox.setText("\nCan't Hit You Burned.");
				}else{
					MsgBox.setText("\nbutton HIT not availble at this stage.");
				}
	}
	
	@FXML
	public void btnStandListener(ActionEvent evnt) throws EmptyDeckException
	{
		if(btnSetting[2])
		{
			btnSetting[2]=false;
			btnSetting[1]=false;
			bj.showAllDealerCards();
			MsgBox.appendText("\nDealer Hand:\n"+bj.getHand(false));
			dealerCard.getChildren().clear();
			appendPicToHand(bj.getDealerCards(),dealerCard);
			while(bj.getDealerValue()<17)
			{
				Card c = bj.dealerDraw();
				appendSinglePic(c, dealerCard);
				MsgBox.appendText("Dealer Draw: "+c.toString()+"\nhand value:"+bj.getDealerValue());
			}
			bj.checkVictory(MsgBox);
		}else{
			MsgBox.setText("\nbutton STAND not availble at this stage.");
		}
	}
	
	@FXML
	public void btnQuitListener(ActionEvent evnt)
	{
		if(btnSetting[3])
		{
			Platform.exit();
		}
	}
	
	/**
	 * this function receives a player or dealer(!) hand, and a refernce to the HBOX that will display it.
	 * and append the picture of the card to the HBox on the board.
	 * @param playerCards
	 * @param hb
	*/
	private void appendPicToHand(ArrayList<Card> playerCards,HBox hb) {
		for(Card c : playerCards){
			appendSinglePic(c,hb);
		}
	}
	
	
	private void appendSinglePic(Card c, HBox hb) {
		ImageView iv1 = new ImageView();
		Image card = new Image(c.getImageLocation(), true);
		iv1.setImage(card);
		hb.setSpacing(-40);
		hb.getChildren().add(iv1);
	}
	
	@Override
	public void setScreenParent(ScreenController screenPage) {
		myController = screenPage;
	}

}
