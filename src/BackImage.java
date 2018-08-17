import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

public class BackImage extends Canvas{
	
	Image image; 
	public BackImage() {
		image=Toolkit.getDefaultToolkit().getImage("C:\\Users\\Owner\\Desktop\\background.jpg");
	}
	
	public void pain(Graphics g) {
		int width =getSize().width; 
		int height= getSize().height;
		
		Graphics2D g2= (Graphics2D)g;
		g2.drawImage(image, 0, 0, width, height,this);
	}
}
