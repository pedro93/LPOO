package CUI;

import java.util.InputMismatchException;
import java.util.Scanner;

import Game_Logic.Dragon;
import Game_Logic.Game_Loop;
import Game_Logic.Hero;
import maze.Labirynth;
import Game_Logic.Sword;


// TODO: Auto-generated Javadoc
/**
 * The Class Game_CUI.
 */
public class Game_CUI {

	/** 
	 * The cui. 
	 * Object of this class used for singleton purposes
	 * */
	private static Game_CUI CUI = null;
	
	/**
	 * Instantiates a new game_ cui.
	 */
	private Game_CUI() {}
	
	/**
	 * Gets the single instance of Game_CUI.
	 *
	 * @return single instance of Game_CUI
	 */
	public static Game_CUI getInstance() {
		if (CUI == null) 
			CUI = new Game_CUI();
		return CUI;
	}
	
	/**
	 * Inits the game.
	 *
	 * Creates a menu in console used to personalize a the game parameters
	 * in accordance to the user's will
	 *
	 * @param game the game instance
	 * @param input the input to be used by the user
	 * @param dungeon_size the dungeon_size
	 * @param number_dragons the number_dragons
	 * @param standard_maze the standard_maze
	 */
	public void initGame(Game_Loop game, Scanner input, int dungeon_size, int number_dragons, boolean standard_maze) {
		System.out.println("Welcome to Dragon's dungeon!");
		String user_choice;

		do {
			System.out.print("Would like to play the standard maze (Y/N:) ");
			user_choice=input.next();
		} 
		while ( (!user_choice.equals("y") && !user_choice.equals("Y")) && (!user_choice.equals("n") && !user_choice.equals("N")));

		if(user_choice.equals("y") || user_choice.equals("Y")) //standard maze
		{
			standard_maze=true;
			dungeon_size=10;
		}
		else //random maze
		{
			standard_maze=false;
			do 
			{
				try {
					System.out.println("Please select the size of the dungeon you would like to play (minimum is 7): ");
					dungeon_size=input.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Wrong input, please insert an integer");
					input.next();
				}
			} 
			while (dungeon_size<7 || dungeon_size >200);

		}
		
		do {
			try {
				System.out.println("How many dragons would you like to fight? ");
				number_dragons = input.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Wrong input, please insert an integer");
				input.next();
			}
		} while (number_dragons <1 || number_dragons>Math.floor(dungeon_size/5));
		
		do
		{
			try {
				System.out.println("Please select the Game mode you would like:");
				System.out.println("1-Imobile dragon");
				System.out.println("2-Random moving dragon");
				System.out.println("3-Random moving or sleeping dragon");
				game.setDragon_mode(input.nextInt());
			} catch (InputMismatchException e) {
				System.out.println("Wrong input, please insert an integer");
				input.next();
			}
		} 
		while (!(game.getDragon_mode()==1 || game.getDragon_mode()==2 || game.getDragon_mode() ==3));
		System.out.println();

		//Initializing game parameters

		Labirynth.getInstance().generate(dungeon_size, standard_maze);

		//initialize Game elements (positions, booleans, game_modes, symbols....)
		game.enemies = new Dragon[number_dragons];

		for(int i=0; i<number_dragons;i++)
		{//add all dragons
			Dragon auxDragon = new Dragon();
			game.enemies[i]= auxDragon.init(game.getDragon_mode(), game);
			game.elements.add(auxDragon);
		}
		Hero auxHero = new Hero();
		game.HERO=auxHero.init(game);
		game.elements.add(auxHero);
		Sword auxSword = new Sword();
		game.SWORD=auxSword.init(game);
		game.elements.add(auxSword);
	}	
	
	/**
	 * Prints the Game.
	 * 
	 * Prints into the console the current game state,
	 * used to show the user the game
	 *
	 * @param game the game instance
	 * 	 */
	public void print(Game_Loop game)
	{
		//print Game Elements
		Boolean dragon_placed = false;
		for(int i=0;i<Labirynth.getInstance().COL_SIZE;i++)
		{
			for (int j = 0; j < Labirynth.getInstance().ROW_SIZE; j++) 
			{
				for(int z = 0; z<game.enemies.length; z++)
				{
					if(game.enemies[z].getY() ==i && game.enemies[z].getX()==j && !game.enemies[z].isDead())
					{
						System.out.print(game.enemies[z].getSymbol() + " "); //pode estar a dormir
						dragon_placed = true;
						break; //dragon placement cycle
					}
					else {
						dragon_placed = false;
					}
				}
				if(!dragon_placed)
				{
					if(game.HERO.getY()==i && game.HERO.getX()==j && !game.HERO.isDead())
					{
						System.out.print(game.HERO.getSymbol() + " ");
					}
					else if(game.SWORD.getY()==i &&game.SWORD.getX()==j && !game.SWORD.isUsed())
					{
						System.out.print(game.SWORD.getSymbol() + " ");
					}
					else if (i == Labirynth.getInstance().EXIT_Y && j == Labirynth.getInstance().EXIT_X && i==game.HERO.getEagle_y() && j==game.HERO.getEagle_x()) //eagle is in exit square for some reason
						System.out.print(game.HERO.getEagle() +"S");
					else if (i == Labirynth.getInstance().EXIT_Y && j == Labirynth.getInstance().EXIT_X)
						System.out.print("S ");
					else if(Labirynth.getInstance().getGenerated_maze()[i][j].isFilled() && i==game.HERO.getEagle_y() && j==game.HERO.getEagle_x())
						System.out.print(game.HERO.getEagle() +"X");
					else if (Labirynth.getInstance().getGenerated_maze()[i][j].isFilled())
						System.out.print("X ");
					else if (i==game.HERO.getEagle_y() && j==game.HERO.getEagle_x())
						System.out.print(game.HERO.getEagle() +" ");
					else
						System.out.print("  ");
				}
			}
			System.out.println();
		}
	}
	
}
