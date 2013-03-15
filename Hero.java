import java.util.Random;


public class Hero extends Mobile {

	private char eagle;
	private boolean eagle_alive;
	private int eagle_x;
	private int eagle_y;
	private boolean eagle_sent;
	private int lastHero_x;
	private int lastHero_y;

	public int getLastHero_x() {
		return lastHero_x;
	}

	public void setLastHero_x(int lastHero_x) {
		this.lastHero_x = lastHero_x;
	}

	public int getLastHero_y() {
		return lastHero_y;
	}

	public void setLastHero_y(int lastHero_y) {
		this.lastHero_y = lastHero_y;
	}

	//Geters and Seters
	public char getEagle() {
		return eagle;
	}

	public void setEagle(char eagle) {
		this.eagle = eagle;
	}

	public boolean isEagle_alive() {
		return eagle_alive;
	}

	public void setEagle_alive(boolean eagle_alive) {
		this.eagle_alive = eagle_alive;
	}

	public int getEagle_x() {
		return eagle_x;
	}

	public void setEagle_x(int eagle_x) {
		this.eagle_x = eagle_x;
	}

	public int getEagle_y() {
		return eagle_y;
	}

	public void setEagle_y(int eagle_y) {
		this.eagle_y = eagle_y;
	}

	public void heroMovement(String s, Labirynth maze) {
		switch (s) {
		case "w":
			if(!maze.getGenerated_maze()[y-1][x].filled || (maze.EXIT_Y == y-1 && maze.EXIT_X == x) )
				y--;
			break;
		case "s":
			if(!maze.getGenerated_maze()[y+1][x].filled || (maze.EXIT_Y == y+1 && maze.EXIT_X == x) )
				y++;
			break;
		case "a":
			if(!maze.getGenerated_maze()[y][x-1].filled || (maze.EXIT_Y == y && maze.EXIT_X == x-1) )
				x--;
			break;
		case "d":
			if(!maze.getGenerated_maze()[y][x+1].filled || (maze.EXIT_Y == y && maze.EXIT_X == x+1))
				x++;
			break;
		case "r": //send eagle
			if(symbol != 'A' && !dead && !eagle_sent) //hero is unarmed and alive and eagle is ready to fly
			{
				setEagle_sent(true);
				lastHero_x = x;
				lastHero_y = y;
			}
			else
				setEagle_sent(false);
			break;
		}
	}

	public boolean isEagle_sent() {
		return eagle_sent;
	}

	public void setEagle_sent(boolean eagle_sent) {
		this.eagle_sent = eagle_sent;
	}

	public Hero init(Game_Loop jogo) {
		Hero auxHero=new Hero();
		auxHero.dead=false;
		auxHero.symbol='H';
		do 
		{
			Random rand = new Random();
			auxHero.x=rand.nextInt(Labirynth.getInstance().COL_SIZE-2)+1;
			auxHero.y=rand.nextInt(Labirynth.getInstance().ROW_SIZE-2)+1;
		} while (Labirynth.getInstance().getGenerated_maze()[auxHero.y][auxHero.x].filled || jogo.ocupied_byElements(auxHero.x, auxHero.y) || jogo.nextToAnyDragon(auxHero.x,auxHero.y));

		auxHero.eagle = 'v';
		auxHero.eagle_alive = true;
		auxHero.eagle_x = auxHero.x;
		auxHero.eagle_y = auxHero.y;
		auxHero.setEagle_sent(false);
		auxHero.lastHero_x=0;
		auxHero.lastHero_y=0;
		jogo.elementos.add(auxHero);
		return auxHero;
	}


	public void eagleMovement(Game_Loop jogo) 
	{

		if(jogo.HEROI.dead) //hero dead so cant send eagle
		{
			eagle = ' '; 
			eagle_sent = false;
			return;
		}

		if(jogo.ESPADA.used && jogo.HEROI.symbol == 'A') // sword already picked up by hero(only possible Game element who can besides eagle)
		{
			eagle = ' ';
			eagle_sent = false;
			return;
		}

		if(eagle != 'y') //eagle doesnt have sword yet
		{/*
			if(eagle_alive && eagle_x == jogo.ESPADA.x && eagle_y == jogo.ESPADA.y && !jogo.ESPADA.used && eagle !='y') //eagle not dead, on top of sword location, sword has not yet been picked up, and eagle does not have sword with it(for whatever possible reason (possible redundancy))
			{
				jogo.ESPADA.used=true;
				eagle = 'y';
				return;
			}*/
			if (eagle_x < jogo.ESPADA.x) //not killed imediatly by dragons and must move right to get sword
			{
				eagle_x++;
			}
			else if(eagle_x > jogo.ESPADA.x) //must go left
			{
				eagle_x--;
			}
			else if (eagle_y<jogo.ESPADA.y) //eagle is in column and not dead yet and must go down
			{
				eagle_y++;
			}
			else if (eagle_y>jogo.ESPADA.y) //in column, not dead and must go up
			{
				eagle_y--;
			}

			if(eagle_alive && eagle_x == jogo.ESPADA.x && eagle_y == jogo.ESPADA.y && !jogo.ESPADA.used && eagle !='y') //eagle not dead, on top of sword location, sword has not yet been picked up, and eagle does not have sword with it(for whatever possible reason (possible redundancy))
			{
				if(!jogo.nextToAnyDragon(eagle_x, eagle_y))
				{
					jogo.ESPADA.used=true;
					eagle = 'y';
					return;
				}
				else
				{
					eagle_alive = false;
					eagle = ' ';
					System.out.println("!!!!!!!!!!!!!Eagle has died!!!!!!!!!!!");
					return;
				}
			}

		}
		else //return trip to hero
		{
			if(eagle_alive && eagle_x == jogo.HEROI.lastHero_x && eagle_y == jogo.HEROI.lastHero_y) //eagle not dead, in last know (to her) hero_location
			{
				if(!jogo.nextToAnyDragon(eagle_x, eagle_y))
				{
					eagle_alive = false;
					eagle = ' ';
					jogo.ESPADA.symbol = 'E';
					jogo.ESPADA.used = false;
					jogo.ESPADA.x=eagle_x;
					jogo.ESPADA.y=eagle_y;
					System.out.println("!!!!!!!!!!!!!Eagle has died!!!!!!!!!!!");
					return;
				}
				else if (eagle_x == jogo.HEROI.x && eagle_y==jogo.HEROI.y && eagle_alive && !jogo.HEROI.dead) //hero is where he last was when eagle left him and they are both alive
				{
					jogo.HEROI.symbol = 'A';
					eagle_sent = false; //eagle done its job can return to master
				}
			}
			else if (eagle_x<jogo.HEROI.lastHero_x) //not killed imediatly by dragons and must move right to get sword
			{
				eagle_x++;
			}
			else if(eagle_x>jogo.HEROI.lastHero_x) //must go left
			{
				eagle_x--;
			}
			else if (eagle_y<jogo.HEROI.lastHero_y) //eagle is in column and not dead yet and must go down
			{
				eagle_y++;
			}
			else if (eagle_y > jogo.HEROI.lastHero_y) //in column, not dead and must go up
			{
				eagle_y--;
			}

		}

	}
}