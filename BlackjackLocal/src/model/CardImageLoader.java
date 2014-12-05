package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.BasicCard.Rank;
import model.BasicCard.Suit;

/**
 * this is a helper class to assign each card it's picture location string.
 */
public class CardImageLoader {
	
	
	private static final String imgExtention = ".png";
	/**
	 * 
	 * @param s card suit
	 * @param value card rank
	 * @return the function builds in accordance to rank and suit and known images locations the location string to the card.
	 */
	
	public static BufferedImage loadImage(Suit s, Rank value){
		BufferedImage img = null;
		String imageFile = getImageLocation(s, value);
		try {
			img = ImageIO.read(new File(imageFile));
		}
		catch (IOException e){
			System.out.println("Could not Find image... " + imageFile);
		}
		return img;
	}
	
	public static String getImageLocation(Suit s, Rank value){
		StringBuilder result = new StringBuilder("");
		String location = null;
		String valueChar = null;
			switch(s){
			case HEARTS:	location = "/view/fxml/img/HEART/h";
					break;
			case CLUBS: location ="/view/fxml/img/CLUB/c";
					break;
			case DIAMONDS: location = "/view/fxml/img/DIAMOND/d";
					break;
			case SPADES: location = "/view/fxml/img/SPADE/s";
					break;
			default: location= null;
			}
			
			switch (value) {
	        case DEUCE:   valueChar= "2";break;
	        case THREE:   valueChar="3";break;
	        case FOUR:   valueChar="4";break;
	        case FIVE:   valueChar="5";break;
	        case SIX:  	valueChar="6";break;
	        case SEVEN:   valueChar="7";break;
	        case EIGHT:   valueChar="8";break;
	        case NINE:   valueChar="9";break;
	        case TEN:   valueChar="10";break;
	        case JACK:  valueChar="j";break;
	        case QUEEN:  valueChar="q";break;
	        case KING:  valueChar="k";break;
	        case ACE:  valueChar="1";break;
	        default: valueChar="0";
		}
		
		
		result.append(location);
		result.append(valueChar);
		result.append(imgExtention);
		
		return result.toString();
		
	}
}
