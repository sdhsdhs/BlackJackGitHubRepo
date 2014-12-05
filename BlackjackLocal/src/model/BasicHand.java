package model;

import java.util.ArrayList;

/**
 * theInterface for different hands.
 * 
 */
public interface BasicHand {
	/**
	 * 
	 * @return the accurate value of all the cards in the hand.
	 */
	public int handValue();
	
	/**
	 * this function prints to the consle the all the cards in the hand along with their value
	 */
	public void printHand();
	
	/**
	 * clear all the cards from current hand.
	 */
	public void clearHand();
	
	/**
	 * @param c a Card instance 
	 * the function adds the card to the hand.
	 */
	public void AddCard(Card c);
	
	/**
	 * the function return the array list representing the hand.
	 * @return
	 */
	public ArrayList<Card> getHand();
}
