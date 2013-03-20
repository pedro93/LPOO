package Game_Logic;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import CUI.Game_CUI;
import GUI.Game_GUI;
import maze.Labirynth;

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
		
		
		//modo gráfico
		Game_GUI.getInstance().initGame();
		
		//modo texto
		/*
		Game_CUI.getInstance().initGame(jogo, input, dungeon_size, number_dragons, standard_maze);
		Game_CUI.getInstance().print(jogo);
		*/
		
		
		String s;
		outerloop:
			do {
				s=input.next();

				jogo.HEROI.heroMovement(s,Labirynth.getInstance());

				for(int i=0; i<jogo.inimigos.length;i++) //dragon related movements
				{
					if(jogo.dragon_mode == 1) //sleeping
					{
						jogo.inimigos[i].setSymbol('d');
					}
					else if(jogo.dragon_mode==2) //awake
						jogo.inimigos[i].setSymbol('D');
					else //dragon mode == 3 which happens
					{
						Random rand = new Random();
						int sym = rand.nextInt(2);
						switch (sym) {
						case 0:
							//dragon sleeps
							jogo.inimigos[i].setSymbol('d');
							break;
						case 1:
							//dragon moves
							jogo.inimigos[i].setSymbol('D');
							break;
						}
					}
					if(jogo.inimigos[i].getSymbol() =='D' && !jogo.inimigos[i].isDead()) //if dragon is awake and alive MOVE
						jogo.inimigos[i].dragonMovement(jogo);

					if( jogo.inimigos[i].getX() == jogo.ESPADA.getX() && jogo.inimigos[i].getY() == jogo.ESPADA.getY() && !jogo.ESPADA.isUsed())
					{
						jogo.inimigos[i].setSymbol('F');
					}

					if (jogo.nextToDragon(jogo.HEROI, jogo.inimigos[i]) && !jogo.inimigos[i].isDead())	
					{
						if (jogo.HEROI.getSymbol() == 'A') //se armado
						{
							System.out.println("YOU HAVE DEFEATED A DRAGON");
							jogo.inimigos[i].setDead(true);
						}
						else if (jogo.inimigos[1].getSymbol() == 'd') //sleeping dragon and implicitly hero is not armed 
						{
							System.out.println("Your lucky the dragon is sleeping! Get a sword to kill him \n");
						}
						else
						{
							System.out.println("THE DRAGON DEFEATS YOU");
							jogo.HEROI.setDead(true);
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
					jogo.HEROI.setEagle_x(jogo.HEROI.getX());
					jogo.HEROI.setEagle_y(jogo.HEROI.getY());
				}

				//Hero only related conditions
				if(jogo.HEROI.getY() == Labirynth.getInstance().EXIT_Y && jogo.HEROI.getX() == Labirynth.getInstance().EXIT_X && jogo.HEROI.getSymbol() == 'A')
				{
					System.out.println("YOU WON!");
					break;
				}
				else if(jogo.HEROI.getY() == Labirynth.getInstance().EXIT_Y && jogo.HEROI.getX() == Labirynth.getInstance().EXIT_X && jogo.HEROI.getSymbol() == 'H')
				{//will depend on which side wall exit is in
					jogo.HEROI.setX(jogo.HEROI.getX() - 1);
				}
				else if(jogo.HEROI.getSymbol() == 'H' && jogo.HEROI.getX() == jogo.ESPADA.getX() && jogo.HEROI.getY() == jogo.ESPADA.getY() && !jogo.ESPADA.isUsed())
				{
					System.out.println("YOU HAVE PICKED UP THE SWORD");
					jogo.HEROI.setSymbol('A');
					jogo.ESPADA.setUsed(true);
				}

				Game_CUI.getInstance().print(jogo);

			} while (s != "q");

		Game_CUI.getInstance().print(jogo);
		input.close(); //so no leaks!
	}


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

	public boolean nextToAnyDragon(int test_x, int test_y)
	{
		for(int i=0; i<inimigos.length;i++)
		{
			if( test_x  == inimigos[i].getX() && test_y - 1 == inimigos[i].getY() && !inimigos[i].isDead())
				return true;
			else if ( test_x == inimigos[i].getX() && test_y + 1 == inimigos[i].getY() && !inimigos[i].isDead())
				return true;
			else if ( test_x + 1 ==inimigos[i].getX() && test_y == inimigos[i].getY() && !inimigos[i].isDead())
				return true;
			else if ( test_x - 1 == inimigos[i].getX() && test_y == inimigos[i].getY() && !inimigos[i].isDead())
				return true;
			else if( test_x  == inimigos[i].getX() && test_y == inimigos[i].getY() && !inimigos[i].isDead())
				return true;
		}
		return false;
	}

	public boolean ocupied_byElements(int check_x, int check_y)
	{
		for(int i=0; i<elementos.size();i++)
		{
			if(elementos.get(i).getX()==check_x && elementos.get(i).getY()==check_y)
				return true;
		}
		return false;
	}
}





