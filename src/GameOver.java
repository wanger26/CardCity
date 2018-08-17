import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * This will be the screen when any game comes to and end
 * @author Jakob Wanger
 *
 */
public class GameOver implements ActionListener {

	private JFrame frame;
	private JPanel winnerPanel; 
	private JPanel optionsPanel; 
	private JButton playAgain, mainMenu, exit; 
	private char game; 
	
	public GameOver(boolean user, char game) {
		Font titleFont= new Font ("Arial Black", Font.BOLD, 130); 

		this.game=game; 
		frame = new JFrame ("Card City"); //Setting Frame Title
	    frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	    frame.setSize (1920, 1200);
	    frame.setVisible(true);
	    frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
	 
	    
	    winnerPanel= new JPanel (); 
	    optionsPanel= new JPanel(); 
	    
	    playAgain= new JButton(" Play Again");
	    playAgain.setAlignmentY(Component.CENTER_ALIGNMENT);
	    playAgain.setAlignmentY(Component.CENTER_ALIGNMENT);
	    playAgain.addActionListener(this);
	    playAgain.setFont(titleFont);

	    mainMenu= new JButton ("Main Menu ");	    
	    mainMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
	    mainMenu.setAlignmentY(Component.CENTER_ALIGNMENT);
	    mainMenu.addActionListener(this);
	    mainMenu.setFont(titleFont);

	    exit= new JButton ("Exit"); 
	    exit.setAlignmentX(Component.CENTER_ALIGNMENT);
	    exit.setAlignmentY(Component.CENTER_ALIGNMENT);
	    exit.addActionListener(this);
	    exit.setFont(titleFont);
	    
	    
	    optionsPanel.setLayout(new GridLayout(1,3));
	    optionsPanel.add(playAgain);
	    optionsPanel.add(mainMenu);
	    optionsPanel.add(exit);
	    
	    if (user) {
	    	JLabel temp= new JLabel ("Game Over. Winner: You");
	    	temp.setFont(titleFont);
	    	winnerPanel.add(temp); 
	    }
	    else {
	    	JLabel temp= new JLabel ("Game Over. Winner: AI");
	    	temp.setFont(titleFont);
	    	winnerPanel.add(temp); 
	    }
		
	    	// add panel to frame
	       Container contentPane = frame.getContentPane ();
	       contentPane.add (winnerPanel, BorderLayout.NORTH);
	       contentPane.add (optionsPanel, BorderLayout.CENTER);
		frame.repaint();
		frame.revalidate();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==playAgain) {
			if (game=='C') {
				frame.setVisible(false);
				new CrazyEight (); 
			}
			else if (game=='G') {
				frame.setVisible(false);
				new GoFish (); 			
			}
			else {
				frame.setVisible(false);
				new War (); 			
			}
		}
		else if (e.getSource()==mainMenu)
		{
			frame.setVisible(false);
			new MainMenu (); 
		}
		else if(e.getSource()==exit) {
			frame.setVisible(false);
			System.exit(0);
		}
	
	}

	
}
