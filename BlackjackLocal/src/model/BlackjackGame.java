package model;

import java.util.ArrayList;

import javafx.scene.control.TextArea;

import model.Deck.EmptyDeckException;




/**
 * the Model in the MVC structure, this is the game Logic that uses all the basic classes
 * 
 */

public class BlackjackGame // model class
{
	private DealerHand dealer;//the dealer
	private PlayerHand player;//dear mr. player may the odds always be in your favor.
	private Deck deck;
	private boolean playerBurn;
	private boolean dealerBurn;
	private int turn;
	private int turnScore;
	private int Totalscore;

	public BlackjackGame() {
		dealer = new DealerHand();// dealer hand-rules differ in class the itself
		player = new PlayerHand();// player hand
		playerBurn=false;
		dealerBurn=false;
		deck = new Deck();
		deck.shuffle();//we directly shuffle the deck we don't want any errors.
		turnScore=0;
		Totalscore=0;
		turn=0;
	}
	
	/**
	 * 
	 * @return the player arrayList hand.
	 */
	public ArrayList<Card> getPlayerCards(){
		return player.getHand();
	}
	
	/**
	 * 
	 * @return the Dealer arrayList hand.
	 */
	public ArrayList<Card> getDealerCards(){
		return dealer.getHand();
	}
	
	/**
	 * 
	 * @return the player hand value
	 */
	public int getPlayerValue() {
		return (player.handValue());
	}

	/**
	 * 
	 * @return the dealer hand value
	 */
	public int getDealerValue() {
		return (dealer.handValue());
	}
	
	/**
	 * after player will press stand he sees all dealer cards
	 * this function will make sure they are shown.
	 */
	public void showAllDealerCards() {
		dealer.faceUpCards();
	}
	
	/**
	 * this function deals 2 cards to player and dealer
	 * @throws EmptyDeckException 
	 */
	public void beginGame() throws EmptyDeckException {
		for (int i = 0; i < 2; i++)
			player.AddCard(deck.deal());
		for (int i = 0; i < 2; i++)
			dealer.AddCard(deck.deal());
		
		turnScore=0;
		dealerBurn=false;
		playerBurn=false;
	}
	
	/**
	 * 
	 * @param pORd define which hand to return true for player false for dealer
	 * @return the chosen hand cards in simple text.
	 */
	public String getHand(boolean pORd)
	{
		if (pORd) {
			return player.toString();
		} else {
			return dealer.toString();
		}
	}
	
	/**
	 * this function lets the player draw after pressing the HIT button.
	 * not yet implemented, will be for iteration 2.
	 * @throws EmptyDeckException 
	 */
	public Card playerDraw() throws EmptyDeckException {
		Card c = deck.deal();
		player.AddCard(c);
		return player.getLastCard();
	}
	
	/**
	 * this function lets the Dealer draw after player pressed the STAND button.
	 * not yet implemented, will be for iteration 2.
	 * @throws EmptyDeckException 
	 */
	public Card dealerDraw() throws EmptyDeckException {
		
		Card c = deck.deal();
		dealer.AddCard(c);
		return dealer.getLastCard();
	}
	
	public void dealerRevelCard()
	{
		dealer.faceUpCards();
	}
	
	/**
	 * function compare which has the best hand
	 * not yet fully implemented, will be for iteration 2.
	 */
	public void checkVictory(TextArea msgBox) {
		
		if (dealer.handValue() > 21) {
			setDealerBurn();
			msgBox.appendText("\nDealer burns Player wins");
			msgBox.appendText("\n--------------------------");
			msgBox.appendText("\nPress Deal to play again.");
			calculateTurnScore();
			updateTotalScore();
			return;
		}
		if (dealer.handValue() >= player.handValue()) {
			msgBox.appendText("\nDealer: "+dealer.handValue()+"\nPlayer:"+player.handValue()+"\n**Dealer Wins**" );
			msgBox.appendText("\n--------------------------");
			msgBox.appendText("\nPress Deal to play again.");
			calculateTurnScore();
			updateTotalScore();
			
			return;
		} else{
			msgBox.appendText("\nDealer: "+dealer.handValue()+"\nPlayer:"+player.handValue()+"\n**Player Wins**" );
			msgBox.appendText("\n--------------------------");
			msgBox.appendText("\nPress Deal to play again.");
			calculateTurnScore();
			updateTotalScore();
			return;
		}
	}
	
	public void updateScores()
	{
		calculateTurnScore();
		updateTotalScore();
	}
	
	public void nextTurn()
	{
		this.dealer.clearHand();
		this.player.clearHand();
		this.turnScore=0;
		this.deck = new Deck();
		deck.shuffle();
		try {
			this.beginGame();
		} catch (EmptyDeckException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getTurn(){
		return this.turn;
	}
	public void incTurn()
	{
		this.turn++;
	}
	
	private void calculateTurnScore(){
		//calculate value
		if(turn%2==0)
			this.turnScore = getPlayerValue()*2;
		else
			this.turnScore = getPlayerValue()*3;
		
	}// calculate value.
	
	private void updateTotalScore()
	{
		//if negative is penalty 
		if(playerBurn || (player.handValue()<=dealer.handValue() && !dealerBurn))
			this.turnScore*=-1;	
		
		this.Totalscore+=this.turnScore;
	}
	
	public int getTotalScore(){
		return this.Totalscore;
	}
	
	public int getTurnScore(){
		calculateTurnScore();
		return this.turnScore;
	}
	
	public void setPlayerBurn() {
		this.playerBurn=true;
	}
	
	public void setDealerBurn() {
		this.dealerBurn=true;
	}

	public boolean getPlayerBurn() {
		return playerBurn;
	}
	
	public boolean getdealerBurn() {
		return dealerBurn;
	}

}// end of class