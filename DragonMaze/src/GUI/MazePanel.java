package GUI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import Game_Logic.*;
import maze.Labirynth;
import java.util.Hashtable;

// TODO: Auto-generated Javadoc
/**
 * The Class MazePanel.
 * Class extended of JPanel used to display graphically the game
 * Graphical equivalent of print function in CUI class
 */
@SuppressWarnings("serial")
public class MazePanel extends JPanel {

	/** Hashtable containing the images to be used in the graphical display . */
	private Hashtable<String,BufferedImage> images = new Hashtable<String,BufferedImage>();

	/**
	 * Instantiates a new maze panel.
	 */
	public MazePanel() 
	{
		try {
			LoadImages();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		super.paint(g);

		Boolean dragon_placed = false;		

		//method 1 by cell
		for(int i=0;i<Labirynth.getInstance().COL_SIZE;i++)
		{
			for (int j = 0; j < Labirynth.getInstance().ROW_SIZE; j++) 
			{
				for(int z = 0; z<Game_Loop.getInstance().enemies.length; z++)
				{
					if(Game_Loop.getInstance().enemies[z].getY() ==i && Game_Loop.getInstance().enemies[z].getX()==j && !Game_Loop.getInstance().enemies[z].isDead())
					{
						if(Game_Loop.getInstance().enemies[z].getSymbol() == 'd') //modo a dormir
						{
							g.drawImage(images.get("walkway"), j*20, i*20, 20, 20,null);
							g.drawImage(images.get("dragon_sleeping"), j*20, i*20, 20, 20,null);
						}
						else 
						{
							g.drawImage(images.get("walkway"), j*20, i*20, 20, 20,null);
							g.drawImage(images.get("dragon"), j*20, i*20, 20, 20,null);
						}
						dragon_placed = true;
						break; //dragon placement cycle
					}
					else {
						dragon_placed = false;
					}
				}
				if(!dragon_placed)
				{
					if(Game_Loop.getInstance().HERO.getY()==i && Game_Loop.getInstance().HERO.getX()==j && !Game_Loop.getInstance().HERO.isDead())
					{
						if(Game_Loop.getInstance().HERO.getSymbol()=='H')
						{
							g.drawImage(images.get("walkway"), j*20, i*20, 20, 20,null);
							g.drawImage(images.get("hero"), j*20, i*20, 20, 20,null);
						}
						else if(Game_Loop.getInstance().HERO.getSymbol()=='A')
						{
							g.drawImage(images.get("walkway"), j*20, i*20, 20, 20,null);
							g.drawImage(images.get("armed"), j*20, i*20, 20, 20,null);
						}
					}
					else if(Game_Loop.getInstance().SWORD.getY()==i &&Game_Loop.getInstance().SWORD.getX()==j && !Game_Loop.getInstance().SWORD.isUsed())
					{
						g.drawImage(images.get("walkway"), j*20, i*20, 20, 20,null);
						g.drawImage(images.get("sword"), j*20, i*20, 20, 20,null);
					}
					else if (i == Labirynth.getInstance().EXIT_Y && j == Labirynth.getInstance().EXIT_X)
					{
						g.drawImage(images.get("walkway"), j*20, i*20, 20, 20,null);
						g.drawImage(images.get("door"), j*20, i*20, 20, 20,null);
					}
					else if(Labirynth.getInstance().getGenerated_maze()[i][j].isFilled() && i==Game_Loop.getInstance().HERO.getEagle_y() && j==Game_Loop.getInstance().HERO.getEagle_x() && Game_Loop.getInstance().HERO.isEagle_alive())
					{
						g.drawImage(images.get("walkway"), j*20, i*20, 20, 20,null);
						g.drawImage(images.get("eagle"), j*20, i*20, 20, 20,null);	
					}
					else if (Labirynth.getInstance().getGenerated_maze()[i][j].isFilled())
						g.drawImage(images.get("wall"), j*20, i*20, 20, 20,null);	
					else if (i==Game_Loop.getInstance().HERO.getEagle_y() && j==Game_Loop.getInstance().HERO.getEagle_x() && Game_Loop.getInstance().HERO.isEagle_alive())
					{						
						g.drawImage(images.get("walkway"), j*20, i*20, 20, 20,null);
						g.drawImage(images.get("eagle"), j*20, i*20, 20, 20,null);
					}	
					else
						g.drawImage(images.get("walkway"), j*20, i*20, 20, 20,null);
				}
			}
		}
	}

	/**
	 * Load images.
	 * Function used to load images to be used from resource folder
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void LoadImages() throws IOException
	{
		images.put("dragon", ImageIO.read(getClass().getResource("/Resource/dragon.gif")));
		images.put("dragon_sleeping", ImageIO.read(getClass().getResource("/Resource/sleeping.gif")));
		images.put("hero", ImageIO.read(getClass().getResource("/Resource/hero.gif")));
		images.put("sword", ImageIO.read(getClass().getResource("/Resource/sword.gif")));
		images.put("eagle", ImageIO.read(getClass().getResource("/Resource/eagle.gif")));
		images.put("wall", ImageIO.read(getClass().getResource("/Resource/brick.jpg")));
		images.put("walkway", ImageIO.read(getClass().getResource("/Resource/wood.jpg")));
		images.put("door", ImageIO.read(getClass().getResource("/Resource/door.png")));
		images.put("armed", ImageIO.read(getClass().getResource("/Resource/armed.gif")));
	}
}

