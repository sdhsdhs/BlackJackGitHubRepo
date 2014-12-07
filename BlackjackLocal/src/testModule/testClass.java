package testModule;


import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;










import model.BasicCard.Rank;
import model.BasicCard.Suit;
import model.Card;
import model.DealerHand;
import model.Deck;
import model.Deck.EmptyDeckException;
import model.PlayerHand;

public class testClass {
	/**
	 * in this test case we have a deck and an array list of cards.
	 * we draw from the deck the first card and check if it exist in the array list in it doesn't we add it, then we reset the deck
	 * and shuffle it again.
	 * when the array list size reach 52 we stop sort(with custom comperator) and print the list to visually see that all cards have been drawn 
	 * @throws EmptyDeckException if we step put of bound on deck.
	 */
	private static void TestAllCardsAreDrawn() throws EmptyDeckException
	{
		ArrayList<Card> cards= new ArrayList<Card>();
		Deck d = new Deck();
		d.shuffle();
		//test if all cards get to get drawn.
		while(cards.size()<52){
				Card c = d.deal();
				if(!cards.contains(c))
					cards.add(c);
			d.reuseDeck();
			d.shuffle();
		}
		
		class CustomComparator implements Comparator<Card> {
			   
		    public int compare(Card c1, Card c2) {
		        return c1.getRank().compareTo(c2.getRank());
		    }
		}
		
		Collections.sort(cards,new CustomComparator());
		for(Card c: cards){
			c.flip();
			System.out.println(" "+c.toString()+" ");
		}
	}

	/**
	 * this function check 6 different Equivalence class we devised for all hands.(and the ace change sum to dealer)
	 * and prints their value to make sure it is right. in both dealer and player.
	 * the length is dew to the test cases.
	 * this function also test the correctness of the printing of the player hand.
	 */
	private static void testAcevalueCorrect() {
		PlayerHand p = new PlayerHand();
		DealerHand d = new DealerHand();
		Deck deck = new Deck();
	//test 4 Ace  Equivalence class 
		System.out.println("Player 4 aces, result should be: 14");
		p.AddCard(deck.drawSpecific(Rank.ACE, Suit.CLUBS));
		p.AddCard(deck.drawSpecific(Rank.ACE, Suit.DIAMONDS));
		p.AddCard(deck.drawSpecific(Rank.ACE, Suit.HEARTS));
		p.AddCard(deck.drawSpecific(Rank.ACE, Suit.SPADES));
		System.out.print(p.toString());
		System.out.println(Integer.toString(p.handValue()));
		deck.reuseDeck();
		System.out.println("Dealer 4 aces, result should be: 14");
		d.AddCard(deck.drawSpecific(Rank.ACE, Suit.CLUBS));
		d.AddCard(deck.drawSpecific(Rank.ACE, Suit.DIAMONDS));
		d.AddCard(deck.drawSpecific(Rank.ACE, Suit.HEARTS));
		d.AddCard(deck.drawSpecific(Rank.ACE, Suit.SPADES));
		System.out.print(d.toString());
		System.out.println(Integer.toString(p.handValue()));
		deck.reuseDeck();
		p.clearHand();
		d.clearHand();
		System.out.println("******************************************************");
	// test Ace Face  Equivalence class should be P:21, D:21
		System.out.println("Player Ace Face, result should be: 21");
		p.AddCard(deck.drawSpecific(Rank.ACE, Suit.CLUBS));
		p.AddCard(deck.drawSpecific(Rank.JACK, Suit.HEARTS));
		System.out.print(p.toString());
		System.out.println(Integer.toString(p.handValue()));
		deck.reuseDeck();
		System.out.println("Dealer Ace Face, result should be: 21");
		d.AddCard(deck.drawSpecific(Rank.ACE, Suit.DIAMONDS));
		d.AddCard(deck.drawSpecific(Rank.JACK, Suit.SPADES));
		System.out.print(d.toString());
		System.out.println(Integer.toString(d.handValue()));
		deck.reuseDeck();
		p.clearHand();
		d.clearHand();
		System.out.println("******************************************************");
	// test Ace NUMBER Face  Equivalence class should be P:11+x+10, D:1+X+10 (test dealer ACE change from 11 to 1)
		System.out.println("Player Ace Face, result should be: 29");
		p.AddCard(deck.drawSpecific(Rank.ACE, Suit.CLUBS));
		p.AddCard(deck.drawSpecific(Rank.EIGHT, Suit.DIAMONDS));
		p.AddCard(deck.drawSpecific(Rank.JACK, Suit.HEARTS));
		System.out.print(p.toString());
		System.out.println(Integer.toString(p.handValue()));
		deck.reuseDeck();
		System.out.println("Dealer Ace Face, result should be: 19");
		d.AddCard(deck.drawSpecific(Rank.ACE, Suit.DIAMONDS));
		d.AddCard(deck.drawSpecific(Rank.EIGHT, Suit.DIAMONDS));
		d.AddCard(deck.drawSpecific(Rank.JACK, Suit.SPADES));
		System.out.print(d.toString());
		System.out.println(Integer.toString(d.handValue()));
		deck.reuseDeck();
		p.clearHand();
		d.clearHand();
		System.out.println("******************************************************");
	//test ace doesn't change for dealer
		System.out.println("Dealer Ace Face, result should be: 21");
		d.AddCard(deck.drawSpecific(Rank.ACE, Suit.DIAMONDS));
		d.AddCard(deck.drawSpecific(Rank.EIGHT, Suit.DIAMONDS));
		d.AddCard(deck.drawSpecific(Rank.DEUCE, Suit.SPADES));
		System.out.print(d.toString());
		System.out.println(Integer.toString(d.handValue()));
		System.out.println("******************************************************");
		deck.reuseDeck();
		d.clearHand();
		System.out.println("******************************************************");
	// test face face quivalence class should be P:20, D:20
		System.out.println("Player Ace Face, result should be: 20");
		p.AddCard(deck.drawSpecific(Rank.KING, Suit.CLUBS));
		p.AddCard(deck.drawSpecific(Rank.JACK, Suit.HEARTS));
		System.out.print(p.toString());
		System.out.println(Integer.toString(p.handValue()));
		deck.reuseDeck();
		System.out.println("Dealer Ace Face, result should be: 20");
		d.AddCard(deck.drawSpecific(Rank.KING, Suit.DIAMONDS));
		d.AddCard(deck.drawSpecific(Rank.JACK, Suit.SPADES));
		System.out.print(d.toString());
		System.out.println(Integer.toString(d.handValue()));
		deck.reuseDeck();
		p.clearHand();
		d.clearHand();
		System.out.println("******************************************************");
	// test Number Number equivalence class should be P:x+y, D:x+y
		System.out.println("Player Ace Face, result should be: 13");
		p.AddCard(deck.drawSpecific(Rank.EIGHT, Suit.CLUBS));
		p.AddCard(deck.drawSpecific(Rank.FIVE, Suit.HEARTS));
		System.out.print(p.toString());
		System.out.println(Integer.toString(p.handValue()));
		deck.reuseDeck();
		System.out.println("Dealer Ace Face, result should be: 13");
		d.AddCard(deck.drawSpecific(Rank.EIGHT, Suit.DIAMONDS));
		d.AddCard(deck.drawSpecific(Rank.FIVE, Suit.SPADES));
		System.out.print(d.toString());
		System.out.println(Integer.toString(d.handValue()));
		deck.reuseDeck();
		p.clearHand();
		d.clearHand();
	}

	
	/**
	 * this function test the randomness of the deck.
	 * we have an array the size of 53(first cell is sentinel).
	 * we draw 52*35 times, 52 for cards in the deck, 35 for assumption of Uniform distribution(minimum is 30 we took some extra)
	 * each card has a specific place in the array, and on draw increments it by 1;
	 * the function than prints the array of draw,that should show different values for each cell,and on each run.
	 * also it show the max and minimum number of appearances of cards.
	 * @throws EmptyDeckException
	 */
	public static void checkRandom() throws EmptyDeckException{
		Deck d = new Deck();
		int[] arr = new int[53];
		
		for(int i = 1; i<=1820; i++){
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
		System.out.println(Arrays.toString(arr));
		Arrays.sort(arr);
	    System.out.println("Min value of apperances: "+arr[1]);
	    System.out.println("Max value of apperances: "+arr[arr.length-1]);
	}
	
	/**
	 * run tests
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{

		TestAllCardsAreDrawn();
		System.out.println("\n-----------------------------------------------------------------------------\n");
		testAcevalueCorrect();
		System.out.println("\n-----------------------------------------------------------------------------\n");
		checkRandom();	
	}

}
