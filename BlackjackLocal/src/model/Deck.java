package model;



import java.util.Random;

import model.BasicCard.Rank;
import model.BasicCard.Suit;

/**
 *this class represents the Deck of cards containing 52 cards with no jokers.
 *with 4 suits and rank from deuce to Ace
 */
public class Deck {
	private Card[] cards; // the array of all the cards.
	public static final int DECK_SIZE = 52;// fix size to array
	int head = 0; //the reference to where are we in the deck.
	
	/**
	 * default and only ctor.
	 * creates the deck and all the cards instances.
	 */
	public Deck()
    {
        cards = new Card[DECK_SIZE];
        int i=0;
        for (BasicCard.Suit suit: Card.Suit.values ()) {
                  for (BasicCard.Rank rank: Card.Rank.values ()) {
                	  cards[i++]= new Card (rank, suit);
        	      }
        }
    }
	
	/**
	 * function to reset a deck to its start position still not shuffled.
	 */
	public void reuseDeck()
    {
    	head=0;
    	int i=0;
    	for (Card.Suit suit: Card.Suit.values ()) {
            for (Card.Rank rank: Card.Rank.values ()) {
          	  cards[i++]= new Card (rank, suit);
  	      }
    	}
     }
	
	/**
	 *this function shuffles the deck to a completely randomized position.
	 *shuffle logic: calculate random index up to DECK_SIZE, swap card at position i with card at position random.
	 */
	public void shuffle() {
		int rand;
		Card temp;
		Random randIndex = new Random();
		// Research shows a deck need at least 7 shuffles to be really randomized.
		for (int j = 0; j < 7; j++)
			for (int i = 0; i < DECK_SIZE; i++) {
				randIndex.setSeed(System.nanoTime());//Different seed for each iteration for randmoness.
				rand = randIndex.nextInt(DECK_SIZE);
				temp = cards[i];
				cards[i] = cards[rand];
				cards[rand] = temp;
			}
	}
	
	

	/**
	 * this is a custom exception for case of empty deck.
	 */
	public class EmptyDeckException extends Exception {
		private static final long serialVersionUID = 1L;
		public EmptyDeckException () {
	        super("The Deck is empty reuse it or use a new deck.");
	    }
	}
	
	/**
	 * the function get a new card and advance the Deck head by 1.
	 * @return
	 * @throws EmptyDeckException if the deck is empty.
	 */
	public Card deal() throws EmptyDeckException {
		if (head > DECK_SIZE) // check for an empty deck
			throw new EmptyDeckException();

		return cards[head++];
	}

	/**
	 * this function is to be used only for testing!!!!!
	 * @param r
	 * @param s
	 * @return the requested card by suit and rank.
	 */
	public Card drawSpecific(Rank r,Suit s){
		for(Card c: cards)
			if(c.getRank().equals(r) && c.getSuit().equals(s))
				return c;
    	return null;
    }
	
	/**
	 * 
	 * @return the entire deck as a String.
	 */
	public String getDeck() {
		String str = new String();
		for (int i = head; i < DECK_SIZE; i++) {
			str += "[" + cards[i] + "] ";
		}
		return str;
	}
	
	@Override
	public String toString() {
		return "There are " + (cards.length - head)
				+ " cards remaining in deck: " + getDeck();

	}
}
