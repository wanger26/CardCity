import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * This replicates the card game of War
 * @author Jakob Wanger
 *
 */
public class War extends JPanel implements ActionListener {
	
	private JFrame frame;  //Frame for the game
	private JPanel userPanel, AIPanel, playingPanel, instructionsPanel; //Panels for the game
	private JLabel instructions, playerScore, aiScore, yourCard, aiCard;  
	private DeckOfCards deck; 
	private int aiNumOfCards=26, userNumOfCards=26; 
	private ArrayList<Card> onPlayingSurface; 
	private JButton takeThem; 
	private MyQueue <Card> userCards;
	private MyQueue <Card> aiCards; 
	private Card userPlayedCard, aiPlayedCard; 
	private PlayingStackofCards stack; 
	private Font instructionsFont= new Font ("Arial Black", Font.BOLD, 60); 

	
	public War () {
		onPlayingSurface= new ArrayList<Card>(); 
		userCards= new MyQueue<Card>(); 
		aiCards= new MyQueue<Card>(); 

		instructions= new JLabel ();
		instructions.setFont(instructionsFont);

		playerScore= new JLabel(); 
		playerScore.setFont(instructionsFont);

		aiScore= new JLabel(); 
		aiScore.setFont(instructionsFont);
		
		yourCard= new JLabel("Your Card(s): "); 
		yourCard.setFont(instructionsFont);
		
		aiCard= new JLabel("     AI's Card(s): "); 
		aiCard.setFont(instructionsFont);

		
		takeThem= new JButton("AI take the Cards"); 
		takeThem.setFont(instructionsFont);
		
		
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
		instructionsPanel.setLayout(new GridLayout(4,1));
		instructionsPanel.add(playerScore); 
		instructionsPanel.add(aiScore); 
		instructionsPanel.add(instructions); 
		instructionsPanel.add(takeThem); 
		takeThem.setVisible(false);
		takeThem.addActionListener(this);
		
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
        ArrayList<Card> temp= new ArrayList<Card>();
        temp.add(stack.pickUp());
	    for (int x=0; x<stack.cardsLeft(); x++) {
	    	Random rand = new Random();
			int  n = rand.nextInt(temp.size()) + 0;
			temp.add(n, stack.pickUp());
			x--;
	    }
	    
	    // Randomly locating cards to user and ai 
	    for (int x=0;x<temp.size();x++) {
	    	if (x%2==0) {
	    		userCards.enqueue(temp.get(x));
	    	}
	    	else {
	    		aiCards.enqueue(temp.get(x));
	    	}
	    }
	    
	    playerScore.setText("Your Total Number of Cards: "+userCards.size());
		aiScore.setText("AI's Total Number of Cards: "+aiCards.size());
		
	    userPanel.add(userCards.peek().getBackofCardBTN());
	    userCards.peek().getBackofCardBTN().addActionListener(this);
		userPanel.revalidate();
	    userPanel.repaint();	    
	    
	    AIPanel.add(aiCards.peek().getCardLabel());
	    aiCards.peek().getBackofCardBTN().addActionListener(this);
	    AIPanel.revalidate();
	    AIPanel.repaint();
	}
	
	/**
	 * This will be where the if a button is pressed, the action listiner will make the program respond accordingly 
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==userCards.peek().getBackofCardBTN()){
			
			userPlayedCard= userCards.dequeue(); 
			aiPlayedCard= aiCards.dequeue(); 
			
			onPlayingSurface.add(userPlayedCard); 
			onPlayingSurface.add(aiPlayedCard); 
			
			userPlayedCard.getCardButton().addActionListener(this);
			aiPlayedCard.getCardButton().addActionListener(this);

			userPanel.removeAll();
			userPanel.add(userCards.peek().getBackofCardBTN());
			userCards.peek().getBackofCardBTN().addActionListener(this);
			userCards.peek().getBackofCardBTN().setEnabled(false);

			userPanel.revalidate();
			userPanel.repaint();
			
			playingPanel.add(yourCard); 
			playingPanel.add(userPlayedCard.getCardButton());
			playingPanel.add(aiCard); 
			playingPanel.add(aiPlayedCard.getCardButton());
			playingPanel.repaint();
			playingPanel.revalidate();
			
			if (userPlayedCard.getValue()>aiPlayedCard.getValue()) {
				instructions.setText("You Won the battle! Pick up your cards");
				aiNumOfCards--; 
				userNumOfCards++; 
				instructionsPanel.revalidate();
				instructionsPanel.repaint();
				userCards.enqueue(aiPlayedCard);
				userCards.enqueue(userPlayedCard); 
			}
			else if (userPlayedCard.getValue()<aiPlayedCard.getValue()) {
				instructions.setText("The AI Won the battle!");
				instructionsPanel.revalidate();
				instructionsPanel.repaint();
				
				aiNumOfCards++; 
				userNumOfCards--; 
				
				takeThem.setVisible(true);
				aiCards.enqueue(aiPlayedCard);
				aiCards.enqueue(userPlayedCard);
				
				for (int x=0;x<onPlayingSurface.size();x++) {
					onPlayingSurface.get(x).getCardButton().setEnabled(false);
				}
			}
			else {
				war(); 
			}
		}
		else {
			for (int x=0;x<onPlayingSurface.size();x++) {
				if (e.getSource()==onPlayingSurface.get(x).getCardButton()) {
					playingPanel.remove(onPlayingSurface.get(x).getCardButton());
					playingPanel.remove(yourCard);
					playingPanel.remove(aiCard);
					onPlayingSurface.remove(x); 					
					playingPanel.revalidate();
					playingPanel.repaint();
				}
			}
		}
		
		
		
		
		if (e.getSource()==takeThem) {
			for (int x=0;x<onPlayingSurface.size();x++) {
				onPlayingSurface.get(x).getCardButton().setEnabled(true);
			}
			playingPanel.removeAll();
			playingPanel.revalidate();
			playingPanel.repaint();
			userCards.peek().getBackofCardBTN().setEnabled(true);
			onPlayingSurface.clear();
			takeThem.setVisible(false);
			
		}
		playerScore.setText("Your Total Number of Cards: "+userCards.size());
		aiScore.setText("AI's Total Number of Cards: "+aiCards.size());


		if (onPlayingSurface.isEmpty()) {
			userCards.peek().getBackofCardBTN().setEnabled(true);
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
					deck.addCard(new Card (suit, number, fileName, "back3.png"));
			}

		}
		return deck;
	}
	
	
	
	/**
	 * Checks the playing stack to make sure it is not empty. If it is, it will cards which are no longer in play
	 */
	private void checkStack() {
		if (stack.cardsLeft()<8) {
			for (int x=0; x<stack.cardsLeft();x++) deck.addCard(stack.pickUp());
			stack=new PlayingStackofCards(deck); 
			deck.clearDeck();
		}
	}
	
	/**
	 * Checks if the game is over (i.e. no cards left), if it is over will exit game and bring the user to a new screen
	 */
	private void gameOver() {
		if (userCards.isEmpty()) {
			frame.setVisible(false);
			GameOver over= new GameOver (true,'C'); 
		}
		else if (aiCards.isEmpty()) {
			frame.setVisible(false);
			GameOver over= new GameOver (false,'C'); 
		}
	}
	
	
	private void war () {
		instructions.setText("War!");
		instructionsPanel.revalidate();
		instructionsPanel.repaint();
		
		int counter=2;
		Card temp;
		
		playingPanel.add(yourCard); 
		while (counter!=0 && !userCards.isEmpty()) {
			temp = userCards.dequeue(); 
			temp.getCardButton().addActionListener(this);
			onPlayingSurface.add(temp);  
			playingPanel.add(temp.getCardButton()); 
			if (counter==1) {
				userPlayedCard=temp; 
			}
			counter--; 
		}
		counter=2; 
		playingPanel.add(aiCard); 
		while (counter!=0 && !aiCards.isEmpty()) {
			temp = aiCards.dequeue(); 
			temp.getCardButton().addActionListener(this);
			onPlayingSurface.add(temp);  
			playingPanel.add(temp.getCardButton()); 
			if (counter==1) {
				aiPlayedCard=temp; 
			}
			counter--; 
		}
		
		playingPanel.revalidate();
		playingPanel.repaint();
		
		
		userPanel.removeAll();
		userPanel.add(userCards.peek().getBackofCardBTN());
		userCards.peek().getBackofCardBTN().addActionListener(this);
		userCards.peek().getBackofCardBTN().setEnabled(false);

		userPanel.revalidate();
		userPanel.repaint();
		
		if (userPlayedCard.getValue()>aiPlayedCard.getValue()) {
			instructions.setText("War! You Won the battle!");
			aiNumOfCards--; 
			userNumOfCards++; 
			instructionsPanel.revalidate();
			instructionsPanel.repaint();
			userCards.enqueue(aiPlayedCard);
			userCards.enqueue(userPlayedCard); 
		}
		else if (userPlayedCard.getValue()<aiPlayedCard.getValue()) {
			instructions.setText("War! The AI Won the battle!");
			instructionsPanel.revalidate();
			instructionsPanel.repaint();
			
			aiNumOfCards++; 
			userNumOfCards--; 
			
			takeThem.setVisible(true);
			aiCards.enqueue(aiPlayedCard);
			aiCards.enqueue(userPlayedCard);
			
			for (int x=0;x<onPlayingSurface.size();x++) {
				onPlayingSurface.get(x).getCardButton().setEnabled(false);
			}
		}
		else {
			war(); 
		}
		
	}
	
		
}
