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
	
	@Test
	public void testPlayerHandValue() {
		p.AddCard(deck.drawSpecific(Rank.ACE, Suit.CLUBS));
		p.AddCard(deck.drawSpecific(Rank.ACE, Suit.DIAMONDS));
		p.AddCard(deck.drawSpecific(Rank.ACE, Suit.HEARTS));
		p.AddCard(deck.drawSpecific(Rank.ACE, Suit.SPADES));
		assertEquals(14, p.handValue());
		deck.reuseDeck();
		p.clearHand();
		p.AddCard(deck.drawSpecific(Rank.ACE, Suit.CLUBS));
		p.AddCard(deck.drawSpecific(Rank.JACK, Suit.HEARTS));
		assertEquals(21, p.handValue());
		deck.reuseDeck();
		p.clearHand();
		p.AddCard(deck.drawSpecific(Rank.ACE, Suit.CLUBS));
		p.AddCard(deck.drawSpecific(Rank.EIGHT, Suit.DIAMONDS));
		p.AddCard(deck.drawSpecific(Rank.JACK, Suit.HEARTS));
		assertEquals(29, p.handValue());
		deck.reuseDeck();
		p.clearHand();
		p.AddCard(deck.drawSpecific(Rank.KING, Suit.CLUBS));
		p.AddCard(deck.drawSpecific(Rank.JACK, Suit.HEARTS));
		assertEquals(20, p.handValue());
		deck.reuseDeck();
		p.clearHand();
		p.AddCard(deck.drawSpecific(Rank.EIGHT, Suit.CLUBS));
		p.AddCard(deck.drawSpecific(Rank.FIVE, Suit.HEARTS));
		assertEquals(13, p.handValue());
		deck.reuseDeck();
		p.clearHand();
	}
	
	@Test
	public void testDealerHandValue() {
		d.AddCard(deck.drawSpecific(Rank.ACE, Suit.CLUBS));
		d.AddCard(deck.drawSpecific(Rank.ACE, Suit.DIAMONDS));
		d.AddCard(deck.drawSpecific(Rank.ACE, Suit.HEARTS));
		d.AddCard(deck.drawSpecific(Rank.ACE, Suit.SPADES));
		assertEquals(14, d.handValue());
		d.clearHand();
		deck.reuseDeck();		
		//
		d.AddCard(deck.drawSpecific(Rank.ACE, Suit.DIAMONDS));
		d.AddCard(deck.drawSpecific(Rank.JACK, Suit.SPADES));
		assertEquals(21, d.handValue());
		d.clearHand();
		deck.reuseDeck();		
		//
		d.AddCard(deck.drawSpecific(Rank.ACE, Suit.DIAMONDS));
		d.AddCard(deck.drawSpecific(Rank.EIGHT, Suit.DIAMONDS));
		d.AddCard(deck.drawSpecific(Rank.JACK, Suit.SPADES));
		assertEquals(19, d.handValue());
		d.clearHand();
		deck.reuseDeck();		
		//
		d.AddCard(deck.drawSpecific(Rank.ACE, Suit.DIAMONDS));
		d.AddCard(deck.drawSpecific(Rank.EIGHT, Suit.DIAMONDS));
		d.AddCard(deck.drawSpecific(Rank.DEUCE, Suit.SPADES));
		assertEquals(21, d.handValue());
		d.clearHand();
		deck.reuseDeck();		
		//
		d.AddCard(deck.drawSpecific(Rank.KING, Suit.DIAMONDS));
		d.AddCard(deck.drawSpecific(Rank.JACK, Suit.SPADES));
		assertEquals(20, d.handValue());
		d.clearHand();
		deck.reuseDeck();		
		//
		d.AddCard(deck.drawSpecific(Rank.EIGHT, Suit.DIAMONDS));
		d.AddCard(deck.drawSpecific(Rank.FIVE, Suit.SPADES));
		assertEquals(13, d.handValue());
		d.clearHand();
		deck.reuseDeck();
		
	}
	
}
