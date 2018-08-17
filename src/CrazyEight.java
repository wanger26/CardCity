import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This replicates the card game of crazy 8's
 * @author Jakob Wanger
 *
 */
public class CrazyEight extends JPanel implements ActionListener {
	
	private JFrame frame;  //frame for the game
	private JPanel userPanel, AIPanel, playingPanel, instructionsPanel; //panels for the game
	private Card layingCard; //the card which is currently the one in play
	private LinkedList <Card>userCards= new LinkedList<Card>(); //the cards the user has in-hand
	private LinkedList <Card>aiCards= new LinkedList<Card>(); //the cards the ai has in-hand
	private JButton pickUp, spades, hearts, diamonds, clubs; //the buttons used for the user to pick suit 
	private JLabel instructions; //instructions for the user
	private DeckOfCards deck; 
	private PlayingStackofCards stack; 
	private char pickedFace; 
	private int AIcardNum=0, userCardNum=0; 
	private Font instructionsFont= new Font ("Arial Black", Font.BOLD, 60); 

	
	public CrazyEight () {
		spades= new JButton (new ImageIcon ("C:\\Users\\Owner\\OneDrive\\CardCity\\CardCity\\src\\spades.png")); 
		clubs= new JButton (new ImageIcon ("C:\\Users\\Owner\\OneDrive\\CardCity\\CardCity\\src\\clubs.png")); 
		diamonds= new JButton (new ImageIcon ("C:\\Users\\Owner\\OneDrive\\CardCity\\CardCity\\src\\diamonds.png")); 
		hearts= new JButton (new ImageIcon ("C:\\Users\\Owner\\OneDrive\\CardCity\\CardCity\\src\\hearts.png")); 

		spades.addActionListener(this);
		clubs.addActionListener(this);
		diamonds.addActionListener(this);
		hearts.addActionListener(this);
	
		//Create the window "frame"
       frame = new JFrame ("Card City"); //Setting Frame Title
       frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
       BackImage b= new BackImage();
       frame.add(b);
       
       
       //Initializing JPanels****************************************************************************************
       userPanel = new JPanel ();

       AIPanel = new JPanel ();

       playingPanel = new JPanel ();
       playingPanel.setBackground (Color.GREEN);

       instructionsPanel = new JPanel ();
       instructionsPanel.setBackground (Color.GREEN);
       
       JPanel pickUpPanel= new JPanel(); 
       pickUpPanel.setBackground(Color.GREEN); 
    
       
       // add panel to frame
       Container contentPane = frame.getContentPane ();
       contentPane.add (AIPanel, BorderLayout.NORTH);
       contentPane.add (instructionsPanel, BorderLayout.WEST);
       contentPane.add (playingPanel, BorderLayout.CENTER);
       contentPane.add(pickUpPanel, BorderLayout.EAST);  
       contentPane.add (userPanel, BorderLayout.SOUTH);
       
       //size the window.
	    frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
       frame.setVisible (true);
		
       stack= new PlayingStackofCards (fillDeck()); 
       deck.clearDeck();
      
       //Making sure to not start game with 8
       do {
    	   layingCard=stack.pickUp(); 
       }while (layingCard.getValue()==8); 
       
       //If starting card is a 2 or Queen of spades give user the penalty as the starter of the game
       layingCard.setInPlay(true);
       if (layingCard.getValue()==2) {
    	   addCard(stack.pickUp(),true); 
    	   addCard(stack.pickUp(),true); 
       }
       else if (layingCard.getFace()=='S'&& layingCard.getValue()==12) {
   			for (int x=0; x<4; x++) addCard(stack.pickUp(), false);      
       }
       
       playingPanel.add(layingCard.getCardButton());
       pickUp= new JButton (new ImageIcon ("C:/Users/Owner/OneDrive/CardCity/CardCity/src/pickUp.png"));
       pickUp.addActionListener(this);
       
       instructions= new JLabel ("Your Turn"); 
       instructions.setFont(instructionsFont);
       instructionsPanel.add(instructions); 
       
       instructionsPanel.revalidate();
       pickUpPanel.add(pickUp);
       pickUpPanel.revalidate();
       
       for (int card=0; card<5; card++) {
			addCard(stack.pickUp(), true); 
		}
		for (int card=0; card<5; card++) {
			addCard(stack.pickUp(), false); 
		}
		
		
	}
	
	/**
	 * This will be where the if a button is pressed, the action listiner will make the program respond accordingly 
	 */
	public void actionPerformed(ActionEvent e) {
		boolean complete=false;
		boolean eight=false; 
		boolean pickedUp=false; 
		
		checkStack(); //Checking if the pick up stack is empty
		gameOver(); //Checking if the game is over
		if (stack.cardsLeft()<5)stack= new PlayingStackofCards(deck); //If there are less then 5 cards on the stack reshuffel 
		if (e.getSource()==pickUp) { //If user hit pick up button pick up a card from the stack accordingly
			addCard(stack.pickUp(), true);
			eight=true; 
			complete=true; //Operational Matter to make sure we let the ai play its turn
			pickedUp=true; 
		}
		else if (e.getSource()==spades || e.getSource()==clubs || e.getSource()==diamonds || e.getSource()==hearts) { //If the face selection buttons where up check to see whats to be player
			for (int x=0; x<userCards.size();x++) 
				userCards.get(x).getCardButton().setEnabled(true);
			pickUp.setEnabled(true);
			if (e.getSource()==spades) pickedFace= 'S';
			else if (e.getSource()==clubs)pickedFace='C'; 
			else if (e.getSource()==diamonds)pickedFace='D';
			else if (e.getSource()==hearts)pickedFace='H';
			playingPanel.removeAll();
			playingPanel.repaint();
			playingPanel.revalidate();
			eight=true; 
			complete=true; 
		}
		else { // Check what card got clicked on and see if it matches the card thats in play right now
			for (int x=0; x<userCards.size(); x++) {
				if (e.getSource() == userCards.get(x).getCardButton()) {
					if (userCards.get(x).getFace()==layingCard.getFace() || userCards.get(x).getValue()==layingCard.getValue() || userCards.get(x).getValue()==8 || layingCard.getValue()==8) {
						removeCard (true, userCards.get(x));
						
						if (layingCard.getValue()==4 || layingCard.getValue()==11) { //If we skip the AI's turn 
							for (int j=0; j<userCards.size();j++) 
								userCards.get(j).getCardButton().setEnabled(true);
							pickUp.setEnabled(true);
							complete=false;
						}
						else complete=true; 
						}
					}
				}
			}
		if (layingCard.getValue()==8 && !eight)pickSuit(); //If user selected a 8 call on the class to let the user decide of what suit is to be played
		else if (complete) {
			instructions.setText("AI's Turn"); 
			instructionsPanel.revalidate();
			instructionsPanel.repaint();
			AI(pickedUp); //Call on thge AI Class
		}
	}	
	
	//Helper Methods
	
	/***
	 * This will fill the deck of cards by making a standard 52 card deck with visual representations 
	 * @return A 52 Standard Deck of Cards
	 */
	private DeckOfCards fillDeck() {		
		deck= new DeckOfCards(); 
		char suit; 
		for (int suitNumber=1; suitNumber<=4; suitNumber++)
		{	
			if (suitNumber==1) suit='S'; else if (suitNumber==2) suit='H'; else if (suitNumber==3) suit='D'; else suit='C'; 
			for (int number=1; number<=13; number++) {
				String fileName="card"+number+suitNumber+".png";
					deck.addCard(new Card (suit, number, fileName, "back.png"));
			}

		}
		return deck;
	}
	
	/**
	 * Used to add cards to panel and label them in play for both user and AI
	 * @param newCard the new card which is to be added
	 * @param user is the card is to be added to the user (true) or the AI (false)
	 */
	private void addCard(Card newCard, boolean user) {
		if (user) {
			userCardNum++; 
			newCard.setInPlay(true);
			userCards.add(0, newCard);
			userPanel.add(newCard.getCardButton());
			newCard.getCardButton().addActionListener(this);
			userPanel.revalidate();
		}
		else {
			AIcardNum++; 
			newCard.setInPlay(false);
			aiCards.add(0, newCard);
			AIPanel.add(newCard.getCardLabel());
			AIPanel.revalidate();
		}
	}
	
	/**
	 * Used to disable everything to make user pick a suit to play
	 */
	private void pickSuit() {
		playingPanel.removeAll();
		for (int x=0; x<userCards.size();x++) userCards.get(x).getCardButton().setEnabled(false);
		pickUp.setEnabled(false);		
		instructions.setText("Pick what suit you want to play:");
		playingPanel.add(clubs);
		playingPanel.add(diamonds);
		playingPanel.add(hearts);
		playingPanel.add(spades); 
		
		instructionsPanel.revalidate();
		instructionsPanel.repaint();
		playingPanel.revalidate();
		playingPanel.repaint();
		
	}
	
	/**
	 * This will remove a certain card from the appropriate panel
	 * @param user if it is to be removed from the user (true) or the AI (false)
	 * @param card is the card which is to be removed from the panel; 
	 */
	private void removeCard(boolean user, Card card) {
		
		deck.addCard(layingCard);
		checkStack();
		playingPanel.remove(layingCard.getCardButton());
		layingCard= card;
		layingCard.setInPlay(false);
		playingPanel.add(layingCard.getCardButton());

		if (user){
			userCardNum--; 
			userCards.remove(card); 
			userPanel.remove(layingCard.getCardButton());
			userPanel.revalidate();
			userPanel.repaint();
		}
		else {
			AIcardNum--;
			aiCards.remove(card); 
			AIPanel.remove(layingCard.getCardLabel());
			AIPanel.revalidate();
			AIPanel.repaint();
		}
		playingPanel.revalidate();

	}
	
	/**
	 * Checks the playing stack to make sure it is not empty. If it is, it will cards which are no longer in play
	 */
	private void checkStack() {
		if (stack.cardsLeft()<8) {
			for (int x=0; x<stack.cardsLeft();x++) deck.addCard(stack.pickUp());
			stack=new PlayingStackofCards(deck); 
			System.out.println(stack.cardsLeft());
			deck.clearDeck();
		}
	}
	
	/**
	 * Checks if the game is over (i.e. no cards left), if it is over will exit game and bring the user to a new screen
	 */
	private void gameOver() {
		if (userCardNum==0) {
			frame.setVisible(false);
			GameOver over= new GameOver (true,'C'); 
		}
		else if (AIcardNum==0) {
			frame.setVisible(false);
			GameOver over= new GameOver (false,'C'); 
		}
	}
	
	
	//AI
	/**
	 * This acts as the 'AI' Artificial Opponent for the user
	 */
	private void AI(boolean userPickedUp) {
		checkStack(); //Checking Stack to make sure it is no empty
		gameOver(); //Checking to see if the game is over
		
		if (stack.cardsLeft()<5)stack= new PlayingStackofCards(deck); //If the playing stack has less then 5 cards refill it with cards no longer in play
		for (int x=0; x<userCards.size();x++) userCards.get(x).getCardButton().setEnabled(true); 
		
		boolean pickUp= true;
		if (layingCard.getValue()==2 && !userPickedUp) { //If the user put down a 2, let the AI pick up 2 cards
			addCard(stack.pickUp(), false);
			addCard(stack.pickUp(), false);
			if (deck.getLastCard().getFace()=='S'&& deck.getLastCard().getValue()==12) { //If that 2 was putt over a Queen of Spades make the AI pick up 7 cards
				for (int x=0; x<4; x++) addCard(stack.pickUp(), false);
			}
			if (deck.getLastCard().getValue()==2) //If the 2 was put over top of another 2 make the AI pick up 4
			{
				addCard(stack.pickUp(), false);
				addCard(stack.pickUp(), false);
				try {
					if (deck.getCard(deck.getSize()-2).getValue()==2) //If the last 3 cards have been 2's make the AI pick up 6 cards
					{
						addCard(stack.pickUp(), false);
						addCard(stack.pickUp(), false);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					if (deck.getCard(deck.getSize()-3).getValue()==2) //If the last 4 cards have been 2's make the AI pick up 8 cards
					{
						addCard(stack.pickUp(), false);
						addCard(stack.pickUp(), false);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
		}
		if (layingCard.getValue()==12 && layingCard.getFace()=='S' && !userPickedUp) { //If the last card placed was a Queen of Spades make the AI pick up 4 cards
			for (int x=0; x<4; x++) addCard(stack.pickUp(), false);
		}
		AIPanel.revalidate();
		AIPanel.repaint();
		
		for (int x=0; x<aiCards.size(); x++) {
			if (layingCard.getValue()==8) { //If the user put down a 8, check and play according to the users Picked Suit
				if (aiCards.get(x).getFace()==pickedFace) {
					removeCard(false, aiCards.get(x));
					pickUp=false; 
					break;
				}
			}
			else {
				if (aiCards.get(x).getFace()==layingCard.getFace() || aiCards.get(x).getValue()==layingCard.getValue() || aiCards.get(x).getValue()==8) { //If AI has a card that can be played, play it and remove it
					removeCard(false, aiCards.get(x));
					pickUp=false; 
					break;
					}
				}
			}
		if (pickUp) { //If all of the above did not execute, make the AI pick up a card
			addCard(stack.pickUp(), false);
		}
		if (layingCard.getValue()==2 && !pickUp) { //If the ai put down a 2, make the user pick up 2
			if (deck.getLastCard().getFace()=='S'&& deck.getLastCard().getValue()==12 && !pickUp) { //If the ai put a 2 on top of a queen of spades make user pick up 7
				for (int x=0; x<4; x++) addCard(stack.pickUp(), true);
			}
			if (deck.getLastCard().getValue()==2) //If the ai put down a 2 on top of a 2, make the user pick up 4
			{
				addCard(stack.pickUp(), true);
				addCard(stack.pickUp(), true);
				try {
					if (deck.getCard(deck.getSize()-2).getValue()==2)//If the ai put down a 2 after the previous 2 cards where played, make the user pick up 6
					{
						addCard(stack.pickUp(), true); 
						addCard(stack.pickUp(), true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					if (deck.getCard(deck.getSize()-3).getValue()==2) //If the ai put down a 2 after the previous 3 cards where played, make the user pick up 8
					{
						addCard(stack.pickUp(), true);
						addCard(stack.pickUp(), true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			addCard(stack.pickUp(), true);
			addCard(stack.pickUp(), true);
		}
		if (layingCard.getValue()==12 && layingCard.getFace()=='S' && !pickUp) { //If AI put down a spades make the user pick up 
			for (int x=0; x<4; x++) addCard(stack.pickUp(), true);
		}
		if (layingCard.getValue()==4 || layingCard.getValue()==11) AI(false); //If a Jack or 4 was put down, skip the users turn
		if (layingCard.getValue()==8 && !pickUp) { //If the ai played a 8 let it pick the suit of which it has the most cards of 
			int [] counter= new int [4];  
			for (int x=0; x<aiCards.size(); x++) {
				char face= aiCards.get(x).getFace();
				if (face=='S') counter[0]++; 
				else if (face=='D') counter[1]++;
				else if (face=='H') counter[2]++;
				else counter[3]++; 
			}
			int max=0; 
			for (int x=0; x<3;x++) {
				if (counter[x]>counter[x+1]) max=x; 
			}
			if (max==0) pickedFace='S';
			else if (max==1) pickedFace='D';
			else if (max==2)pickedFace='H'; 
			
			else pickedFace='C'; 
			
			
			for (int x=0; x<userCards.size(); x++) {
				if (userCards.get(x).getFace()!=pickedFace) userCards.get(x).getCardButton().setEnabled(false); 
			}
		}
		
		
		instructions.setText("Your Turn");
		instructionsPanel.revalidate();
		instructionsPanel.repaint();
		userPanel.revalidate();
		userPanel.repaint();
		gameOver();

	}
	
}
