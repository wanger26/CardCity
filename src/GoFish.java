import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
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

/**
 * This replicates the card game of Go Fish
 * @author Jakob Wanger
 *
 */
public class GoFish implements ActionListener {
	
	private JFrame frame;  //frame for the game
	private JPanel userPanel, AIPanel, playingPanel, instructionsPanel; //panels for the game
	private LinkedList <Card> userCards= new LinkedList<Card>(); //holds the cards in the user hands
	private LinkedList <Card>pickedValues= new LinkedList<Card>(); //holds the values of which the user picked
	private LinkedList <Card>aiCards= new LinkedList<Card>(); //holds the card of the ai
	private JButton pickUp, goFish;  
	private JLabel communication, tempCard, userScore, aiScore; 
	private DeckOfCards deck; 
	private Stack <Card>lastCards= new Stack <Card>(); 
	private PlayingStackofCards stack; 
	private boolean userTurn=true, aiConseq=false; 
	private int askedNumber, userMatchTotal=0, aiMatchTotal=0, matchCount=0; 
	private Font instructionsFont= new Font ("Arial Black", Font.BOLD, 60);

	
	public GoFish() {
	
		//Create the window "frame"
       frame = new JFrame ("Card City"); //Setting Frame Title
       frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
       BackImage b= new BackImage();
       frame.add(b);
       
       userPanel = new JPanel ();
       userPanel.setLayout(new FlowLayout());


       AIPanel = new JPanel ();

       playingPanel = new JPanel ();
       playingPanel.setBackground (Color.GREEN);

       instructionsPanel = new JPanel ();
       instructionsPanel.setBackground (Color.GREEN);
       instructionsPanel.setLayout(new GridLayout(5,1));
       
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
       
       Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
       userPanel.setPreferredSize(new Dimension((int) screenSize.getWidth(), 900));

		
       stack= new PlayingStackofCards (fillDeck()); 
    
       pickUp= new JButton (new ImageIcon ("C:/Users/Owner/OneDrive/CardCity/CardCity/src/pickUp.png"));
       pickUp.addActionListener(this);
       
       goFish= new JButton("Go Fish!"); 
       goFish.setFont(instructionsFont);
       goFish.addActionListener(this);
       
       communication= new JLabel ("Please Pick what to Fish for:"); 
       communication.setFont(instructionsFont);
       
       userScore= new JLabel ("Users Match Count: "+userMatchTotal); 
       userScore.setFont(instructionsFont);
       aiScore= new JLabel ("AI's Match Count: "+aiMatchTotal); 
       aiScore.setFont(instructionsFont);

       instructionsPanel.add(userScore); 
       instructionsPanel.add(aiScore); 
       instructionsPanel.add(communication); 
       
       
       instructionsPanel.revalidate();
       pickUpPanel.add(pickUp);
       pickUpPanel.revalidate();
       
       // Giving user and ai its starting cards
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
		gameOver(); 
		if (e.getSource()==pickUp) {
			addCard(true, stack.pickUp());
			lastCards.clear();
			AI(); 
		}
		else if (e.getSource()==goFish) {
			instructionsPanel.remove(goFish);
			instructionsPanel.remove(tempCard);
			instructionsPanel.revalidate();
			instructionsPanel.repaint();
			
			userTurn=true; 
			if (stack.cardsLeft()>0) {
				Card temp= stack.pickUp();
				aiCards.add(temp); 
				addCard(false, temp); 
			}
			else {
				pickUp.setEnabled(false);
				AI(); 
			}
			userButtonControl(true, 3); 
			
			for (int x=0; x<pickedValues.size();x++)
				pickedValues.get(x).getCardButton().setEnabled(true);
			for (int x=0; x<userCards.size(); x++)
				userCards.get(x).getCardButton().setEnabled(true);
			
			communication.setText("AI go fish! Your Turn:");
		}
		else if (userTurn) {
			userButtonControl(true,2);
			userTurn=false;
			
			
			for (int x=0; x< userCards.size(); x++) {
				 if (e.getSource()==userCards.get(x).getCardButton()) {
					 userTurn=true; 
					if (lastCards.isEmpty()) {
						lastCards.push(userCards.get(x)); 
						userCards.get(x).getCardButton().setEnabled(false);
					}
					else if (lastCards.peek().getValue()==userCards.get(x).getValue()) {
						lastCards.push(userCards.get(x)); 
						userCards.get(x).getCardButton().setEnabled(false);
					}
					else {
						for (int y=0; y< userCards.size(); y++) userCards.get(y).getCardButton().setEnabled(true);
						lastCards.clear();
					}
					
					if (lastCards.size()==4) {
							while (!lastCards.isEmpty()) {
							removeCard(true, lastCards.peek()); 
							userCards.remove(lastCards.pop());
						}
						userMatchTotal++; 
						userScore.setText("Users Match Count: "+userMatchTotal);
					}
				}
			}
			
			for (int x=0; x<pickedValues.size();x++) {
				if (e.getSource()==pickedValues.get(x).getCardButton()) {
					askedNumber= pickedValues.get(x).getValue(); 
				}
			}
			
			for (int x=0; x<aiCards.size();x++) {				
				if (aiCards.get(x).getValue()==askedNumber) {
					addCard(true, aiCards.get(x)); 
					removeCard(false, aiCards.get(x));
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
			for (int x=0; x<userCards.size();x++) {
				if (e.getSource()==userCards.get(x).getCardButton()) {
					addCard(false, userCards.get(x)); 
					removeCard(true, userCards.get(x)); 
					userTurn=false; 
					instructionsPanel.remove(tempCard);
					instructionsPanel.revalidate();
					instructionsPanel.repaint();
					aiConseq=true; 
					
					if (matchCount==1) {
						matchCount=0; 
						lastCards.clear();
						AI();
					}
					else {
						matchCount--;

					}


				}
			}
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
			newCard.setInPlay(true);
			userCards.add(0, newCard);
			userPanel.add(newCard.getCardButton());
			newCard.getCardButton().addActionListener(this);
			userPanel.revalidate();
			userPanel.repaint();
		}
		else {
			newCard.setInPlay(false);
			aiCards.add(0, newCard);
			AIPanel.add(newCard.getCardLabel());
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
			userCards.remove(card); 
			userPanel.revalidate();
			userPanel.repaint();
		}
		else {
			AIPanel.remove(card.getCardLabel());
			aiCards.remove(card); 
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
			for (int x=0; x<userCards.size(); x++)
				userCards.get(x).getCardButton().setEnabled(false);
		}
		else if (user && (processCode==1 || processCode==3)) {
			for (int x=0; x<userCards.size(); x++)
				userCards.get(x).getCardButton().setEnabled(true);
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
		int avaliable=0; 
		userButtonControl(true, 1);
		pickUp.setEnabled(false);
		int [] matches= new int [13]; 
		//First Check to see if AI has any matches
		for (int x=0;x<aiCards.size();x++) {
			matches[aiCards.get(x).getValue()-1]=matches[aiCards.get(x).getValue()-1]+1; 
		}
		
		for (int x=0; x<matches.length;x++) {
			if (matches[x]==4) {
				for (int y=0; y<aiCards.size();y++) {
					if (aiCards.get(y).getValue()==(x+1)) {
						removeCard(false, aiCards.get(y)); 
						matches[x]=matches[x]-1; 
						}
					}
				aiMatchTotal++; 
				aiScore.setText("AI's Match Count: "+aiMatchTotal);
				matches[x]=-1; 
			}
				
			}
		
		int aiPickedNumber=0; 
		
		Random rand = new Random();
		int  n = rand.nextInt(2) + 1;
		
		if (aiConseq) {
			n=1; 
		}
		
		boolean aiTurn=false; 
		if (n!=1) {
			int max; 
			max=matches[0];
			aiPickedNumber=1; 
			for (int x=1; x<matches.length;x++) {
				if (matches[x]>max) {
					max=matches[x]; 
					aiPickedNumber=x+1; 
				}
			}
		}
		else {
			boolean done=true; 
			do {
				aiPickedNumber=rand.nextInt(12)+1; 
				for (int x=0;x<matches.length;x++) {
					if (matches[(aiPickedNumber-1)]==-1) {
						done=false;
					}
					else {
						done=true; 
					}
				}
			}while (!done);
		}
		
		
		communication.setText("The AI is fishing for:");
		
		tempCard= new JLabel (new ImageIcon ("C:\\Users\\Owner\\OneDrive\\CardCity\\CardCity\\src\\"+aiPickedNumber+".png")); 
		instructionsPanel.add(tempCard); 
		instructionsPanel.repaint();
		instructionsPanel.revalidate();
		
		
		
		for (int x=0; x<userCards.size();x++) {
			if (userCards.get(x).getValue() == aiPickedNumber) {
				aiTurn=true; 
				matchCount++; 
			}
		}		
		
		for (int x=0; x<userCards.size();x++) {
			if (userCards.get(x).getValue() != aiPickedNumber) {
				userCards.get(x).getCardButton().setEnabled(false);
			}
		}	
		
		if (!aiTurn) {
			instructionsPanel.add(goFish);
			instructionsPanel.revalidate();
			instructionsPanel.repaint();
		}
		else {
			userTurn=false; 
		}
		aiConseq=false; 
		gameOver(); 

		
	}
	
	
}
