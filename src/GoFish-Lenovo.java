import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

public class GoFish implements ActionListener {
	
	
	private JFrame frame;  
	private JPanel userPanel, AIPanel, playingPanel, instructionsPanel; 
	private LinkedList <Card> onScreen= new LinkedList<Card>();
	private LinkedList <Card>pickedValues= new LinkedList<Card>();
	private LinkedList <Card>AIScreen= new LinkedList<Card>();
	private JButton pickUp, goFish; 
	private JLabel communication, tempCard; 
	private DeckOfCards deck; 
	private Stack <Card>lastCards= new Stack <Card>(); 
	private PlayingStackofCards stack; 
	private boolean userTurn=true; 
	private int AIcardNum=0, userCardNum=0, askedNumber, userMatchTotal=0, aiMatchTotal=0; 
	private Card lastCard; 
	private Font instructionsFont= new Font ("Arial Black", Font.BOLD, 60);

	
	public GoFish() {
	
		//Create the window "frame"
       frame = new JFrame ("Card City"); //Setting Frame Title
       frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
       BackImage b= new BackImage();
       frame.add(b);
       
       userPanel = new JPanel ();
      // userPanel.setBackground (Color.white);

       AIPanel = new JPanel ();
       //AIPanel.setBackground (Color.white);

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
    
       pickUp= new JButton (new ImageIcon ("C:/Users/Owner/OneDrive/CardCity/CardCity/src/pickUp.png"));
       pickUp.addActionListener(this);
       
       goFish= new JButton("Go Fish!"); 
       goFish.setFont(instructionsFont);
       goFish.addActionListener(this);
       
       communication= new JLabel ("Please Pick what to Fish for:"); 
       communication.setFont(instructionsFont);
       instructionsPanel.add(communication); 
       
       instructionsPanel.revalidate();
       pickUpPanel.add(pickUp);
       pickUpPanel.revalidate();
       
       for (int card=0; card<7; card++) {
			addCard(true, stack.pickUp()); 
		}
		for (int card=0; card<7; card++) {
			addCard(false, stack.pickUp()); 
		}
		
		fillOptions(); 
		
		
		
	}
	
	/**
	 * This will be where the if a button is pressed, the action listener will make the program respond accordingly 
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==pickUp) {
			addCard(true, stack.pickUp());
			AI(); 
		}
		if (userTurn) {
			userButtonControl(true,2);
			userTurn=false;
			
			
			for (int x=0; x< onScreen.size(); x++) {
				 if (e.getSource()==onScreen.get(x).getCardButton()) {
					 userTurn=true; 
					if (lastCards.isEmpty()) {
						lastCards.push(onScreen.get(x)); 
					}
					else if (lastCards.peek().getValue()==onScreen.get(x).getValue()) {
						lastCards.push(onScreen.get(x)); 
					}
					else {
						lastCards.clear();
					}
					
					if (lastCards.size()==4) {
							while (!lastCards.isEmpty()) {
							removeCard(true, lastCards.peek()); 
							onScreen.remove(lastCards.pop());
						}
						userMatchTotal++; 
					}
				}
			}
			
			for (int x=0; x<pickedValues.size();x++) {
				if (e.getSource()==pickedValues.get(x).getCardButton()) {
					askedNumber= pickedValues.get(x).getValue(); 
				}
			}
			
			for (int x=0; x<AIScreen.size();x++) {				
				if (AIScreen.get(x).getValue()==askedNumber) {
					addCard(true, AIScreen.get(x)); 
					removeCard(false, AIScreen.get(x));
					userTurn=true; 
					x--; 
				}
			}
			
			if (!userTurn) {
				if (stack.cardsLeft()>0) {
				userButtonControl(false, 3);
				pickUp.setEnabled(true);
				}
				communication.setText("Don't have it. Go Fish!");

			}
		}
		else {
			System.out.println("Checking to see what card it is before calling AI");
			for (int x=0; x<onScreen.size();x++) {
				if (e.getSource()==onScreen.get(x).getCardButton()) {
					System.out.println("Calling on AI");
					addCard(false, onScreen.get(x)); 
					removeCard(true, onScreen.get(x)); 
					userTurn=false; 
					instructionsPanel.remove(tempCard);
					instructionsPanel.revalidate();
					instructionsPanel.repaint();
					System.out.println("Here");
					AI(); 


				}
			}
			if (e.getSource()==goFish) {
				instructionsPanel.remove(goFish);
				instructionsPanel.remove(tempCard);
				instructionsPanel.revalidate();
				instructionsPanel.repaint();
				
				userTurn=true; 
				if (stack.cardsLeft()>0) {
					Card temp= stack.pickUp();
					AIScreen.add(temp); 
					addCard(false, temp); 
				}
				userButtonControl(true, 3); 
				
				for (int x=0; x<pickedValues.size();x++)
					pickedValues.get(x).getCardButton().setEnabled(true);
				for (int x=0; x<onScreen.size(); x++)
					onScreen.get(x).getCardButton().setEnabled(true);
				
				communication.setText("AI go fish! Your Turn:");
			}
		}
		
		//if (!userTurn) {
		//	userButtonControl(false, 2); 
			//(); 
		//}
		
		
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
	
	private void fillOptions() {
		for (int x=1; x<=13; x++) {
			String fileName=x+".png";
			Card temp= new Card ('0', x,fileName,"back.png");
			temp.getCardButton().addActionListener(this);
			playingPanel.add(temp.getCardButton());
			pickedValues.add(temp); 
		}
		playingPanel.revalidate();
		playingPanel.repaint();
	}
	/**
	 * Used to add cards to panel and label them in play for both user and AI
	 * @param newCard the new card which is to be added
	 * @param user is the card is to be added to the user (true) or the AI (false)
	 */
	private void addCard(boolean user, Card newCard) {
		if (user) {
			userCardNum++; 
			newCard.setInPlay(true);
			onScreen.add(0, newCard);
			userPanel.add(newCard.getCardButton());
			newCard.getCardButton().addActionListener(this);
			userPanel.revalidate();
			userPanel.repaint();
		}
		else {
			AIcardNum++; 
			newCard.setInPlay(false);
			AIScreen.add(0, newCard);
			AIPanel.add(newCard.getCardButton());
			AIPanel.revalidate();
			AIPanel.repaint();
		}
	}
	
	
	/**
	 * This will remove a certain card from the appropriate panel
	 * @param user if it is to be removed from the user (true) or the AI (false)
	 * @param card is the card which is to be removed from the panel; 
	 */
	private void removeCard(boolean user, Card card) {
		if (user){
			userPanel.remove(card.getCardButton());
			onScreen.remove(card); 
			userPanel.revalidate();
			userPanel.repaint();
		}
		else {
			AIPanel.remove(card.getCardLabel());
			AIScreen.remove(card); 
			AIPanel.revalidate();
			AIPanel.repaint();
		}
		
	}
	
	/**
	 * This controls what buttons the user can click on
	 * @param user true if the user gets enabled buttons
	 * @param processCode 1 for just users cards, 2 for just option cards, 3 for both
	 */
	private void userButtonControl (boolean user, int processCode) {
		if (!user && (processCode==1 || processCode==3)) {
			for (int x=0; x<onScreen.size(); x++)
				onScreen.get(x).getCardButton().setEnabled(false);
		}
		else if (user && (processCode==1 || processCode==3)) {
			for (int x=0; x<onScreen.size(); x++)
				onScreen.get(x).getCardButton().setEnabled(true);
		}
		
		if (processCode==2 || processCode==3) {
			for (int x=0; x<pickedValues.size();x++)
				pickedValues.get(x).getCardButton().setEnabled(user);
		}

	}
	
	/**
	 * Checks if the game is over (i.e. no cards left), if it is over will exit game and bring the user to a new screen
	 */
	private void gameOver() {
		if ((userMatchTotal+aiMatchTotal)==12) {
				new GameOver (userMatchTotal>aiMatchTotal, 'G'); 
		}
	}
	
	
	//AI
	/**
	 * This acts as the 'AI' Artificial Opponent for the user
	 */
	private void AI() {
		userButtonControl(true, 1);
		pickUp.setEnabled(false);
		int [] matches= new int [13]; 
		//First Check to see if AI has any matches
		for (int x=0;x<AIScreen.size();x++) {
			matches[AIScreen.get(x).getValue()-1]=matches[AIScreen.get(x).getValue()-1]+1; 
		}
		
		for (int x=0; x<matches.length;x++) {
			if (matches[x]==4) {
				for (int y=0; y<AIScreen.size();y++) {
					if (AIScreen.get(y).getValue()==(x+1)) {
						removeCard(false, AIScreen.get(y)); 
						AIScreen.remove(y); 
						y=y-1; 
					}
				}
				matches[x]=-1; 
				aiMatchTotal++; 
			}
		}
		
		//
		int aiPickedNumber=0; 
		
		Random rand = new Random();
		int  n = rand.nextInt(25) + 1;
		
	
		
		boolean aiTurn=false; 
		if (n!=1) {
			int max; 
			
			max=matches[0];
			for (int x=1; x<AIScreen.size();x++) {
				if (matches[x]>max) {
					max=matches[x]; 
					aiPickedNumber=x+1; 
				}
			}
		}
		else {
			aiPickedNumber=rand.nextInt(AIScreen.size()-1); 
		}
		
		communication.setText("The AI is fishing for:");
		System.out.println(aiPickedNumber);
		
		tempCard= new JLabel (new ImageIcon ("C:\\Users\\Owner\\OneDrive\\CardCity\\CardCity\\src\\"+aiPickedNumber+".png")); 
		instructionsPanel.add(tempCard); 
		
		
		
		for (int x=0; x<onScreen.size();x++) {
			if (onScreen.get(x).getValue() == aiPickedNumber) {
				aiTurn=true; 
			}
		}		
		
		for (int x=0; x<onScreen.size();x++) {
			if (onScreen.get(x).getValue() != aiPickedNumber) {
				onScreen.get(x).getCardButton().setEnabled(false);
			}
		}	
		
		if (!aiTurn) {
			instructionsPanel.add(goFish);
			instructionsPanel.revalidate();
			instructionsPanel.repaint();
			
		}
		
	}
	
}
