package Game_Logic;
import java.io.Serializable;
import java.util.Random;
import maze.Labirynth;

// TODO: Auto-generated Javadoc
/**
 * The Class Hero.
 * Class used by the user to interact with the game
 */
@SuppressWarnings("serial")
public class Hero extends Mobile implements Serializable{

	/** 
	 * The eagle. 
	 * The "object" eagle used in-game to fetch the sword on user command
	 * */
	private char eagle;
	
	/** 
	 * The eagle_alive.
	 * Indicates whether eagle is alive or not 
	 */
	private boolean eagle_alive;
	
	/**
	 * The eagle_x. 
	 * Eagle's x coordinate
	 * */
	private int eagle_x;
	
	/** 
	 * The eagle_y. 
	 * Eagle's y coordinate
	 * */
	private int eagle_y;
	
	/**
	 * The eagle_sent. 
	 * Indicates whether eagle has recieved command to fetch sword
	 * */
	private boolean eagle_sent;
	
	/**
	 * The last hero_x.
	 * X coordinate of hero position when eagle was sent  
	 * */
	private int lastHero_x;
	
	/**
	 * The last hero_y.
	 * Y coordinate of hero position when eagle was sent 
	 * */
	private int lastHero_y;

	/**
	 * Gets the last hero_x.
	 *
	 * @return the last hero_x
	 */
	public int getLastHero_x() {
		return lastHero_x;
	}

	/**
	 * Sets the last hero_x.
	 *
	 * @param lastHero_x the new last hero_x
	 */
	public void setLastHero_x(int lastHero_x) {
		this.lastHero_x = lastHero_x;
	}

	/**
	 * Gets the last hero_y.
	 *
	 * @return the last hero_y
	 */
	public int getLastHero_y() {
		return lastHero_y;
	}

	/**
	 * Sets the last hero_y.
	 *
	 * @param lastHero_y the new last hero_y
	 */
	public void setLastHero_y(int lastHero_y) {
		this.lastHero_y = lastHero_y;
	}

	//Geters and Seters
	/**
	 * Gets the eagle.
	 *
	 * @return the eagle
	 */
	public char getEagle() {
		return eagle;
	}

	/**
	 * Sets the eagle.
	 *
	 * @param eagle the new eagle
	 */
	public void setEagle(char eagle) {
		this.eagle = eagle;
	}

	/**
	 * Checks if is eagle_alive.
	 *
	 * @return true, if is eagle_alive
	 */
	public boolean isEagle_alive() {
		return eagle_alive;
	}

	/**
	 * Sets the eagle_alive.
	 *
	 * @param eagle_alive the new eagle_alive
	 */
	public void setEagle_alive(boolean eagle_alive) {
		this.eagle_alive = eagle_alive;
	}

	/**
	 * Gets the eagle_x.
	 *
	 * @return the eagle_x
	 */
	public int getEagle_x() {
		return eagle_x;
	}

	/**
	 * Sets the eagle_x.
	 *
	 * @param eagle_x the new eagle_x
	 */
	public void setEagle_x(int eagle_x) {
		this.eagle_x = eagle_x;
	}

	/**
	 * Gets the eagle_y.
	 *
	 * @return the eagle_y
	 */
	public int getEagle_y() {
		return eagle_y;
	}

	/**
	 * Sets the eagle_y.
	 *
	 * @param eagle_y the new eagle_y
	 */
	public void setEagle_y(int eagle_y) {
		this.eagle_y = eagle_y;
	}

	/**
	 * Checks if is eagle_sent.
	 *
	 * @return true, if is eagle_sent
	 */
	public boolean isEagle_sent() {
		return eagle_sent;
	}

	/**
	 * Sets the eagle_sent.
	 *
	 * @param eagle_sent the new eagle_sent
	 */
	public void setEagle_sent(boolean eagle_sent) {
		this.eagle_sent = eagle_sent;
	}
	
	/**
	 * Hero movement.
	 * Function used to move the hero 
	 * 
	 * @param s the string indicating the direction the hero will move to
	 * @param maze the maze will the game is acting on, used to control hero collisions against walls or door
	 */
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
			//else setEagle_sent(false);
			break;
		}
	}

	/**
	 * Inits.
	 * Initiates the hero with the specified parameters and returns it
	 * @param jogo the game instance
	 * @return the hero
	 */
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
		jogo.elements.add(auxHero);
		return auxHero;
	}
	
	/**
	 * InitTest.
	 * Initializes the hero with specific parameters suitable for testing (Junit) purposes
	 * @param jogo the game instance
	 * @return the hero
	 */
	public Hero initTest(Game_Loop jogo) {
		Hero auxHero=new Hero();
		auxHero.setDead(false);
		auxHero.setSymbol('H');
		auxHero.setX(1);
		auxHero.setY(1);
		auxHero.eagle = 'v';
		auxHero.eagle_alive = true;
		auxHero.eagle_x = auxHero.getX();
		auxHero.eagle_y = auxHero.getY();
		auxHero.setEagle_sent(false);
		auxHero.lastHero_x=0;
		auxHero.lastHero_y=0;
		jogo.elements.add(auxHero);
		return auxHero;
	}


	/**
	 * Eagle movement.
	 *
	 * Function used to move the eagle according to it's position relative to the sword's. 
	 *
	 * @param jogo the game instance
	 */
	public void eagleMovement(Game_Loop jogo) 
	{

		if(jogo.HERO.isDead()) //hero dead so cant send eagle
		{
			eagle = ' '; 
			eagle_sent = false;
			return;
		}

		if(jogo.SWORD.isUsed() && jogo.HERO.getSymbol() == 'A') // sword already picked up by hero(only possible Game element who can besides eagle)
		{
			eagle = ' ';
			eagle_sent = false;
			return;
		}

		if(eagle != 'y') //eagle doesnt have sword yet
		{
			if (eagle_x < jogo.SWORD.getX()) //not killed imediatly by dragons and must move right to get sword
			{
				eagle_x++;
			}
			else if(eagle_x > jogo.SWORD.getX()) //must go left
			{
				eagle_x--;
			}
			else if (eagle_y<jogo.SWORD.getY()) //eagle is in column and not dead yet and must go down
			{
				eagle_y++;
			}
			else if (eagle_y>jogo.SWORD.getY()) //in column, not dead and must go up
			{
				eagle_y--;
			}

			if(eagle_alive && eagle_x == jogo.SWORD.getX() && eagle_y == jogo.SWORD.getY() && !jogo.SWORD.isUsed() && eagle !='y') //eagle not dead, on top of sword location, sword has not yet been picked up, and eagle does not have sword with it(for whatever possible reason (possible redundancy))
			{
				if(!jogo.nextToAnyDragon(eagle_x, eagle_y))
				{
					jogo.SWORD.setUsed(true); //sword in the air cant be picked
					eagle = 'y';
					return;
				}
				else
				{
					jogo.SWORD.setUsed(false);
					eagle_alive = false;
					eagle = ' ';
					return;
				}
			}

		}
		else //return trip to hero
		{
			if(eagle_alive && eagle_x == jogo.HERO.lastHero_x && eagle_y == jogo.HERO.lastHero_y) //eagle not dead, in last know (to her) hero_location
			{
				if(jogo.nextToAnyDragon(eagle_x, eagle_y))
				{
					eagle_alive = false;
					eagle = ' ';
					jogo.SWORD.setSymbol('E');
					jogo.SWORD.setUsed(false);
					jogo.SWORD.setX(eagle_x);
					jogo.SWORD.setY(eagle_y);
					return;
				}
				else if (eagle_x == jogo.HERO.getX() && eagle_y==jogo.HERO.getY() && eagle_alive && !jogo.HERO.isDead()) //hero is where he last was when eagle left him and they are both alive
				{
					jogo.SWORD.setUsed(false);
					jogo.SWORD.setX(eagle_x);
					jogo.SWORD.setY(eagle_y);
					jogo.HERO.eagle_sent = false; //eagle done its job can return to master
				}
			}
			else if (eagle_x<jogo.HERO.lastHero_x) //not killed imediatly by dragons and must move right to get sword
			{
				eagle_x++;
			}
			else if(eagle_x>jogo.HERO.lastHero_x) //must go left
			{
				eagle_x--;
			}
			else if (eagle_y<jogo.HERO.lastHero_y) //eagle is in column and not dead yet and must go down
			{
				eagle_y++;
			}
			else if (eagle_y > jogo.HERO.lastHero_y) //in column, not dead and must go up
			{
				eagle_y--;
			}

		}

	}
}