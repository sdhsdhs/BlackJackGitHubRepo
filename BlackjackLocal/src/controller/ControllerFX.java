package controller;



import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import model.BlackjackGame;
import model.Card;
import model.Deck.EmptyDeckException;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.transform.Rotate;
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
	private javafx.scene.control.Label DealerVal;
	@FXML
	private javafx.scene.control.Label dealerVal;
	@FXML
	private Label turnScore;
	@FXML
	private Label totalScore;
	@FXML
	private Label turn;
	@FXML
	private Label labelDeck;
	

	private ScreenController myController;
	
	private double dealerPosOffset = -50;
	private double playerPosOffset = -50;
	
	SequentialTransition cardsFlow = new SequentialTransition();
	
	ImageView imageToFlip;
	Card cardToFlip;
	boolean flagFlippedCard = true;
	
	private boolean[] btnSetting;
	
	/*
	 * the constructor with a reference to a game(the model).
	 */
	public ControllerFX() throws EmptyDeckException {
		//round =0;
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
	
	public int getRound(){
		return this.round;
	}
	*/
	
	/**
	 * this function is the listener to the view DEAL button.
	 * it set the text of the hands to their appropriate field in the view.
	 * this function works only on round 0;
	 * @param evnt
	 */
	@FXML
	public void btnDealListener(ActionEvent evnt){
		
		if(btnSetting[0]){
			MsgBox.clear();
			bj.incTurn();
			turn.setText("Round: "+Integer.toString(bj.getTurn()));
			totalScore.setText("Total Score: "+Integer.toString(bj.getTotalScore()));
			btnSetting[0]=false;
			MsgBox.setText("Player Hand:\n"+bj.getHand(true)+"Hand Value: "+Integer.toString(bj.getPlayerValue()));//get from the model the player hand in text and set it to its proper field in the view
			playerVal.setText("Player hand: "+Integer.toString(bj.getPlayerValue()));//same but with value
			dealerVal.setText("Dealer Hand");
			appendPicToHandAnimated(bj.getPlayerCards(),"player");
			cardsFlow.play();
			cardsFlow = new SequentialTransition();
			MsgBox.appendText("\nDealer Hand:\n"+bj.getHand(false));	
			appendPicToHandAnimated(bj.getDealerCards(),"dealer");
			cardsFlow.play();
			cardsFlow = new SequentialTransition();
			turnScore.setText("Current Turn Score: "+Integer.toString(bj.getTurnScore()));
			btnSetting[1]=true;//enable hit.
			btnSetting[2]=true;//enable stand.
		}else{
			MsgBox.appendText("\nDeal not availble.");
		}
	}
	
	@FXML
	public void btnHitListener(ActionEvent evnt) throws EmptyDeckException
	{
		if(btnSetting[1])
		{
			Card c = bj.playerDraw();
			appendSinglePicAnimated(c, "player");
			cardsFlow.play();
			cardsFlow = new SequentialTransition();
			MsgBox.appendText("\nPlayer Draw: "+c.toString()+"\nHand value:"+bj.getPlayerValue());
			playerVal.setText("\nPlayer hand: "+Integer.toString(bj.getPlayerValue()));
			turnScore.setText("Current Turn Score: "+Integer.toString(bj.getTurnScore()));
			if(bj.getPlayerValue()==21){
				MsgBox.appendText("\nyou have 21 dealer draws:");
				btnStandListener(evnt);
			}
			
			if(bj.getPlayerValue()>21)
			{
				btnSetting[2]=false;
				btnSetting[1]=false;
				bj.setPlayerBurn();
				bj.updateScores();
				MsgBox.appendText("\n**Player Burns, Dealer Win**");
				resetController();
				MsgBox.appendText("\n--------------------------");
				MsgBox.appendText("\nPress Deal to play again.");
				
			}
		}else if(bj.getPlayerBurn())
				{
					MsgBox.setText("\nCan't Hit You Burned.");
					MsgBox.appendText("\n--------------------------");
					MsgBox.appendText("\nPress Deal to play again.");
				}else{
					MsgBox.setText("\nbutton HIT not availble at this stage.");
				}
	}
	
	private void resetController() {
		// TODO Auto-generated method stub
		btnSetting = new boolean[]{true,false,false,true};
		bj.nextTurn();
		for (Node n : myController.getChildren()) {
			if(n.toString().contains("ImageView") )
				animateOut(n);
		}
		
		
		dealerPosOffset = -50;
		playerPosOffset = -50;
		flagFlippedCard = true;
	}
	
	@FXML
	public void btnStandListener(ActionEvent evnt) throws EmptyDeckException
	{
		if(btnSetting[2])
		{
			btnSetting[2]=false;
			btnSetting[1]=false;
			dealerVal.setText("Dealer hand: "+Integer.toString(bj.getDealerValue()));
			bj.showAllDealerCards();
			MsgBox.appendText("\nDealer Hand:\n"+bj.getHand(false));
			
			//appendPicToHandAnimated(bj.getDealerCards(),"dealer");
			//cardsFlow.play();
			//cardsFlow = new SequentialTransition();
			while(bj.getDealerValue()<17)
			{
				Card c = bj.dealerDraw();
				appendSinglePicAnimated(c, "dealer");
				
				
				cardsFlow.play();
				cardsFlow = new SequentialTransition();
				dealerVal.setText("Dealer hand: "+Integer.toString(bj.getDealerValue()));
				MsgBox.appendText("\nDealer Draw: "+c.toString()+"\nhand value:"+bj.getDealerValue());
			}
			bj.checkVictory(MsgBox);
			flipCardAnimated();
			
			cardsFlow.play();
			
			cardsFlow = new SequentialTransition();
			/*
			cardsFlow.setOnFinished(new EventHandler<ActionEvent>() {
				
				
				@Override
	            public void handle(ActionEvent event) {
					btnSetting = new boolean[]{true,false,false,true};
					bj.nextTurn();
					for (Node n : myController.getChildren()) {
						if(n.toString().contains("ImageView") )
							animateOut(n);
					}
					
					
					dealerPosOffset = -50;
					playerPosOffset = -50;
					flagFlippedCard = true;
				}
				
			});*/
			resetController();
			
		}else{
			MsgBox.setText("\nbutton STAND not availble at this stage.");
			
		}
	}
	
	
	private void flipCardAnimated() {
		Image cardFace = new Image(cardToFlip.getImageLocation(),true);
		//double x = card.getWidth();
		//double y = card.getHeight();
		RotateTransition rotationY = new RotateTransition();
        rotationY.setAxis( Rotate.Y_AXIS );
        rotationY.setDuration( Duration.seconds(1));
        rotationY.setByAngle( 90 );
        rotationY.setNode( imageToFlip );
        rotationY.setCycleCount(1);
        rotationY.setOnFinished(new EventHandler<ActionEvent>() {
        	
            @Override
            public void handle(ActionEvent event) {
            		Image i = new Image("/view/fxml/img/CardBack.png");
            		imageToFlip.setImage(i);
            		rotationY.setByAngle(-90);
            		rotationY.setOnFinished(null);
            		
            		
            }
        });
        
		imageToFlip.setImage(cardFace);
	   
	    
	    cardsFlow.getChildren().add(rotationY);
		
	}
	@FXML
	public void btnQuitListener(ActionEvent evnt)
	{
		if(btnSetting[3])
		{
			Platform.exit();
		}
	}
	
	


	

	private void appendSinglePic(Card c, HBox hb)  {
	
		ImageView iv1 = new ImageView();
		Image card = new Image(c.getImageLocation(), true);
		Image cardBack = new Image("/view/fxml/img/CardBack.png",true);
		//double x = card.getWidth();
		//double y = card.getHeight();
		RotateTransition rotationY = new RotateTransition();
        rotationY.setAxis( Rotate.Y_AXIS );
        rotationY.setDuration( Duration.seconds(1));
        rotationY.setByAngle( 90 );
        rotationY.setNode( iv1 );
        rotationY.setCycleCount(1);
        rotationY.setOnFinished(new EventHandler<ActionEvent>() {
        	
            @Override
            public void handle(ActionEvent event) {
            		iv1.setImage(card);
            		rotationY.setByAngle(-90);
            		rotationY.setOnFinished(null);
            		rotationY.play();
            		
            }
        });
        
		iv1.setImage(cardBack);
	    hb.getChildren().add(iv1);
	    hb.setSpacing(-40);
	    
	    rotationY.play();
	    Path path = new Path();
	   // x=labelDeck.getScaleX();
	  //  y=labelDeck.getScaleY();
	  //  System.out.println("("+x+" , "+y+")");
	    MoveTo start = new MoveTo(400,0);
	    LineTo end = new LineTo(hb.getScene().getX()+23, hb.getScene().getY()+23);//checked view to correct position error.
	    path.getElements().add(start);
	    path.getElements().add(end);
	    PathTransition trans = new PathTransition();
	    trans.setDuration(Duration.millis(1500));
	    trans.setNode(iv1);
	    trans.setPath(path);
	    trans.setOnFinished(new EventHandler<ActionEvent>() {
        	
            @Override
            public void handle(ActionEvent event) {
            	//	System.out.println("DFHAS");    		
            		
            }
        });
	    trans.play();
	    
	}
	

	/**
	 * this function receives a player or dealer(!) hand, and type of hand to move cards
	 * and append the picture of the card to the ImageView
	 * @param playerCards
	 * @param hb
	*/
private void appendPicToHandAnimated(ArrayList<Card> playerCards,String handString) {
		for(Card c : playerCards){
			ImageView iv = new ImageView();
			Image card = new Image("/view/fxml/img/CardBack.png", true);
			iv.setImage(card);
			if(flagFlippedCard && handString.equals("dealer"))
			{
				imageToFlip = iv;
				cardToFlip = c;
				flagFlippedCard = false;
			}
			myController.getChildren().add(iv);
	
			animatePlayerCard(iv,c,handString,1200);
	
		}
	}
	
private void animatePlayerCard(ImageView iv ,Card c,String hand,double speed){
	
		
		
		Path path = new Path();
		if(hand.equals("player"))
		{ 
			
	        path.getElements().add(new MoveTo(500,-500));
	        path.getElements().add(new CubicCurveTo(360,-200, 250, 130, playerPosOffset, 120));
	        playerPosOffset+=30;
	      
		}
		else
		{
			path.getElements().add(new MoveTo(500,-500));
	        path.getElements().add(new CubicCurveTo(360, -200, 200, -90, dealerPosOffset, -100));
	        dealerPosOffset +=30;
		}
        PathTransition pathTransition = new PathTransition();
       
        pathTransition.setDuration(Duration.millis(speed));
        pathTransition.setPath(path);
        pathTransition.setNode(iv);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
    
        Image card = new Image(c.getImageLocation(), true);
        iv.setImage(card);
        cardsFlow.getChildren().add(pathTransition);
	}
	
	private void appendSinglePicAnimated(Card c, String hand) {
		ImageView iv = new ImageView();
		Image card = new Image(c.getImageLocation(), true);
		iv.setImage(card);   
		myController.getChildren().add(iv);
		animatePlayerCard(iv, c, hand,200);
	
		
		
	}
	
	private void animateOut(Node n)
	{
		TranslateTransition translateTransition =
	            new TranslateTransition(Duration.millis(500), n);
	        
	        translateTransition.setToY(500);
	        translateTransition.play();
	}
	
	
	
	
	@Override
	public void setScreenParent(ScreenController screenPage) {
		myController = screenPage;
	}

}
