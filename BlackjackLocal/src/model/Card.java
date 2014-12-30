package model;


/**
 * the Card class for blackjack game
 * implements the interface BasicCard.
 */
public class Card  implements BasicCard 
{  
	     private final Rank rank; //when a card is created it can't change rank or suit
	     private final Suit suit;
	     private boolean faceup;// defines if the cards is faceup or down.
	     private String loc;// the location string of the specific card location.
//	     private FlippableCard fc;
	   /**
	    * this ctor sets the rank and suit of the card and in accordance gets the coorect string.
	    * @param rank
	    * @param suit
	    */
	   public Card (final Rank rank, final Suit suit) {
		   //super(CardImageLoader.getImageLocation(suit, rank), "/view/fxml/img/CardBack.png");
		   this.rank = rank;
	       this.suit = suit;
	       faceup=false; // default is face down
	       loc=CardImageLoader.getImageLocation(this.suit, this.rank);
//	       this.fc = FlippableCard.createInstance(loc, "/view/fxml/img/CardBack.png");
	   }
	   
/*	   public void flipAnim()
	   {
		   fc.flip();
	   }
	   
	*/   
	   /**
	    * returns the card rank.
	    */
	   public Rank getRank()
	   {
		   return rank;
	   }
	   /**
	    * returns the card suit
	    */
	   public Suit getSuit()
	   {
		   return suit;
				   
	   }
	   
	   /**
	    * for UI use.
	    * @return the location of the correct picture to show for the card.(card back or front);
	    */
	   public String getImageLocation()
	   {
		   if(faceup)
			   return this.loc;
		   else return "/view/fxml/img/CardBack.png"; //card back
	   }
	   /**
	    * 
	    * @return the card state.
	    */
	   public boolean faceUp()
	   {
		   return faceup;
	   }
	   /**
	    * flip the card from faceup to down and viceversa 
	    */
	   public void flip(){
		   this.faceup= !faceup;
	   }
	   
	   /**
	    * calculate the value of the specific card in accordance to blackjack rules
	    * only ace has special treatment in the Hand(player/dealer) function, it's default is 11
	    */
	   public int getValue()
	   {
		   switch (rank) {
	           case DEUCE:   return 2;
	           case THREE:   return 3;
	           case FOUR:   return 4;
	           case FIVE:   return 5;
	           case SIX:   return 6;
	           case SEVEN:   return 7;
	           case EIGHT:   return 8;
	           case NINE:   return 9;
	           case TEN:   return 10;
	           case JACK:  return 10;
	           case QUEEN:  return 10;
	           case KING:  return 10;
	           case ACE:  return 11;
	           default: return 0;
		   }
	   }
	     
	   @Override
	    public String toString () {
		   if(!faceup)
			   return String.format ("[covered Card]");
		   else
			   return String.format ("%s of %s", rank, suit);
	  }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (rank != other.rank)
			return false;
		if (suit != other.suit)
			return false;
		return true;
	}

	
	   
}