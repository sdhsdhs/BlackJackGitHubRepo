package model;

import java.util.ArrayList;
import model.BasicCard.Rank;

/**
 * this class represents the player hand and implements the BasicHand interface.
 */
public class PlayerHand implements BasicHand {
	//the cards in the hand of the player
	private ArrayList<Card> hand;

	/**
	 * default ctor.
	 */
	public PlayerHand()
	{
		hand = new ArrayList<Card>();
	}
	
	/**
	 * this function calculate the hand value of the player.
	 * the calculation logic is: add each card value as is except Ace,
	 * in case of Ace the first one values 11 all the others after it value 1 in ANY case.
	 */
	@Override
	public int handValue() {
		short value = 0;
		boolean hasAce = false;
		for(Card c : hand)
		{
			if(c.getRank().equals(Rank.ACE))
			{
				if(hasAce){
					value+=1;
				}
				else{
					value+=c.getValue();
					hasAce=true;
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
		//System.out.println("hand Value is:"+handValue());
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
		hand.clear();
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see model.BasicHand#AddCard(model.Card)
	 */
	@Override
	public void AddCard(Card c) {
		if(!c.faceUp()){ //cards are always faced up to the player.
			c.flip();
			hand.add(c);
		}else{
			hand.add(c);
		}
	}
	
	public Card getLastCard(){
		return this.hand.get(hand.size()-1);
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
