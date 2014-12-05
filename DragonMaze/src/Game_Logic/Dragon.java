package Game_Logic;
import java.io.Serializable;
import java.util.Random;
import maze.Labirynth;

// TODO: Auto-generated Javadoc
/**
 * The Class Dragon.
 * The "enemy" class of the user
 * 
 */
@SuppressWarnings("serial")
public class Dragon extends Mobile implements Serializable{
	
	/**
	 * The mode.
	 * Indicates the behaviour of each dragon object  
	 * */
	private int mode; //dragon game mode

	/**
	 * Sets the mode.
	 *
	 * @param x the new mode
	 */
	public void setMode(int x)
	{
		mode =x;
	}

	/**
	 * Gets the mode.
	 *
	 * @return the mode
	 */
	public int getMode()
	{
		return mode;
	}

	/**
	 * Dragon movement.
	 * Function used to move a dragon 
	 * Uses a class myRandom to indicate the direction
	 * the dragon shall move to.
	 *
	 * @param jogo the game instance
	 */
	public void dragonMovement(Game_Loop jogo) {
		int n = myRandom.getInstance().nextInt(4);
		int mov; //1 = y-1; 2 = y+1; 3 = x-1; 4 = x+1;
		switch(n) {
		case 1:
			mov = 1;
			if(!Labirynth.getInstance().getGenerated_maze()[getY()-1][getX()].isFilled() && !dragonOverlay(jogo.enemies, mov))
				setY(getY() - 1);
			break;
		case 2:
			mov = 2;
			if(!Labirynth.getInstance().getGenerated_maze()[getY()+1][getX()].isFilled() && !dragonOverlay(jogo.enemies, mov))
				setY(getY() + 1);
			break;
		case 3:
			mov = 3;
			if(!Labirynth.getInstance().getGenerated_maze()[getY()][getX()-1].isFilled() && !dragonOverlay(jogo.enemies, mov))
				setX(getX() - 1);
			break;
		case 4:
			mov = 4;
			if(!Labirynth.getInstance().getGenerated_maze()[getY()][getX()+1].isFilled() && !dragonOverlay(jogo.enemies, mov))
				setX(getX() + 1);
			break;
		}
	}

	/**
	 * Dragon overlay.
	 *
	 * @param inimigos all the dragons in the game
	 * @param mov the potencial movement
	 * @return true, if successful indicating that 2 or more dragons will overlap in the position
	 * @return false, if no dragons will overlap
	 */
	private boolean dragonOverlay(Dragon[] inimigos, int mov)
	{
		for(int i = 0; i < inimigos.length; i++)
		{

			if(!(this.getX() == inimigos[i].getX() && this.getY() == inimigos[i].getY()))
			{
				switch(mov)
				{
				case 1:
					if(this.getX() == inimigos[i].getX() && (this.getY()-1) == (inimigos[i].getY()))
						return true;
					break;
				case 2:
					if(this.getX() == inimigos[i].getX() && (this.getY()+1) == (inimigos[i].getY()))
						return true;
					break;
				case 3:
					if((this.getX()-1) == inimigos[i].getX() && this.getY() == inimigos[i].getY())
						return true;
					break;
				case 4:
					if((this.getX()+1) == inimigos[i].getX() && this.getY() == inimigos[i].getY())
						return true;
					break;
				}
			}

		}

		return false;
	}

	/**
	 * Inits.
	 *
	 * Initiates a dragon with the specified parameters and returns it
	 *
	 * @param mode the mode
	 * @param jogo the game instance
	 * @return the dragon created
	 */
	public Dragon init(int mode, Game_Loop jogo) {

		Dragon drag = new Dragon();		
		drag.setDead(false);
		drag.mode=mode;
		switch (mode)
		{
		case 1:
			drag.setSymbol('D');
			break;
		case 2:
			drag.setSymbol('D');			
			break;
		case 3:
			Random rand = new Random();
			int sym = rand.nextInt(2);
			if(sym == 0) //dragon is awake
				drag.setSymbol('D'); 
			else 	 //dragon starts game sleeping
				drag.setSymbol('d'); 
			break;
		}
		int test_x,test_y;
		do 
		{
			Random rand = new Random();
			test_x=rand.nextInt(Labirynth.getInstance().COL_SIZE-2)+1;
			test_y=rand.nextInt(Labirynth.getInstance().ROW_SIZE-2)+1;
		} while (Labirynth.getInstance().getGenerated_maze()[test_y][test_x].isFilled() || jogo.ocupied_byElements(test_x, test_y));
		drag.setX(test_x);
		drag.setY(test_y);

		jogo.elements.add(drag);
		return drag;
	}

	/**
	 * InitTest.
	 *
	 * Initializes a dragon with specific parameters suitable for testing (Junit) purposes
	 *
	 * @param mode the mode
	 * @param jogo the game instance
	 * @return the dragon created
	 */
	public Dragon initTest(int mode, Game_Loop jogo) {

		Dragon drag = new Dragon();		
		drag.setDead(false);
		drag.mode=mode;
		drag.setSymbol('D');
		int test_x,test_y;
		do 
		{
			Random rand = new Random();
			test_x=rand.nextInt(Labirynth.getInstance().COL_SIZE-2)+1;
			test_y=rand.nextInt(Labirynth.getInstance().ROW_SIZE-2)+1;
		} while ((Labirynth.getInstance().getGenerated_maze()[test_y][test_x].isFilled() || jogo.ocupied_byElements(test_x, test_y)) && test_x != 1 && test_y !=1 && test_y !=8);
		drag.setX(test_x);
		drag.setY(test_y);

		jogo.elements.add(drag);
		return drag;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		Dragon temp = (Dragon) obj;
		return (getX() == temp.getX() && getY()==temp.getY());
	}



}
