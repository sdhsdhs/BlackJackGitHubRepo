package model;

import java.util.ArrayList;

import javafx.scene.control.Label;
import model.Deck.EmptyDeckException;




/**
 * the Model in the MVC structure, this is the game Logic that uses all the basic classes
 * 
 */

public class BlackjackGame // model class
{
	private DealerHand dealer;//the dealer
	private PlayerHand player;//dear Mr. player may the odds always be in your favor.
	private Deck deck;
	private boolean playerBurn;
	private boolean dealerBurn;
	private int turn;
	private int turnScore;// the hand value during the draw phase
	private int Totalscore;//the total score of the player up untill current round/

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
	
	/**
	 * set all the cards to be faced up in dealer hand
	 */
	public void dealerRevelCard()
	{
		dealer.faceUpCards();
	}
	
	/**
	 * function compare which has the best hand
	 * and send the corresponding msg.
	 */
	public void checkVictory(Label resultLable,Label msgLabel) {
		
		if (dealer.handValue() > 21) {
			setDealerBurn();
			
			msgLabel.setText("Press Deal to play again.");
			resultLable.setText("Player Win");
			calculateTurnScore();
			updateTotalScore();
			return;
		}
		if (dealer.handValue() >= player.handValue()) {
			
			msgLabel.setText("Press Deal to play again.");
			resultLable.setText("Dealer Win");
			calculateTurnScore();
			updateTotalScore();
			return;
		} else{
			
			msgLabel.setText("Press Deal to play again.");
			resultLable.setText("Player Win");
			calculateTurnScore();
			updateTotalScore();
			return;
		}
	}
	
	/**
	 * call the function to undate the scores.
	 */
	public void updateScores()
	{
		calculateTurnScore();
		updateTotalScore();
	}
	
	/**
	 * check if dealer has hit soft 17
	 * @return true if so and False any other case
	 */
	public boolean dealerSoft17()
	{
		return dealer.isSoft17();
	}
	
	/**
	 * set's the game logic ready for next turn
	 */
	public void nextTurn()
	{
		this.dealer.clearHand();
		this.player.clearHand();
		this.turnScore=0;
		this.deck = new Deck();//we use a new deck each round because we are professional :D
		deck.shuffle();
		try {
			this.beginGame();
		} catch (EmptyDeckException e) {
			System.out.println("Next turn Failed.");
			e.printStackTrace();
		}
	}
	
	/**
	 * common you can guess what this does.
	 * @return
	 */
	public int getTurn(){
		return this.turn;
	}
	/**
	 * increment the turn count.
	 */
	public void incTurn()
	{
		this.turn++;
	}
	
	/**
	 *Logic: odd round the hand value is tripled.
	 *even round hand value is doubled.
	 */
	private void calculateTurnScore(){
		//calculate value
		if(turn%2==0)
			this.turnScore = getPlayerValue()*2;
		else
			this.turnScore = getPlayerValue()*3;
		
	}// calculate value.
	
	/**
	 * add's the current turn score to the total score the sign is depending on player win or lose
	 */
	private void updateTotalScore()
	{
		//if negative is penalty 
		if(playerBurn || (player.handValue()<=dealer.handValue() && !dealerBurn))
			this.turnScore*=-1;	
		
		this.Totalscore+=this.turnScore;
	}
	//getter setters...
	public int getTotalScore(){
		return this.Totalscore;
	}
	
	public int getTurnScore(){
		calculateTurnScore();//get current.
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