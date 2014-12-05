package maze;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class mazeEditorPrint.
 * Implements the graphic interface for the maze editor.
 */
@SuppressWarnings("serial")
public class mazeEditorPrint extends JPanel{

	/** The grid size. */
	private static int gridSize;
	
	/** The walk image. */
	Image walk = new ImageIcon(this.getClass().getClassLoader().getResource("Resource/wood.jpg")).getImage();
	
	/** The wall image. */
	Image wall = new ImageIcon(this.getClass().getClassLoader().getResource("Resource/brick.jpg")).getImage();
	
	/** The door image. */
	Image door = new ImageIcon(this.getClass().getClassLoader().getResource("Resource/door.png")).getImage();
	
	/** The sword image. */
	Image sword = new ImageIcon(this.getClass().getClassLoader().getResource("Resource/sword.gif")).getImage();
	
	/** The hero image. */
	Image hero = new ImageIcon(this.getClass().getClassLoader().getResource("Resource/hero.gif")).getImage();
	
	/** The dragon image. */
	Image dragon = new ImageIcon(this.getClass().getClassLoader().getResource("Resource/dragon.gif")).getImage();
	
	/** The sleeping dragon image. */
	Image sleepingDragon = new ImageIcon(this.getClass().getClassLoader().getResource("Resource/sleeping.gif")).getImage();

	/**
	 * Instantiates a new maze editor print.
	 *
	 * @param dimension the dimension for the instance of the maze editor.
	 */
	public mazeEditorPrint(int dimension) {
		gridSize = dimension;
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		super.paint(g);

		for(int i=0;i<gridSize;i++)
		{
			for(int j=0;j<gridSize;j++)
			{
				if(manualGen.getInstance().getUserMaze()[i][j]=="walk")
					g.drawImage(walk, i*20,j*20,20,20,null);
				else if(manualGen.getInstance().getUserMaze()[i][j]=="wall")
					g.drawImage(wall, i*20,j*20,20,20,null);
				else if(manualGen.getInstance().getUserMaze()[i][j]=="hero")
				{
					g.drawImage(walk, i*20,j*20,20,20,null);
					g.drawImage(hero, i*20,j*20,20,20,null);
				}
				else if(manualGen.getInstance().getUserMaze()[i][j]=="dragon")
				{
					g.drawImage(walk, i*20,j*20,20,20,null);
					g.drawImage(dragon, i*20,j*20,20,20,null);
				}
				else if(manualGen.getInstance().getUserMaze()[i][j]=="sleepingDragon")
				{
					g.drawImage(walk, i*20,j*20,20,20,null);
					g.drawImage(sleepingDragon, i*20,j*20,20,20,null);
				}
				else if(manualGen.getInstance().getUserMaze()[i][j]=="sword")
				{
					g.drawImage(walk, i*20,j*20,20,20,null);
					g.drawImage(sword, i*20,j*20,20,20,null);
				}
				else if(manualGen.getInstance().getUserMaze()[i][j]=="door")
				{
					g.drawImage(walk, i*20,j*20,20,20,null);
					g.drawImage(door, i*20,j*20,20,20,null);
				}
			}
		}	
	}
}
