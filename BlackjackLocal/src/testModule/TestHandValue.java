package testModule;

import static org.junit.Assert.*;
import model.DealerHand;
import model.Deck;
import model.PlayerHand;
import model.BasicCard.Rank;
import model.BasicCard.Suit;

import org.junit.Test;

public class TestHandValue {
	private PlayerHand p = new PlayerHand();
	private DealerHand d = new DealerHand();
	private Deck deck = new Deck();
	/**
	 * test to check all Equivalence class we devised.
	 */
	@Test
	public void testPlayerHandValue() {
		//check 4 aces for all combination of aces.
		p.AddCard(deck.drawSpecific(Rank.ACE, Suit.CLUBS));
		p.AddCard(deck.drawSpecific(Rank.ACE, Suit.DIAMONDS));
		p.AddCard(deck.drawSpecific(Rank.ACE, Suit.HEARTS));
		p.AddCard(deck.drawSpecific(Rank.ACE, Suit.SPADES));
		assertEquals(14, p.handValue());
		deck.reuseDeck();
		p.clearHand();
		//Equivalence class  of face ace
		p.AddCard(deck.drawSpecific(Rank.ACE, Suit.CLUBS));
		p.AddCard(deck.drawSpecific(Rank.JACK, Suit.HEARTS));
		assertEquals(21, p.handValue());
		deck.reuseDeck();
		p.clearHand();
		//Equivalence class  of face ace and number.
		p.AddCard(deck.drawSpecific(Rank.ACE, Suit.CLUBS));
		p.AddCard(deck.drawSpecific(Rank.EIGHT, Suit.DIAMONDS));
		p.AddCard(deck.drawSpecific(Rank.JACK, Suit.HEARTS));
		assertEquals(29, p.handValue());
		deck.reuseDeck();
		p.clearHand();
		//Equivalence class of face face
		p.AddCard(deck.drawSpecific(Rank.KING, Suit.CLUBS));
		p.AddCard(deck.drawSpecific(Rank.JACK, Suit.HEARTS));
		assertEquals(20, p.handValue());
		deck.reuseDeck();
		p.clearHand();
		//Equivalence class of number number
		p.AddCard(deck.drawSpecific(Rank.EIGHT, Suit.CLUBS));
		p.AddCard(deck.drawSpecific(Rank.FIVE, Suit.HEARTS));
		assertEquals(13, p.handValue());
		deck.reuseDeck();
		p.clearHand();
	}
	
	@Test
	public void testDealerHandValue() {
		//as is in player
		d.AddCard(deck.drawSpecific(Rank.ACE, Suit.CLUBS));
		d.AddCard(deck.drawSpecific(Rank.ACE, Suit.DIAMONDS));
		d.AddCard(deck.drawSpecific(Rank.ACE, Suit.HEARTS));
		d.AddCard(deck.drawSpecific(Rank.ACE, Suit.SPADES));
		assertEquals(14, d.handValue());
		d.clearHand();
		deck.reuseDeck();		
		//as in player
		d.AddCard(deck.drawSpecific(Rank.ACE, Suit.DIAMONDS));
		d.AddCard(deck.drawSpecific(Rank.JACK, Suit.SPADES));
		assertEquals(21, d.handValue());
		d.clearHand();
		deck.reuseDeck();		
		//check ace Choose value 1 or 11.
		d.AddCard(deck.drawSpecific(Rank.ACE, Suit.DIAMONDS));
		d.AddCard(deck.drawSpecific(Rank.EIGHT, Suit.DIAMONDS));
		d.AddCard(deck.drawSpecific(Rank.JACK, Suit.SPADES));
		assertEquals(29, d.handValue());//choose 11
		d.clearHand();
		deck.reuseDeck();
		d.AddCard(deck.drawSpecific(Rank.EIGHT, Suit.DIAMONDS));
		d.AddCard(deck.drawSpecific(Rank.JACK, Suit.SPADES));
		d.AddCard(deck.drawSpecific(Rank.ACE, Suit.DIAMONDS));
		assertEquals(19, d.handValue());//choose 1
		d.clearHand();
		deck.reuseDeck();
		//as in player
		d.AddCard(deck.drawSpecific(Rank.ACE, Suit.DIAMONDS));
		d.AddCard(deck.drawSpecific(Rank.EIGHT, Suit.DIAMONDS));
		d.AddCard(deck.drawSpecific(Rank.DEUCE, Suit.SPADES));
		assertEquals(21, d.handValue());
		d.clearHand();
		deck.reuseDeck();		
		//as in player
		d.AddCard(deck.drawSpecific(Rank.KING, Suit.DIAMONDS));
		d.AddCard(deck.drawSpecific(Rank.JACK, Suit.SPADES));
		assertEquals(20, d.handValue());
		d.clearHand();
		deck.reuseDeck();		
		//as in player
		d.AddCard(deck.drawSpecific(Rank.EIGHT, Suit.DIAMONDS));
		d.AddCard(deck.drawSpecific(Rank.FIVE, Suit.SPADES));
		assertEquals(13, d.handValue());
		d.clearHand();
		deck.reuseDeck();
		//check value if ace was 1 already.
		d.AddCard(deck.drawSpecific(Rank.JACK, Suit.CLUBS));	
		d.AddCard(deck.drawSpecific(Rank.FIVE, Suit.SPADES));		
		d.AddCard(deck.drawSpecific(Rank.ACE, Suit.SPADES));		
		d.AddCard(deck.drawSpecific(Rank.KING, Suit.SPADES));		
		d.AddCard(deck.drawSpecific(Rank.QUEEN, Suit.DIAMONDS));
		assertEquals(36, d.handValue());
		d.clearHand();
		deck.reuseDeck();
	}//end of method.
	
}//end of class testHandValue
