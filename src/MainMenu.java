import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * This will be the main menu used to navigate exits, instructions and starting a game
 * @author Jakob Wanger
 *
 */
public class MainMenu implements ActionListener {
	private JFrame frame;
	private JPanel titlePanel; 
	private JPanel optionsPanel; 
	private JButton crazy8, war, goFish, exit, instructions ; 
	
	public MainMenu() {
		Font titleFont= new Font ("Arial Black", Font.BOLD, 130); 

		frame = new JFrame ("Card City"); //Setting Frame Title
	    frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);
	    frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
	 
	    
	    titlePanel= new JPanel (); 
	    titlePanel.add(new JLabel(new ImageIcon ("C:/Users/Owner/OneDrive/CardCity/CardCity/src/carddance.gif")));
	    
	    optionsPanel= new JPanel(); 
	    
	    crazy8= new JButton("Crazy 8's");
	    crazy8.setAlignmentY(Component.CENTER_ALIGNMENT);
	    crazy8.setAlignmentY(Component.CENTER_ALIGNMENT);
	    crazy8.addActionListener(this);
	    crazy8.setFont(titleFont);
	    
	    goFish= new JButton ("Go Fish");	    
	    goFish.setAlignmentX(Component.CENTER_ALIGNMENT);
	    goFish.setAlignmentY(Component.CENTER_ALIGNMENT);
	    goFish.addActionListener(this);
	    goFish.setFont(titleFont);

	    war= new JButton ("War");	    
	    war.setAlignmentX(Component.CENTER_ALIGNMENT);
	    war.setAlignmentY(Component.CENTER_ALIGNMENT);
	    war.addActionListener(this);
	    war.setFont(titleFont);
	    
	    instructions= new JButton ("Instructions");	    
	    instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
	    instructions.setAlignmentY(Component.CENTER_ALIGNMENT);
	    instructions.addActionListener(this);
	    instructions.setFont(titleFont);
	    
	    exit= new JButton ("Exit"); 
	    exit.setAlignmentX(Component.CENTER_ALIGNMENT);
	    exit.setAlignmentY(Component.CENTER_ALIGNMENT);
	    exit.addActionListener(this);
	    exit.setFont(titleFont);
	    
	    
	    optionsPanel.setLayout(new GridLayout(5,1));
	    optionsPanel.add(crazy8);
	    optionsPanel.add(goFish);
	    optionsPanel.add(war);
	    optionsPanel.add(instructions);
	    optionsPanel.add(exit);
	    
	    JLabel temp= new JLabel ("Card City");
    	temp.setFont(titleFont);
    	titlePanel.add(temp); 
		
	    // add panel to frame
	     Container contentPane = frame.getContentPane ();
	     contentPane.add (titlePanel, BorderLayout.NORTH);
	     contentPane.add (optionsPanel, BorderLayout.CENTER);
	     frame.repaint();
	     frame.revalidate();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==crazy8) {
			frame.setVisible(false);
			new CrazyEight (); 
		}
		else if (e.getSource()==goFish)
		{
			frame.setVisible(false);
			new GoFish(); 
		}
		else if(e.getSource()==war) {
			frame.setVisible(false);
			new War(); 
		}
		else if (e.getSource()==instructions) {
			frame.setVisible(false);
			new Instructions(); 
		}
		else if(e.getSource()==exit) {
			frame.setVisible(false);
			System.exit(0);
		}
	
	}

	
}
