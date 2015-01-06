package controller;



import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import model.BlackjackGame;
import model.Card;
import model.Deck.EmptyDeckException;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 * this function is the controller in the MVC structure
 *it has a reference to the game, and is the defined controller in the view
 *in accordance to javaFX rules.(the controller is defined in the view not the other way around)
 */
public class ControllerFX implements Initializable, ControlledScreen {

	
	private BlackjackGame bj; //reference to model
	@FXML 
	private javafx.scene.control.Label playerVal;//will show the player hand value in the view
	@FXML
	private javafx.scene.control.Label dealerVal; //label of dealer hand value.
	@FXML
	private Label turnScore; //total hand current value
	@FXML
	private Label totalScore;//total value all rounds played.
	@FXML
	private Label turn;//current turn
	@FXML
	private Label resultLable;
	@FXML
	private Label msgLabel;

	

	private ScreenController myController;//for start animation purpose's.
	
	private double dealerPosOffset = -50;//for dealer hand Images position.
	private double playerPosOffset = -50;//for player hand Images position.
	
	SequentialTransition cardsFlow = new SequentialTransition();
	
	ImageView imageToFlip;
	Card cardToFlip;
	boolean flagFlippedCard = true;
	
	private boolean[] btnSetting;//array to control buttons availability.
	
	/**
	 * the constructor with a reference to a game(the model).
	 */
	public ControllerFX() throws EmptyDeckException {
		
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
		msgLabel.setText("Press Deal To Start");
		resultLable.setTextFill(Paint.valueOf("white")); //cosmetics.
		turn.setTextFill(Paint.valueOf("white"));
		turnScore.setTextFill(Paint.valueOf("white"));
		totalScore.setTextFill(Paint.valueOf("white"));		
	}
	
	/**
	 * this is the listener to the reset button
	 * it resets the game scores and rounds and puts the game back
	 * at starting position.
	 */
	public void btnResetListener(ActionEvent evnt){
		resetGame();
		turn.setText("Round: ");
		totalScore.setText("Total Score: ");
		turnScore.setText("Current Turn Score: ");
		playerVal.setText("Player hand");
		dealerVal.setText("Dealer Hand");
		msgLabel.setText("Press Deal To Start");
	}
	
	/**
	 *the method clear all cards from the table
	 *begins a new game and reset the controller to start position. 
	 */
	private void resetGame()
	{
		clearBoard();
		bj.resetScore();
		try {
			bj.beginGame();
		} catch (EmptyDeckException e) {
			e.printStackTrace();
		}
		resetController();
	}
	
	/**
	 * use animation to take cards if exist back to the dealer.
	 */
	private void clearBoard()
	{
		for (Node n : myController.getChildren()) 
		{
			if(n.toString().contains("ImageView") )
				animateOut(n);
		}
	}
	/**
	 * this function is the listener to the view DEAL button.
	 * it set the Images of the hands to their appropriate position in the Stage.
	 * 
	 * @param evnt the event of button press.
	 */
	@FXML
	public void btnDealListener(ActionEvent evnt){
		
		if(btnSetting[0]){
			//clear the board
			resetController();
			clearBoard();			
			
			//start new turn
			btnSetting[0]=false;// deal no longer available.
			bj.incTurn();
			turn.setText("Round: "+Integer.toString(bj.getTurn()));
			totalScore.setText("Total Score: "+Integer.toString(bj.getTotalScore()));
			playerVal.setText("Player hand");
			dealerVal.setText("Dealer Hand");
			//set the animation of the cards flow to Player.
			appendPicToHandAnimated(bj.getPlayerCards(),"player"); 
			cardsFlow.setOnFinished(new EventHandler<ActionEvent>() { //make sure the hand value display only after cards. 
	            @Override
	            public void handle(ActionEvent event) {
	            	playerVal.setText("Player hand: "+Integer.toString(bj.getPlayerValue()));
	            }
			});
			cardsFlow.play();
			//reset the transition and make the Dealer flow animation.
			cardsFlow = new SequentialTransition();
			appendPicToHandAnimated(bj.getDealerCards(),"dealer");
			cardsFlow.play();
			cardsFlow = new SequentialTransition();
			turnScore.setText("Current Turn Score: "+Integer.toString(bj.getTurnScore()));
			btnSetting[1]=true;//enable hit.
			btnSetting[2]=true;//enable stand.
		}else{//deal not available display msg
			
			msgLabel.setText("Deal Not Availble");
		}
	}//end of deal listener
	
	/**
	 * this function Deals a card from the deck to the player.
	 * It also fire the animation the the View.
	 * @param evnt
	 * @throws EmptyDeckException
	 */
	@FXML
	public void btnHitListener(ActionEvent evnt) throws EmptyDeckException
	{
		if(btnSetting[1])
		{
			Card c = bj.playerDraw();
			appendSinglePicAnimated(c, "player"); //prepare the animation.
			cardsFlow.play();//fire the animation.
			cardsFlow = new SequentialTransition();//reset the animation.
		
			playerVal.setText("Player hand: "+Integer.toString(bj.getPlayerValue()));
			turnScore.setText("Current Turn Score: "+Integer.toString(bj.getTurnScore()));
			if(bj.getPlayerValue()==21){//Case Player have 21 now turn move to dealer.
				
				btnStandListener(evnt);
			}
			//case Player Burn.
			if(bj.getPlayerValue()>21)
			{
				btnSetting[0]=true;
				btnSetting[2]=false;
				btnSetting[1]=false;
				bj.setPlayerBurn();
				bj.updateScores();
			
				msgLabel.setText("Press Deal to play again.");
				resultLable.setText("Dealer Win");
			}
		}else if(bj.getPlayerBurn()) //case Player Burned and hit.
				{
					msgLabel.setText("You Burned press Deal or Quit");
					
				}else{
				
					msgLabel.setText("HIT not availble");
				}
	}//end of hit listener
	
	/**
	 * this function reset the View and Model to the base Position
	 */
	private void resetController() {
		
		btnSetting = new boolean[]{true,false,false,true};
		bj.nextTurn();
		dealerPosOffset = -50;
		playerPosOffset = -50;
		flagFlippedCard = true;
		resultLable.setText("");
		msgLabel.setText("");
	}
	
	/**
	 * this function is the reaction to Stand button pressing
	 * it revel the dealer Cards and if he needs he draws
	 * it also fire the corresponding animations.
	 * @param evnt
	 * @throws EmptyDeckException
	 */
	@FXML
	public void btnStandListener(ActionEvent evnt) throws EmptyDeckException
	{
		if(btnSetting[2])
		{	
			//unable Stand and Hit.
			btnSetting[2]=false;
			btnSetting[1]=false;
			
			dealerVal.setText("Dealer hand: "+Integer.toString(bj.getDealerValue()));//show value
			bj.showAllDealerCards();//make the card apparent.
			ArrayList<Card> addedCards = new ArrayList<Card>();
			//if dealer has soft 17 he draws a card and then continue as usual.
			if(bj.dealerSoft17())
			{
				Card c = bj.dealerDraw();
				addedCards.add(c);
				
				cardsFlow = new SequentialTransition();
				
			}
			//dealer draws until he has 17 or more.
			while(bj.getDealerValue()<17)
			{
				Card c = bj.dealerDraw();
				addedCards.add(c);
				cardsFlow = new SequentialTransition();
				
			}
			//make the animation happen
			flipCardAnimated();  
			flipCardAnimated();//that isn't a mistake the double call is needed.
			appendPicToHandAnimated(addedCards,"dealer");
			//set on the end of animation actions
			cardsFlow.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	dealerVal.setText("Dealer hand: "+Integer.toString(bj.getDealerValue()));//display dealer total hand value
            	bj.checkVictory(resultLable,msgLabel);//check who wins and display msg.
            	btnSetting[0]=true;//enable deal
            }
			});
			cardsFlow.play();		
			cardsFlow = new SequentialTransition();
			
		}else{
			if(bj.getPlayerBurn())
			{	msgLabel.setText("Can't Stand You Burned.");
				
			}
			else{
				
				msgLabel.setText("STAND not availble");
			}
			
		}
	}//end of stand listener
	
	/**
	 * this function make the magic of flip animation happen.
	 */
	private void flipCardAnimated() {
		Image cardFace = new Image("/view/fxml/img/CardBack.png",true);	
		RotateTransition rotationY = new RotateTransition();
        rotationY.setAxis( Rotate.Y_AXIS );
        rotationY.setDuration( Duration.millis(200));
        rotationY.setByAngle( 90 );
        rotationY.setNode( imageToFlip );
        rotationY.setCycleCount(1); 
        rotationY.setOnFinished(new EventHandler<ActionEvent>() {
        	
            @Override
            public void handle(ActionEvent event) {
            		Image i = new Image(cardToFlip.getImageLocation());
            		imageToFlip.setImage(i);
            		rotationY.setByAngle(-90);
            		rotationY.setOnFinished(null);
            }
        });
        
		imageToFlip.setImage(cardFace);
	    cardsFlow.getChildren().add(rotationY);//add the rotation to the sequence
	}
	
	/**
	 * this incredibly complicated method set the most complex mechanism of the entire app
	 * it closes the app.
	 * crazy i know..
	 * @param evnt-the Big Bang!! no just kidding only a button QUIT press...
	 */
	@FXML
	public void btnQuitListener(ActionEvent evnt)
	{
		if(btnSetting[3])
		{
			Platform.exit();//yea that's all it takes. and may the force be with you.
		}
	}

	
	/**
	 * this function make the magic of adding cards animation to happen.
	 * it gets an arrayList of cards and the name of the player or dealer for position.
	 * 
	 * @param playerCards
	 * @param handString
	 */
	private void appendPicToHandAnimated(ArrayList<Card> playerCards,String handString) {
		//Prepare the ImageView's and add them
		for(Card c : playerCards){
			ImageView iv = new ImageView();
			Image card = new Image("/view/fxml/img/CardBack.png", true);
			iv.setImage(card);
			if(flagFlippedCard && handString.equals("dealer"))//for case of first card not shown
			{
				imageToFlip = iv;
				cardToFlip = c;
				flagFlippedCard = false;
			}
			myController.getChildren().add(iv);			
			animatePlayerCard(iv,c,handString,900);//make the single animation.
			}
		}
	
	/**
	 * set the individual animate card.
	 * @param iv image view of the card Image
	 * @param c the Card
	 * @param hand the Hand(Player or dealer)
	 * @param speed the speed of the transition.
	 */
	private void animatePlayerCard(ImageView iv ,Card c,String hand,double speed){
		Path path = new Path(); 
		if(hand.equals("player")) //set the position according to player or dealer.
		{ 
			
	        path.getElements().add(new MoveTo(500,-500));
	        path.getElements().add(new CubicCurveTo(360,-200, 250, 130, playerPosOffset, 120));//check function description it's well documented
	        playerPosOffset+=30;
	      
		}
		else
		{
			path.getElements().add(new MoveTo(500,-500));
	        path.getElements().add(new CubicCurveTo(360, -200, 200, -90, dealerPosOffset, -100));
	        dealerPosOffset +=30;
		}
        PathTransition pathTransition = new PathTransition();
        //set the transition
        pathTransition.setDuration(Duration.millis(speed));
        pathTransition.setPath(path);
        pathTransition.setNode(iv);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        
        Image card = new Image(c.getImageLocation(), true);
        iv.setImage(card);
        cardsFlow.getChildren().add(pathTransition);
	}//end of animation.
	
	/**
	 * setter of animation
	 * @param c
	 * @param hand
	 */
	private void appendSinglePicAnimated(Card c, String hand) {
		ImageView iv = new ImageView();
		Image card = new Image(c.getImageLocation(), true);
		iv.setImage(card);   
		myController.getChildren().add(iv);
		animatePlayerCard(iv, c, hand,600);
	}
	
	/**
	 * this function clear the board of the Images in the direction of the dealer.
	 * @param n
	 */
	private void animateOut(Node n)
	{
		TranslateTransition translateTransition =
	            new TranslateTransition(Duration.millis(500), n);
	        
	        translateTransition.setToY(-500);//clear card's toward dealer
	        translateTransition.play();
	}

	@Override
	public void setScreenParent(ScreenController screenPage) {
		myController = screenPage;
	}

}
