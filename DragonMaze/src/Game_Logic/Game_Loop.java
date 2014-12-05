package Game_Logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import CUI.Game_CUI;
import GUI.ConfigPanel;
import GUI.Game_GUI;
import GUI.StartWindow;
import maze.Labirynth;

// TODO: Auto-generated Javadoc
/**
 * The Class Game_Loop.
 * Super class which works as Game Engine
 */
public class Game_Loop {

	//	public static Labirynth MAZE=null;
	/** 
	 * Elements. 
	 * Contains all Game Elements of a certain game instance, 
	 * used to control colisions and overlaps during game initialization.
	 */
	public Vector<Game_Elements> elements = new Vector<Game_Elements>();
	
	/** 
	 * The Sword. 
	 * Object used to refer to the sword game element 
	 */
	public Sword SWORD=null;
	
	/**
	 * The Hero.
	 * Object instance of the hero, the game element directly controlled by the user 
	 */
	public Hero HERO=null;
	
	/** 
	 * The enemies. 
	 * Array of enemies used to control all enemies in a identical fashion
	 * in every game turn
	 * */
	public Dragon[] enemies= null;
	
	/** 
	 * The dragon_mode. 
	 * Number specifying the play mode of the enemies (if static, moving, sleeping or moving)
	 * 1-> Immovable/Stationary enemies
	 * 2-> Moving enemies
	 * 3-> Alternating sleeping (stationary and can't kill hero) with moving dragon
	 */
	private int dragon_mode =0;
	
	/** 
	 * The graphic_mode.
	 * Specifies if game should be in console mode (false) or graphical mode (true)
	 */
	private static boolean graphic_mode =false;

	/**
	 * Checks if is graphic_mode.
	 *
	 * @return true, if is graphic_mode
	 */
	public static boolean isGraphic_mode() {
		return graphic_mode;
	}

	/**
	 * Sets the graphic_mode.
	 *
	 * @param mode the new graphic_mode
	 */
	public void setGraphic_mode(boolean mode) {
		graphic_mode = mode;
	}

	/**
	 * Gets the dragon_mode.
	 *
	 * @return the dragon_mode
	 */
	public int getDragon_mode() {
		return dragon_mode;
	}

	/**
	 * Sets the dragon_mode.
	 *
	 * @param dragon_mode the new dragon_mode
	 */
	public void setDragon_mode(int dragon_mode) {
		this.dragon_mode = dragon_mode;
	}

	/**
	 *  The game.
	 *  Object instance of Game_loop upon which all user 
	 *  actions and interactions will happen
	 */
	private static Game_Loop game = null;
	
	/**
	 * Instantiates a new game_ loop.
	 */
	private Game_Loop() {}

	/**
	 * Gets the single instance of Game_Loop.
	 *
	 * @return single instance of Game_Loop
	 */
	public static Game_Loop getInstance() {
		if (game == null) 
			game = new Game_Loop();
		return game;
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * Starts game either in console or graphical mode according to graphic_mode variable
	 */
	public static void main(String[] args) 
	{
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //tries to use OS style graphics
			//Custom button text
			Object[] options = {"Gui!", "Console!"};
			int n = JOptionPane.showOptionDialog(null,"Would you like play the game in gui or console mode?", "Choose YOUR GAMING MODE",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,options,options[1]);
			if(n == 0)
				graphic_mode = true;
			else if(n==1)
				graphic_mode = false;
			else 
				return;
			
		} catch (Exception e) { }
		if(graphic_mode)
		{
			SwingUtilities.invokeLater(new Runnable() {

				public void run() {
					StartWindow janela = new StartWindow();
					janela.setVisible(true);
				}
			});
		}
		else
		{
			//modo texto
			Scanner input = new Scanner(System.in);
			int dungeon_size=0;
			int number_dragons=0;
			Boolean standard_maze = true;//maze option of generation (standard 10x10 (false)) or randomly generation(true), has content so no null exception pointer errors
			Game_CUI.getInstance().initGame(Game_Loop.getInstance(), input, dungeon_size, number_dragons, standard_maze);
			Game_CUI.getInstance().print(Game_Loop.getInstance());
			String s;

			do {
				s = input.next();
				Game_Loop.getInstance().game_conditions(s);
				Game_CUI.getInstance().print(Game_Loop.getInstance());
			} while (s !="q");
			input.close(); //so no leaks!
		}
	}

	/**
	 * Game_conditions.
	 *
	 * Function used as Game Engine, 
	 * calls movement related functions (hero and dragon's) and 
	 * changes the various game_elements according to predetermined conditions
	 *
	 * @param s the direction input made the user,
	 * specifying the direction the hero object must mode to
	 */
	public void game_conditions(String s) 
	{
		HERO.heroMovement(s,Labirynth.getInstance());

		for(int i=0; i<enemies.length;i++) //dragon related movements
		{
			//dragon mode == 3 (alternate dragon states)
			if(getDragon_mode()==3) 
			{
				Random rand = new Random();
				int sym = rand.nextInt(2);
				switch (sym) {
				case 0:
					//dragon sleeps
					enemies[i].setSymbol('d');
					break;
				case 1:
					//dragon moves
					enemies[i].setSymbol('D');
					break;
				}
			}
			if(enemies[i].getSymbol() =='D' || enemies[i].getSymbol()=='F' && !enemies[i].isDead()) //if dragon is awake and alive MOVE
				enemies[i].dragonMovement(game);
			
			//Dragon-related conditions
			s = dragonConditions(s, i);
		}
		//end of dragon-related conditions & movements

		//eagle-related conditions & functions
		eagleConditions();
		
		//Hero only related conditions
		heroConditions(s);
	}

	/**
	 * Dragon conditions.
	 *
	 * @param s the direction of the hero (important only to break the game loop in console mode)
	 * @param i the index of dragon to be moved 
	 * @return the s (important only to determine if game has ended in console mode)
	 */
	private String dragonConditions(String s, int i) {
		if( enemies[i].getX() == SWORD.getX() && enemies[i].getY() == SWORD.getY() && !SWORD.isUsed())
		{
			enemies[i].setSymbol('F');
		}

		if (nextToDragon(HERO, enemies[i]) && !enemies[i].isDead())	
		{
			if (HERO.getSymbol() == 'A') //se armado
			{
				if(graphic_mode) Game_GUI.getInstance().setstateGame("YOU HAVE DEFEATED A DRAGON");
				else System.out.println("YOU HAVE DEFEATED A DRAGON");
				enemies[i].setDead(true);
			}
			else if (enemies[i].getSymbol() == 'd') //sleeping dragon and implicitly hero is not armed 
			{
				if(graphic_mode) Game_GUI.getInstance().setstateGame("Your lucky the dragon is sleeping!");
				else System.out.println("Your lucky the dragon is sleeping! Get a sword to kill him \n");
			}
			else if(!HERO.isDead()) //both awake and hero should die
			{
				if(graphic_mode) Game_GUI.getInstance().setstateGame("THE DRAGON DEFEATS YOU");
				else System.out.println("THE DRAGON DEFEATS YOU");
				HERO.setDead(true);
				HERO.setEagle_alive(false);
				s="q";
				return s;
			}
		}
		return s;
	}

	/**
	 * Eagle conditions.
	 * 
	 * Eagle related conditions, specifies in eagle should move or not according to
	 * user command and the eagle's current state
	 * 
	 */
	private void eagleConditions() {
		if (HERO.isEagle_alive() && HERO.isEagle_sent()) //eagle is alive and set to get sword (implicitly hero is unarmed and alive aswell)
		{
			HERO.eagleMovement(game);
		}
		else if(!HERO.isEagle_sent()) //eagle can get sword but hero hasnt sent her (so no action), eagle on top of hero
		{
			HERO.setEagle_x(HERO.getX());
			HERO.setEagle_y(HERO.getY());
		}
	}

	/**
	 * Hero conditions.
	 *
	 * @param s the direction the object "Heroi" will move to
	 */
	private void heroConditions(String s) {
		if(HERO.getY() == Labirynth.getInstance().EXIT_Y && HERO.getX() == Labirynth.getInstance().EXIT_X && HERO.getSymbol() == 'A' && allDragonsDead())
		{
			if(graphic_mode) Game_GUI.getInstance().setstateGame("YOU WON!");
			else System.out.println("YOU WON!");
			s="q";
			return;
		}
		else if(HERO.getY() == Labirynth.getInstance().EXIT_Y && HERO.getX() == Labirynth.getInstance().EXIT_X && (HERO.getSymbol() == 'H'|| !allDragonsDead()))
		{
			//will depend on which side wall exit is in
			if(Labirynth.getInstance().EXIT_Y ==0 && Labirynth.getInstance().EXIT_X !=0) //on top
				HERO.setY(HERO.getY() + 1);
			else if (Labirynth.getInstance().EXIT_Y ==Labirynth.getInstance().COL_SIZE-1 && Labirynth.getInstance().EXIT_X !=0) //bottom
				HERO.setY(HERO.getY() - 1);
			else if (Labirynth.getInstance().EXIT_Y !=0 && Labirynth.getInstance().EXIT_X ==0) //left
				HERO.setX(HERO.getX() + 1);
			else //right
				HERO.setX(HERO.getX() - 1);
			if(HERO.getSymbol() =='A') //at least one dragon alive
			{
				if(graphic_mode) Game_GUI.getInstance().setstateGame("KILL ALL THE DRAGONS BEFORE LEAVING!");
				else System.out.println("KILL ALL THE DRAGONS BEFORE LEAVING!");	
			}
			
		}
		else if(HERO.getSymbol() == 'H' && HERO.getX() == SWORD.getX() && HERO.getY() == SWORD.getY() && !SWORD.isUsed() && !HERO.isDead())
		{
			if(graphic_mode) Game_GUI.getInstance().setstateGame("YOU HAVE PICKED UP THE SWORD");
			else System.out.println("YOU HAVE PICKED UP THE SWORD");
			HERO.setSymbol('A');
			SWORD.setUsed(true);
		}
	}

	/**
	 * Next to dragon.
	 *
	 * @param h the hero object
	 * @param d the dragon object we are using
	 * @return true, if successful the hero is in an adjacent "cell",
	 * implying a "fight" will happen between this dragon and the hero
	 */
	public boolean nextToDragon(Hero h, Dragon d)
	{
		if( h.getX()  == d.getX() && h.getY() - 1 == d.getY() && !d.isDead())
			return true;
		else if ( h.getX() == d.getX() && h.getY() + 1 == d.getY() && !d.isDead())
			return true;
		else if ( h.getX() + 1 ==d.getX() && h.getY() == d.getY() && !d.isDead())
			return true;
		else if ( h.getX() - 1 == d.getX() && h.getY() == d.getY() && !d.isDead())
			return true;
		else if( h.getX()  == d.getX() && h.getY() == d.getY() && !d.isDead())
			return true;
		else
			return false;
	}

	/**
	 * Next to any dragon.
	 *
	 * @param test_x the x coordinate to be tested
	 * @param test_y the y coordinate to be tested
	 * @return true, if successful there is a dragon in an adjacent square to the (test_y,test_x) position
	 */
	public boolean nextToAnyDragon(int test_x, int test_y)
	{
		for(int i=0; i<enemies.length;i++)
		{
			if( test_x  == enemies[i].getX() && test_y - 1 == enemies[i].getY() && !enemies[i].isDead() && enemies[i].getSymbol()!='d')
				return true;
			else if ( test_x == enemies[i].getX() && test_y + 1 == enemies[i].getY() && !enemies[i].isDead()&& enemies[i].getSymbol()!='d')
				return true;
			else if ( test_x + 1 ==enemies[i].getX() && test_y == enemies[i].getY() && !enemies[i].isDead()&& enemies[i].getSymbol()!='d')
				return true;
			else if ( test_x - 1 == enemies[i].getX() && test_y == enemies[i].getY() && !enemies[i].isDead()&& enemies[i].getSymbol()!='d')
				return true;
			else if( test_x  == enemies[i].getX() && test_y == enemies[i].getY() && !enemies[i].isDead()&& enemies[i].getSymbol()!='d')
				return true;
		}
		return false;
	}

	/**
	 * All dragons dead.
	 *
	 * @return true, if successful all dragons are dead
	 */
	private boolean allDragonsDead()
	{
		for(int i=0; i<enemies.length;i++)
			if(!enemies[i].isDead())
				return false;
		return true;
	}

	/**
	 * Ocupied_by elements.
	 *
	 * @param check_x the x coordinate to be tested
	 * @param check_y the y coordinate to be tested
	 * @return true, if successful if (test_y,test_x) position already contains an object
	 */
	public boolean ocupied_byElements(int check_x, int check_y)
	{
		for(int i=0; i<elements.size();i++)
		{
			if(elements.get(i).getX()==check_x && elements.get(i).getY()==check_y)
				return true;
		}
		return false;
	}

	/**
	 * Save.
	 *Function used to save all data related to a game (only acessable through graphical mode)
	 *
	 * @param file the file used to save all necessary variables
	 */
	public void Save(File file)
	{
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file));
			os.writeBoolean(Game_GUI.getInstance().isStandard_maze());
			os.writeObject(Labirynth.getInstance());
			os.writeObject(Game_Loop.getInstance().HERO);
			os.writeObject(Game_Loop.getInstance().SWORD);
			os.writeInt(Game_Loop.getInstance().enemies.length);
			for(int i =0; i<Game_Loop.getInstance().enemies.length;i++)
				os.writeObject(Game_Loop.getInstance().enemies[i]);
			os.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();	
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load.
	 *
	 * Function loads data necessary for game, replaces current data with data loaded from file
	 *
	 * @param file the file where data will be loaded from
	 * @return true, if Load was successful
	 */
	@SuppressWarnings("finally")
	public boolean Load(File file) 
	{
		ObjectInputStream is = null;
		boolean sucess = false;
		try {
			is = new ObjectInputStream(new FileInputStream(file));
			Vector<Game_Elements> novosElements = new Vector<Game_Elements>();
			boolean standard = is.readBoolean();
			Labirynth lab = (Labirynth) is.readObject();
			Hero hero = (Hero) is.readObject();
			Sword espada = (Sword) is.readObject();
			int dragon_number = is.readInt();
			Dragon[] xDragons = new Dragon[dragon_number];
			for(int i = 0; i<dragon_number;i++)
			{
				xDragons[i] = (Dragon) is.readObject();
				novosElements.add(xDragons[i]);
			}
			novosElements.add(hero);
			novosElements.add(espada);

			//overwrite current game parameters
			Labirynth.getInstance().setGenerated_maze(lab.getGenerated_maze());
			Labirynth.getInstance().maze = lab.maze;
			Labirynth.getInstance().COL_SIZE = lab.COL_SIZE;
			Labirynth.getInstance().ROW_SIZE = lab.ROW_SIZE;
			Labirynth.getInstance().EXIT_X = lab.EXIT_X;
			Labirynth.getInstance().EXIT_Y = lab.EXIT_Y;
			Game_Loop.getInstance().elements = novosElements;
			Game_Loop.getInstance().SWORD = espada;
			Game_Loop.getInstance().HERO = hero;
			Game_Loop.getInstance().enemies = xDragons;

			Game_GUI.resetInstance();
			Game_GUI.getInstance(lab.COL_SIZE).setStandard_maze(standard); //checkbox is checked
			Game_GUI.getInstance().setDungeon_size(lab.COL_SIZE);
			Game_GUI.getInstance().setNumber_dragons(xDragons.length);
			Game_GUI.setGame_finished(false);
			Game_GUI.getInstance().setstateGame("");
			//Get's key from config panel if standard keys (load(xfile) resets keys9 are diferent from new ones
			if(Game_GUI.getInstance().anyKeyDifferent(ConfigPanel.getInstance().getCommandkeys()))
				Game_GUI.getInstance().setKeys(ConfigPanel.getInstance().getCommandkeys());
			Game_GUI.getInstance().setVisible(true);
			sucess= true;

		}
		catch(FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(Game_GUI.getInstance(), "Could not find the selected file \nPlease select the correct file", "Loading Error", JOptionPane.INFORMATION_MESSAGE);
			sucess=  false;
		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(Game_GUI.getInstance(), "Could not load from selected file \nPlease select the correct file", "Loading Error", JOptionPane.INFORMATION_MESSAGE);
			sucess= false;
		}
		catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(Game_GUI.getInstance(), "Could not load class from selected file \nPlease try again", "Loading Error", JOptionPane.INFORMATION_MESSAGE);
			sucess= false;
		}
		finally
		{
			try {
				if(is!=null)
					is.close();
				return sucess;
			} catch (IOException e) {
				return true;
			}
		}
	}
	//////////////////////////////////////////////////////TEST-RELATED FUNCTIONS ///////////////////////////////////
	
	/**
	 * Inits the test game.
	 *
	 * Function used sorely for Junit related functions
	 * Initializes game in console mode, with standard maze (maze of the first iteration)
	 * Hero in (1,1), sword in (1,8), dragons are placed randomly due to a variable number of dragons
	 *
	 * @param nDragons the number of dragons
	 */
	public void InitTestGame(int nDragons)
	{
		Game_Loop.getInstance().setGraphic_mode(false);
		//Initializing game parameters
		Labirynth.getInstance().generate(10, true);

		//initialize Game elements (positions, booleans, game_modes, symbols....)
		Game_Loop.getInstance().setDragon_mode(2);
		Game_Loop.getInstance().enemies = new Dragon[nDragons];
		Game_Loop.getInstance().elements = new Vector<Game_Elements>();

		for(int i=0; i<nDragons;i++)
		{//add all dragons
			Dragon auxDragon = new Dragon();
			Game_Loop.getInstance().enemies[i]= auxDragon.initTest(Game_Loop.getInstance().getDragon_mode(), Game_Loop.getInstance());
			Game_Loop.getInstance().elements.add(auxDragon);
		}

		Hero auxHero = new Hero();
		Game_Loop.getInstance().HERO=auxHero.initTest(Game_Loop.getInstance());
		Game_Loop.getInstance().elements.add(auxHero);
		Sword auxSword = new Sword();
		Game_Loop.getInstance().SWORD=auxSword.initTest(Game_Loop.getInstance());
		Game_Loop.getInstance().elements.add(auxSword);
		return;
	}
	
	
}
