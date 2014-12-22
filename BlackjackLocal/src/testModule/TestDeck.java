package testModule;

import static org.junit.Assert.*;

import java.util.ArrayList;


import model.Card;
import model.Deck;



import org.junit.Test;

public class TestDeck {
	Deck d = new Deck();
	
	@Test
	public void testDealAllCards() {
		d.reuseDeck();
		try{
		ArrayList<Card> cards= new ArrayList<Card>();
		d.shuffle();
		//test if all cards get to be drawn.
		while(cards.size()<52){
				Card c = d.deal();
				c.flip();
				if(!cards.contains(c))
					cards.add(c);
			d.reuseDeck();
			d.shuffle();
		}
		
		boolean flag = true;
		Deck deck = new Deck();// create a new deck to see all cards in that deck exist in array
		while(!deck.isEmpty()){
			
			if(!cards.contains(deck.deal()))
			{
				flag=false;
			}
		}
		
		assertTrue(flag);
		
		}catch(Exception e){
			System.out.println(e.getLocalizedMessage());
			fail("exception accured."+e.getLocalizedMessage());
		}
	}
	
	
	@Test
	public void testRandmoness()
	{
		int[] arr = new int[53];
		try{
		for(int i = 1; i<=1820; i++)
		{
			d.shuffle();
			int result=0;
				Card c = d.deal();
				c.flip();
				switch (c.getRank()) {
		           case DEUCE:   result = 2; break;
		           case THREE:   result = 3; break;
		           case FOUR:   result = 4; break;
		           case FIVE:   result = 5; break;
		           case SIX:   result = 6; break;
		           case SEVEN:   result = 7; break;
		           case EIGHT:   result = 8; break;
		           case NINE:   result = 9; break;
		           case TEN:   result = 10; break;
		           case JACK:  result = 11; break;
		           case QUEEN: result = 12; break;
		           case KING:  result = 13; break;
		           case ACE:  result = 1; break;
		           default: result = 0; break;
			   }
				switch(c.getSuit()){
					case DIAMONDS: result+=13; break;
					case HEARTS: result+=26; break;
					case SPADES: result+=39; break;
					default: ;
				}
			arr[result]++;
			d.reuseDeck();//reset deck.
		}
		boolean flag = true;
		for(int i = 1;i<53;i++){
			if(arr[i]<6 || arr[i]>65)//check that all results are in max 2 standard deviation of 35 that is the mean.
			{
				flag = false;
				System.out.println(i+" "+arr[i]);
			}
		}
		assertTrue(flag);
		
		}catch(Exception e){
			fail(e.getLocalizedMessage());
		}
	}
}
