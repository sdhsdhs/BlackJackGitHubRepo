package testModule;


import java.util.ArrayList;
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
	
	private static void TestAllCardsAreDrawn() throws EmptyDeckException
	{
		ArrayList<Card> cards= new ArrayList<Card>();
		PlayerHand h = new PlayerHand();
		Deck d = new Deck();
		d.shuffle();
		//test if all cards get to get drawn.
		while(cards.size()<52){
			//for(int i =0;i<3;i++){
				Card c = d.deal();
				h.AddCard(c);
				if(!cards.contains(c))
					cards.add(c);
			//}
			d.reuseDeck();
			d.shuffle();
			h.clearHand();
		}
		
		class CustomComparator implements Comparator<Card> {
			   
		    public int compare(Card c1, Card c2) {
		        return c1.getRank().compareTo(c2.getRank());
		    }
		}
		
		Collections.sort(cards,new CustomComparator());
		for(Card c: cards)
			System.out.println(" "+c.toString()+" ");
	}

	
	private static void testAcevalueCorrect() {
		PlayerHand P = new PlayerHand();
		DealerHand D = new DealerHand();
		Deck d = new Deck();
		//test Ace value change
		D.AddCard(d.drawSpecific(Rank.ACE, Suit.DIAMONDS));
		D.AddCard(d.drawSpecific(Rank.FIVE, Suit.DIAMONDS));
		D.AddCard(d.drawSpecific(Rank.JACK, Suit.SPADES));
		D.faceUpCards();
		System.out.println("Dealer hand ");
		D.printHand();
		D.clearHand();
		//test Ace value difference.
		P.AddCard(d.drawSpecific(Card.Rank.JACK, Card.Suit.CLUBS));
		P.AddCard(d.drawSpecific(Card.Rank.EIGHT, Card.Suit.CLUBS));
		P.AddCard(d.drawSpecific(Card.Rank.ACE, Card.Suit.CLUBS));
		System.out.println("Player hand ");
		P.printHand();
		//test Ace Value difference.
		d.reuseDeck();
		D.AddCard(d.drawSpecific(Card.Rank.JACK, Card.Suit.CLUBS));
		D.AddCard(d.drawSpecific(Card.Rank.EIGHT, Card.Suit.CLUBS));
		D.AddCard(d.drawSpecific(Card.Rank.ACE, Card.Suit.CLUBS));
		D.faceUpCards();
		System.out.println("Dealer hand ");
		D.printHand();

	}


	private static void testPrint() throws Exception {
		PlayerHand P = new PlayerHand();
		Deck d = new Deck();
		d.shuffle();
		P.AddCard(d.deal());
		P.AddCard(d.deal());
		System.out.println("Hand of Player: "+P.toString());
		DealerHand de = new DealerHand();
		de.AddCard(d.deal());
		de.AddCard(d.deal());
		System.out.println("Hand of Dealer: "+de.toString());
	}
	
	
	public static void main(String[] args) throws Exception{

		TestAllCardsAreDrawn();
		 testAcevalueCorrect();
		testPrint();
		
		
	}

}
