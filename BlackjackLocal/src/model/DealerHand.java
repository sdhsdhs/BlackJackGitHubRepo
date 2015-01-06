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
	boolean hasAce=false;
	/**
	 * major function: this function calculates the value of all the cards in the hand.
	 * the Logic for the calculation is as followed: add card value as is expect in aces then set to 1 or 11 as best fit.
	 * at the end if an ace was 11 and the total excessed 21 the ace turn to 1.
	 */
	@Override
	public int handValue() {
		short value = 0;
		for(Card c : hand)
		{
			if(c.getRank().equals(Rank.ACE))//enforce logic: Ace value for dealer 1 or 11 based on hand value.
			{
					if(value+c.getValue() > 21)
						value+=1;
					else{
						value+=c.getValue();
						
					}
				
			}
			else
			{
				value+=c.getValue();
			}
		}
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
	
	public Card getLastCard(){
		return this.hand.get(hand.size()-1);
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
	/**
	 * this function checks if dealer hit soft 17.
	 * @return true if dealer openning hand contains a Six and an Ace.
	 */
	public boolean isSoft17(){
		if(this.hand.size()==2)
			if(hand.get(0).getRank().equals(Rank.ACE)|| hand.get(1).getRank().equals(Rank.ACE))
				if(hand.get(0).getRank().equals(Rank.SIX)|| hand.get(1).getRank().equals(Rank.SIX))
					return true;
		return false;
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
