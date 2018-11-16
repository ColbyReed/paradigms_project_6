import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javax.swing.JButton;
import java.awt.Color;

class View extends JPanel
{
	Model model;
	Mario mario;
	Sprite sprites;
	
	//static Image[] mario_images = null;
	static Image backdrop = null;
	static Image brickpic = null;
	
	
	View(Controller c, Model m)
	{
			model = m;

			try
			{
			backdrop =ImageIO.read(new File("backdrop.jpg"));
			}
			catch(Exception e)
			{
				e.printStackTrace(System.err);
				System.exit(1);
			}
			
			try
			{
			brickpic =ImageIO.read(new File("Brick_Block.png"));
			}
			catch(Exception e)
			{
				e.printStackTrace(System.err);
				System.exit(1);
			}
		}
	//}
	
	//Draws background and Boxes
		public void paintComponent(Graphics g)
	{
		
		//Clear Screen
 		g.setColor(new Color(60, 188, 253));
 		g.fillRect(0, 0, this.getWidth(), this.getHeight());
 		
 		//Draw backdrop image
 		g.drawImage(backdrop, -model.scrollPos / 2 - 125, 0, this.getWidth() * 2, this.getHeight(), null);
 		
 		//Draw ground
		g.setColor(new Color(15, 200, 65));
 		g.fillRect(0, 645, this.getWidth(), this.getHeight());

		//Draw Sprites
		g.setColor(new Color(0,0,0));
		for(int i = 0; i < model.sprites.size(); i++)
		{
			Sprite s = model.sprites.get(i);
			s.draw(g, model);
		}

	}
}
