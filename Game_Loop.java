import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class Game_Loop {

//	public static Labirynth MAZE=null;
	public Vector<Game_Elements> elementos = new Vector<Game_Elements>();
	public Sword ESPADA=null;
	public Hero HEROI=null;
	public Dragon[] inimigos= null;
	public int dragon_mode =0;

	public static void main(String[] args) 
	{
		Game_Loop jogo = new Game_Loop();

		Scanner input = new Scanner(System.in);
		int dungeon_size=0;
		int number_dragons=0;
		Boolean standard_maze=true; //maze option of generation (standard 10x10 (false) ) or randomly generation(true)

		initGame(jogo, input, dungeon_size, number_dragons, standard_maze);

		print(jogo);

		String s;
		outerloop:
			do {
				s=input.next();

				jogo.HEROI.heroMovement(s,Labirynth.getInstance());

				for(int i=0; i<jogo.inimigos.length;i++) //dragon related movements
				{
					if(jogo.dragon_mode == 1) //sleeping
					{
						jogo.inimigos[i].symbol='d';
					}
					else if(jogo.dragon_mode==2) //awake
						jogo.inimigos[i].symbol = 'D';
					else //dragon mode == 3 which happens
					{
						Random rand = new Random();
						int sym = rand.nextInt(2);
						switch (sym) {
						case 0:
							//dragon sleeps
							jogo.inimigos[i].symbol='d';
							break;
						case 1:
							//dragon moves
							jogo.inimigos[i].symbol='D';
							break;
						}
					}
					if(jogo.inimigos[i].symbol =='D' && !jogo.inimigos[i].dead) //if dragon is awake and alive MOVE
						jogo.inimigos[i].dragonMovement(jogo);

					if( jogo.inimigos[i].x == jogo.ESPADA.x && jogo.inimigos[i].y == jogo.ESPADA.y && !jogo.ESPADA.used)
					{
						jogo.inimigos[i].symbol = 'F';
					}

					if (jogo.nextToDragon(jogo.HEROI, jogo.inimigos[i]) && !jogo.inimigos[i].dead)	
					{
						if (jogo.HEROI.symbol == 'A') //se armado
						{
							System.out.println("YOU HAVE DEFEATED A DRAGON");
							jogo.inimigos[i].dead = true;
						}
						else if (jogo.inimigos[1].symbol == 'd') //sleeping dragon and implicitly hero is not armed 
						{
							System.out.println("Your lucky the dragon is sleeping! Get a sword to kill him \n");
						}
						else
						{
							System.out.println("THE DRAGON DEFEATS YOU");
							jogo.HEROI.dead = true;
							break outerloop;
						}
					}
				}
				//end of dragon-related conditions & movements
				
				//eagle-related conditions & functions
				if (jogo.HEROI.isEagle_alive() && jogo.HEROI.isEagle_sent()) //eagle is alive and set to get sword (implicitly hero is unarmed and alive aswell)
				{
					jogo.HEROI.eagleMovement(jogo);
				}
				else if(!jogo.HEROI.isEagle_sent()) //eagle can get sword but hero hasnt sent her (so no action), eagle on top of hero
				{
					jogo.HEROI.setEagle_x(jogo.HEROI.x);
					jogo.HEROI.setEagle_y(jogo.HEROI.y);
				}
				
				//Hero only related conditions
				if(jogo.HEROI.y == Labirynth.getInstance().EXIT_Y && jogo.HEROI.x == Labirynth.getInstance().EXIT_X && jogo.HEROI.symbol == 'A')
				{
					System.out.println("YOU WON!");
					break;
				}
				else if(jogo.HEROI.y == Labirynth.getInstance().EXIT_Y && jogo.HEROI.x == Labirynth.getInstance().EXIT_X && jogo.HEROI.symbol == 'H')
				{//will depend on which side wall exit is in
					jogo.HEROI.x--;
				}
				else if(jogo.HEROI.symbol == 'H' && jogo.HEROI.x == jogo.ESPADA.x && jogo.HEROI.y == jogo.ESPADA.y && !jogo.ESPADA.used)
				{
					System.out.println("YOU HAVE PICKED UP THE SWORD");
					jogo.HEROI.symbol = 'A';
					jogo.ESPADA.used=true;
				}

				print(jogo);

			} while (s != "q");

		print(jogo);
		input.close(); //so no leaks!
	}

	private static void initGame(Game_Loop jogo, Scanner input, int dungeon_size, int number_dragons, boolean standard_maze) {
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

	private static void print(Game_Loop jogo)
	{
		//print Game Elements
		Boolean dragon_placed = false;
		for(int i=0;i<Labirynth.getInstance().COL_SIZE;i++)
		{
			for (int j = 0; j < Labirynth.getInstance().ROW_SIZE; j++) 
			{
				for(int z = 0; z<jogo.inimigos.length; z++)
				{
					if(jogo.inimigos[z].y ==i && jogo.inimigos[z].x==j && !jogo.inimigos[z].dead)
					{
						System.out.print(jogo.inimigos[z].symbol + " "); //pode estar a dormir
						dragon_placed = true;
						break; //dragon placement cycle
					}
					else {
						dragon_placed = false;
					}
				}
				if(!dragon_placed)
				{
					if(jogo.HEROI.y==i && jogo.HEROI.x==j && !jogo.HEROI.dead)
					{
						System.out.print(jogo.HEROI.symbol + " ");
					}
					else if(jogo.ESPADA.y==i &&jogo.ESPADA.x==j && !jogo.ESPADA.used)
					{
						System.out.print(jogo.ESPADA.symbol + " ");
					}
					else if (i == Labirynth.getInstance().EXIT_Y && j == Labirynth.getInstance().EXIT_X && i==jogo.HEROI.getEagle_y() && j==jogo.HEROI.getEagle_x()) //eagle is in exit square for some reason
						System.out.print(jogo.HEROI.getEagle() +" ");
					else if (i == Labirynth.getInstance().EXIT_Y && j == Labirynth.getInstance().EXIT_X)
						System.out.print("S ");
					else if(Labirynth.getInstance().getGenerated_maze()[i][j].filled && i==jogo.HEROI.getEagle_y() && j==jogo.HEROI.getEagle_x())
						System.out.print(jogo.HEROI.getEagle() +" ");
					else if (Labirynth.getInstance().getGenerated_maze()[i][j].filled)
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

	public boolean nextToDragon(Hero h, Dragon d)
	{
		if( h.x  == d.x && h.y - 1 == d.y && !d.dead)
			return true;
		else if ( h.x == d.x && h.y + 1 == d.y && !d.dead)
			return true;
		else if ( h.x + 1 ==d.x && h.y == d.y && !d.dead)
			return true;
		else if ( h.x - 1 == d.x && h.y == d.y && !d.dead)
			return true;
		else if( h.x  == d.x && h.y == d.y && !d.dead)
			return true;
		else
			return false;
	}

	public boolean nextToAnyDragon(int test_x, int test_y)
	{
		for(int i=0; i<inimigos.length;i++)
		{
			if( test_x  == inimigos[i].x && test_y - 1 == inimigos[i].y && !inimigos[i].dead)
				return true;
			else if ( test_x == inimigos[i].x && test_y + 1 == inimigos[i].y && !inimigos[i].dead)
				return true;
			else if ( test_x + 1 ==inimigos[i].x && test_y == inimigos[i].y && !inimigos[i].dead)
				return true;
			else if ( test_x - 1 == inimigos[i].x && test_y == inimigos[i].y && !inimigos[i].dead)
				return true;
			else if( test_x  == inimigos[i].x && test_y == inimigos[i].y && !inimigos[i].dead)
				return true;
		}
		return false;
	}

	public boolean ocupied_byElements(int check_x, int check_y)
	{
		for(int i=0; i<elementos.size();i++)
		{
			if(elementos.get(i).x==check_x && elementos.get(i).y==check_y)
				return true;
		}
		return false;
	}
}





