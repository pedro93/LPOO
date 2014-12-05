package maze;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import GUI.ConfigPanel;
import GUI.Game_GUI;
import GUI.StartWindow;
import Game_Logic.Dragon;
import Game_Logic.Game_Elements;
import Game_Logic.Game_Loop;
import Game_Logic.Hero;
import Game_Logic.Sword;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Random;
import java.util.Vector;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class manualGen.
 * Gives the user a possibility to create a maze and implements the necessary methods in order to do so.
 */
@SuppressWarnings("serial")
public class manualGen extends JDialog {

	/** The object type. */
	private String objectType="wall";
	
	/** The sword object. */
	private Sword sword=null;
	
	/** The hero object. */
	private Hero hero=null;
	
	/** The dragon vector. */
	private Vector<Dragon> dragons= new Vector<Dragon>();
	
	/** The dragons placed. */
	private int dragonsPlaced = 0;
	
	/** The door position in x. */
	private int doorX=-1;
	
	/** The door position in y. */
	private int doorY=-1;
	
	/** The user defined maze. */
	private static String[][] userMaze = null;
	
	/** The size of the maze. */
	static int size = 0;

	/**
	 * Gets the sword.
	 *
	 * @return the sword
	 */
	public Sword getSword() {
		return sword;
	}

	/**
	 * Sets the sword.
	 *
	 * @param sword the new sword
	 */
	public void setSword(Sword sword) {
		this.sword = sword;
	}

	/**
	 * Gets the hero.
	 *
	 * @return the hero
	 */
	public Hero getHero() {
		return hero;
	}

	/**
	 * Sets the hero.
	 *
	 * @param hero the new hero
	 */
	public void setHero(Hero hero) {
		this.hero = hero;
	}

	/**
	 * Gets the dragons.
	 *
	 * @return the dragon vector
	 */
	public Vector<Dragon> getDragons() {
		return dragons;
	}

	/**
	 * Sets the dragons.
	 *
	 * @param dragons the new dragons
	 */
	public void setDragons(Vector<Dragon> dragons) {
		this.dragons = dragons;
	}

	/**
	 * Gets the door position in x.
	 *
	 * @return the door position in x
	 */
	public int getDoorX() {
		return doorX;
	}

	/**
	 * Sets the door position in x.
	 *
	 * @param doorX the new door position in x
	 */
	public void setDoorX(int doorX) {
		this.doorX = doorX;
	}

	/**
	 * Gets the door position in y.
	 *
	 * @return the door position in y
	 */
	public int getDoorY() {
		return doorY;
	}

	/**
	 * Sets the door y.
	 *
	 * @param doorY the new door position in y
	 */
	public void setDoorY(int doorY) {
		this.doorY = doorY;
	}

	/** The Maze edit. */
	private static manualGen MazeEdit = null;
	
	/**
	 * Gets the single instance of manualGen.
	 *
	 * @return single instance of manualGen
	 */
	public static manualGen getInstance() {
		if (MazeEdit == null) 
			MazeEdit = new manualGen(size);
		return MazeEdit;
	}

	/**
	 * Gets the user maze.
	 *
	 * @return the user maze
	 */
	public String[][] getUserMaze() {
		return userMaze;
	}

	/**
	 * Sets the user maze.
	 *
	 * @param userMadeMaze the new user maze
	 */
	public void setUserMaze(String[][] userMadeMaze) {
		userMaze = userMadeMaze;
	}

	/**
	 * Initializes the maze for the edit
	 *
	 * @param dimension the dimension of the maze.
	 * @return the string[][] prototype of the maze.
	 */
	private String[][] initialize(int dimension) {
		String[][] aux = new String[dimension][dimension];
		String x = "walk";
		for(int i=0; i<dimension;i++)
		{
			for(int j=0; j<dimension;j++)
				aux[i][j] = x;
		}
		for(int i=0;i<dimension;i++)
		{
			aux[i][0] = "wall";
			aux[0][i] = "wall";
			aux[i][dimension-1] = "wall";
			aux[dimension-1][i] = "wall";
		}
		return aux;
	}

	/**
	 * Instantiates a new manual generated maze.
	 * Displays a graphical window giving the user the power to create a custom maze to his liking.
	 * The user has still the possibility to test the maze that he has created to fulfill the specified requirements of the maze even though this is not obligatory.
	 * @param dimension the dimension of the maze.
	 */
	public manualGen(final int dimension) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				JOptionPane.showMessageDialog(null, "This sandbox-style creation window includes a button \n'test' to check if your maze is of the same style of the\nauto generated mazes.\nIt is not mandatory for your maze to comply with these rules.\nThey are just to show you what rules we followed when\ncreating the auto-generated mazes.\nWhen you finished the maze just click 'apply' to play it!  ", "Rules", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		size=dimension;
		userMaze = initialize(size);
		if(dimension<10) //automatic window resize
		{
			setBounds(100, 100,200+60,200+70);
		}
		else
		{
			setBounds(100, 100, dimension*20+60, dimension*20+70); //70 for object view 90 (in x) for buttons
		}
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());

		final JPanel panel = new mazeEditorPrint(dimension);
		panel.setBounds(0, 0, dimension*20, dimension*20);
		getContentPane().add(panel, BorderLayout.EAST);
		panel.grabFocus();
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int i=getIndex(e.getY());
				int j=getIndex(e.getX()); 

				//overwrite possible user mistake
				if(userMaze[j][i] == "hero")
					hero = null;
				else if(userMaze[j][i] == "sword")
					sword = null;
				else if(userMaze[j][i] == "door")
				{doorX=-1;doorY=-1;}
				else if (userMaze[j][i] == "wall" && objectType == "wall")
					objectType = "walk";
				else if (userMaze[j][i] == "dragon")
				{
					//auxilary dragon used to find the index of specific dragon which requires change
					Dragon aux = new Dragon();
					aux.setX(j);
					aux.setY(i);
					if(Game_Loop.getInstance().getDragon_mode() == 3)
					{
						dragonsPlaced--;						
						dragons.remove(aux);
						objectType= "sleepingDragon";
					}
					else
					{
						dragonsPlaced--;
						dragons.remove(aux);
						objectType = "wall";
					}
				}
				else if (userMaze[j][i] == "sleepingDragon")
				{
					Dragon aux = new Dragon();
					aux.setX(j);
					aux.setY(i);
					dragonsPlaced--;
					dragons.remove(aux);
					objectType = "wall";
				}
				if((i==0 || j==0 || i==dimension-1 || j == dimension-1) && objectType != "door")
					objectType = "wall";

				userMaze[j][i]=objectType;

				if(objectType == "hero")
				{	
					hero = new Hero();
					hero.setX(j);
					hero.setY(i);
				}
				else if (objectType == "sword")
				{
					sword = new Sword();
					sword.setX(j);
					sword.setY(i);
				}
				else if(objectType == "door")
				{
					doorX=j;
					doorY=i;
				}
				else if(objectType =="dragon")
				{
					Dragon x = new Dragon();
					x.setX(j);
					x.setY(i);
					x.setSymbol('D');
					dragons.add(x);
					dragonsPlaced++;
				}
				else if(objectType == "sleepingDragon")
				{
					Dragon x = new Dragon();
					x.setX(j);
					x.setY(i);
					x.setSymbol('d');
					dragons.add(x);
					dragonsPlaced++;
				}
				objectType="wall";
				validate();
				repaint();

			}
		});
		panel.setFocusable(true);

		final JPanel ObjectWindow = new JPanel();
		getContentPane().add(ObjectWindow, BorderLayout.EAST);
		ObjectWindow.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel lblHero = new JLabel("Hero:  ", new ImageIcon(this.getClass().getClassLoader().getResource("Resource/hero.gif")), JLabel.CENTER);
		lblHero.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if(hero == null)
					objectType="hero";
				else 
					JOptionPane.showMessageDialog(ObjectWindow, "You already placed the hero!", "Error!", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		lblHero.setVerticalTextPosition(JLabel.TOP);
		lblHero.setHorizontalTextPosition(JLabel.CENTER);
		ObjectWindow.add(lblHero);

		JLabel lblDragon = new JLabel("Dragon:",new ImageIcon(this.getClass().getClassLoader().getResource("Resource/dragon.gif")),JLabel.CENTER);
		lblDragon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(dragonsPlaced<Game_GUI.getInstance().getNumber_dragons())
				{
					switch (Game_Loop.getInstance().getDragon_mode()) {
					case 1:
						objectType="sleepingDragon";
						break;
					case 2:
						objectType="dragon";
						break;
					case 3:
						objectType = "dragon";
						break;

					}
				}
				else 
					JOptionPane.showMessageDialog(ObjectWindow, "You can't add more dragons!", "Error!", JOptionPane.INFORMATION_MESSAGE);
			}});
		lblDragon.setVerticalTextPosition(JLabel.TOP);
		lblDragon.setHorizontalTextPosition(JLabel.CENTER);
		ObjectWindow.add(lblDragon);

		JLabel lblSword = new JLabel("Sword: ",new ImageIcon(this.getClass().getClassLoader().getResource("Resource/sword.gif")),JLabel.CENTER);
		lblSword.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(sword==null)
					objectType="sword";
				else
					JOptionPane.showMessageDialog(ObjectWindow, "You already have the sword in the maze!", "Error!", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		lblSword.setVerticalTextPosition(JLabel.TOP);
		lblSword.setHorizontalTextPosition(JLabel.CENTER);
		ObjectWindow.add(lblSword);

		JLabel lblExit = new JLabel("Door:  ",new ImageIcon(this.getClass().getClassLoader().getResource("Resource/door.png")),JLabel.CENTER);
		lblExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if(doorX==-1||doorY==-1)
					objectType="door"; 
				else
					JOptionPane.showMessageDialog(ObjectWindow, "You already have the door in the maze!", "Error!", JOptionPane.INFORMATION_MESSAGE);

			}
		});
		lblExit.setVerticalTextPosition(JLabel.TOP);
		lblExit.setHorizontalTextPosition(JLabel.CENTER);
		ObjectWindow.add(lblExit);

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.SOUTH);

		JButton btnApply = new JButton("Apply");
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cell[][] Mazex = new Cell[size][size];
				for(int i=0;i<size;i++)
				{
					for(int j=0;j<size;j++)
					{
						if(userMaze[i][j]=="wall" || userMaze[i][j]=="door")
						{Cell aux=new Cell(); aux.setFilled(true);Mazex[j][i]=aux;}
						else 
						{Cell aux=new Cell(); aux.setFilled(false);Mazex[j][i]=aux;}
					}
				}

				//set Labirynth
				Labirynth.getInstance().setGenerated_maze(Mazex);
				Labirynth.getInstance().COL_SIZE=size;
				Labirynth.getInstance().ROW_SIZE=size;
				Labirynth.getInstance().EXIT_X=doorX; //in graphical mode the order is inverted
				Labirynth.getInstance().EXIT_Y=doorY;

				//set Game_loop variables
				Game_Loop.getInstance().elements = new Vector<Game_Elements>();
				Game_Loop.getInstance().enemies = new Dragon[dragonsPlaced];
				//set dragons
				for(int i=0; i<dragonsPlaced;i++)
				{
					dragons.get(i).setDead(false);
					dragons.get(i).setMode(Game_Loop.getInstance().getDragon_mode());
					switch (Game_Loop.getInstance().getDragon_mode())
					{
					case 1:
						dragons.get(i).setSymbol('d');
						break;
					case 2:
						dragons.get(i).setSymbol('D');			
						break;
					case 3:
						Random rand = new Random();
						int sym = rand.nextInt(2);
						if(sym == 0) //dragon is awake
							dragons.get(i).setSymbol('D'); 
						else 	 //dragon starts game sleeping
							dragons.get(i).setSymbol('d'); 
						break;
					}
					Game_Loop.getInstance().elements.add(dragons.get(i));
					Game_Loop.getInstance().enemies[i] = dragons.get(i);
				}
				//set hero
				hero.setDead(false);
				hero.setSymbol('H');
				hero.setEagle('v');
				hero.setEagle_alive(true);
				hero.setEagle_x(hero.getX());
				hero.setEagle_y(hero.getY());
				hero.setEagle_sent(false);
				hero.setLastHero_x(0);
				hero.setLastHero_y(0);
				Game_Loop.getInstance().elements.add(hero);
				Game_Loop.getInstance().HERO = hero;

				//set sword (setX and setY already done when sword placed in maze)
				sword.setUsed(false);
				sword.setSymbol('E');
				Game_Loop.getInstance().elements.add(sword);
				Game_Loop.getInstance().SWORD =sword;
				Game_GUI.getInstance().setVisible(true);
				File xFile = new File("src/Resource/manualMaze.txt");
				Game_Loop.getInstance().Save(xFile);
				ConfigPanel.getInstance().SaveConfig("src/Resource/newConfigurations.txt", false, size, dragonsPlaced, Game_Loop.getInstance().getDragon_mode());
				dispose();
			}
		});
		panel_1.add(btnApply);

		JButton btnTest = new JButton("Test");
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//test maze
				Cell[][] Mazex = new Cell[size][size];
				for(int i=0;i<size;i++)
				{
					for(int j=0;j<size;j++)
					{
						if(userMaze[i][j]=="wall")
						{Cell aux=new Cell(); aux.setFilled(true);Mazex[i][j]=aux;}
						else 
						{Cell aux=new Cell(); aux.setFilled(false);Mazex[i][j]=aux;}
					}
				}

				if(sword == null)
				{
					JOptionPane.showMessageDialog(ObjectWindow, "No Sword on the map!", "Wrong Maze!", JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				if(hero == null)
				{
					JOptionPane.showMessageDialog(ObjectWindow, "No Hero on the map!", "Wrong Maze!", JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				if(doorX == -1 || doorY == -1)
				{
					JOptionPane.showMessageDialog(ObjectWindow, "Exit undefined!", "Wrong Maze!", JOptionPane.INFORMATION_MESSAGE);
					return;
				}	

				if(Game_GUI.getInstance().getNumber_dragons() != dragonsPlaced)
				{
					JOptionPane.showMessageDialog(ObjectWindow, "Wrong number of dragons on the maze!", "Wrong Maze!", JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				for(int i = 0; i < dragons.size(); i++)
				{
					if(dragons.get(i).getX() == hero.getX()+1 || dragons.get(i).getX() == hero.getX()-1)
					{
						if(dragons.get(i).getY() == hero.getY())
						{
							JOptionPane.showMessageDialog(ObjectWindow, "Hero next to a Dragon!", "Wrong Maze!", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					}

					else if(dragons.get(i).getY() == hero.getY()+1 || dragons.get(i).getY() == hero.getY()-1)
					{
						if(dragons.get(i).getX() == hero.getX())
						{
							JOptionPane.showMessageDialog(ObjectWindow, "Hero next to a Dragon!", "Wrong Maze!", JOptionPane.INFORMATION_MESSAGE);
							return;
						}

					}
				}
				if(Labirynth.getInstance().testMaze(Mazex, doorY, doorX)==false)
				{
					JOptionPane.showMessageDialog(ObjectWindow, "This maze is not allowed!", "Wrong Maze!", JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				JOptionPane.showMessageDialog(ObjectWindow, "NICE MAZE!", "Correct Maze!", JOptionPane.INFORMATION_MESSAGE);

			}
		});
		panel_1.add(btnTest);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				StartWindow janela = new StartWindow();
				janela.setVisible(true);
				dispose();
			}
		});
		panel_1.add(btnCancel);

	}

	/**
	 * Gets the index of the mouse.
	 *
	 * @param mouseX2 the mouse x2
	 * @return the index
	 */
	private int getIndex(int mouseX2) {
		return (int) Math.floor(mouseX2/20);
	}
}
