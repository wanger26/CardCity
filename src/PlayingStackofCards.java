import java.util.*; 
/**
 * Represents a common 52 set of playing  cards
 * @author Jakob Wanger
 *
 */
public class PlayingStackofCards {
	private Stack<Card> deck= new Stack<Card> ();  //the deck of cards 
	private Card[] cards= new Card [52]; //the cards
	private int size; //the size of the deck
	
	/**
	 * Will create a common 52 set of playing cards
	 * @param deckofCards the deck of cards
	 */
	public PlayingStackofCards (DeckOfCards deckofCards) {
		int max=deckofCards.getSize(); 
		for (int index=0; index<max; index++) {
			try {
				cards[index]= deckofCards.getCard(index);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		
		for (int index=0; index<max; index++) {
			Random rand= new Random(); 
			int temp= rand.nextInt(deckofCards.getSize());
			deck.push(deckofCards.removeAt(temp));
			size++; 
		}
	}
	
	/**
	 * Will pick up the top of the stack of cards
	 * @return Card of the top of the stack
	 */
	public Card pickUp() {
		Card temp=deck.pop(); 
		temp.setInPlay(true);
		size--; 
		return temp; 
	}
	/**
	 * Will return the number of cards left in the deck of cards
	 * @return int nnumber of cards left in the stack
	 */
	public int cardsLeft() {
		return this.size; 
	}
	
	/**
	 * Shuffles the deck of cards in random order
	 */
	public void shuffle () {
		Card[] outOfPlay= new Card[52];
		int counter=0; 
		for (int index=0; index<cards.length; index++) {
			if (!cards[index].getInPlay()) {
				outOfPlay[index]= cards[index];
				counter++; 
			}
		}
		for (int index=0; index<counter; index++) {
			Random rand= new Random(); 
			int temp= rand.nextInt(counter);
			deck.push(outOfPlay[temp]);
			counter--; 
		}
	}
	
}
