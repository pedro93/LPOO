package CUI;

import java.util.Scanner;

import Game_Logic.Dragon;
import Game_Logic.Game_Loop;
import Game_Logic.Hero;
import maze.Labirynth;
import Game_Logic.Sword;


public class Game_CUI {

	private static Game_CUI CUI = null;
	private Game_CUI() {}
	public static Game_CUI getInstance() {
		if (CUI == null) 
			CUI = new Game_CUI();
		return CUI;
	}
	
	public void initGame(Game_Loop jogo, Scanner input, int dungeon_size, int number_dragons, boolean standard_maze) {
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
			do {
				System.out.println("Please select the size of the dungeon you would like to play (minimum is 7): ");
				dungeon_size=input.nextInt();
			} 
			while (dungeon_size<7);

		}
		
		do {
			System.out.println("How many dragons would you like to fight? ");
			number_dragons = input.nextInt();
		} while (number_dragons <1 && number_dragons<Math.floor(dungeon_size/5));
		
		do
		{
			System.out.println("Please select the Game mode you would like:");
			System.out.println("1-Sleeping dragon");
			System.out.println("2-Random moving dragon");
			System.out.println("3-Random moving or sleeping dragon");
			jogo.dragon_mode=input.nextInt();
		} 
		while (!(jogo.dragon_mode==1 || jogo.dragon_mode==2 || jogo.dragon_mode ==3));
		System.out.println();

		//Initializing game parameters
		Labirynth.getInstance().ROW_SIZE=dungeon_size-2;
		Labirynth.getInstance().COL_SIZE=dungeon_size-2;

		Labirynth.getInstance().generate(dungeon_size, standard_maze);

		Labirynth.getInstance().ROW_SIZE=dungeon_size;
		Labirynth.getInstance().COL_SIZE=dungeon_size;
		
		//initialize Game elements (positions, booleans, game_modes, symbols....)
		jogo.inimigos = new Dragon[number_dragons];

		for(int i=0; i<number_dragons;i++)
		{//add all dragons
			Dragon auxDragon = new Dragon();
			jogo.inimigos[i]= auxDragon.init(jogo.dragon_mode, jogo);
			jogo.elementos.add(auxDragon);
		}
		Hero auxHero = new Hero();
		jogo.HEROI=auxHero.init(jogo);
		jogo.elementos.add(auxHero);
		Sword auxSword = new Sword();
		jogo.ESPADA=auxSword.init(jogo);
		jogo.elementos.add(auxSword);
	}	
	
	public void print(Game_Loop jogo)
	{
		//print Game Elements
		Boolean dragon_placed = false;
		for(int i=0;i<Labirynth.getInstance().COL_SIZE;i++)
		{
			for (int j = 0; j < Labirynth.getInstance().ROW_SIZE; j++) 
			{
				for(int z = 0; z<jogo.inimigos.length; z++)
				{
					if(jogo.inimigos[z].getY() ==i && jogo.inimigos[z].getX()==j && !jogo.inimigos[z].isDead())
					{
						System.out.print(jogo.inimigos[z].getSymbol() + " "); //pode estar a dormir
						dragon_placed = true;
						break; //dragon placement cycle
					}
					else {
						dragon_placed = false;
					}
				}
				if(!dragon_placed)
				{
					if(jogo.HEROI.getY()==i && jogo.HEROI.getX()==j && !jogo.HEROI.isDead())
					{
						System.out.print(jogo.HEROI.getSymbol() + " ");
					}
					else if(jogo.ESPADA.getY()==i &&jogo.ESPADA.getX()==j && !jogo.ESPADA.isUsed())
					{
						System.out.print(jogo.ESPADA.getSymbol() + " ");
					}
					else if (i == Labirynth.getInstance().EXIT_Y && j == Labirynth.getInstance().EXIT_X && i==jogo.HEROI.getEagle_y() && j==jogo.HEROI.getEagle_x()) //eagle is in exit square for some reason
						System.out.print(jogo.HEROI.getEagle() +" ");
					else if (i == Labirynth.getInstance().EXIT_Y && j == Labirynth.getInstance().EXIT_X)
						System.out.print("S ");
					else if(Labirynth.getInstance().getGenerated_maze()[i][j].isFilled() && i==jogo.HEROI.getEagle_y() && j==jogo.HEROI.getEagle_x())
						System.out.print(jogo.HEROI.getEagle() +" ");
					else if (Labirynth.getInstance().getGenerated_maze()[i][j].isFilled())
						System.out.print("X ");
					else if (i==jogo.HEROI.getEagle_y() && j==jogo.HEROI.getEagle_x())
						System.out.print(jogo.HEROI.getEagle() +" ");
					else
						System.out.print("  ");
				}
			}
			System.out.println();
		}
	}
	
}
