package model;

import java.util.ArrayList;

import model.Deck.EmptyDeckException;




/**
 * the MODEL in the MVC structure, this is the game Logic that uses all the basic classes
 * 
 */

public class BlackjackGame // model class
{
	private DealerHand dealer;//the dealer
	private PlayerHand player;//dear mr. player may the odds always be in your favor.
	private Deck deck;

	public BlackjackGame() {
		dealer = new DealerHand();// dealer hand-rules differ in class the itself
		player = new PlayerHand();// player hand
		deck = new Deck();
		deck.shuffle();//we directly shuffle the deck we don't want any errors.
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
	 */
	public void playerDraw() {
		//player.AddCard(deck.deal());
		// TODO Auto-generated method stub
	}
	
	/**
	 * this function lets the Dealer draw after player pressed the STAND button.
	 * not yet implemented, will be for iteration 2.
	 */
	public void dealerDraw() {
		//dealer.AddCard(deck.deal());
		// TODO Auto-generated method stub
	}
	
	/**
	 * function compare which has the best hand
	 * not yet fully implemented, will be for iteration 2.
	 */
	public void checkVictory() {
		if (dealer.handValue() > 21) {
			// TODO Auto-generated method stub
		}
		if (dealer.handValue() >= player.handValue()) {
			// TODO Auto-generated method stub
		} else{
			// TODO Auto-generated method stub
		}
	}

}// end of class