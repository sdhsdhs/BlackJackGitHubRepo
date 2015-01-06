package testModule;

import static org.junit.Assert.*;
import model.Card;
import model.Deck;

import org.junit.Test;

public class TestIndividualCardValue {
	private Deck d = new Deck();
	/**
	 * test of value of each card by dealing an entire deck.
	 */
	@Test
	public void testGetValue() {
		try{
			boolean flag = true;
			while(d.isEmpty()){
				int val;
				Card c = d.deal();
				switch (c.getRank()) {
		           case DEUCE:   val = 2; break;
		           case THREE:   val = 3; break;
		           case FOUR:   val = 4; break;
		           case FIVE:   val = 5; break;
		           case SIX:   val = 6; break;
		           case SEVEN:   val = 7; break;
		           case EIGHT:   val = 8; break;
		           case NINE:   val = 9; break;
		           case TEN:   val = 10; break;
		           case JACK:  val = 10; break;
		           case QUEEN: val = 10; break;
		           case KING:  val = 10; break;
		           case ACE:  val = 11; break;
		           default: val = 0; break;
			   }
			if(val!=c.getValue())
				flag = false;
		}//end while
		
		assertTrue(flag);	
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
