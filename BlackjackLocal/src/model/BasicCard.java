package model;

/**
 * theInterface for Cards 
 * contain the enum for Suit and Rank.
 */
public interface BasicCard {
	public enum Suit {CLUBS, DIAMONDS, HEARTS, SPADES}
	public enum Rank {
	    	  DEUCE, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
	      }
	
	public Rank getRank();
	public Suit getSuit();
	public int getValue();
}
