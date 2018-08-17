import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * This class will represent and create a Card
 * @author Jakob
 *
 */

public class Card{

	private char face; // the face of the card: 'H'- Hearts, 'D'- Diamonds, 'S'- Spades, 'C'- clubs
	private int value; // the value of the card in a integer representation (A=1, 2=2, 3=3, ... Jack=11, Queen=12, King=13)
	private boolean inPlay; // if the card is in play
	private JButton cardButton; //A JButton for the front of the card
	private JButton backofCardButton; //A JButton for the back of the card
	private JLabel cardLabel; //A JLabel for the for the back of the card
	
	/**
	 * Will create a card object
	 * @param face char value for the card 'H'- Hearts, 'D'- Diamonds, 'S'- Spades, 'C'- clubs
	 * @param value int value of the card in a integer representation (A=1, 2=2, 3=3, ... Jack=11, Queen=12, King=13)
	 * @param frontLocation file name of the picture for the front of the card
	 * @param backLocation file name of the picture of the back of the card
	 */
	public Card (char face, int value, String frontLocation, String backLocation) {
		this.face=face;
		this.value=value; 
		this.inPlay=false; 
		
		this.cardButton= new JButton (new ImageIcon ("C:\\Users\\Owner\\OneDrive\\CardCity\\CardCity\\src\\"+frontLocation));
		this.backofCardButton= new JButton (new ImageIcon ("C:\\Users\\Owner\\OneDrive\\CardCity\\CardCity\\src\\"+backLocation));
		this.cardLabel= new JLabel (new ImageIcon ("C:\\Users\\Owner\\OneDrive\\CardCity\\CardCity\\src\\"+backLocation));
	}
	
	//Mutators----------------------------------------------------
	/**
	 * 
	 * @param inPlay boolean for new inPlay for card
	 */
	public void setInPlay(boolean inPlay) {
		this.inPlay=inPlay; 
	}
	/**
	 * 
	 * @param value int for new value for card
	 */
	public void setValue (int value) {
		this.value=value; 
	}
	/**
	 * 
	 * @param face char for new face of the card
	 */
	public void setFace(char face) {
		this.face=face;
	}
	
	//Accessors------------------------------------------------------------------------------------
	public boolean getInPlay () {
		return this.inPlay; 
	}
	public int getValue () {
		return this.value; 
	}
	public char getFace() {
		return this.face; 
	}
	public JButton getCardButton () {
		return this.cardButton; 
	}
	public JLabel getCardLabel () {
		return this.cardLabel; 
	}
	public JButton getBackofCardBTN() {
		return this.backofCardButton; 
	}
	
	
}
