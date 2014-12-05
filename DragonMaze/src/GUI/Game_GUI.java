package GUI;

import java.awt.BorderLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import Game_Logic.Dragon;
import Game_Logic.Game_Loop;
import Game_Logic.Hero;
import maze.Labirynth;
import Game_Logic.Sword;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.io.File;
import java.io.Serializable;
import java.awt.Component;
import java.awt.GridLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class Game_GUI.
 */
@SuppressWarnings("serial")
public class Game_GUI extends JFrame implements Serializable{

	/** The content pane.
	 * Contains the maze and game elements 
	 *  */
	private JPanel contentPane;

	/** The dungeon_size.
	 *  The size of the dungeon, used to calculate the dimensions of the 
	 *  window necessary for an accurate visualization
	 *  */
	private static int dungeon_size=0;

	/** The standard_maze.
	 * Indicates whether game to be played should be the standard one or not
	 *  */
	private boolean standard_maze=true;

	/** The number_dragons. */
	private int number_dragons=1;

	/** The game state.
	 *  Indicates the current game state
	 *  */
	private static String stateGame = " ";

	/** The game_finished. 
	 * Boolean used to end game 
	 * */
	private static boolean game_finished = false;

	/** The up key. */
	private int upKey=87;

	/** The left key. */
	private int leftKey=65;

	/** The down key. */
	private int downKey=83;

	/** The right key. */
	private int rightKey=68;

	/** The eagle key. */
	private int eagleKey=82;


	/** The gui.
	 * singleton
	 *  */
	private static Game_GUI GUI = null;

	/**
	 * Gets the single instance of Game_GUI.
	 *
	 * @return single instance of Game_GUI
	 */
	public static Game_GUI getInstance() {
		if (GUI == null) 
			GUI = new Game_GUI(dungeon_size);
		return GUI;
	}

	/**
	 * Gets the single instance of Game_GUI.
	 *
	 * @param dimension the dimension
	 * @return single instance of Game_GUI
	 */
	public static Game_GUI getInstance(int dimension) {
		if (GUI == null) 
			dungeon_size = dimension;
		GUI = new Game_GUI(dungeon_size);
		return GUI;
	}

	/**
	 * Reset instance.
	 * Sets instance to null for possible overwrite of game gui
	 */
	public static void resetInstance()
	{
		GUI = null;
	}

	/**
	 * Create the frame.
	 * Implements the interface needed in order to do a graphic display of the game.
	 * @param blocks the blocks
	 */

	private Game_GUI(int blocks) {
		dungeon_size = blocks;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//40 pixels in the y direction are for "x" button and borders

		setBounds(100, 100, blocks*20+20, blocks*20+120);	//25 pixeis for menu, 100 for stateGame text and buttons		
		setResizable(false);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		final JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser saveGame = new JFileChooser();
				saveGame.setDialogTitle("Save as"); 
				int result = saveGame.showSaveDialog(mnFile); //mnFile is the parent frame (container)
				if (result == JFileChooser.APPROVE_OPTION) {
					String filePath = saveGame.getSelectedFile().getAbsoluteFile() + ".sav";
					Game_Loop.getInstance().Save(new File(filePath));
				}
			}
		});

		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				setstateGame("waiting");
				setEnabled(false);
				StartWindow janela = new StartWindow();
				janela.setVisible(true);
			}
		});
		mnFile.add(mntmNew);
		mnFile.add(mntmSave);

		final JMenuItem mntmLoad = new JMenuItem("Load");
		mntmLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser loadGame;
				boolean loadSucessful = false;
				int result;
				setstateGame(" ");
				while(!loadSucessful)
				{
					loadGame = new JFileChooser();
					loadGame.setDialogTitle("Load File"); 
					result = loadGame.showSaveDialog(mnFile); //mnFile is the parent frame (container)
					if (result == JFileChooser.APPROVE_OPTION) {
						setVisible(false);
						if (loadGame.getSelectedFile().getAbsolutePath().contains(".sav")) {
							dispose();		
							loadSucessful=Game_Loop.getInstance().Load(loadGame.getSelectedFile()); //Something went wrong with load (try again)							
						}
						else
						{
							JOptionPane.showMessageDialog(mntmLoad, "Could not load from selected file \nIncorrect file extension", "Loading Error", JOptionPane.INFORMATION_MESSAGE);
							setVisible(true);
						}
					}
					else if(result == JFileChooser.CANCEL_OPTION)
						break;
				}
			}
		});
		mnFile.add(mntmLoad);

		JMenu mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);

		JMenuItem mntmCustomizeMaze = new JMenuItem("Customize maze");
		mntmCustomizeMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				ConfigPanel.getInstance().setVisible(true);
			}
		});
		mnOptions.add(mntmCustomizeMaze);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmGameRules = new JMenuItem("Game Rules");
		mntmGameRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(contentPane, " Use WASD to move the hero (laughing guy)\n Pick up the sword to kill the enemies or they will own you!\n To win the game get the sword and head to the exit!\n Check options to customize the maze", "Game Rules!", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnHelp.add(mntmGameRules);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));


		JPanel GamestateGame = new JPanel();
		GamestateGame.setLayout(new BorderLayout());
		final JLabel estado = new JLabel();
		estado.setText(getstateGame());
		contentPane.add(GamestateGame, BorderLayout.NORTH);
		GamestateGame.add(estado, BorderLayout.CENTER);

		//xJPanel.setBorder(new LineBorder(Color.BLACK));

		final JPanel panel = new MazePanel();
		panel.setBounds(0, 0, blocks*20, blocks*20);
		contentPane.add(panel, BorderLayout.CENTER);

		panel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				setstateGame(" ");
				int c = arg0.getKeyCode();

				if(c == getUpKey())
					Game_Loop.getInstance().game_conditions("w");
				else if(c == getLeftKey())
					Game_Loop.getInstance().game_conditions("a");
				else if(c == getDownKey())
					Game_Loop.getInstance().game_conditions("s");
				else if (c == getRightKey())
					Game_Loop.getInstance().game_conditions("d");
				else if(c == getEagleKey())
					Game_Loop.getInstance().game_conditions("r");

				if(getstateGame() == "YOU WON!"||getstateGame()=="THE DRAGON DEFEATS YOU")
				{
					Game_GUI.setGame_finished(true);
				}

				if(Game_GUI.isGame_finished())
				{
					panel.setEnabled(false);
					JOptionPane.showMessageDialog(contentPane, "The Game has ended!");
					File xFile = new File("src/Resource/manualMaze.txt");						
					File confFile = new File("src/Resource/newConfigurations.txt");						

					if(xFile.exists())
					{
						//Custom button text
						Object[] options = {"Yes, my maze AGAIN", "Yes, but generate it yourself!", "No!"};
						int option = JOptionPane.showOptionDialog(Game_GUI.getInstance(), "Do you really want to play a new game !?", "New Game",JOptionPane.YES_NO_OPTION , JOptionPane.QUESTION_MESSAGE,null,options,options[2]);

						if(option== 0)
						{
							dispose();
							Game_Loop.getInstance().Load(xFile);
							Game_GUI.setGame_finished(false); //reset endgame condition
							panel.validate();
							panel.setVisible(true);
							panel.setEnabled(true);
							panel.grabFocus();	
							return;

						}
						if(option == 1)
						{
							panel.setVisible(false);
							Game_GUI.getInstance().setstateGame("");
							//estado.setText("");

							if(confFile.exists())
							{
								dispose();
								panel.setVisible(false);
								ConfigPanel.getInstance().LoadConfig(confFile);
								panel.validate();
								panel.repaint();
								panel.setVisible(true);
								panel.setEnabled(true);
								panel.grabFocus();
							}
							return;

						}
						else if(option==2) //0 in case of messageDialog(meaning failure to read), 2 means clicked "No!"
							if (end()) 
								System.exit(0);
							else
							{
								//Possible error
								JOptionPane.showMessageDialog(null, "Unexpected input " + option);
							}
					}
					else 
					{
						int option = JOptionPane.showConfirmDialog(Game_GUI.getInstance(), "Do you really want to play a new game !?", "Play again?", JOptionPane.YES_NO_OPTION);
						if(option == JOptionPane.YES_OPTION)
						{
							setVisible(false);

							if(confFile.exists())
							{
								dispose();
								ConfigPanel.getInstance().LoadConfig(confFile);
							}
							else 
							{
								if(end())
									System.exit(0);
							}
						}
					}
				}
				//print current game stateGame
				estado.setText(getstateGame());
				validate();
				repaint();
			}
		});
		panel.setFocusable(true);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);

		JButton btnNewGame = new JButton("Again");
		btnNewGame.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg1) {
				panel.setEnabled(false);
				File xFile = new File("src/Resource/manualMaze.txt");						
				File confFile = new File("src/Resource/newConfigurations.txt");						

				if(xFile.exists())
				{
					//Custom button text
					Object[] options = {"Yes, my maze AGAIN", "Yes, but generate it yourself!", "No!"};
					int option = JOptionPane.showOptionDialog(Game_GUI.getInstance(), "Do you really want to play a new game !?", "New Game",JOptionPane.YES_NO_OPTION , JOptionPane.QUESTION_MESSAGE,null,options,options[2]);

					if(option== 0)
					{
						dispose();
						Game_Loop.getInstance().Load(xFile);
						Game_GUI.setGame_finished(false); //reset endgame condition
						panel.validate();
						panel.setVisible(true);
						panel.setEnabled(true);
						panel.grabFocus();	
						return;

					}
					if(option == 1)
					{
						panel.setVisible(false);
						Game_GUI.getInstance().setstateGame("");
						//estado.setText("");

						if(confFile.exists())
						{
							dispose();
							panel.setVisible(false);
							ConfigPanel.getInstance().LoadConfig(confFile);
							panel.validate();
							panel.repaint();
							panel.setVisible(true);
							panel.setEnabled(true);
							panel.grabFocus();
						}
						return;

					}
					else if(option==2) //0 in case of messageDialog(meaning failure to read), 2 means clicked "No!"
						if (end()) 
							System.exit(0);
						else
						{
							//Possible error
							JOptionPane.showMessageDialog(null, "Unexpected input " + option);
						}
				}
				else 
				{
					int option = JOptionPane.showConfirmDialog(Game_GUI.getInstance(), "Do you really want to play a new game !?", "Play again?", JOptionPane.YES_NO_OPTION);
					if(option == JOptionPane.YES_OPTION)
					{
						setVisible(false);

						if(confFile.exists())
						{
							dispose();
							ConfigPanel.getInstance().LoadConfig(confFile);
						}
						else 
						{
							if(end())
								System.exit(0);
						}
					}
				}
			}
		});
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));
		panel_1.add(btnNewGame);

		JButton btnExit = new JButton("Exit");
		btnExit.setAlignmentX(Component.RIGHT_ALIGNMENT);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				if(end())
					System.exit(0);
			}
		});
		//btnExit.setBounds(65-5, 5, 55, 23);
		panel_1.add(btnExit);

	}

	/**
	 * Initiates the game in graphical mode with content 
	 * stored in other classes.
	 */
	public void initGame() {
		//Initializing game parameters
		Labirynth.getInstance().generate(Game_GUI.getInstance().getDungeon_size(), Game_GUI.getInstance().standard_maze);
		Game_Loop.getInstance().enemies = new Dragon[Game_GUI.getInstance().number_dragons];
		for(int i=0; i<number_dragons;i++)
		{//add all dragons
			Dragon auxDragon = new Dragon();
			Game_Loop.getInstance().enemies[i]= auxDragon.init(Game_Loop.getInstance().getDragon_mode(), Game_Loop.getInstance());
			Game_Loop.getInstance().elements.add(auxDragon);
		}
		Hero auxHero = new Hero();
		Game_Loop.getInstance().HERO=auxHero.init(Game_Loop.getInstance());
		Game_Loop.getInstance().elements.add(auxHero);
		Sword auxSword = new Sword();
		Game_Loop.getInstance().SWORD=auxSword.init(Game_Loop.getInstance());
		Game_Loop.getInstance().elements.add(auxSword);
	}

	/**
	 * Resets game, and changes necessary data according to parameters
	 *
	 * @param dungeon_size the dungeon_size
	 * @param number_dragons the number_dragons
	 * @param standard_maze
	 */
	public void resetGame(int dungeon_size, int number_dragons, boolean standard_maze) {

		Labirynth.generated_maze = null;
		Labirynth.getInstance().generate(dungeon_size, standard_maze);
		Game_Loop.getInstance().elements.clear();
		Game_Loop.getInstance().enemies = new Dragon[number_dragons];

		for(int i=0; i<number_dragons;i++)
		{//add all dragons
			Dragon auxDragon = new Dragon();
			Game_Loop.getInstance().enemies[i]= auxDragon.init(Game_Loop.getInstance().getDragon_mode(), Game_Loop.getInstance());
			Game_Loop.getInstance().elements.add(auxDragon);
		}
		Hero auxHero = new Hero();
		Game_Loop.getInstance().HERO=auxHero.init(Game_Loop.getInstance());
		Game_Loop.getInstance().elements.add(auxHero);
		Sword auxSword = new Sword();
		Game_Loop.getInstance().SWORD=auxSword.init(Game_Loop.getInstance());
		Game_Loop.getInstance().elements.add(auxSword);
	}

	/**
	 * End, function used to eliminate temporary files.
	 *
	 * @return true, if successful
	 */
	private boolean end()
	{
		//Custom button text
		Object[] options = {"OK"};
		int option = JOptionPane.showOptionDialog(this,"Hope you enjoyed the game!","Game Over", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
		if(option ==0)
		{
			//delete temp files
			String tempFileMazeManual = "src/Resource/manualMaze.txt";
			String tempFileConfig= "src/Resource/newConfigurations.txt";
			//Delete if tempFile exists
			File fileTemp = new File(tempFileMazeManual);

			if (fileTemp.exists()){
				fileTemp.delete();
			}

			fileTemp = new File(tempFileConfig);
			if (fileTemp.exists()){
				fileTemp.delete();
			}
			return true;
		}
		return false;
	}

	/**
	 * Sets the command keys, with the array received in parameter
	 *
	 * @param commandKeys the new keys
	 */
	public void setKeys(int[] commandKeys) {
		setUpKey(commandKeys[0]);
		setDownKey(commandKeys[1]);
		setLeftKey(commandKeys[2]);
		setRightKey(commandKeys[3]);
		setEagleKey(commandKeys[4]);
	}

	/**
	 * Any key different. 
	 * Function used to test if any key is different to the current one
	 *  
	 * @param test the new keys to be used
	 * @return true, if successful -> at least 1 key is different indicating a need to change the command keys
	 */
	public boolean anyKeyDifferent(int[] test)
	{
		if(test[0]==upKey&&test[1]==downKey&&test[2]==leftKey&&test[3]==rightKey&&test[4]==eagleKey)
			return false;
		else 
			return true;
	}

	/**
	 * Gets the dungeon_size.
	 *
	 * @return the dungeon_size
	 */
	public int getDungeon_size() {
		return dungeon_size;
	}

	/**
	 * Sets the dungeon_size.
	 *
	 * @param tamanho the new dungeon_size
	 */
	public void setDungeon_size(int tamanho) {
		dungeon_size = tamanho;
	}

	/**
	 * Checks if is standard_maze.
	 *
	 * @return true, if is standard_maze
	 */
	public boolean isStandard_maze() {
		return standard_maze;
	}

	/**
	 * Sets the standard_maze.
	 *
	 * @param standard the new standard_maze
	 */
	public void setStandard_maze(boolean standard) {
		standard_maze = standard;
	}

	/**
	 * Gets the number_dragons.
	 *
	 * @return the number_dragons
	 */
	public int getNumber_dragons() {
		return number_dragons;
	}

	/**
	 * Sets the number_dragons.
	 *
	 * @param nDragons the new number_dragons
	 */
	public void setNumber_dragons(int nDragons) {
		number_dragons = nDragons;
	}

	/**
	 * Checks if is game_finished.
	 *
	 * @return true, if is game_finished
	 */
	public static boolean isGame_finished() {
		return game_finished;
	}

	/**
	 * Sets the game_finished.
	 *
	 * @param done the new game_finished
	 */
	public static void setGame_finished(boolean done) {
		game_finished = done;
	}

	/**
	 * Gets the Game State
	 *
	 * @return the Game State
	 */
	public String getstateGame() {
		return stateGame;
	}

	/**
	 * Sets the Game State
	 *
	 * @param currentState the new Game State
	 */
	public void setstateGame(String currentState) {
		stateGame = currentState;
	}

	/**
	 * Gets the up key.
	 *
	 * @return the up key
	 */
	public int getUpKey() {
		return upKey;
	}

	/**
	 * Sets the up key.
	 *
	 * @param upKey the new up key
	 */
	public void setUpKey(int upKey) {
		this.upKey = upKey;
	}

	/**
	 * Gets the left key.
	 *
	 * @return the left key
	 */
	public int getLeftKey() {
		return leftKey;
	}

	/**
	 * Sets the left key.
	 *
	 * @param leftKey the new left key
	 */
	public void setLeftKey(int leftKey) {
		this.leftKey = leftKey;
	}

	/**
	 * Gets the down key.
	 *
	 * @return the down key
	 */
	public int getDownKey() {
		return downKey;
	}

	/**
	 * Sets the down key.
	 *
	 * @param downKey the new down key
	 */
	public void setDownKey(int downKey) {
		this.downKey = downKey;
	}

	/**
	 * Gets the right key.
	 *
	 * @return the right key
	 */
	public int getRightKey() {
		return rightKey;
	}

	/**
	 * Sets the right key.
	 *
	 * @param rightKey the new right key
	 */
	public void setRightKey(int rightKey) {
		this.rightKey = rightKey;
	}

	/**
	 * Gets the eagle key.
	 *
	 * @return the eagle key
	 */
	public int getEagleKey() {
		return eagleKey;
	}

	/**
	 * Sets the eagle key.
	 *
	 * @param eagleKey the new eagle key
	 */
	public void setEagleKey(int eagleKey) {
		this.eagleKey = eagleKey;
	}
}