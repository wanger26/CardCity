import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This will be used for all instructions for all games. Each game has a web link for instructions using the users default browser
 * @author Jakob Wanger
 *
 */
public class Instructions implements ActionListener {
	private JFrame frame;
	private JPanel titlePanel; 
	private JPanel optionsPanel; 
	private JPanel basicInstructions; 
	private JButton crazy8, war, goFish, back, instructions ; 
	
	public Instructions() {
		Font titleFont= new Font ("Arial Black", Font.BOLD, 130); 
		Font subTitleFont= new Font ("Arial Black", Font.BOLD, 90); 

		frame = new JFrame ("Card City"); //Setting Frame Title
	    frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);
	    frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
	 
	    
	    titlePanel= new JPanel (); 
	    titlePanel.add(new JLabel(new ImageIcon ("C:/Users/Owner/OneDrive/CardCity/CardCity/src/carddance.gif")));
	    JLabel temp= new JLabel ("Card City");
    	temp.setFont(titleFont);
    	titlePanel.add(temp); 
    	
    	basicInstructions= new JPanel (); 
    	JLabel temp2= new JLabel("Please Select which game you would like instructions for:");
    	temp2.setFont(subTitleFont);
    	basicInstructions.add(temp2); 
    	
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
	    
	    back= new JButton ("Back"); 
	    back.setAlignmentX(Component.CENTER_ALIGNMENT);
	    back.setAlignmentY(Component.CENTER_ALIGNMENT);
	    back.addActionListener(this);
	    back.setFont(titleFont);
	    
	    
	    optionsPanel.setLayout(new GridLayout(5,1));
	    optionsPanel.add(crazy8);
	    optionsPanel.add(goFish);
	    optionsPanel.add(war);
	    optionsPanel.add(back);
	    
	  
		
	    // add panel to frame
	     Container contentPane = frame.getContentPane ();
	     contentPane.add (titlePanel, BorderLayout.NORTH);
	     contentPane.add(basicInstructions, BorderLayout.CENTER); 
	     contentPane.add (optionsPanel, BorderLayout.SOUTH);
	     frame.repaint();
	     frame.revalidate();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==crazy8) {
			try {
			    Desktop.getDesktop().browse(new URL("https://www.bicyclecards.com/how-to-play/crazy-eights/").toURI());
			} catch (Exception l) {}
		}
		else if (e.getSource()==goFish)
		{
			try {
		    Desktop.getDesktop().browse(new URL("https://www.bicyclecards.com/how-to-play/go-fish/").toURI());
			} catch (Exception l) {}
		}
		else if(e.getSource()==war) {
			try {
			    Desktop.getDesktop().browse(new URL("https://www.bicyclecards.com/how-to-play/war/").toURI());
				} catch (Exception l) {}
		}
		else if(e.getSource()==back) {
			frame.setVisible(false);
			new MainMenu(); 
		}
	
	}

	
}

