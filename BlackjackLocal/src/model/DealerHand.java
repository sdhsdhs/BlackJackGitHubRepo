package model;

import java.util.ArrayList;

import model.BasicCard.Rank;

/**
 *	this is the class that represents the Dealer hand and implements the basic hand interface
 *	
 */
public class DealerHand implements BasicHand{
	private ArrayList<Card> hand;
	
	/**
	 * default and only ctor
	 */
	public DealerHand(){
		this.hand = new ArrayList<Card>();
	}
	/**
	 * major function: this function calculates the value of all the cards in the hand.
	 * the Logic for the calculation is as followed: add card value as is expect in aces then set to 1 or 11 as best fit.
	 * at the end if an ace was 11 and the total excessed 21 the ace turn to 1.
	 */
	@Override
	public int handValue() {
		short value = 0;
		boolean hasAce=false;
		for(Card c : hand)
		{
			if(c.getRank().equals(Rank.ACE))//enforce logic: Ace value for dealer 1 or 11 based on hand value.
			{
					if(value+c.getValue() > 21)
						value+=1;
					else
						value+=c.getValue();
				hasAce=true;
			}
			else
			{
						value+=c.getValue();
			}
		}
		if(hasAce && value>21)
			return (value-10); //critical line: If First Ace valued 11 and following cards passed 21,this line enforce the Ace to be equal 1.
		else
			return value;
	}
	
	/*
	 * (non-Javadoc)
	 * @see model.BasicHand#printHand()
	 */
	@Override
	public void printHand()
	{
		for(Card c: hand)
		{
			if(c.faceUp())
			System.out.println(c.toString()+" ");
			else{
				System.out.println(" [covered card] ");
			}
		}
		System.out.println("hand Value is:"+handValue());
	}
	
	/*
	 * (non-Javadoc)
	 * @see model.BasicHand#getHand()
	 */
	@Override
	public ArrayList<Card> getHand()
	{
		return this.hand;
	}
	
	/*
	 * (non-Javadoc)
	 * @see model.BasicHand#clearHand()
	 */
	@Override
	public void clearHand() {
		this.hand.clear();
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see model.BasicHand#AddCard(model.Card)
	 */
	@Override
	public void AddCard(Card c) {
		if(hand.isEmpty()){
			hand.add(c);//default for card is face down
		}else{
			c.flip();
			hand.add(c);
		}
	}
	
	/**
	 * this function restore all cards to face up position so every one can see how pretty they are.
	 */
	public void faceUpCards() {
		for(Card c : hand)
			if(!c.faceUp())
				c.flip();
	}
	
	@Override
	public String toString() {
		String str="";
		for(Card c: hand){
			str+=c.toString()+"\n";
		}
		return str;
	}
}
