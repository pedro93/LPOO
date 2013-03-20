package Game_Logic;
import java.util.Random;
import maze.Labirynth;

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
			if(!maze.getGenerated_maze()[getY()-1][getX()].isFilled() || (maze.EXIT_Y == getY()-1 && maze.EXIT_X == getX()) )
				setY(getY() - 1);
			break;
		case "s":
			if(!maze.getGenerated_maze()[getY()+1][getX()].isFilled() || (maze.EXIT_Y == getY()+1 && maze.EXIT_X == getX()) )
				setY(getY() + 1);
			break;
		case "a":
			if(!maze.getGenerated_maze()[getY()][getX()-1].isFilled() || (maze.EXIT_Y == getY() && maze.EXIT_X == getX()-1) )
				setX(getX() - 1);
			break;
		case "d":
			if(!maze.getGenerated_maze()[getY()][getX()+1].isFilled() || (maze.EXIT_Y == getY() && maze.EXIT_X == getX()+1))
				setX(getX() + 1);
			break;
		case "r": //send eagle
			if(getSymbol() != 'A' && !isDead() && !eagle_sent) //hero is unarmed and alive and eagle is ready to fly
			{
				setEagle_sent(true);
				lastHero_x = getX();
				lastHero_y = getY();
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
		auxHero.setDead(false);
		auxHero.setSymbol('H');
		do 
		{
			Random rand = new Random();
			auxHero.setX(rand.nextInt(Labirynth.getInstance().COL_SIZE-2)+1);
			auxHero.setY(rand.nextInt(Labirynth.getInstance().ROW_SIZE-2)+1);
		} while (Labirynth.getInstance().getGenerated_maze()[auxHero.getY()][auxHero.getX()].isFilled() || jogo.ocupied_byElements(auxHero.getX(), auxHero.getY()) || jogo.nextToAnyDragon(auxHero.getX(),auxHero.getY()));

		auxHero.eagle = 'v';
		auxHero.eagle_alive = true;
		auxHero.eagle_x = auxHero.getX();
		auxHero.eagle_y = auxHero.getY();
		auxHero.setEagle_sent(false);
		auxHero.lastHero_x=0;
		auxHero.lastHero_y=0;
		jogo.elementos.add(auxHero);
		return auxHero;
	}


	public void eagleMovement(Game_Loop jogo) 
	{

		if(jogo.HEROI.isDead()) //hero dead so cant send eagle
		{
			eagle = ' '; 
			eagle_sent = false;
			return;
		}

		if(jogo.ESPADA.isUsed() && jogo.HEROI.getSymbol() == 'A') // sword already picked up by hero(only possible Game element who can besides eagle)
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
			if (eagle_x < jogo.ESPADA.getX()) //not killed imediatly by dragons and must move right to get sword
			{
				eagle_x++;
			}
			else if(eagle_x > jogo.ESPADA.getX()) //must go left
			{
				eagle_x--;
			}
			else if (eagle_y<jogo.ESPADA.getY()) //eagle is in column and not dead yet and must go down
			{
				eagle_y++;
			}
			else if (eagle_y>jogo.ESPADA.getY()) //in column, not dead and must go up
			{
				eagle_y--;
			}

			if(eagle_alive && eagle_x == jogo.ESPADA.getX() && eagle_y == jogo.ESPADA.getY() && !jogo.ESPADA.isUsed() && eagle !='y') //eagle not dead, on top of sword location, sword has not yet been picked up, and eagle does not have sword with it(for whatever possible reason (possible redundancy))
			{
				if(!jogo.nextToAnyDragon(eagle_x, eagle_y))
				{
					jogo.ESPADA.setUsed(true);
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
					jogo.ESPADA.setSymbol('E');
					jogo.ESPADA.setUsed(false);
					jogo.ESPADA.setX(eagle_x);
					jogo.ESPADA.setY(eagle_y);
					System.out.println("!!!!!!!!!!!!!Eagle has died!!!!!!!!!!!");
					return;
				}
				else if (eagle_x == jogo.HEROI.getX() && eagle_y==jogo.HEROI.getY() && eagle_alive && !jogo.HEROI.isDead()) //hero is where he last was when eagle left him and they are both alive
				{
					jogo.HEROI.setSymbol('A');
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