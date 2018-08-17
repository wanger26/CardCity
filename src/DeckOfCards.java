/**
 * This class will create a deck of cards implemented using an array
 * @author Jakob Wanger
 *
 */

public class DeckOfCards {
	private int size;
	private Card [] deck; 
	private final int FULL_DECK=52; 
	
	public DeckOfCards() {
		size=0; 
		deck = new Card[53]; 
	}

	//Getters
	/**
	 * This method returns the number of cards in the deck
	 * @return the number of cards in deck
	 */
	public int getSize() {
		return size; 
	}
	
	public Card getCard(int position)throws Exception{
		if (position<52) return deck [position]; 
		else throw new Exception ("Not in deck"); 
	}
	
	public Card getLastCard() {
		return deck[size-1]; 
	}
	
	//Functional/Behavioral Methods
	/**
	 * Adds a new card to the Deck of Cards
	 * @param newCard the new card to be added to the deck of cards
	 * @throws FullDeckException is thrown if the deck of cards is already full (52 Cards) 
	 */
	public void addCard (Card newCard)  {
		if (size==FULL_DECK) System.out.println("Deck of Cards is already full");
		else {
			deck[size]=newCard; 
			size++;
		}
	}
	
	/**
	 * Removes the last card in the deck
	 * @return the removed card
	 */
	public Card removeLast () {
		Card temp= deck[size-1]; 
		deck[size-1]= null; 
		size--;
		return temp; 
	}
	public Card removeAt (int position) {
		Card temp= deck[position];
		if (position!=this.size-1) {
			for (int index=position; index<this.size; index++) {
				deck[index]=deck[index+1]; 
			}
		}
		this.size--; 
		deck[size]=null; 
		return temp; 
	}
	
	/**
	 * Clear's the entire deck
	 */
	public void clearDeck() {
		for (int index=0; index<size; index++)
			deck[index]=null;
		size=0; 
	}
	
	/**
	 * Creates a string representation of the deck of the cards
	 * @return returns a string representation of the deck of cards
	 */
	public String toString() {
		String str="";
		for (int index=0; index<size;index++) {
			str=str+deck[index].toString()+"\n";
		}
		return str; 
	}
}
