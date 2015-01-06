package testModule;

import static org.junit.Assert.*;
import model.BasicCard.Rank;
import model.BasicCard.Suit;
import model.DealerHand;
import model.Deck;

import org.junit.Test;

public class TestSoft17 {
	DealerHand dealer = new DealerHand();
	Deck deck = new Deck();
	/**
	 * test of Soft 17 different states and validate only cases of Six and Ace.
	 */
	@Test
	public void test() {
		//check ACE SIX
		dealer.AddCard(deck.drawSpecific(Rank.ACE, Suit.CLUBS));
		dealer.AddCard(deck.drawSpecific(Rank.SIX, Suit.DIAMONDS));
		assertTrue(dealer.isSoft17());
		deck.reuseDeck();
		dealer.clearHand();
		//Check SIX ACE
		dealer.AddCard(deck.drawSpecific(Rank.SIX, Suit.HEARTS));
		dealer.AddCard(deck.drawSpecific(Rank.ACE, Suit.SPADES));
		assertTrue(dealer.isSoft17());
		deck.reuseDeck();
		dealer.clearHand();
		//check no 17 if SIX and ACE present in hand.
		dealer.AddCard(deck.drawSpecific(Rank.SIX, Suit.CLUBS));
		dealer.AddCard(deck.drawSpecific(Rank.ACE, Suit.DIAMONDS));
		dealer.AddCard(deck.drawSpecific(Rank.ACE, Suit.SPADES));
		assertTrue(!dealer.isSoft17());
		deck.reuseDeck();
		dealer.clearHand();
		//Check normal 17 is not soft 17
		dealer.AddCard(deck.drawSpecific(Rank.SEVEN, Suit.CLUBS));
		dealer.AddCard(deck.drawSpecific(Rank.TEN, Suit.DIAMONDS));
		assertTrue(!dealer.isSoft17());
		dealer.clearHand();
	}

}
